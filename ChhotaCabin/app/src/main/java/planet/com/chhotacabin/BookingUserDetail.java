package planet.com.chhotacabin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.chhotacabin.pojo.CatagoryPojo;
import planet.com.chhotacabin.pojo.TimeFromPojo;
import planet.com.chhotacabin.pojo.TimeToPojo;
import planet.com.chhotacabin.utils.CommanMethod;
import planet.com.chhotacabin.utils.MyPreferences;
import planet.com.chhotacabin.venderoption.pojo.TypeTime;

public class BookingUserDetail extends AppCompatActivity implements PaymentResultListener {

    EditText edit_name, edit_email, edit_mobile;
    CircleImageView profile_image;
    ImageView pick_profile;
    private File finalFile;
    Toolbar toolbar;
    Button btnSubmit;
    ProgressDialog progressDialog;
    Dialog dialog;
    TextView failed, sucees;
    Button btnDone;
    String price = "";
    String txnId = "";
    TextView cabinTotal, cabinReward, grandTotal;
    private static final String TAG = BookingUserDetail.class.getSimpleName();
    String proId = "";
    String name = "";
    String email = "";
    String mobile = "";
    String propertyType = "";
    String pro_assress = "";
    Spinner spintimefrom, spintimeto, spinPerson;
    TextView dateText, dateto;
    TextView timeText, timeto;
    TimePicker timePicker;
    Dialog dialogdate;
    DatePicker datePicker;
    AppCompatButton dialog_button, next_Action;
    int day, month, year;
    Button nextBtn;
    String cityName = "";
    ArrayList<TypeTime> typeTime = new ArrayList<>();
    ArrayList<TimeFromPojo> timeFromPojos = new ArrayList<>();
    ArrayList<TimeToPojo> timeToPojos = new ArrayList<>();
    ArrayList<CatagoryPojo> personPojo = new ArrayList<>();
    ArrayList<String> stName = new ArrayList<>();
    ArrayList<String> timeS = new ArrayList<>();
    ArrayList<String> araystringtimefrom = new ArrayList<>();
    ArrayList<String> araystringtimeto = new ArrayList<>();
    ArrayList<String> personArr = new ArrayList<>();
    Spinner spintimeSelectTo, spintimeSelectfrom;

    LinearLayout linearDate, linearTiming, linearTime;
    String selectType = "";
    String cabinSelId = "";
    String selectCabinType = "";
    String stringtimefrom = "";
    String stringtimeto = "";
    RadioGroup radioGroup;
    RadioButton classicRadio, premiumRadio, supremeRadio;
    String timing = "";
    String person = "";
    TextView toolText;
    String[] aarayString = {"Person", "1+5", "5+10", "Custom"};
    Calendar calendar;
    TimePickerDialog timepickerdialog;

