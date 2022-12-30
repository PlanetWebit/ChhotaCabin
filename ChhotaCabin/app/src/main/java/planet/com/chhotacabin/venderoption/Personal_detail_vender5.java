package planet.com.chhotacabin.venderoption;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.io.ByteArrayOutputStream;
import java.io.File;

import planet.com.chhotacabin.R;

public class Personal_detail_vender5 extends AppCompatActivity {
    File finalFile, finalFile1, finalFile2,finalFile3, finalFile4, finalFile5, file;
    private static final int PERMISSION_REQUEST_CODE = 200;
    LinearLayout document1, document2, document3,document4, document5, document6;
    ImageView image,image1,image2,image3,image4,image5;
    Toolbar toolbar;
    String click ="";
    Button btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_detail_vender5);
        document1 = findViewById(R.id.document1);
        document2 = findViewById(R.id.document2);
        document3 = findViewById(R.id.document3);
        document4 = findViewById(R.id.document4);
        document5 = findViewById(R.id.document5);
        document6 = findViewById(R.id.document6);
        image = findViewById(R.id.image);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        btnNext = findViewById(R.id.btnNext);
        document1.setVisibility(View.VISIBLE);
        document2.setVisibility(View.VISIBLE);
        document3.setVisibility(View.VISIBLE);
        document4.setVisibility(View.VISIBLE);
        document5.setVisibility(View.VISIBLE);
        document6.setVisibility(View.VISIBLE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Personal_detail_vender5.this,VenderDashbord.class);
                startActivity(in);
            }
        });
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
                click = "4";
            }
        });
        document5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "5";
            }
        });
        document6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
                click = "6";
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
                    if (click.equalsIgnoreCase("4")) {
                        Uri selectedImage = data.getData();
                        image3.setImageURI(selectedImage);
                        finalFile3 = new File(getRealPathFromURI(selectedImage));
                        document4.setVisibility(View.GONE);
                        //  document1.setVisibility(View.INVISIBLE);


                    }
                    if (click.equalsIgnoreCase("5")) {
                        Uri selectedImage = data.getData();
                        image4.setImageURI(selectedImage);
                        finalFile4 = new File(getRealPathFromURI(selectedImage));
                        document5.setVisibility(View.GONE);
                        //  document2.setVisibility(View.INVISIBLE);

                    }
                    if (click.equalsIgnoreCase("6")) {
                        Uri selectedImage = data.getData();
                        image5.setImageURI(selectedImage);
                        finalFile5 = new File(getRealPathFromURI(selectedImage));
                        document6.setVisibility(View.GONE);
                        //  document2.setVisibility(View.INVISIBLE);

                    }

                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {

                    if (click.equalsIgnoreCase("1")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Personal_detail_vender5.this, photo);
                        finalFile = new File(getRealPathFromURI(tempUri));
                        // imageViewlicecli.setVisibility(View.INVISIBLE);
                        document1.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("2")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image1.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Personal_detail_vender5.this, photo);
                        finalFile1 = new File(getRealPathFromURI(tempUri));
                        document2.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("3")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image2.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Personal_detail_vender5.this, photo);
                        finalFile2 = new File(getRealPathFromURI(tempUri));
                        document3.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("4")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image3.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Personal_detail_vender5.this, photo);
                        finalFile3 = new File(getRealPathFromURI(tempUri));
                        // imageViewlicecli.setVisibility(View.INVISIBLE);
                        document4.setVisibility(View.GONE);
                    }
                    if (click.equalsIgnoreCase("5")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image4.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Personal_detail_vender5.this, photo);
                        finalFile4 = new File(getRealPathFromURI(tempUri));
                        document5.setVisibility(View.GONE);

                    }
                    if (click.equalsIgnoreCase("6")) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        image5.setImageBitmap(photo);

                        Uri tempUri = getImageUri(Personal_detail_vender5.this, photo);
                        finalFile5 = new File(getRealPathFromURI(tempUri));
                        document6.setVisibility(View.GONE);

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
        if (Personal_detail_vender5.this.getContentResolver() != null) {
            Cursor cursor = Personal_detail_vender5.this.getContentResolver().query(uri, null, null, null, null);
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
                Personal_detail_vender5.this);
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
}
