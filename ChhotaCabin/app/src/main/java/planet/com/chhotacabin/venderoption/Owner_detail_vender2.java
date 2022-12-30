package planet.com.chhotacabin.venderoption;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.chhotacabin.ImagesActivity;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.utils.CommanMethod;
import planet.com.chhotacabin.utils.MyPreferences;
import planet.com.chhotacabin.utils.WebServices;

public class Owner_detail_vender2 extends AppCompatActivity {
    Spinner cabintypeSpinner, spintimefrom, spintimeto;
    String[] cabinType = {"Cabin Type", "Classic", "Premium", "Superme"};
    String[] cabinTimeFrom = {"Time From", "9 am", "10am", "11am"};
    String[] cabintimeTo = {"Time To", "10 am", "11 am", "12 pm"};
    TextView dateText;
    TextView datefrom, dateto;
    Dialog dialogdate;
    ProgressDialog progressDialog;
    DatePicker datePicker;
    AppCompatButton dialog_button, next_Action;
    int day, month, year;
    Button btnNext;
    CircleImageView profile_image;
    ImageView pick_profile;
    private File bankfile;
    private static final int PERMISSION_REQUEST_CODE = 200;
    File aadharfrontfile, aadharbackfile, pancardfile, ownerprooffile;
    LinearLayout document1, document2, document3, document4, document5;
    ImageView image, image1, image2, image3, image4;
    Toolbar toolbar;
    String click = "";
    String aadharfront = "";
    String backaadhar = "";
    String pancard = "";
    String ownershipProf = "";
    String bankdetail = "";
    EditText edit_name, edit_mobile, edit_email, edit_address, edit_properAddress, edit_pannumber, edit_adharnumber, edit_bankName, edit_bankbranch, edit_bankaccnumber, edit_bankIfsc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_detail_vender2);

        edit_bankaccnumber = findViewById(R.id.edit_bankaccnumber);
        edit_bankIfsc = findViewById(R.id.edit_bankIfsc);
        edit_bankbranch = findViewById(R.id.edit_bankbranch);
        edit_bankName = findViewById(R.id.edit_bankName);
        edit_adharnumber = findViewById(R.id.edit_adharnumber);
        edit_pannumber = findViewById(R.id.edit_pannumber);

        btnNext = findViewById(R.id.btnNext);
        document1 = findViewById(R.id.document1);
        document2 = findViewById(R.id.document2);
        document3 = findViewById(R.id.document3);
        progressDialog = new ProgressDialog(Owner_detail_vender2.this);
        document4 = findViewById(R.id.document4);
        document5 = findViewById(R.id.document5);
        image = findViewById(R.id.image);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        document1.setVisibility(View.VISIBLE);
        document2.setVisibility(View.VISIBLE);
        document3.setVisibility(View.VISIBLE);
        document4.setVisibility(View.VISIBLE);
        document5.setVisibility(View.VISIBLE);
        btnNext = findViewById(R.id.btnNext);

        if (checkPermission()) {

            checkPermission();
        }

        if (!checkPermission()) {

            requestPermission();

        }
        document1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "1";
                aadharfront = "aadharfront";
            }
        });
        document2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "2";
                backaadhar = "aadharback";
            }
        });
        document3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "3";
                pancard = "pancard";
            }
        });
        document4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "4";
                ownershipProf = "ownershipProf";
            }
        });
        document5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "5";
                bankdetail = "bankdetail";
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent in = new Intent(Owner_detail_vender2.this,Personal_detail_vender2.class);
                startActivity(in);*/
                if (validationLogin()) {
                    try {
                        sendData();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private boolean validationLogin() {
        if (edit_adharnumber.getText().toString().equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please enter Aadhar card number", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (aadharfront.equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please add Aadhar card front image", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (backaadhar.equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please add Aadhar card back image", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_pannumber.getText().toString().equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please enter pan card number", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (pancard.equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please add pan card image", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_bankName.getText().toString().equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please enter Bank Name", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_bankbranch.getText().toString().equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please enter Bank Branch", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_bankaccnumber.getText().toString().equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please enter Bank Account Number", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_bankIfsc.getText().toString().equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please enter Bank IFSC code", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (bankdetail.equalsIgnoreCase("")) {
            CommanMethod.showAlert("Please add Bank account / cancelled cheque image", Owner_detail_vender2.this);
            // edit_email.setError("Please enter your email.");
            return false;
        }
        return true;

    }

    public void openDialog() {

        dialogdate = new Dialog(Owner_detail_vender2.this);
        dialogdate.setContentView(R.layout.datepickerdialog);
        datePicker = (DatePicker) dialogdate.findViewById(R.id.datePicker);
        dialog_button = (AppCompatButton) dialogdate.findViewById(R.id.dialog_button);

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day = datePicker.getDayOfMonth();
                month = (datePicker.getMonth() + 1);
                year = datePicker.getYear();

                //dob.setText(month + "-" + day + "-" + year);
                dialogdate.dismiss();


            }
        });
        dialogdate.show();
    }

    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {


                    if (click.equalsIgnoreCase("1")) {
                        Uri selectedImage = data.getData();
                        image.setImageURI(selectedImage);
                        aadharfrontfile = new File(getRealPathFromURI(selectedImage));
                        document1.setVisibility(View.GONE);
                        //  document1.setVisibility(View.INVISIBLE);


                    }
                    if (click.equalsIgnoreCase("2")) {
                        Uri selectedImage = data.getData();
                        image1.setImageURI(selectedImage);
                        aadharbackfile = new File(getRealPathFromURI(selectedImage));
                        document2.setVisibility(View.GONE);
                        //  document2.setVisibility(View.INVISIBLE);

                    }
                    if (click.equalsIgnoreCase("3")) {
                        Uri selectedImage = data.getData();
                        image2.setImageURI(selectedImage);
                        pancardfile = new File(getRealPathFromURI(selectedImage));
                        document3.setVisibility(View.GONE);
                        //  document2.setVisibility(View.INVISIBLE);

                    }

                    if (click.equalsIgnoreCase("5")) {
                        Uri selectedImage = data.getData();
                        image4.setImageURI(selectedImage);
                        bankfile = new File(getRealPathFromURI(selectedImage));
                        document5.setVisibility(View.GONE);
                        //document1.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("4")) {
                        Uri selectedImage = data.getData();
                        image3.setImageURI(selectedImage);
                        ownerprooffile = new File(getRealPathFromURI(selectedImage));
                        document4.setVisibility(View.GONE);
                        //document1.setVisibility(View.GONE);
                    }


                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {

                    if (click.equalsIgnoreCase("1")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Owner_detail_vender2.this, photo);
                        aadharfrontfile = new File(getRealPathFromURI(tempUri));
                        // imageViewlicecli.setVisibility(View.INVISIBLE);
                        document1.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("2")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image1.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Owner_detail_vender2.this, photo);
                        aadharbackfile = new File(getRealPathFromURI(tempUri));
                        document2.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("3")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image2.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Owner_detail_vender2.this, photo);
                        pancardfile = new File(getRealPathFromURI(tempUri));
                        document3.setVisibility(View.GONE);

                    }

                    if (click.equalsIgnoreCase("4")) {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image3.setImageBitmap(photo);
                        Uri tempUri = getImageUri(Owner_detail_vender2.this, photo);
                        ownerprooffile = new File(getRealPathFromURI(tempUri));
                        document4.setVisibility(View.GONE);
                        //  document1.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("5")) {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image4.setImageBitmap(photo);
                        Uri tempUri = getImageUri(Owner_detail_vender2.this, photo);
                        bankfile = new File(getRealPathFromURI(tempUri));
                        document5.setVisibility(View.GONE);
                        //  document1.setVisibility(View.GONE);

                    }


                }
                break;
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (Owner_detail_vender2.this.getContentResolver() != null) {
            Cursor cursor = Owner_detail_vender2.this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                Owner_detail_vender2.this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, 0);


                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 1);
                    }
                });
        myAlertDialog.show();
    }

    public void sendData() throws FileNotFoundException {

        final RequestParams params = new RequestParams();
        params.put("aadher_no", edit_adharnumber.getText().toString());
        params.put("pan_card", edit_pannumber.getText().toString());
        params.put("bank_name", edit_bankName.getText().toString());
        params.put("branch", edit_bankbranch.getText().toString());
        params.put("account_no", edit_bankaccnumber.getText().toString());
        params.put("ifsc_code", edit_bankIfsc.getText().toString());
        params.put("owner_id", MyPreferences.getActiveInstance(Owner_detail_vender2.this).getUserId());
        if (aadharfrontfile != null) {
            params.put("aadher_image1", aadharfrontfile);
        } else {
            params.put("aadher_image1", "");
        }
        if (aadharbackfile != null) {
            params.put("aadher_image2", aadharbackfile);
        } else {
            params.put("aadher_image2", "");
        }
        if (pancardfile != null) {
            params.put("pan_image", pancardfile);
        } else {
            params.put("pan_image", "");
        }

        if (bankfile != null) {
            params.put("bank_passbook_image", bankfile);
        } else {
            params.put("bank_passbook_image", "");
        }

        //  https://chhotacabin.com/Apicontrollers/
        Log.e("Pare", ">>>>" + params);
        WebServices.post("owner_proof", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog.show();
                progressDialog.setMessage("Please wait..");
                super.onStart();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("createData", ">>>>" + response);
                progressDialog.hide();

                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String msg = obj.getString("msg");
                    String status = obj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        Intent in = new Intent(Owner_detail_vender2.this, ImagesActivity.class);
                        startActivity(in);
                        Toast.makeText(Owner_detail_vender2.this, msg, Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.hide();
                        Toast.makeText(Owner_detail_vender2.this, msg, Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFinish() {

                super.onFinish();
                progressDialog.hide();


            }
        });


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {


                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;


                    if (cameraAccepted && readAccepted && writeAccepted)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                                                            , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                    }, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                }
        }

    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Owner_detail_vender2.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}