    String timefrom = "";
    String timetoStr = "";
    Double pointPrice = 0.0;
    int cabinDis, graTot;
    String totalPrice;
    String format = "";
    int hour;
    int min;
    String time = "";
    private int CalendarHour, CalendarMinute;
    String event_home = "q";
    TextView currentdateText;
    LinearLayout linearcurrentDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_user_detail);

        edit_name = findViewById(R.id.edit_name);
        edit_mobile = findViewById(R.id.edit_mobile);
        edit_email = findViewById(R.id.edit_email);
        profile_image = findViewById(R.id.profile_image);
        pick_profile = findViewById(R.id.pick_profile);
        toolbar = findViewById(R.id.toolbar);
        linearTime = findViewById(R.id.linearTime);
        linearDate = findViewById(R.id.linearDate);
        linearTiming = findViewById(R.id.linearTiming);
        btnSubmit = findViewById(R.id.btnSubmit);
        cabinTotal = findViewById(R.id.cabinTotal);
        cabinReward = findViewById(R.id.cabinReward);
        grandTotal = findViewById(R.id.grandTotal);
        spintimefrom = findViewById(R.id.spintimefrom);
        spintimeto = findViewById(R.id.spintimeto);
        spintimeSelectTo = findViewById(R.id.spintimeSelectTo);
        dateText = findViewById(R.id.dateText);
        dateto = findViewById(R.id.dateto);
        spintimeSelectfrom = findViewById(R.id.spintimeSelectfrom);
        currentdateText = findViewById(R.id.currentdateText);
        linearcurrentDate = findViewById(R.id.linearcurrentDate);
        spinPerson = findViewById(R.id.spinPerson);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        progressDialog = new ProgressDialog(BookingUserDetail.this);
        price = getIntent().getStringExtra("price");
        proId = getIntent().getStringExtra("proId");
        propertyType = getIntent().getStringExtra("propertyType");
        pro_assress = getIntent().getStringExtra("pro_assress");
        person = getIntent().getStringExtra("person");
        /*Date currentTime = Calendar.getInstance().getTime();
        currentdateText.setText(currentTime.toString());*/
        String dateStr = "04/05/2010";

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        currentdateText.setText(formattedDate);
        if (propertyType.equalsIgnoreCase("viaSearch")) {
            cityName = getIntent().getStringExtra("cityName");
            selectType = getIntent().getStringExtra("selectType");
            timing = getIntent().getStringExtra("timing");
            timefrom = getIntent().getStringExtra("timefrom");
            timetoStr = getIntent().getStringExtra("timeto");

            Log.e("citynameB", ">>" + cityName);
            Log.e("selectTypeB", ">>" + selectType);
            Log.e("timingB", ">>" + timing);
            Log.e("personB", ">>" + person);
            Log.e("timefromB", ">>>" + timefrom);
            Log.e("timetoB", ">>" + timeto);
            dateText.setText(timefrom);
            dateto.setText(timetoStr);

            if (selectType != null && selectType.equalsIgnoreCase("Event")) {
                event_home = "event";
                linearDate.setVisibility(View.VISIBLE);
                linearTiming.setVisibility(View.GONE);
                linearTime.setVisibility(View.GONE);


            } else {
                linearDate.setVisibility(View.GONE);
                linearcurrentDate.setVisibility(View.VISIBLE);
            }

            araystringtimefrom.add(timefrom);
            araystringtimeto.add(timetoStr);
            timeS.add(timing);
            personArr.add(person);


            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, personArr);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinPerson.setAdapter(dataAdapter);

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, timeS);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spintimefrom.setAdapter(dataAdapter1);

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, araystringtimefrom);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spintimeSelectfrom.setAdapter(dataAdapter2);

            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, araystringtimeto);
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spintimeSelectTo.setAdapter(dataAdapter3);
        } else {
            if (propertyType != null && propertyType.equalsIgnoreCase("Cabin")) {
                cabinSelId = "1";
                linearcurrentDate.setVisibility(View.VISIBLE);
            } else if (propertyType != null && propertyType.equalsIgnoreCase("Conference")) {
                cabinSelId = "2";
                linearcurrentDate.setVisibility(View.VISIBLE);
            } else if (propertyType != null && propertyType.equalsIgnoreCase("Event")) {
                cabinSelId = "4";

                linearcurrentDate.setVisibility(View.GONE);

            } else {
                cabinSelId = "3";
                linearcurrentDate.setVisibility(View.VISIBLE);
            }
            selectType = propertyType;

            Log.e("propertyType", selectType);
            cityName = getIntent().getStringExtra("cityName");
            if (propertyType != null && propertyType.equalsIgnoreCase("Event")) {
                event_home = "event";
                linearDate.setVisibility(View.VISIBLE);
                linearTiming.setVisibility(View.GONE);
                linearTime.setVisibility(View.GONE);

            } else {
                linearDate.setVisibility(View.GONE);

            }
            personArr.add(person);


            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, personArr);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinPerson.setAdapter(dataAdapter);

            cabinTiming();
