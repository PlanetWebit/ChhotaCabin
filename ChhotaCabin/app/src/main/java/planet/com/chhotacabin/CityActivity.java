package planet.com.chhotacabin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import planet.com.chhotacabin.adapter.City_Adapter;
import planet.com.chhotacabin.locationgps.GPSTracker;
import planet.com.chhotacabin.pojo.City_Module;

public class CityActivity extends AppCompatActivity implements City_Adapter.cityClick {

    RecyclerView cityRcy;
    ArrayList<City_Module> arrayList = new ArrayList<City_Module>();
    String cityName;
    Toolbar toolbar;
    Double lati, longi;
    String value = "";
    SwipeRefreshLayout pullToRefresh;
    public static final int REQUEST_CODE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);


        gpsEnable();
        requestLocationPermission();

        Intent intent = getIntent();
        cityName =intent.getStringExtra("cityName");

        cityRcy = findViewById(R.id.cityRcy);
        cityRcy.setLayoutManager(new GridLayoutManager(CityActivity.this,2));
       if (value.equalsIgnoreCase("1")){
           city();
       }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
    }

    private void city() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(CityActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/get_property_by_location", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                if (response != null){

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equalsIgnoreCase("success")){
                            JSONArray array = object.getJSONArray("data");
                            for (int i=0;i<array.length();i++){
                                arrayList.clear();
                                 //Toast.makeText(CityActivity.this, ""+array, Toast.LENGTH_SHORT).show();
                                City_Module module = new City_Module();
                                JSONObject jsonObject = array.getJSONObject(i);

                                module.setId(jsonObject.getString("id"));
                                module.setCity(jsonObject.getString("city"));
                                module.setProperty_name(jsonObject.getString("property_name"));
                                module.setProperty_address(jsonObject.getString("property_address"));
                                module.setProperty_type(jsonObject.getString("property_type"));
                                module.setPerson_limit(jsonObject.getString("person_limit"));
                                module.setPrice(jsonObject.getString("price"));
                                module.setUser_image(jsonObject.getString("user_image"));
                                arrayList.add(module);
                                //Toast.makeText(CityActivity.this, "Gehfdh", Toast.LENGTH_SHORT).show();
                            }
                            City_Adapter adapter = new City_Adapter(CityActivity.this,arrayList,CityActivity.this);
                            cityRcy.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(CityActivity.this, "server error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> getParams = new HashMap<>();
                getParams.put("property_city",cityName);
                getParams.put("latitude", String.valueOf(lati));
                getParams.put("longitude", String.valueOf(longi));
                Log.e("getParams",""+getParams);
                return getParams;
            }
        };
        queue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void cityClick(String id, String name, String propertyType, String pro_assress, String person_limit) {
        Intent in = new Intent(CityActivity.this, ProductDetail.class);
        in.putExtra("proName", name);
        in.putExtra("proId", id);
        in.putExtra("propertyType", propertyType);
        in.putExtra("pro_assress", pro_assress);
        in.putExtra("person", person_limit);
        startActivity(in);
    }



    private void gpsEnable() {
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.canGetLocation()) {
            value = "1";
            lati = gpsTracker.getLatitude();
            longi = gpsTracker.getLongitude();
          //  city();
        } else {
            gpsTracker.showSettingsAlert();
        }

    }

    private void requestLocationPermission() {

        boolean foreground = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (foreground) {
            boolean background = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
                handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            boolean foreground = false, background = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
                        continue;
                    } else {
                        Toast.makeText(getApplicationContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        background = true;
                        Toast.makeText(getApplicationContext(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        //        Toast.makeText(getApplicationContext(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            if (foreground) {
                if (background) {
                    handleLocationUpdates();
                } else {
                    handleForegroundLocationUpdates();
                }
            }
        }
    }

    private void handleLocationUpdates() {
        //foreground and background
        Toast.makeText(getApplicationContext(), "Start Foreground and Background Location Updates", Toast.LENGTH_SHORT).show();
    }

    private void handleForegroundLocationUpdates() {
        //handleForeground Location Updates
        Toast.makeText(getApplicationContext(), "Start foreground location updates", Toast.LENGTH_SHORT).show();
    }

}