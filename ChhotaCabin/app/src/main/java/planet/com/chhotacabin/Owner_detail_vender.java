package planet.com.chhotacabin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.chhotacabin.pojo.TypeData;
import planet.com.chhotacabin.utils.CommanMethod;
import planet.com.chhotacabin.utils.MyPreferences;
import planet.com.chhotacabin.utils.WebServices;
import planet.com.chhotacabin.venderoption.pojo.TypeTime;

public class Owner_detail_vender extends AppCompatActivity {
    Spinner cabintypeSpinner, spintimefrom, spintimeto;
    ProgressDialog progressDialog;
    String[] cabinType = {"Cabin Type", "Classic", "Premium", "Superme"};
    String[] cabinTimeFrom = {"Time From", "9 am", "10am", "11am"};
    String[] cabintimeTo = {"Time To", "10 am", "11 am", "12 pm"};
    TextView dateText;
    TextView datefrom, dateto;
    Dialog dialogdate;
    DatePicker datePicker;
    AppCompatButton dialog_button, next_Action;
    int day, month, year;
    Button btnNext;
    CircleImageView profile_image;
    ImageView pick_profile;
    private File finalFilePro;
    String cabinTypeString = "";
    private static final int PERMISSION_REQUEST_CODE = 200;
    File finalFile, finalFile1, finalFile2, file3, ownerFile;
    LinearLayout document1, document2, document3, document4, document5;
    ImageView image, image1, image2, image3, image4;
    Toolbar toolbar;
    String click = "";
    String timing = "";
    ArrayList<TypeData> typeData = new ArrayList<>();
    ArrayList<TypeTime> typeTime = new ArrayList<>();
    ArrayList<String> stName = new ArrayList<>();
    ArrayList<String> timeS = new ArrayList<>();
    RadioGroup cabiRadioGroup;
    EditText edit_name, edit_mobile, edit_email, edit_address, edit_properAddress, edit_pannumber, edit_adharnumber, edit_bankName, edit_bankbranch, edit_bankaccnumber, edit_bankIfsc;
    RadioButton classicRadio, premiumRadio, supremeRadio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_detail_vender);
        classicRadio = findViewById(R.id.classicRadio);
        edit_name = findViewById(R.id.edit_name);
        edit_mobile = findViewById(R.id.edit_mobile);
        edit_bankaccnumber = findViewById(R.id.edit_bankaccnumber);
        edit_bankIfsc = findViewById(R.id.edit_bankIfsc);
        edit_bankbranch = findViewById(R.id.edit_bankbranch);
        edit_bankName = findViewById(R.id.edit_bankName);
        cabiRadioGroup = findViewById(R.id.cabiRadioGroup);
        premiumRadio = findViewById(R.id.premiumRadio);
        supremeRadio = findViewById(R.id.supremeRadio);
        edit_email = findViewById(R.id.edit_email);
        edit_address = findViewById(R.id.edit_address);
        edit_properAddress = findViewById(R.id.edit_properAddress);
        edit_adharnumber = findViewById(R.id.edit_adharnumber);
        cabintypeSpinner = findViewById(R.id.cabintypeSpinner);
        edit_pannumber = findViewById(R.id.edit_pannumber);
        spintimefrom = findViewById(R.id.spintimefrom);
        spintimeto = findViewById(R.id.spintimeto);
        dateText = findViewById(R.id.dateText);
        btnNext = findViewById(R.id.btnNext);
        document1 = findViewById(R.id.document1);
        document2 = findViewById(R.id.document2);
        document3 = findViewById(R.id.document3);
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
        progressDialog = new ProgressDialog(Owner_detail_vender.this);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        pick_profile = (ImageView) findViewById(R.id.pick_profile);
        classicRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cabinTypeString = "Classic";
            }
        });
        premiumRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cabinTypeString = "Premium";
            }
        });
        supremeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cabinTypeString = "Supreme";
            }
        });
        cabintypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    cabinTypeString = typeData.get(position - 1).getName();

                    if (cabinTypeString.equalsIgnoreCase("Cabin")) {
                        cabiRadioGroup.setVisibility(View.VISIBLE);
                        cabinTypeString = typeData.get(position - 1).getId();
                        cabinTiming();
                    } else {
                        cabinTypeString = typeData.get(position - 1).getId();
                        Log.e("data", ">>" + cabinTypeString);
                        cabiRadioGroup.setVisibility(View.GONE);
                        cabinTiming();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spintimefrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    timing = typeTime.get(position - 1).getName();

                    Log.e("data", ">>" + timing);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            }
        });
        document2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "2";
            }
        });
        document3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "3";
            }
        });
        document4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "5";
            }
        });
        document5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "6";
            }
        });

        pick_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "4";
            }
        });


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationLogin()) {

                    if (cabinTypeString.equalsIgnoreCase("Cabin")) {
                        CommanMethod.showAlert("Please select any one cabin type", Owner_detail_vender.this);
                    } else {
                        try {
                            sendData();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        });
        cabinType();
    }

    private boolean validationLogin() {
        if (edit_name.getText().toString().trim().length() == 0) {
            edit_name.setError("Please enter your name.");
            return false;
        }else if (edit_mobile.getText().toString().trim().length() == 0) {
            edit_mobile.setError("Please enter your mobile.");
            return false;
        } else if (edit_email.getText().toString().trim().length() == 0) {
            edit_email.setError("Please enter your email.");
            return false;
        }  else if (edit_address.getText().toString().trim().length() == 0) {
            edit_address.setError("Please enter your address.");
            return false;
        } else if (edit_properAddress.getText().toString().trim().length() == 0) {
            edit_properAddress.setError("Please enter your property address.");
            return false;
        } else if (cabintypeSpinner.getSelectedItem().toString().trim() == "Select Cabin Type") {
            CommanMethod.showAlert("Please Select Cabin Type", Owner_detail_vender.this);
            return false;
        }
        return true;

    }

    public void cabinType() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chhotacabin.com/Apicontrollers/cabin_type",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("getcountry...", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                                JSONArray jsonArray = obj.getJSONArray("data");


                                stName.add("Select Cabin Type");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    TypeData countrydata = new TypeData();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    countrydata.setName(jsonObject.getString("name"));
                                    countrydata.setId(jsonObject.getString("id"));


                                    typeData.add(countrydata);
                                    stName.add(jsonObject.getString("name"));

                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Owner_detail_vender.this, android.R.layout.simple_spinner_item, stName);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cabintypeSpinner.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Owner_detail_vender.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        // Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("driver_id",driverid);


                //  Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(Owner_detail_vender.this);
        requestQueue.add(stringRequest);


    }

    public void cabinTiming() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/get_time",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("timing...", response);
                        //timeS.add("Select Timing");
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                                JSONArray jsonArray = obj.getJSONArray("data");
                                timeS.clear();

                                timeS.add("Select Timing");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    TypeTime countrydata = new TypeTime();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    countrydata.setName(jsonObject.getString("name"));
                                    // countrydata.setId(jsonObject.getString("id"));


                                    typeTime.add(countrydata);
                                    timeS.add(jsonObject.getString("name"));

                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Owner_detail_vender.this, android.R.layout.simple_spinner_item, timeS);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spintimefrom.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Owner_detail_vender.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        // Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cabin_id", cabinTypeString);


                //  Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(Owner_detail_vender.this);
        requestQueue.add(stringRequest);


    }

    public void sendData() throws FileNotFoundException {

        final RequestParams params = new RequestParams();
        params.put("name", edit_name.getText().toString());
        params.put("mobile_no", edit_mobile.getText().toString());
        params.put("email", edit_email.getText().toString());
        params.put("address", edit_address.getText().toString());
        params.put("p_address", edit_properAddress.getText().toString());
        params.put("cabin_type", cabinTypeString);
        params.put("time", timing);
        params.put("date", dateText.getText().toString());

        // params.put("Id", MyPreferences.getActiveInstance(Owner_detail_vender.this).getUserId());
        if (finalFilePro != null) {
            params.put("user_image", finalFilePro);
        } else {
            params.put("user_image", "");
        }
        if (ownerFile != null) {
            params.put("owner_proof", ownerFile);
        } else {
            params.put("owner_proof", "");
        }

        Log.e("OwnerData", ">>>>" + params);
        WebServices.post("owner_profile", params, new JsonHttpResponseHandler() {
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

                        MyPreferences.getActiveInstance(Owner_detail_vender.this).setUserId(obj.getString("id"));
                        Intent in = new Intent(Owner_detail_vender.this, ImagesActivity.class);
                        startActivity(in);
                        Toast.makeText(Owner_detail_vender.this, msg, Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.hide();
                        Toast.makeText(Owner_detail_vender.this, msg, Toast.LENGTH_LONG).show();
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

    public void openDialog() {

        dialogdate = new Dialog(Owner_detail_vender.this);
        dialogdate.setContentView(R.layout.datepickerdialog);
        datePicker = (DatePicker) dialogdate.findViewById(R.id.datePicker);
        dialog_button = (AppCompatButton) dialogdate.findViewById(R.id.dialog_button);

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day = datePicker.getDayOfMonth();
                month = (datePicker.getMonth() + 1);
                year = datePicker.getYear();

                dateText.setText(day + "-" + month + "-" + year);
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
                        finalFile = new File(getRealPathFromURI(selectedImage));
                        document1.setVisibility(View.GONE);
                        //  document1.setVisibility(View.INVISIBLE);


                    }
                    if (click.equalsIgnoreCase("2")) {
                        Uri selectedImage = data.getData();
                        image1.setImageURI(selectedImage);
                        finalFile1 = new File(getRealPathFromURI(selectedImage));
                        document2.setVisibility(View.GONE);
                        //  document2.setVisibility(View.INVISIBLE);

                    }
                    if (click.equalsIgnoreCase("3")) {
                        Uri selectedImage = data.getData();
                        image2.setImageURI(selectedImage);
                        finalFile2 = new File(getRealPathFromURI(selectedImage));
                        document3.setVisibility(View.GONE);
                        //  document2.setVisibility(View.INVISIBLE);

                    }

                    if (click.equalsIgnoreCase("4")) {
                        Uri selectedImage = data.getData();
                        profile_image.setImageURI(selectedImage);
                        finalFilePro = new File(getRealPathFromURI(selectedImage));
                        //document1.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("5")) {
                        Uri selectedImage = data.getData();
                        image3.setImageURI(selectedImage);
                        file3 = new File(getRealPathFromURI(selectedImage));
                        //document1.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("6")) {
                        Uri selectedImage = data.getData();
                        image4.setImageURI(selectedImage);
                        ownerFile = new File(getRealPathFromURI(selectedImage));
                        document5.setVisibility(View.GONE);
                    }


                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {

                    if (click.equalsIgnoreCase("1")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Owner_detail_vender.this, photo);
                        finalFile = new File(getRealPathFromURI(tempUri));
                        // imageViewlicecli.setVisibility(View.INVISIBLE);
                        document1.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("2")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image1.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Owner_detail_vender.this, photo);
                        finalFile1 = new File(getRealPathFromURI(tempUri));
                        document2.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("3")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image2.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Owner_detail_vender.this, photo);
                        finalFile2 = new File(getRealPathFromURI(tempUri));
                        document3.setVisibility(View.GONE);

                    }

                    if (click.equalsIgnoreCase("4")) {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        profile_image.setImageBitmap(photo);
                        Uri tempUri = getImageUri(Owner_detail_vender.this, photo);
                        finalFilePro = new File(getRealPathFromURI(tempUri));
                        //  document1.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("5")) {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image3.setImageBitmap(photo);
                        Uri tempUri = getImageUri(Owner_detail_vender.this, photo);
                        file3 = new File(getRealPathFromURI(tempUri));
                        //  document1.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("6")) {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image4.setImageBitmap(photo);
                        Uri tempUri = getImageUri(Owner_detail_vender.this, photo);
                        ownerFile = new File(getRealPathFromURI(tempUri));
                        document5.setVisibility(View.GONE);

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
        if (Owner_detail_vender.this.getContentResolver() != null) {
            Cursor cursor = Owner_detail_vender.this.getContentResolver().query(uri, null, null, null, null);
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
                Owner_detail_vender.this);
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
        new AlertDialog.Builder(Owner_detail_vender.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