//            cabinPerson();


        }

        getProfile();

        pick_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
            }
        });
        spintimefrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    timing = typeTime.get(position - 1).getName();
                    timefrom = timing;
                    Log.e("timing", ">>" + timing);
                    if (timing.equalsIgnoreCase("Full Day")) {

                        timefrom = "9 AM";
                        timetoStr = "9 PM";

                        araystringtimefrom.clear();
                        araystringtimeto.clear();
                        araystringtimefrom.add("9 AM");
                        araystringtimeto.add("9 PM");

                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, araystringtimefrom);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spintimeSelectfrom.setAdapter(dataAdapter2);

                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, araystringtimeto);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spintimeSelectTo.setAdapter(dataAdapter3);
                    } else {
                        araystringtimeto.clear();
                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, araystringtimeto);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spintimeSelectTo.setAdapter(dataAdapter3);
                        timeFrom();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spintimeSelectTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stringtimeto = timeToPojos.get(position - 1).getName();
                    timetoStr = stringtimeto;
                    Log.e("timing", ">>" + stringtimeto);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spintimeSelectfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stringtimefrom = timeFromPojos.get(position - 1).getName();
                    timefrom = stringtimefrom;
                    timeto();
                    Log.e("timing", ">>" + stringtimefrom);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stringtimeto = personPojo.get(position - 1).getName();
                    person = stringtimeto;
                    Log.e("person", ">>" + person);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timing.equalsIgnoreCase("")) {
                    Toast.makeText(BookingUserDetail.this, "Please Select Timing", Toast.LENGTH_SHORT).show();

                } else if (spintimeSelectfrom.getSelectedItem().toString().trim()=="From") {
                    Toast.makeText(BookingUserDetail.this, "Please Select Time From", Toast.LENGTH_SHORT).show();
                }else if (spintimeSelectTo.getSelectedItem().toString().trim()=="To") {
                    Toast.makeText(BookingUserDetail.this, "Please Select Time to", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(BookingUserDetail.this, Payment.class);

                    intent.putExtra("CabinTotal", totalPrice);
                    intent.putExtra("Discount", String.valueOf(cabinDis));
                    intent.putExtra("GrandTotal", price);
                    intent.putExtra("propertyType", selectType);
                    intent.putExtra("pro_assress", pro_assress);
                    intent.putExtra("cityName", cityName);
                    intent.putExtra("selectType", selectType);
                    intent.putExtra("cabinSelId", cabinSelId);
                    intent.putExtra("stringtimefrom", stringtimefrom);
                    intent.putExtra("stringtimeto", stringtimeto);
                    intent.putExtra("timing", timing);
                    intent.putExtra("person", person);
                    intent.putExtra("timefrom", timefrom);
                    intent.putExtra("timetoStr", timetoStr);
                    intent.putExtra("booking_date", currentdateText.getText().toString());
                    intent.putExtra("pro_Id", proId);
                    startActivity(intent);
                }

            }
        });

        currentdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurreDateText();
            }
        });
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (propertyType.equalsIgnoreCase("viaSearch")) {

                } else {
                    openDateText();
                }
            }
        });
        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (propertyType.equalsIgnoreCase("viaSearch")) {

                } else {
                    opendateto();
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
                    Uri tempUri = getImageUri(BookingUserDetail.this, photo);
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
        if (BookingUserDetail.this.getContentResolver() != null) {
            Cursor cursor = BookingUserDetail.this.getContentResolver().query(uri, null, null, null, null);
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
                BookingUserDetail.this);
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

    private boolean validationLogin() {
        if (edit_name.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your name.", BookingUserDetail.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_mobile.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your mobile.", BookingUserDetail.this);
            // edit_email.setError("Please enter your email.");
            return false;
        } else if (edit_email.getText().toString().trim().length() == 0) {
            CommanMethod.showAlert("Please enter your email.", BookingUserDetail.this);
            // edit_email.setError("Please enter your email.");
            return false;
        }

        return true;

    }

    public void checkreferPointData() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/booking_amount",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Log.e("response", ">>>" + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getString("status").equalsIgnoreCase("success")) {
                                    JSONObject obj = object.getJSONObject("data");

                                    totalPrice = obj.getString("property_price");

                                    cabinTotal.setText("\u20b9 " + totalPrice);
                                    cabinDis = obj.getInt("discount");
                                    graTot = obj.getInt("total_amount");
                                    price = String.valueOf(graTot);
                                    cabinReward.setText("\u20b9 " + String.valueOf(cabinDis));
                                    grandTotal.setText("\u20b9 " + String.valueOf(graTot));

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
                params.put("user_id", MyPreferences.getActiveInstance(BookingUserDetail.this).getUserId());
                params.put("cabin_id", proId);
                params.put("selected_time", timing);

                Log.e("sendparams", ">>>" + params);


                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(BookingUserDetail.this);
        queue.add(request);
    }

    public void registerSuccess() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/booking",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                            Toast.makeText(BookingUserDetail.this, "Server error", Toast.LENGTH_SHORT).show();
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
                params.put("user_id", MyPreferences.getActiveInstance(BookingUserDetail.this).getUserId());
                params.put("in_time", timefrom);
                params.put("out_time", timetoStr);
                params.put("booking_date", currentdateText.getText().toString());
                params.put("type", selectType);
                params.put("price", price);
                params.put("seleted_person", person);
                params.put("seleted_time", timing);
                params.put("property_name", cityName);
                params.put("property_id", proId);
                params.put("transaction_id", txnId);
                params.put("property_address", pro_assress);
                Log.e("paramsbooking", "kitna gya" + params);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(BookingUserDetail.this);
        queue.add(request);
    }
   /* public void registerSuccess() throws FileNotFoundException {

        final RequestParams params = new RequestParams();
        params.put("name", edit_name.getText().toString().trim());
        params.put("email", edit_email.getText().toString().trim());
        params.put("mobile_no", edit_mobile.getText().toString().trim());
        params.put("user_id", MyPreferences.getActiveInstance(BookingUserDetail.this).getUserId());


        if (finalFile != null) {
            params.put("image", finalFile);
        } else {
            params.put("image", "");
        }


        Log.e("ParmaCreate", ">>>>" + params);
        WebServices.post("user_profile", params, new JsonHttpResponseHandler() {
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
                        //   Toast.makeText(BookingUserDetail.this, msg, Toast.LENGTH_LONG).show();
                        // openDialog();
                       // startPayment();
                        sucessDialog();

                    } else {
                        progressDialog.hide();
                        Toast.makeText(BookingUserDetail.this, msg, Toast.LENGTH_LONG).show();
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


    }*/

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Chota Cabin");
            options.put("description", "Registration Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Integer.valueOf(price) * 100);//Integer.valueOf(amount.getText().toString().trim()) * 100);
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
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {

            txnId = razorpayPaymentID;
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
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
    @Override
    public void onPaymentError(int code, String response) {
        try {
            faildDialog();
            //  Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_LONG).show();
            Log.e("reeere", ">>" + response);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    public void sucessDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dialog);
        sucees = (TextView) dialog.findViewById(R.id.sucees);

        sucees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookingUserDetail.this, "Your order booking successfully !", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(BookingUserDetail.this, MainActivity.class);
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
        failed = (TextView) dialog.findViewById(R.id.failed);

        failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(BookingUserDetail.this, MainActivity.class);
                startActivity(in);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();

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
                                Picasso.with(BookingUserDetail.this)
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
                params.put("user_id", MyPreferences.getActiveInstance(BookingUserDetail.this).getUserId());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(BookingUserDetail.this);
        queue.add(request);
    }

    public void cabinTiming() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/getcabintime",
                new Response.Listener<String>() {
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

                                    countrydata.setName(jsonObject.getString("time"));
                                    countrydata.setId(jsonObject.getString("id"));


                                    typeTime.add(countrydata);
                                    timeS.add(jsonObject.getString("time"));

                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, timeS);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spintimefrom.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookingUserDetail.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        // Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cabin_id", cabinSelId);


                Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookingUserDetail.this);
        requestQueue.add(stringRequest);


    }

    public void timeFrom() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/in_time_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("timing...", response);
                        //timeS.add("Select Timing");
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                                JSONArray jsonArray = obj.getJSONArray("data");
                                araystringtimefrom.clear();

                                araystringtimefrom.add("From");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    TimeFromPojo countrydata = new TimeFromPojo();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (jsonObject.getString("time").equalsIgnoreCase("")) {

                                    } else {
                                        countrydata.setName(jsonObject.getString("time"));
                                        countrydata.setId(jsonObject.getString("id"));
                                        timeFromPojos.add(countrydata);
                                        araystringtimefrom.add(jsonObject.getString("time"));
                                    }
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, araystringtimefrom);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spintimeSelectfrom.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookingUserDetail.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        // Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("select_time", timing);
                params.put("property_type", selectType);


                Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookingUserDetail.this);
        requestQueue.add(stringRequest);


    }

    public void timeto() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/out_time_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("timing...", response);
                        //timeS.add("Select Timing");
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                                // JSONArray jsonArray = obj.getJSONArray("data");
                                araystringtimeto.clear();

                                araystringtimeto.add(obj.getString("data"));
                                timetoStr = obj.getString("data");
                             /*   for (int i = 0; i < jsonArray.length(); i++) {
                                    TimeToPojo countrydata = new TimeToPojo();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    countrydata.setName(jsonObject.getString("time"));
                                    countrydata.setId(jsonObject.getString("id"));


                                    timeToPojos.add(countrydata);
                                    araystringtimeto.add(jsonObject.getString("time"));

                                }*/
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, araystringtimeto);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spintimeSelectTo.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookingUserDetail.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        // Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("select_time", timing);
                params.put("in_time", timefrom);


                Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookingUserDetail.this);
        requestQueue.add(stringRequest);


    }

    public void cabinPerson() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/getperson",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("timing...", response);
                        //timeS.add("Select Timing");
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                                JSONArray jsonArray = obj.getJSONArray("data");
                                personArr.clear();
                                if (event_home.equalsIgnoreCase("event")) {
                                    personArr.add("Event Type");
                                } else {
                                    personArr.add("Select person");
                                }

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    CatagoryPojo countrydata = new CatagoryPojo();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    countrydata.setName(jsonObject.getString("person"));
                                    countrydata.setId(jsonObject.getString("id"));


                                    personPojo.add(countrydata);
                                    personArr.add(jsonObject.getString("person"));

                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BookingUserDetail.this, android.R.layout.simple_spinner_item, personArr);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinPerson.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookingUserDetail.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        // Toast.makeText(getActivity(), "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cabin_id", cabinSelId);


                Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookingUserDetail.this);
        requestQueue.add(stringRequest);


    }

    public void openCurreDateText() {

        dialogdate = new Dialog(BookingUserDetail.this);
        dialogdate.setContentView(R.layout.datepickerdialog);
        datePicker = (DatePicker) dialogdate.findViewById(R.id.datePicker);
        dialog_button = (AppCompatButton) dialogdate.findViewById(R.id.dialog_button);

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day = datePicker.getDayOfMonth();
                month = (datePicker.getMonth() + 1);
                year = datePicker.getYear();

                currentdateText.setText(day + "-" + month + "-" + year);

                //dob.setText(month + "-" + day + "-" + year);
                dialogdate.dismiss();


            }
        });
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        // datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialogdate.show();
    }

    public void openDateText() {

        dialogdate = new Dialog(BookingUserDetail.this);
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
                timefrom = day + "-" + month + "-" + year;
                //dob.setText(month + "-" + day + "-" + year);
                Toast.makeText(BookingUserDetail.this, "" + timefrom, Toast.LENGTH_SHORT).show();

                dialogdate.dismiss();


            }
        });
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        // datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialogdate.show();
    }

    public void opendateto() {

        dialogdate = new Dialog(BookingUserDetail.this);
        dialogdate.setContentView(R.layout.datepickerdialog);
        datePicker = (DatePicker) dialogdate.findViewById(R.id.datePicker);
        dialog_button = (AppCompatButton) dialogdate.findViewById(R.id.dialog_button);

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day = datePicker.getDayOfMonth();
                month = (datePicker.getMonth() + 1);
                year = datePicker.getYear();

                dateto.setText(day + "-" + month + "-" + year);
                timetoStr = day + "-" + month + "-" + year;

                Toast.makeText(BookingUserDetail.this, "" + timetoStr, Toast.LENGTH_SHORT).show();
                //dob.setText(month + "-" + day + "-" + year);
                dialogdate.dismiss();


            }
        });
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        // datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        dialogdate.show();
    }

    public void openDialog() {

        dialog = new Dialog(BookingUserDetail.this);
        dialog.setContentView(R.layout.success_custom_dialog);
        dialog.setCancelable(false);
        dialog.getContext().getResources().getColor(R.color.colorWhite);

        btnDone = dialog.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
