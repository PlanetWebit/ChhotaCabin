package planet.com.chhotacabin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.chhotacabin.utils.CommanMethod;
import planet.com.chhotacabin.utils.MyPreferences;
import planet.com.chhotacabin.utils.WebServices;

public class UpdateProfile extends AppCompatActivity {

    EditText edit_name, edit_email, edit_mobile;
    CircleImageView profile_image;
    ImageView pick_profile;
    private File finalFile;
    Toolbar toolbar;
    Button btnSubmit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    Dialog dialog;
    TextView failed, sucees;
    Button btnDone;
    String price = "";
    String txnId = "";
    private static final String TAG = UpdateProfile.class.getSimpleName();
    String stringTofile = "";
    private File stringFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile_option);
        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_mobile = findViewById(R.id.edit_mobile);
        profile_image = findViewById(R.id.profile_image);
        pick_profile = findViewById(R.id.pick_profile);
        toolbar = findViewById(R.id.toolbar);
        btnSubmit = findViewById(R.id.btnSubmit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        progressDialog = new ProgressDialog(UpdateProfile.this);

        pick_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
            }
        });
        getProfile();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationLogin()) {
                    if (!edit_email.getText().toString().trim().matches(emailPattern)){
                        edit_email.setError("Please enter valid email.");
                        edit_email.requestFocus();
                    }else {
                        try {
                            registerSuccess();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    profile_image.setImageURI(selectedImage);
                    finalFile = new File(getRealPathFromURI(selectedImage));
                    //document1.setVisibility(View.GONE);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {

                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    profile_image.setImageBitmap(photo);
                    Uri tempUri = getImageUri(UpdateProfile.this, photo);
                    finalFile = new File(getRealPathFromURI(tempUri));
                    //  document1.setVisibility(View.GONE);

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
        if (UpdateProfile.this.getContentResolver() != null) {
            Cursor cursor = UpdateProfile.this.getContentResolver().query(uri, null, null, null, null);
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
                UpdateProfile.this);
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

    public void getProfile() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/get_profile"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.e("res", ">>>" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equalsIgnoreCase("success")) {

                            String img = object.getString("image");
                            String name = object.getString("name");
                            String eml = object.getString("email");
                            String mbl = object.getString("mobile_no");
                            if (img.equalsIgnoreCase("")) {
                                profile_image.setImageResource(R.drawable.profile_icon);
                            } else {
                                Picasso.with(UpdateProfile.this)
                                        .load(img)
                                        .into(profile_image);
                            //    stringFile = new File(img.toString());
                            }
                            if (name.equalsIgnoreCase("")) {
                                edit_name.setHint("Enter name");
                            } else {
                                edit_name.setText(name);
                            }
                            if (eml.equalsIgnoreCase("")) {
                                edit_email.setHint("Enter email");
                            } else {
                                edit_email.setText(eml);
                            }
                            if (mbl.equalsIgnoreCase("")) {
                                edit_mobile.setHint("Enter Mobile");
                            } else {
                                edit_mobile.setText(mbl);
                                edit_mobile.setEnabled(false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", MyPreferences.getActiveInstance(UpdateProfile.this).getUserId());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(UpdateProfile.this);
        queue.add(request);
    }

    private boolean validationLogin() {
        if (edit_name.getText().toString().trim().length() == 0) {
           // CommanMethod.showAlert("Please enter your name.", UpdateProfile.this);
            edit_name.setError("Please enter your name.");
            edit_name.requestFocus();
            return false;
        } else if (edit_email.getText().toString().trim().length() == 0) {
            //CommanMethod.showAlert("Please enter your email.", UpdateProfile.this);
            edit_email.setError("Please enter your email.");
            edit_email.requestFocus();
            return false;
        }
        return true;

    }

    public void registerSuccess() throws FileNotFoundException {

        final RequestParams params = new RequestParams();
        params.put("name", edit_name.getText().toString().trim());
        params.put("email", edit_email.getText().toString().trim());
        params.put("user_id", MyPreferences.getActiveInstance(UpdateProfile.this).getUserId());


        if (finalFile != null) {
            params.put("image", finalFile);
        } else {

                params.put("image", "");


        }


        Log.e("ParmaCreate", ">>>>" + params);
        WebServices.post("profile_update", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog.show();
                progressDialog.setMessage("Please wait...");
                super.onStart();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("create", ">>>>" + response);
                progressDialog.hide();

                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String msg = obj.getString("msg");
                    String status = obj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        //   Toast.makeText(UpdateProfile.this, msg, Toast.LENGTH_LONG).show();
                        // openDialog();

                        MyPreferences.getActiveInstance(UpdateProfile.this).setProfileImage(obj.getString("image"));
                        MyPreferences.getActiveInstance(UpdateProfile.this).setname(obj.getString("name"));
                        Intent in = new Intent(UpdateProfile.this, MainActivity.class);
                        startActivity(in);

                    } else {
                        progressDialog.hide();
                        Toast.makeText(UpdateProfile.this, msg, Toast.LENGTH_LONG).show();
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


}
