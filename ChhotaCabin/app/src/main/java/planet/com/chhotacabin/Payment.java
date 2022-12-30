package planet.com.chhotacabin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import planet.com.chhotacabin.utils.MyPreferences;

public class Payment extends AppCompatActivity {

    private static final String TAG = Payment.class.getSimpleName();
    String total, Discount, GrandTotal, propertyType, booking_date, pro_Id,
            pro_assress, cityName, selectType, cabinSelId,
            stringtimefrom, stringtimeto, timing, person, timefrom, timetoStr;

    String txnId = "";
    Double grandTotal;
    Button confirmpayment;
    TextView cabinTotal, cabinReward, grandTotalText;
    int cabinDis, graTot;
    String totalPrice;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cabinTotal = findViewById(R.id.cabinTotal);
        cabinReward = findViewById(R.id.cabinReward);
        grandTotalText = findViewById(R.id.grandTotal);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        total = getIntent().getStringExtra("CabinTotal");
        Discount = getIntent().getStringExtra("Discount");
        GrandTotal = getIntent().getStringExtra("GrandTotal");

       /* cabinTotal.setText( total);
        cabinReward.setText(Discount);
        grandTotalText.setText(GrandTotal);*/
        propertyType = getIntent().getStringExtra("propertyType");
        pro_assress = getIntent().getStringExtra("pro_assress");
        cityName = getIntent().getStringExtra("cityName");
        selectType = getIntent().getStringExtra("selectType");
        cabinSelId = getIntent().getStringExtra("cabinSelId");
        stringtimefrom = getIntent().getStringExtra("stringtimefrom");
        stringtimeto = getIntent().getStringExtra("stringtimeto");
        timing = getIntent().getStringExtra("timing");
        person = getIntent().getStringExtra("person");
        timefrom = getIntent().getStringExtra("timefrom");
        timetoStr = getIntent().getStringExtra("timetoStr");
        booking_date = getIntent().getStringExtra("booking_date");
        pro_Id = getIntent().getStringExtra("pro_Id");


        confirmpayment = findViewById(R.id.confirmPayment);
        checkreferPointData();
        confirmpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

//        Toast.makeText(Payment.this, "" + total + "  " + Discount + "  " + GrandTotal + "  " + propertyType + "  " + pro_assress + "  " + cityName + "  " + selectType
//                + "  " + cabinSelId + "  " + stringtimefrom + "  " + stringtimeto + "  " + timing + "  " + person + "  " + timefrom + "  " + timetoStr, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void checkreferPointData() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/booking_amount",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Log.e("respongiyise", ">>>" + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getString("status").equalsIgnoreCase("success")) {
                                    JSONObject obj = object.getJSONObject("data");

                                    Double ppp = obj.getDouble("property_price");

                                    totalPrice = String.valueOf(String.format("%.2f", ppp));

                                    cabinTotal.setText("\u20b9 " + totalPrice);
                                    cabinDis = obj.getInt("discount");
                                    Double cabinDisDo = obj.getDouble("discount");
                                    Double graTot = obj.getDouble("total_amount");
                                    grandTotal = ppp - cabinDisDo;
                                    GrandTotal = String.valueOf(String.format("%.2f", grandTotal));
                                    cabinReward.setText("\u20b9 " + String.valueOf(String.format("%.2f", cabinDisDo)));
                                    grandTotalText.setText("\u20b9 " + GrandTotal);

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
                String us = MyPreferences.getActiveInstance(Payment.this).getUserId();
                params.put("user_id", us);
                params.put("cabin_id", pro_Id);
                params.put("seleted_time", timing);

                Log.e("sendparams", ">>>" + params);


                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(Payment.this);
        queue.add(request);
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Chhota Cabin");
            options.put("description", "");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");

            String v1 = String.format("%.1f", grandTotal * 100);
            options.put("amount", Double.valueOf(v1));//Integer.valueOf(amount.getText().toString().trim()) * 100);
            JSONObject preFill = new JSONObject();
            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {

            txnId = razorpayPaymentID;
//            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            // sendBalanceRequest();
            registerSuccess();

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    public void onPaymentError(int code, String response) {
        try {
            faildDialog();
            //  Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_LONG).show();
            Log.e("reeere", ">>" + response);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    public void registerSuccess() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/booking",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res",response);
                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getString("status").equalsIgnoreCase("success")) {
                                    sucessDialog();
                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(Payment.this, "Server error", Toast.LENGTH_SHORT).show();
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
                params.put("user_id", MyPreferences.getActiveInstance(Payment.this).getUserId());
                params.put("in_time", timefrom);
                params.put("out_time", timetoStr);
                params.put("booking_date", booking_date);
                params.put("type", selectType);
                params.put("price", GrandTotal);
                params.put("seleted_person", person);
                params.put("seleted_time", timing);
                params.put("property_name", cityName);
                params.put("property_id", pro_Id);
                params.put("transaction_id", txnId);
                params.put("property_address", pro_assress);
                Log.e("paramsbooking", "kitna gya" + params);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(Payment.this);
        queue.add(request);
    }


    public void sucessDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dialog);
        TextView sucees = (TextView) dialog.findViewById(R.id.sucees);

        sucees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Payment.this, "Your order booking successfully !", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Payment.this, MainActivity.class);
                startActivity(in);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void faildDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.failed_dialog);
        TextView failed = (TextView) dialog.findViewById(R.id.failed);

        failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Payment.this, MainActivity.class);
                startActivity(in);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}