package planet.com.chhotacabin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import planet.com.chhotacabin.adapter.BookingHistoryAdapter;
import planet.com.chhotacabin.locationgps.GPSTracker;
import planet.com.chhotacabin.pojo.BookingHistroyPojo;
import planet.com.chhotacabin.utils.MyPreferences;

public class BookingHistory extends AppCompatActivity implements BookingHistoryAdapter.OnClickMap {
    RecyclerView listUpReRec;
    RecyclerView.Adapter adapter;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ArrayList<BookingHistroyPojo> data = new ArrayList<>();
    Toolbar toolbar;
    LinearLayout no_data_foundImg, bookingLayout;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    ResultReceiver resultReceiver;


    SupportMapFragment mapFragment;
    GoogleMap mMap;
    LatLng origin = new LatLng(28.583715, 77.320827);
    // LatLng dest = new LatLng(28.5839, 77.3231);
    ProgressDialog progressDialog;
    Double latitude = 0.0, longitude = 0.0;
    LocationManager locationManager;
    String address = "";
    Location curentLoction;
    List<Address> addresseslist = null;
    Double destinationLatitude = 0.0, destinationLongitude = 0.0;
    private final static int Map_TIME_OUT = 2000;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_history);

        no_data_foundImg = findViewById(R.id.no_data_foundImg);
        bookingLayout = findViewById(R.id.bookingLayout);
        listUpReRec = findViewById(R.id.listUpReRec);
       /* listUpReRec.setHasFixedSize(true);
        listUpReRec.setLayoutManager(new LinearLayoutManager(BookingHistory.this));
        listUpReRec.setItemAnimator(new DefaultItemAnimator());*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listUpReRec.setLayoutManager(linearLayoutManager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        for (int i = 0; i < 20; i++) {
            // data.add("Dheeraj Sharma");
        }


        if (checkPermission()) {

            checkPermission();
        }

        if (!checkPermission()) {

            requestPermission();

        }

        GPSTracker gpsTracker = new GPSTracker(this);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();


        getBookingList();
    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && readAccepted)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE
                                                    }, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                }
        }

    }*/


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(BookingHistory.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION
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


                    if (cameraAccepted && readAccepted)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void getBookingList() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/booking_history",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Log.e("response", ">>" + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getString("status").equalsIgnoreCase("success")) {
                                    no_data_foundImg.setVisibility(View.GONE);
                                    bookingLayout.setVisibility(View.VISIBLE);
                                    JSONArray array = object.getJSONArray("data");

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject obj = array.getJSONObject(i);
                                        BookingHistroyPojo pojo = new BookingHistroyPojo();
                                        pojo.setProname(obj.getString("property_name"));
                                        pojo.setProPrice(obj.getString("price"));
                                        pojo.setTimetable(obj.getString("in_time"));
                                        pojo.setTimetable1(obj.getString("out_time"));
                                        pojo.setDate(obj.getString("booking_date"));
                                        pojo.setProimg(obj.getString("image"));
                                        pojo.setQunti(obj.getString("seleted_person"));
                                        pojo.setProAdd(obj.getString("property_address"));
                                        pojo.setSelectTime(obj.getString("seleted_time"));
                                        pojo.setTxnId(obj.getString("transaction_id"));
                                        pojo.setLat(obj.getString("lat"));
                                        pojo.setLng(obj.getString("lng"));

                                        data.add(pojo);
                                    }
                                    adapter = new BookingHistoryAdapter(BookingHistory.this, data, BookingHistory.this);
                                    listUpReRec.setAdapter(adapter);
                                } else {
                                    // Toast.makeText(BookingHistory.this, "Server error", Toast.LENGTH_SHORT).show();
                                    no_data_foundImg.setVisibility(View.VISIBLE);
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

                params.put("user_id", MyPreferences.getActiveInstance(BookingHistory.this).getUserId());
                Log.e("parm", ">>" + params);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(BookingHistory.this);
        queue.add(request);
    }


    @Override
    public void onClickMap(ArrayList<BookingHistroyPojo> data, int position) {

     /*   String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", , longitude, "Home Sweet Home", , , "Where the party is at");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);*/

        if (latitude == 0) {
            getDialog();
        } else {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + data.get(position).getLat() + "," + data.get(position).getLng();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            } else {
                statusCheck();
            }
        }
    }

    private void getDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder;
        builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        // builder.setMessage().setTitle();

        //Setting message manually and performing action on button click
        builder.setMessage(R.string.dialog_title)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + BuildConfig.APPLICATION_ID)), 1);
                        // checkPermission();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        androidx.appcompat.app.AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(R.string.dialog_message);
        alert.show();
    }

    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (!checkPermission()) {
                startActivity(new Intent(this, BookingHistory.class));
                finish();
            }
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}

