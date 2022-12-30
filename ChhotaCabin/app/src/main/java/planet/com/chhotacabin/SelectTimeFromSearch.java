package planet.com.chhotacabin;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import planet.com.chhotacabin.pojo.CatagoryPojo;
import planet.com.chhotacabin.pojo.TimeFromPojo;
import planet.com.chhotacabin.pojo.TimeToPojo;
import planet.com.chhotacabin.venderoption.pojo.TypeTime;

public class SelectTimeFromSearch extends AppCompatActivity {
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
    Toolbar toolbar;
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

    String format = "";
    int hour;
    int min;
    String time = "";
    private int CalendarHour, CalendarMinute;
String event_home = "eve";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_time_from_search);
        premiumRadio = findViewById(R.id.premiumRadio);
        supremeRadio = findViewById(R.id.supremeRadio);
        spintimefrom = findViewById(R.id.spintimefrom);
        spintimeto = findViewById(R.id.spintimeto);
        toolText = findViewById(R.id.toolText);
        dateText = findViewById(R.id.dateText);
        dateto = findViewById(R.id.dateto);
        spintimeSelectTo = findViewById(R.id.spintimeSelectTo);
        spintimeSelectfrom = findViewById(R.id.spintimeSelectfrom);

        nextBtn = findViewById(R.id.nextBtn);

        toolbar = findViewById(R.id.toolbar);
        radioGroup = findViewById(R.id.radioGroup);
        classicRadio = findViewById(R.id.classicRadio);
        spinPerson = findViewById(R.id.spinPerson);
        linearTime = findViewById(R.id.linearTime);
        linearDate = findViewById(R.id.linearDate);
        linearTiming = findViewById(R.id.linearTiming);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        selectType = getIntent().getStringExtra("selectType");
        cabinSelId = getIntent().getStringExtra("cabinSelId");
        cityName = getIntent().getStringExtra("cityName");
        toolText.setText(selectType);
        //Date currentTime = Calendar.getInstance().getTime();
        //  timeText.setText(currentTime.toString());
        String currentTime1 = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
