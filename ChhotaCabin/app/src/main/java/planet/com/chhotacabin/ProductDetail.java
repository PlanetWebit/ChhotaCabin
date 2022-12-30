package planet.com.chhotacabin;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import planet.com.chhotacabin.adapter.Custom_View_Pager_Adapter;
import planet.com.chhotacabin.adapter.FacilityAdapter;
import planet.com.chhotacabin.pojo.CatagoryPojo;
import planet.com.chhotacabin.pojo.ViewPagerData;
import planet.com.chhotacabin.utils.CommanMethod;
import planet.com.chhotacabin.utils.MyPreferences;

public class ProductDetail extends AppCompatActivity implements Custom_View_Pager_Adapter.itemClick {
    Toolbar toolbar;
    String proName = "";
    String proId = "";
    ArrayList<ViewPagerData> listImage = new ArrayList<ViewPagerData>();
    ArrayList<CatagoryPojo> catadata = new ArrayList<CatagoryPojo>();
    Custom_View_Pager_Adapter myCustomPagerAdapter;
    public ViewPager viewPager1;
    CircleIndicator circleIndicator;
    String price = "";
    int cunt = 0;
    TextView toolText;
    RecyclerView prop_facility;
    TextView proper_name, proper_price, prop_status, proper_bathroom, prp_state, prop_city, prop_deler_add, proper_deler_mobile, prop_deler_email, prop_descr;
    RatingBar ratingbar;
    Button addCart, wishList, addCartUpdate;
   // ElegantNumberButton elegantNumberButton_quantity;
    String qty = "1";
    String proCart = "";
    String propertyType = "";
    String cityName = "";
    String selectType = "";
    String timing = "";
    String person = "";
    String timefrom = "";
    String timeto = "";
    String pro_assress = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_activity);
        proper_price = findViewById(R.id.proper_price);
        addCart = findViewById(R.id.addCart);
        wishList = findViewById(R.id.wishList);
        prop_descr = findViewById(R.id.prop_descr);
