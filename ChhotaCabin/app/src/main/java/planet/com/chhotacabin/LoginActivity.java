package planet.com.chhotacabin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import planet.com.chhotacabin.utils.CommanMethod;
import planet.com.chhotacabin.utils.MyPreferences;


public class LoginActivity extends AppCompatActivity {

    EditText edit_email, editpass, edit_refre;
    Button btnsignin, btnsignMobile, btnPostProp;
    ProgressDialog dialog;
    TextView forgetPaasword, sihnUpText, message;
    ImageView imgShowPass, imgHidePass, imgShowConPass, imgHideConPass;
    String usertype = "";
    private static final int PERMISSION_REQUEST_CODE = 200;

    String testMessage = "";
    TextView test_message;
    Button copyMessage;
    String proName = "";
    String proId = "";
    String proDetail = "";
    String pro_assress = "";
    String propertyType = "";
    String cityName = "";
    String selectType = "";
    String timing = "";
    String person = "";
    String timefrom = "";
    String timeto = "";
    String device_id = "";
    CheckBox tcCheck;
    TextView termCondiRead;
    private static final String AUTH_KEY = "AAAARljv6II:APA91bFvo8m4QoNuMK4gwaC2GsTI2X7SImSJLl_J-qwenQleIXGc8Az6b9EItf1MA-YSgdH4itDVeB0VHLxl_DJvXAjvRDNW6zmV4EJZtw0rXZRcD1VB601e5vP_TJmAEfoedvuTqPuR";
    private TextView mTextView;
    private String token;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        printKeyHash();
        dialog = new ProgressDialog(LoginActivity.this);

        edit_email = findViewById(R.id.edit_email);
        editpass = findViewById(R.id.editpass);
        btnsignin = findViewById(R.id.btnsignin);
        btnPostProp = findViewById(R.id.btnPostProp);
        message = findViewById(R.id.message);

        edit_refre = findViewById(R.id.edit_refre);
        sihnUpText = findViewById(R.id.sihnUpText);
        imgShowPass = findViewById(R.id.imgShowPass);
        imgHidePass = findViewById(R.id.imgHidePass);
        tcCheck = findViewById(R.id.tcCheck);
        termCondiRead = findViewById(R.id.termCondiRead);

        test_message = findViewById(R.id.message);


        forgetPaasword = (TextView) findViewById(R.id.forgetPaasword);

        proName = getIntent().getStringExtra("proName");
        proId = getIntent().getStringExtra("proId");
        proDetail = getIntent().getStringExtra("proDetail");
        propertyType = getIntent().getStringExtra("propertyType");
        pro_assress = getIntent().getStringExtra("pro_assress");
        cityName = getIntent().getStringExtra("cityName");
        selectType = getIntent().getStringExtra("selectType");
        timing = getIntent().getStringExtra("timing");
        person = getIntent().getStringExtra("person");
        timefrom = getIntent().getStringExtra("timefrom");
        timeto = getIntent().getStringExtra("timeto");


        if (checkPermission()) {

            checkPermission();
        }

        if (!checkPermission()) {

            requestPermission();

        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tmp = "";
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                tmp += key + ": " + value + "\n\n";
            }
            // mTextView.setText(tmp);
        }
