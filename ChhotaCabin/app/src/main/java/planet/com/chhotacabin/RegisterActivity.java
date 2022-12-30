package planet.com.chhotacabin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import planet.com.chhotacabin.pojo.TypeData;
import planet.com.chhotacabin.utils.CommanMethod;

public class RegisterActivity extends AppCompatActivity {

    EditText edit_nickname, edit_mobile, edit_email, edit_password, edit_confirmpass, edit_referral;
    ProgressDialog dialog;
    Button btn_register;
    String mobileNo = "";
    Spinner userTypeSelect;
    String userType = "";
    ImageView imgShowPass, imgHidePass, imgShowConPass, imgHideConPass;
    String[] userModel = {"Select User Type", "Customer", "Owner", "Agent", "Builder", "Indivisual"};
    ArrayList<TypeData> typeData = new ArrayList<>();
    ArrayList<String> stName = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        dialog = new ProgressDialog(RegisterActivity.this);
     //   mobileNo = getIntent().getStringExtra("mobileNo");
        edit_nickname = findViewById(R.id.edit_nickname);
        edit_mobile = findViewById(R.id.edit_mobile);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        edit_confirmpass = findViewById(R.id.edit_confirmpass);
        edit_referral = findViewById(R.id.edit_referral);
        userTypeSelect = findViewById(R.id.userTypeSelect);
        btn_register = findViewById(R.id.btn_register);
        imgShowPass = findViewById(R.id.imgShowPass);
        imgHidePass = findViewById(R.id.imgHidePass);
        imgShowConPass = findViewById(R.id.imgShowConPass);
        imgHideConPass = findViewById(R.id.imgHideConPass);

        imgShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                imgShowPass.setVisibility(View.GONE);
                imgHidePass.setVisibility(View.VISIBLE);
            }
        });
        imgHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgShowPass.setVisibility(View.VISIBLE);
                imgHidePass.setVisibility(View.GONE);
            }
        });
        imgShowConPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_confirmpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                imgShowConPass.setVisibility(View.GONE);
                imgHideConPass.setVisibility(View.VISIBLE);
            }
        });
        imgHideConPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_confirmpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgShowConPass.setVisibility(View.VISIBLE);
                imgHideConPass.setVisibility(View.GONE);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommanMethod.isOnline(RegisterActivity.this)) {
                    if (validationLogin()) {

                 postRegister();

                    }
                } else {

                    CommanMethod.showAlert("No Internet  Connection", RegisterActivity.this);
                }

            }
        });
        userTypeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    userType = typeData.get(position - 1).getId();

                    Log.e("mertpyee>>>>>>>>>>>>>", "" + userType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userType();
    }

    public void userType() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chhotacabin.com/Apicontrollers/user_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("getcountry...", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                                JSONArray jsonArray = obj.getJSONArray("data");

                                stName.add("Select User Type");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    TypeData countrydata = new TypeData();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                                    countrydata.setName(jsonObject.getString("name"));
                                    countrydata.setId(jsonObject.getString("id"));


                                    typeData.add(countrydata);
                                    stName.add(jsonObject.getString("name"));

                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, stName);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                userTypeSelect.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "error occurred", Toast.LENGTH_LONG).show();
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
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);


    }

    public void postRegister() {

        dialog.setMessage("Please wait..");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/user_register",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        Log.e("register", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            String message = obj.getString("msg");
                            if (obj.getString("status").equals("success")) {

                                Intent in = new Intent(RegisterActivity.this, OtpActivity.class);
                                in.putExtra("getmobile",edit_email.getText().toString());
                                startActivity(in);

                            } else if (obj.getString("status").equals("failed")) {
                                dialog.hide();
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            dialog.hide();
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.hide();

                        Toast.makeText(RegisterActivity.this, "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", edit_nickname.getText().toString().trim());
                params.put("email", edit_email.getText().toString().trim());
                params.put("password", edit_password.getText().toString().trim());
              //  params.put("mobile", edit_mobile.getText().toString().trim());
                params.put("type",userType);
                //  params.put("refer_code",edit_referral.getText().toString().trim());

                Log.e("ParmaValuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }

    private boolean validationLogin() {
        if (userTypeSelect.getSelectedItem().toString().trim() == "Select User Type") {

            CommanMethod.showAlert("Please Select User type.", RegisterActivity.this);
            return false;
        } else if (edit_nickname.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your name.", RegisterActivity.this);
            return false;
        }  else if (edit_email.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your email / mobile.", RegisterActivity.this);
            return false;
        } else if (edit_password.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your password.", RegisterActivity.this);
            return false;
        } else if (!edit_password.getText().toString().trim().equalsIgnoreCase(edit_confirmpass.getText().toString().trim())) {
            CommanMethod.showAlert("Don't match password and confirm password.", RegisterActivity.this);
            return false;
        }
        return true;

    }


}