//        specialPri = findViewById(R.id.specialPri);
        prop_status = findViewById(R.id.prop_status);
        addCartUpdate = findViewById(R.id.addCartUpdate);
       // elegantNumberButton_quantity = findViewById(R.id.elegantNumberButton_quantity);
        prp_state = findViewById(R.id.prp_state);
        proper_name = findViewById(R.id.proper_name);
        prop_city = findViewById(R.id.prop_city);
        prop_deler_add = findViewById(R.id.prop_deler_add);
        ratingbar = findViewById(R.id.ratingbar);
        proper_deler_mobile = findViewById(R.id.proper_deler_mobile);

        prop_deler_email = findViewById(R.id.prop_deler_email);
        toolText = findViewById(R.id.toolText);
        proper_bathroom = findViewById(R.id.proper_bathroom);
        prop_facility = findViewById(R.id.prop_facility);

        prop_facility.setLayoutManager(new LinearLayoutManager(this));

        viewPager1 = (ViewPager) findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        // proCart = getIntent().getStringExtra("proCart");
        proName = getIntent().getStringExtra("proName");
        proId = getIntent().getStringExtra("proId");
        propertyType = getIntent().getStringExtra("propertyType");
        cityName = getIntent().getStringExtra("cityName");
        selectType = getIntent().getStringExtra("selectType");
        timing = getIntent().getStringExtra("timing");
        person = getIntent().getStringExtra("person");
        timefrom = getIntent().getStringExtra("timefrom");
        timeto = getIntent().getStringExtra("timeto");
        pro_assress = getIntent().getStringExtra("pro_assress");


        Log.e("cityname", ">>" + cityName);
        Log.e("selectType", ">>" + selectType);
        Log.e("timing", ">>" + timing);
        Log.e("person", ">>" + person);
        Log.e("timefrom", ">>>" + timefrom);
        Log.e("timeto", ">>" + timeto);

        toolText.setText(proName);
       /* elegantNumberButton_quantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = elegantNumberButton_quantity.getNumber();
            }
        });*/
        /*if (proCart.equalsIgnoreCase("proCart")) {
            addCartUpdate.setVisibility(View.VISIBLE);
            addCart.setVisibility(View.GONE);
        } else {
            addCartUpdate.setVisibility(View.GONE);
            addCart.setVisibility(View.VISIBLE);
        }
*/
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommanMethod.isOnline(ProductDetail.this)) {
/*

                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                    //Get the SmsManager instance and call the sendTextMessage method to send message
                    SmsManager sms= SmsManager.getDefault();
                    sms.sendTextMessage("+918755643989", null, "confirm book", pi,null);

                    Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                            Toast.LENGTH_LONG).show();
*/
                    //sendAndCheckBook();
                    if (MyPreferences.getActiveInstance(ProductDetail.this).getCheckloginorNot() == true) {
                        Intent in = new Intent(ProductDetail.this, BookingUserDetail.class);
                        in.putExtra("price", price);
                        in.putExtra("proId", proId);
                        in.putExtra("propertyType", propertyType);
                        in.putExtra("selectType", selectType);
                        in.putExtra("timing", timing);
                        in.putExtra("person", person);
                        in.putExtra("timefrom", timefrom);
                        in.putExtra("timeto", timeto);
                        in.putExtra("pro_assress", pro_assress);
                        in.putExtra("cityName", proName);
                        startActivity(in);

                    } else {
                        Intent in = new Intent(ProductDetail.this, LoginActivity.class);
                        in.putExtra("proName", proName);
                        in.putExtra("proId", proId);
                        in.putExtra("proDetail", "proDetai");
                        in.putExtra("propertyType", propertyType);
                        in.putExtra("pro_assress", pro_assress);
                        in.putExtra("cityName", proName);
                        in.putExtra("selectType", selectType);
                        in.putExtra("timing", timing);
                        in.putExtra("person", person);
                        in.putExtra("timefrom", timefrom);
                        in.putExtra("timeto", timeto);
                        startActivity(in);
                        finish();
                    }
                    // Toast.makeText(ProductDetail.this, "Cooming Soon", Toast.LENGTH_SHORT).show();
                } else {

                    CommanMethod.showAlert("No Internet  Connection", ProductDetail.this);
                }
            }
        });
        addCartUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommanMethod.isOnline(ProductDetail.this)) {


                } else {

                    CommanMethod.showAlert("No Internet  Connection", ProductDetail.this);
                }
            }
        });
        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommanMethod.isOnline(ProductDetail.this)) {


                } else {

                    CommanMethod.showAlert("No Internet  Connection", ProductDetail.this);
                }
            }
        });

        init();
        getDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        RequestQueue sliderQue = Volley.newRequestQueue(ProductDetail.this);

        StringRequest requestSlider = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/getpropertyimage",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response.toString());

                        if (response != null) {

                            try {
                                JSONObject obj = new JSONObject(response);
                                Log.e("ImageResponse", ">>>>" + obj);

                                if (obj.getString("status").equalsIgnoreCase("success")) {
                                    JSONArray jsonArray = obj.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        ViewPagerData imageShow = new ViewPagerData();
                                        JSONObject c = jsonArray.getJSONObject(i);

                                        imageShow.setImageSl(c.getString("image"));
                                        imageShow.setSlidId(c.getString("id"));

                                        Log.e("ImageoneSet", ">>>>" + imageShow);

                                        listImage.add(imageShow);
                                        Log.e("ListImage", ">>>" + listImage);
                                    }
                                    myCustomPagerAdapter = new Custom_View_Pager_Adapter(ProductDetail.this, listImage, ProductDetail.this);
                                    viewPager1.setAdapter(myCustomPagerAdapter);
                                    circleIndicator.setViewPager(viewPager1);

                                    final Handler handler = new Handler();
                                    final Runnable update = new Runnable() {
                                        @Override
                                        public void run() {
                                            if (cunt == listImage.size()) {
                                                cunt = 0;
                                            }
                                            viewPager1.setCurrentItem(cunt++, true);
                                        }
                                    };
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            handler.post(update);
                                        }
                                    }, 2000, 2000);


                                }
                                // final Handler handler = new Handler();

                            } catch (Exception e) {
                                Log.e("error", e.toString());
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("property_id", proId);
                return params;
            }
        };
        sliderQue.add(requestSlider);

    }


    private void getDetails() {
        RequestQueue requestQueuebottom = Volley.newRequestQueue(ProductDetail.this);
        StringRequest stringRequestbotom = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/getpropertydetails",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("bottomres", response.toString());
                        if (response != null) {

                            try {
                                JSONObject objectBottom = new JSONObject(response);
                                if (objectBottom.getString("status").equals("success")) {
                                    JSONArray arrayBottom = objectBottom.getJSONArray("data");

                                    for (int i = 0; i < arrayBottom.length(); i++) {
                                        JSONObject jsonObjectBottom = arrayBottom.getJSONObject(i);

                                        proper_name.setText(jsonObjectBottom.getString("company_name"));
                                        prop_status.setText("Status for " + jsonObjectBottom.getString("company_name"));
                                        Double dprice = jsonObjectBottom.getDouble("price");
                                        if (jsonObjectBottom.getString("property_type").equalsIgnoreCase("Event")) {
                                            proper_price.setText(" \u20b9 " + String.valueOf(String.format("%.2f", dprice)));
                                        } else {
                                            proper_price.setText("2 Hours - \u20b9 " + String.valueOf(String.format("%.2f", dprice)));
                                        }

                                        price = jsonObjectBottom.getString("price");
                                        proper_bathroom.setText("Bathroom Available " + jsonObjectBottom.getString("bathroom"));
                                        prp_state.setText(pro_assress);
                                        String value = jsonObjectBottom.getString("facility");
                                        String [] items = value.split("\\s*,\\s*");
                                        List<String> list = Arrays.asList(items);

                                        FacilityAdapter adapter = new FacilityAdapter(ProductDetail.this,list);
                                        prop_facility.setAdapter(adapter);

                                        // List<String> list = new ArrayList<String>(Arrays.asList(value.split(" , ")));
                                      //  Toast.makeText(ProductDetail.this, ""+list.size(), Toast.LENGTH_SHORT).show();
                                       // prop_facility.setText(jsonObjectBottom.getString("facility"));


                                        prop_city.setText(jsonObjectBottom.getString("property_city")+" , "+jsonObjectBottom.getString("property_state"));
                                        prop_deler_add.setText(jsonObjectBottom.getString("address"));
                                        proper_deler_mobile.setText(jsonObjectBottom.getString("mobile_no"));
                                        prop_deler_email.setText(jsonObjectBottom.getString("email"));
                                        prop_descr.setText(HtmlCompat.fromHtml(jsonObjectBottom.getString("property_description"),0));
                                    }

                                } else {

                                }

                            } catch (Exception e) {
                                Log.e("error", e.toString());
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("bottomerror", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("property_id", proId);
                return params;
            }
        };
        requestQueuebottom.add(stringRequestbotom);
    }

    @Override
    public void itemData(String imageId) {

    }
}
