package planet.com.chhotacabin;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import planet.com.chhotacabin.adapter.OurProductAdapter;
import planet.com.chhotacabin.pojo.ProductListPojo;

public class SearchCityFilterData extends AppCompatActivity implements OurProductAdapter.OnClickPro {

    RecyclerView locaRecy;
    RecyclerView.Adapter adapter, ourAdapter;
    Toolbar toolbar;
    RecyclerView ourRecy;
    ArrayList<String> prodata = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    RecyclerView.LayoutManager ourPrLi;
    int resId = R.anim.layout_animation_fall_down;
    String cityName = "";
    String selectType = "";
    String timing = "";
    String person = "";
    String timefrom = "";
    String timeto = "";
    ArrayList<ProductListPojo> listPojos = new ArrayList<>();
    TextView setFrom, setTo, setCabin;
    private String getType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_city_filter_data_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        setFrom = findViewById(R.id.setFrom);
        setTo = findViewById(R.id.setTo);
        setCabin = findViewById(R.id.setCabin);
        locaRecy = findViewById(R.id.locaRecy);
        ourRecy = findViewById(R.id.ourRecy);
        locaRecy.setHasFixedSize(true);
        locaRecy.setLayoutManager(new LinearLayoutManager(SearchCityFilterData.this, LinearLayoutManager.HORIZONTAL, false));
        Animation animation = AnimationUtils.loadAnimation(SearchCityFilterData.this, R.anim.item_animation_fall_down);
        locaRecy.setAnimation(animation);
        ourRecy.setHasFixedSize(true);
        ourPrLi = new LinearLayoutManager(SearchCityFilterData.this);
        // ourRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ourRecy.setLayoutManager(new GridLayoutManager(SearchCityFilterData.this, 2));
        //  ourRecy.setItemAnimator(new DefaultItemAnimator());
        LayoutAnimationController animation1 = AnimationUtils.loadLayoutAnimation(SearchCityFilterData.this, resId);
        ourRecy.setLayoutAnimation(animation1);

        cityName = getIntent().getStringExtra("cityName");
        selectType = getIntent().getStringExtra("selectType");
        timing = getIntent().getStringExtra("timing");
        person = getIntent().getStringExtra("person");
        timefrom = getIntent().getStringExtra("timefrom");
        timeto = getIntent().getStringExtra("timeto");

        getType=selectType.substring(0, 5);
        setFrom.setText(timefrom);
        setTo.setText(timeto);
        setCabin.setText(selectType);
        Log.e("cityname", ">>" + cityName);
        Log.e("selectType", ">>" + selectType);
        Log.e("timing", ">>" + timing);
        Log.e("person", ">>" + person);
        Log.e("timefrom", ">>>" + timefrom);
        Log.e("timeto", ">>" + timeto);

        getRelateList();
        for (int i = 0; i <= 20; i++) {
            prodata.add("data");
        }

       /* ourAdapter=new OurProductAdapter(SearchCityFilterData.this,prodata, SearchCityFilterData.this);
        ourRecy.setAdapter(ourAdapter);*/
        for (int i = 0; i < 20; i++) {
            data.add("Dheeraj Sharma");
        }

        /*adapter = new SearchCityFilterAdapter(SearchCityFilterData.this, data);
        locaRecy.setAdapter(adapter);
*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void productClick(String id, String name, String propertyType,String pro_assress,String pers_limit) {
       /* if (MyPreferences.getActiveInstance(SearchCityFilterData.this).getUserId().equalsIgnoreCase("")) {
            Intent in = new Intent(SearchCityFilterData.this, LoginActivity.class);
            startActivity(in);
        } else {*/
        Intent in = new Intent(SearchCityFilterData.this, ProductDetail.class);
        in.putExtra("proName", name);
        in.putExtra("proId", id);
        in.putExtra("cityName", cityName);
        in.putExtra("selectType", getType);
        in.putExtra("timing", timing);
        in.putExtra("person", pers_limit);
        in.putExtra("timefrom", timefrom);
        in.putExtra("timeto", timeto);
        in.putExtra("propertyType", "viaSearch");
        in.putExtra("pro_assress", pro_assress);
        startActivity(in);
        // }
    }

    public void getRelateList() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/user_booking",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("re", ">>>" + response);
                        if (response != null) {
                            Log.e("res", ">>>" + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                Log.e("response", ">>>" + response);
                                //  String msg = object.getString("");
                                if (object.getString("status").equalsIgnoreCase("success")) {

                                    JSONArray array = object.getJSONArray("data");

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject obj = array.getJSONObject(i);

                                        ProductListPojo bd = new ProductListPojo();


                                        bd.setName(obj.getString("property_name"));
                                        bd.setImage(obj.getString("user_image"));
                                        bd.setId(obj.getString("id"));
                                        Double dprice = obj.getDouble("price");
                                        String pri = String.valueOf(String.format("%.2f",dprice));

                                        bd.setPrice(pri.substring(0,pri.length()-3));
                                     //   bd.setPrice(String.valueOf(String.format("%.2f",dprice)));
                                    //    bd.setPrice(obj.getString("price"));
                                        bd.setDiscount(obj.getString("property_address"));
                                        bd.setPerson_limit(obj.getString("person_limit"));
                                        bd.setPropertyType(obj.getString("property_type"));

                                        listPojos.add(bd);
                                    }
                                    ourAdapter = new OurProductAdapter(SearchCityFilterData.this, listPojos, SearchCityFilterData.this);
                                    ourRecy.setAdapter(ourAdapter);
                                } else {
                                    //    Toast.makeText(SearchCityFilterData.this, msg, Toast.LENGTH_SHORT).show();
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
                params.put("city", cityName);
                params.put("type", getType);
                params.put("seleted_time", timing);
                params.put("in_time", timefrom);
                params.put("out_time", timeto);
                params.put("seleted_person", person);
                //  params.put("", MyPreferences.getActiveInstance(SearchCityFilterData.this).getUserId());

                Log.e("dataP", "kya gya data" + params);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(SearchCityFilterData.this);
        queue.add(request);
    }
}