//        timeText.setText(currentTime1);

       /* timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(SelectTimeFromSearch.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                } else if (hourOfDay == 12) {

                                    format = "PM";

                                } else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                } else {

                                    format = "AM";
                                }


                                timeText.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();


            }
        });*/
     /*   timeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(SelectTimeFromSearch.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                } else if (hourOfDay == 12) {

                                    format = "PM";

                                } else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                } else {

                                    format = "AM";
                                }


                                timeto.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();


            }
        });*/
        if (selectType.equalsIgnoreCase("Cabin")) {
            radioGroup.setVisibility(View.VISIBLE);
        } else {
            radioGroup.setVisibility(View.GONE);

        }

        if (selectType.equalsIgnoreCase("Event Home")) {
            event_home="event";
            linearDate.setVisibility(View.VISIBLE);
            linearTiming.setVisibility(View.GONE);
            linearTime.setVisibility(View.GONE);

        } else {
            linearDate.setVisibility(View.GONE);
        }

      /*  ArrayAdapter aa = new ArrayAdapter(SelectTimeFromSearch.this,android.R.layout.simple_spinner_item,aarayString);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinPerson.setAdapter(aa);
      */
        spintimefrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    timing = typeTime.get(position - 1).getName();
                    if (timing.equalsIgnoreCase("Full Day")){

                        stringtimefrom = "9 AM";
                        stringtimeto = "9 PM";

                        araystringtimefrom.clear();
                        araystringtimeto.clear();
                        araystringtimefrom.add("9 AM");
                        araystringtimeto.add("9 PM");

                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(SelectTimeFromSearch.this, android.R.layout.simple_spinner_item, araystringtimefrom);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spintimeSelectfrom.setAdapter(dataAdapter2);

                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(SelectTimeFromSearch.this, android.R.layout.simple_spinner_item, araystringtimeto);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spintimeSelectTo.setAdapter(dataAdapter3);
                    }else {
                        araystringtimeto.clear();
                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(SelectTimeFromSearch.this, android.R.layout.simple_spinner_item, araystringtimeto);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spintimeSelectTo.setAdapter(dataAdapter3);
                        timeFrom();
                    }

                    Log.e("timing", ">>" + timing);

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
                    person = personPojo.get(position - 1).getName();

                    Log.e("person", ">>" + person);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        classicRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCabinType = "Classic";
               // selectType = "Classic";

            }
        });
        supremeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCabinType = "Supreme";
                //selectType = "Supreme";
            }
        });
        premiumRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCabinType = "Premium";
                //selectType = "Premium";
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectType.equalsIgnoreCase("Cabin")) {
                    if (selectCabinType.equalsIgnoreCase("")) {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select any one", Toast.LENGTH_SHORT).show();
                    } else if (spintimefrom.getSelectedItem().toString().trim() == "Select Timing") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select Timing", Toast.LENGTH_SHORT).show();
                    } else if (spinPerson.getSelectedItem().toString().trim() == "Select person") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select person", Toast.LENGTH_SHORT).show();
                    } else if (spintimeSelectfrom.getSelectedItem().toString().trim()=="From") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select Time From", Toast.LENGTH_SHORT).show();
                    }else if (spintimeSelectTo.getSelectedItem().toString().trim()=="To") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select Time to", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in = new Intent(SelectTimeFromSearch.this, SearchCityFilterData.class);
                        in.putExtra("cityName", cityName);
                        in.putExtra("selectType", selectType);
                        in.putExtra("timing", timing);
                        in.putExtra("person", person);
                        in.putExtra("timefrom", stringtimefrom);
                        in.putExtra("timeto", stringtimeto);
                        startActivity(in);
                    }

                } else if (selectType.equalsIgnoreCase("Event Home")) {
                    if (dateText.getText().equals("Date from")) {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select date from", Toast.LENGTH_SHORT).show();
                    } else if (dateto.getText().equals("Date to")) {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select date to", Toast.LENGTH_SHORT).show();
                    } else if (spinPerson.getSelectedItem().toString().trim() == "Event Type") {
                        Toast.makeText(SelectTimeFromSearch.this, "Event Type", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in = new Intent(SelectTimeFromSearch.this, SearchCityFilterData.class);
                        in.putExtra("cityName", cityName);
                        in.putExtra("selectType", selectType);
                        in.putExtra("timing", timing);
                        in.putExtra("person", person);
                        in.putExtra("timefrom", dateText.getText().toString());
                        in.putExtra("timeto", dateto.getText().toString());
                        startActivity(in);
                    }
                } else {
                    if (spintimefrom.getSelectedItem().toString().trim() == "Select Timing") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select Timing", Toast.LENGTH_SHORT).show();
                    } else if (spinPerson.getSelectedItem().toString().trim() == "Select person") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select person", Toast.LENGTH_SHORT).show();
                    }else if (spintimeSelectfrom.getSelectedItem().toString().trim()=="From") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select Time From", Toast.LENGTH_SHORT).show();
                    }else if (spintimeSelectTo.getSelectedItem().toString().trim()=="To") {
                        Toast.makeText(SelectTimeFromSearch.this, "Please Select Time to", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in = new Intent(SelectTimeFromSearch.this, SearchCityFilterData.class);
                        in.putExtra("cityName", cityName);
                        in.putExtra("selectType", selectType);
                        in.putExtra("timing", timing);
                        in.putExtra("person", person);
                        in.putExtra("timefrom", stringtimefrom);
                        in.putExtra("timeto", stringtimeto);
                        startActivity(in);
                    }

                }

            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog2();
            }
        });
        cabinTiming();
        cabinPerson();


    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        timeText.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent in = new Intent(SelectTimeFromSearch.this, SelectTypeforUser.class);
        in.putExtra("cityName", cityName);
        startActivity(in);
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void openDialog() {

        dialogdate = new Dialog(SelectTimeFromSearch.this);
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
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        dialogdate.show();
    }

    public void openDialog2() {

        dialogdate = new Dialog(SelectTimeFromSearch.this);
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
                //dob.setText(month + "-" + day + "-" + year);
                dialogdate.dismiss();


            }
        });
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        dialogdate.show();
    }

    public void cabinTiming() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/getcabintime",
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

                                    countrydata.setName(jsonObject.getString("time"));
                                    countrydata.setId(jsonObject.getString("id"));


                                    typeTime.add(countrydata);
                                    timeS.add(jsonObject.getString("time"));

                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SelectTimeFromSearch.this, android.R.layout.simple_spinner_item, timeS);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spintimefrom.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelectTimeFromSearch.this, "error occurred", Toast.LENGTH_LONG).show();
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
                params.put("cabin_id", cabinSelId);


                //  Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(SelectTimeFromSearch.this);
        requestQueue.add(stringRequest);


    }
    public void timeFrom() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/in_time_list",
                new com.android.volley.Response.Listener<String>() {
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

                                    if (jsonObject.getString("time").equalsIgnoreCase("")){

                                    }else {
                                        countrydata.setName(jsonObject.getString("time"));
                                        countrydata.setId(jsonObject.getString("id"));
                                        timeFromPojos.add(countrydata);
                                        araystringtimefrom.add(jsonObject.getString("time"));

                                    }
                                }
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SelectTimeFromSearch.this, android.R.layout.simple_spinner_item, araystringtimefrom);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spintimeSelectfrom.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelectTimeFromSearch.this, "error occurred", Toast.LENGTH_LONG).show();
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
                params.put("select_time", timing);
                params.put("property_type", selectType);


                //  Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(SelectTimeFromSearch.this);
        requestQueue.add(stringRequest);


    }
    public void timeto() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/out_time_list",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("timing...", response);
                        //timeS.add("Select Timing");
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                             //   JSONArray jsonArray = obj.getJSONArray("data");
                                araystringtimeto.clear();

                                araystringtimeto.add(obj.getString("data"));
                                stringtimeto=obj.getString("data");
                               /* for (int i = 0; i < jsonArray.length(); i++) {
                                    TimeToPojo countrydata = new TimeToPojo();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    countrydata.setName(jsonObject.getString("time"));
                                    countrydata.setId(jsonObject.getString("id"));


                                    timeToPojos.add(countrydata);
                                    araystringtimeto.add(jsonObject.getString("time"));

                                }*/
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SelectTimeFromSearch.this, android.R.layout.simple_spinner_item, araystringtimeto);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spintimeSelectTo.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelectTimeFromSearch.this, "error occurred", Toast.LENGTH_LONG).show();
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
                params.put("select_time", timing);
                params.put("in_time", stringtimefrom);


                //  Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(SelectTimeFromSearch.this);
        requestQueue.add(stringRequest);


    }

    public void cabinPerson() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/getperson",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("timing...", response);
                        //timeS.add("Select Timing");
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("status").equals("success")) {

                                JSONArray jsonArray = obj.getJSONArray("data");
                                personArr.clear();
if (event_home.equalsIgnoreCase("event")){
    personArr.add("Event Type");
}else {
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
                                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SelectTimeFromSearch.this, android.R.layout.simple_spinner_item, personArr);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinPerson.setAdapter(dataAdapter);


                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelectTimeFromSearch.this, "error occurred", Toast.LENGTH_LONG).show();
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
                params.put("cabin_id", cabinSelId);


                //  Log.e("Parma Valuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(SelectTimeFromSearch.this);
        requestQueue.add(stringRequest);


    }
}