/*
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    token = task.getException().getMessage();
                    Log.w("FCM TOKEN Failed", task.getException());
                } else {
                    token = task.getResult().getToken();
                    Log.i("FCM TOKEN", token);
                    Log.e("fcmdeviceId",">>"+token);
                }
            }
        });
*/
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        // Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                        Log.e("device", token);

                        // Toast.makeText(LoginActivity.this, device_id, Toast.LENGTH_SHORT).show();
                    }
                });
        // sendWithOtherThread("tokens");
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_refre.getText().toString().equalsIgnoreCase("")) {
                    checkReferPoint();
                } else {
                    edit_refre.setError("Please Enter Code");
                    edit_refre.requestFocus();
                }
            }
        });
        termCondiRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, TermAndCondition.class);
                startActivity(in);
            }
        });
        imgShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                imgShowPass.setVisibility(View.GONE);
                imgHidePass.setVisibility(View.VISIBLE);
            }
        });
        imgHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgShowPass.setVisibility(View.VISIBLE);
                imgHidePass.setVisibility(View.GONE);
            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent in = new Intent(LoginActivity.this, OtpActivity.class);
                in.putExtra("getmobile", edit_email.getText().toString());
                startActivity(in);*/
                if (CommanMethod.isOnline(LoginActivity.this)) {
                    if (validationLogin()) {
                        String data = edit_email.getText().toString();
                        String value = data.substring(0, 1);
                       // Toast.makeText(LoginActivity.this, "uivuy4iuywni4uci4"+value, Toast.LENGTH_SHORT).show();
                        if (Integer.parseInt(value) < 6) {
                            edit_email.setError("Enter valid number");
                            edit_email.requestFocus();
                        } else {
                            if (edit_refre.getText().toString().equalsIgnoreCase("")) {
                                postLogin();
                            } else {
                                if (message.getText().toString().trim().equalsIgnoreCase("Congratulation refer code match")) {
                                    postLogin();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Enter valid refer code", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    }
                } else {

                    CommanMethod.showAlert("No Internet  Connection", LoginActivity.this);
                }
               /* Intent in = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(in);*/
            }
        });
        btnPostProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, PropertyUserTypeDetails.class);
                startActivity(in);
            }
        });
        forgetPaasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        sihnUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPreferences.getActiveInstance(LoginActivity.this).setIsLoggedIn(true);
                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(in);


            }
        });
    }

    private void sendWithOtherThread(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                pushNotification(type);
            }
        }).start();
    }

    private void pushNotification(String type) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            jNotification.put("title", "Google I/O 2016");
            jNotification.put("body", "Firebase Cloud Messaging (App)");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "OPEN_ACTIVITY_1");
            jNotification.put("icon", "ic_notification");

            jData.put("picture", "https://miro.medium.com/max/1400/1*QyVPcBbT_jENl8TGblk52w.png");

            switch (type) {
                case "tokens":
                    JSONArray ja = new JSONArray();
                    ja.put("c5pBXXsuCN0:APA91bH8nLMt084KpzMrmSWRS2SnKZudyNjtFVxLRG7VFEFk_RgOm-Q5EQr_oOcLbVcCjFH6vIXIyWhST1jdhR8WMatujccY5uy1TE0hkppW_TSnSBiUsH_tRReutEgsmIMmq8fexTmL");
                    ja.put(token);
                    jPayload.put("registration_ids", ja);
                    break;
                case "topic":
                    jPayload.put("to", "/topics/news");
                    break;
                case "condition":
                    jPayload.put("condition", "'sport' in topics || 'news' in topics");
                    break;
                default:
                    jPayload.put("to", token);
            }

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
            jPayload.put("data", jData);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", AUTH_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(resp);
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS);


        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS
        }, PERMISSION_REQUEST_CODE);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {


                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean sendsms = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean recivesms = grantResults[3] == PackageManager.PERMISSION_GRANTED;


                    if (cameraAccepted && readAccepted)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                                                            , Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS
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
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void printKeyHash() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.chota.cabin", PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));

                //  JvQc0VaD3GnK2lJEejpl3t/dYMk=
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void postLogin() {
        dialog.setMessage("Please wait..");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/userlogin",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        Log.e("login", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject jsonObject = obj.getJSONObject("data");
                            String message = jsonObject.getString("msg");
                            if (obj.getString("status").equals("success")) {
                                MyPreferences.getActiveInstance(LoginActivity.this).setUserId(jsonObject.getString("user_id"));
                                Intent in = new Intent(LoginActivity.this, OtpActivity.class);
                                in.putExtra("getmobile", edit_email.getText().toString());
                                in.putExtra("proName", proName);
                                in.putExtra("proId", proId);
                                in.putExtra("refer_code", edit_refre.getText().toString());
                                in.putExtra("proDetail", proDetail);
                                in.putExtra("propertyType", propertyType);
                                in.putExtra("pro_assress", pro_assress);
                                in.putExtra("cityName", cityName);
                                in.putExtra("selectType", selectType);
                                in.putExtra("timing", timing);
                                in.putExtra("person", person);
                                in.putExtra("timefrom", timefrom);
                                in.putExtra("timeto", timeto);

                                startActivity(in);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.hide();
                        Toast.makeText(LoginActivity.this, "error occurred", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("mobile_no", edit_email.getText().toString().trim());
                params.put("refer_code", edit_refre.getText().toString().trim());
                params.put("token", token);
                // params.put("password", editpass.getText().toString().trim());


                Log.e("ParmaValuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }

    public void checkReferPoint() {

        dialog.setMessage("Please wait..");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/chack_refer_code",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        Log.e("login", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject data = obj.getJSONObject("data");
                            String msg = data.getString("msg");
                            if (obj.getString("status").equals("success")) {

                                // MyPreferences.getActiveInstance(LoginActivity.this).setIsLoggedIn(true);
                                message.setTextColor(getResources().getColor(R.color.green));
                                message.setText("Congratulation refer code match");

                            } else {

                                message.setTextColor(getResources().getColor(R.color.colorPrimary));
                                message.setText("Refer code invalid");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.hide();

                        Toast.makeText(LoginActivity.this, "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("mobile_no", edit_email.getText().toString().trim());
                params.put("refer_code", edit_refre.getText().toString().trim());
                // params.put("password", editpass.getText().toString().trim());


                Log.e("ParmaValuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }

    private boolean validationLogin() {
        if (edit_email.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your mobile number.", LoginActivity.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_email.length() < 10) {
            edit_email.setError("Mobile number not valid");
            edit_email.requestFocus();
            return false;
        } else if (!tcCheck.isChecked()) {
            CommanMethod.showAlert("Please read Terms and Conditions", LoginActivity.this);
            // edit_email.setError("Please enter your email.");
            return false;
        }
        return true;

    }
}

