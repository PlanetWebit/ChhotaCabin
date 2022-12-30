package planet.com.chhotacabin.venderoption;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.ByteArrayOutputStream;
import java.io.File;

import planet.com.chhotacabin.R;

public class KycDocument extends AppCompatActivity {
    File finalFile, finalFile1, finalFile2, file;
    private static final int PERMISSION_REQUEST_CODE = 200;
    LinearLayout document1, document2, document3;
    ImageView image,image1,image2;
    Toolbar toolbar;
    String click ="";
    Button btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_upload_vender);

        document1 = findViewById(R.id.document1);
        document2 = findViewById(R.id.document2);
        document3 = findViewById(R.id.document3);
        image = findViewById(R.id.image);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        document1.setVisibility(View.VISIBLE);
        document2.setVisibility(View.VISIBLE);
        document3.setVisibility(View.VISIBLE);
        btnNext=findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(KycDocument.this,Personal_detail_vender3.class);
                startActivity(in);
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

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {

                    if (click.equalsIgnoreCase("1")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(photo);

                        Uri tempUri = getImageUri(KycDocument.this, photo);
                        finalFile = new File(getRealPathFromURI(tempUri));
                        // imageViewlicecli.setVisibility(View.INVISIBLE);
                        document1.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("2")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image1.setImageBitmap(photo);

                        Uri tempUri = getImageUri(KycDocument.this, photo);
                        finalFile1 = new File(getRealPathFromURI(tempUri));
                        document2.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("3")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image2.setImageBitmap(photo);

                        Uri tempUri = getImageUri(KycDocument.this, photo);
                        finalFile2 = new File(getRealPathFromURI(tempUri));
                        document3.setVisibility(View.GONE);

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
        if (KycDocument.this.getContentResolver() != null) {
            Cursor cursor = KycDocument.this.getContentResolver().query(uri, null, null, null, null);
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
                KycDocument.this);
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

        return  result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA
                , Manifest.permission.READ_EXTERNAL_STORAGE    }, PERMISSION_REQUEST_CODE);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {


                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;




                    if (cameraAccepted  && readAccepted)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                                                            , Manifest.permission.LOCATION_HARDWARE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
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
        new AlertDialog.Builder(KycDocument.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
