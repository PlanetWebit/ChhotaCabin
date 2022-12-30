package planet.com.chhotacabin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import planet.com.chhotacabin.adapter.SearchCityAdapter;
import planet.com.chhotacabin.pojo.CityData;

public class SearchFilter extends AppCompatActivity implements SearchCityAdapter.onClickCity{
    ImageView backImg;
    SearchView searchImg;
    EditText editSearch;
    RecyclerView searchcityRecy;
    SearchCityAdapter adapter;
    int resId = R.anim.layout_animation_fall_down;
    ArrayList<CityData> data = new ArrayList<>();
    Toolbar toolbarSearch;
    TextView retry;
    LinearLayout linearNoFound,searchLayout;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_filter_activity);
        backImg = findViewById(R.id.backImg);
        searchImg = findViewById(R.id.searchImg);

        linearNoFound = findViewById(R.id.linearNoFound);
        searchcityRecy = findViewById(R.id.searchcityRecy);
        searchLayout = findViewById(R.id.searchLayout);
        retry = findViewById(R.id.retry);
        searchcityRecy.setHasFixedSize(true);
        searchcityRecy.setLayoutManager(new LinearLayoutManager(SearchFilter.this));
      /*  LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(SearchFilter.this, resId);
        searchcityRecy.setLayoutAnimation(animation);*/
        toolbarSearch = (Toolbar) findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSearch.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        linearNoFound.setVisibility(View.GONE);
        progressDialog=new ProgressDialog(SearchFilter.this);
      /*  for (int i = 0; i < 20; i++) {
            data.add("Dheeraj Sharma");
        }
        adapter = new SearchCityAdapter(SearchFilter.this, data);
        searchcityRecy.setAdapter(adapter);*/
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterCity();
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       /* searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editSearch.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(SearchFilter.this, "Please enter city", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });*/

        toolbarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImg.setIconifiedByDefault(true);
                searchImg.setFocusable(true);
                searchImg.setIconified(false);
                searchImg.requestFocusFromTouch();
            }
        });
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImg.setIconifiedByDefault(true);
                searchImg.setFocusable(true);
                searchImg.setIconified(false);
                searchImg.requestFocusFromTouch();
            }
        });
        searchImg.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView(newText);
                return false;
            }
        });

        getRegisterCity();
    }




    public void getRegisterCity() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chhotacabin.com/Apicontrollers/getcomancitylist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            data.clear();
                            if (object.getString("status").equalsIgnoreCase("success")) {
                                linearNoFound.setVisibility(View.GONE);
                                JSONArray array = object.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject obj = array.getJSONObject(i);
                                    CityData cityData = new CityData();

                                    cityData.setCityName(obj.getString("city"));

                                    data.add(cityData);

                                    Collections.sort(data, new Comparator<CityData>() {
                                        @Override
                                        public int compare(CityData name, CityData nameu) {
                                            return name.getCityName().compareToIgnoreCase(nameu.getCityName());
                                        }
                                    });
                                }
                                adapter = new SearchCityAdapter(SearchFilter.this, data,SearchFilter.this);
                                searchcityRecy.setAdapter(adapter);

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SearchFilter.this);
        requestQueue.add(stringRequest);
    }
    private void searchView(String newText) {

        ArrayList<CityData> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (CityData item : data) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCityName().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }


    }



    public void searchCity() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/category",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("status").equalsIgnoreCase("success")) {
                                getRegisterCity();
                            } else if (object.getString("status").equalsIgnoreCase("faild")) {
                                linearNoFound.setVisibility(View.VISIBLE);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("search", editSearch.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SearchFilter.this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void clickCommanCity(String name) {
        Intent in = new Intent(SearchFilter.this, SelectTypeforUser.class);
        in.putExtra("cityName",name);
        startActivity(in);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
