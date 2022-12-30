package planet.com.chhotacabin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import planet.com.chhotacabin.utils.CommanMethod;
import planet.com.chhotacabin.utils.MyPreferences;


public class OtpActivity extends AppCompatActivity {

    EditText editotp;
    Button btnsubmit;
    ProgressDialog dialog;
    String getmobile = "";
    String viaLog = "";
    TextView mobileText;

    String pinvalue = "", refer_code = "";
    String userCheck = "";
    private String otpnN0 = "";
    TextView timer;

    public int counter = 40;
    public int counter1 = 40;
    String checkUrl = "";
    private static final int REQ_USER_CONSENT = 200;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        btnsubmit = findViewById(R.id.btnsubmit);


        editotp = findViewById(R.id.edit_otp);
        mobileText = findViewById(R.id.mobileText);

        dialog = new ProgressDialog(OtpActivity.this);

        viaLog = getIntent().getStringExtra("viaLog");
        getmobile = getIntent().getStringExtra("getmobile");
        proName = getIntent().getStringExtra("proName");
        proId = getIntent().getStringExtra("proId");
        proDetail = getIntent().getStringExtra("proDetail");
        refer_code = getIntent().getStringExtra("refer_code");
        propertyType = getIntent().getStringExtra("propertyType");
        pro_assress = getIntent().getStringExtra("pro_assress");
        cityName = getIntent().getStringExtra("cityName");
        selectType = getIntent().getStringExtra("selectType");
        timing = getIntent().getStringExtra("timing");
        person = getIntent().getStringExtra("person");
        timefrom = getIntent().getStringExtra("timefrom");
        timeto = getIntent().getStringExtra("timeto");


        if (refer_code.equalsIgnoreCase("")) {
            refer_code = "";
        }
        startSmsUserConsent();


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommanMethod.isOnline(OtpActivity.this)) {

                    if (validationLogin()) {
                        postOtp();
                    }
                } else {

                    CommanMethod.showAlert("No Internet  Connection", OtpActivity.this);
                }

            }
        });
    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                //   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
               /* textViewMessage.setText(
                        String.format("%s - %s", getString(R.string.received_message), message));*/

                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            editotp.setText(matcher.group(0));
        }
    }

   /* private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure() {

                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
       // registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  unregisterReceiver(smsBroadcastReceiver);
    }

    public void postOtp() {
        dialog.setMessage("Please wait..");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/chackotp",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        Log.e("otp", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("msg");
                            if (obj.getString("status").equals("success")) {

                                if (proDetail != null && proDetail.equalsIgnoreCase("proDetai")) {
                                    MyPreferences.getActiveInstance(OtpActivity.this).setUserId(obj.getString("user_id"));
                                    MyPreferences.getActiveInstance(OtpActivity.this).setIsLoggedIn(true);
                                    MyPreferences.getActiveInstance(OtpActivity.this).setCheckloginorNot(true);
                                    MyPreferences.getActiveInstance(OtpActivity.this).setRefer_point(obj.getString("refer_code"));
                                    MyPreferences.getActiveInstance(OtpActivity.this).setname(obj.getString("name"));
                                    MyPreferences.getActiveInstance(OtpActivity.this).setProfileImage(obj.getString("image"));
                                    Intent in = new Intent(OtpActivity.this, ProductDetail.class);
                                    in.putExtra("proName", proName);
                                    in.putExtra("proId", proId);
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

                                    MyPreferences.getActiveInstance(OtpActivity.this).setUserId(obj.getString("user_id"));
                                    MyPreferences.getActiveInstance(OtpActivity.this).setRefer_point(obj.getString("refer_code"));
                                    MyPreferences.getActiveInstance(OtpActivity.this).setname(obj.getString("name"));
                                    MyPreferences.getActiveInstance(OtpActivity.this).setProfileImage(obj.getString("image"));
                                    MyPreferences.getActiveInstance(OtpActivity.this).setIsLoggedIn(true);
                                    MyPreferences.getActiveInstance(OtpActivity.this).setCheckloginorNot(true);
                                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {

                                Toast.makeText(OtpActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(OtpActivity.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.hide();

                        Toast.makeText(OtpActivity.this, "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("otp", editotp.getText().toString());
                params.put("refer_code", refer_code);
                params.put("user_id", MyPreferences.getActiveInstance(OtpActivity.this).getUserId());


                Log.e("ParmaValuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        requestQueue.add(stringRequest);
    }

    private boolean validationLogin() {
        if (editotp.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your otp.", OtpActivity.this);
            return false;
        }
        return true;

    }

}

