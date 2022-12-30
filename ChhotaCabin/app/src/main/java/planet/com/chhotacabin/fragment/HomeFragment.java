package planet.com.chhotacabin.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import planet.com.chhotacabin.BuildConfig;
import planet.com.chhotacabin.MainActivity;
import planet.com.chhotacabin.ProductDetail;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.adapter.CityNameAdapter;
import planet.com.chhotacabin.adapter.Custom_View_Pager_Adapter;
import planet.com.chhotacabin.adapter.OurProductAdapter;
import planet.com.chhotacabin.locationgps.GprsTrackers;
import planet.com.chhotacabin.pojo.CityData;
import planet.com.chhotacabin.pojo.ProductListPojo;
import planet.com.chhotacabin.pojo.ViewPagerData;

public class HomeFragment extends Fragment implements OurProductAdapter.OnClickPro, CityNameAdapter.itemClick, Custom_View_Pager_Adapter.itemClick {
    Dialog dialog;
    Dialog dialogOtp;
    ImageView cancelDialog;
    Button btnsignin;
    EditText edit_mobile;
    TextView mobileText;
    String getMobile = "";
    ImageView searchImg, propertyNot;
    RecyclerView ourRecy, cityRecycler;
    ArrayList<CityData> cityImgList = new ArrayList<CityData>();
    ProgressDialog progressDialog;
    Double latitude = 0.0, longitude = 0.0;
    String address = "";
    RecyclerView.LayoutManager ourPrLi;
    RecyclerView.Adapter ourAdapter, cityAdapter;
    ArrayList<String> prodata1 = new ArrayList<>();
    ArrayList<ViewPagerData> listImage = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 200;
    Custom_View_Pager_Adapter myCustomPagerAdapter;
    public ViewPager viewPager1;
    CircleIndicator circleIndicator;
    int cunt = 0;
    RecyclerView productRecy, cataRecy;
    RecyclerView.Adapter adapter, adapter1;
    ArrayList<ProductListPojo> prolistData = new ArrayList<>();
    int resId = R.anim.layout_animation_fall_down;
    LinearLayout nearbyLinear;
    TextView ctab, cotab, Ttab, Etab, alltab;
    String property_type = "";
    String valueBg = "";
    String location = "noida";
    String maximum = "0";
    String minimum = "0";
    LinearLayout filter;
    SwipeRefreshLayout pullToRefresh;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_dashboard, container, false);

        // MobileAds.initialize(getActivity(), "ca-app-pub-3940256099942544~3347511713");
        //  MobileAds.initialize(getActivity(), "ca-app-pub-4016411469669208~8854858989");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");


        ourRecy = view.findViewById(R.id.ourRecy);
        propertyNot = view.findViewById(R.id.propertyNot);
        viewPager1 = (ViewPager) view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleindia);
        ctab = view.findViewById(R.id.ctab);
        alltab = view.findViewById(R.id.alltab);
        cotab = view.findViewById(R.id.cotab);
        Ttab = view.findViewById(R.id.Ttab);
        Etab = view.findViewById(R.id.Etab);
        nearbyLinear = view.findViewById(R.id.nearbyLinear);
        filter = view.findViewById(R.id.filter);

        // view=getWindow().getDecorView().getRootView();
        /*Admob.createLoadBanner(getActivity(), view);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/
        ourRecy.setHasFixedSize(true);
        ourPrLi = new LinearLayoutManager(getActivity());
        ourRecy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        ourRecy.setLayoutAnimation(animation);


        cataRecy = view.findViewById(R.id.cataRecy);
        cataRecy.setHasFixedSize(true);
        cataRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //  cataRecy.setItemAnimator(new DefaultItemAnimator());
        //cataRecy.setLayoutAnimation(animation);
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                R.anim.item_animation_fall_down);
        cataRecy.setAnimation(anim);

        if (checkPermission()) {

            checkPermission();
        }

        if (!checkPermission()) {

            requestPermission();

        }

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog();
            }
        });


        for (int i = 0; i <= 20; i++) {
            prodata1.add("data");
        }
        alltab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_type = "";
                String valueBg = "1";
                typebg(valueBg);
                progressDialog.hide();
                productList(property_type, location, maximum, minimum);
            }
        });
        ctab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_type = "Cabin";
                String valueBg = "2";
                typebg(valueBg);
                progressDialog.hide();
                productList(property_type, location, maximum, minimum);
            }
        });
        cotab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_type = "Conference";
                String valueBg = "3";
                typebg(valueBg);
                progressDialog.hide();
                productList(property_type, location, maximum, minimum);
            }
        });
        Ttab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_type = "Training";
                String valueBg = "4";
                typebg(valueBg);
                progressDialog.hide();
                productList(property_type, location, maximum, minimum);
            }
        });
        Etab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueBg = "5";
                typebg(valueBg);
                property_type = "Event";
                progressDialog.hide();
                productList(property_type, location, maximum, minimum);
            }
        });
        /*ourAdapter = new OurProductAdapter(getActivity(), prodata1, HomeFragment.this);
        ourRecy.setAdapter(ourAdapter);*/

        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                cityListImg();
                onResume();
                productList(property_type, location, maximum, minimum);

                pullToRefresh.setRefreshing(false);
            }
        });

        GprsTrackers gpsTracker = new GprsTrackers(getActivity());


        latitude = gpsTracker.getLat();
        longitude = gpsTracker.getLng();

        Log.e("latitu", ">>>>" + latitude);
        Log.e("long", ">>>" + longitude);

        //  gpsTracker.showAlertDialog();


        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            address = addresses.get(0).getAddressLine(0);
          /*  String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        nearbyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "" + String.valueOf(latitude), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "" + String.valueOf(longitude), Toast.LENGTH_SHORT).show();
            }
        });

        init();
        cityListImg();
        productList(property_type, location, maximum, minimum);


        return view;

    }

    private void typebg(String valueBg) {
        if (valueBg.equalsIgnoreCase("1")) {
            alltab.setBackgroundResource(R.drawable.button);
            alltab.setTextColor(getResources().getColor(R.color.colorWhite));
            ctab.setBackgroundResource(R.drawable.editbox);
            ctab.setTextColor(getResources().getColor(R.color.colorPrimary));
            cotab.setBackgroundResource(R.drawable.editbox);
            cotab.setTextColor(getResources().getColor(R.color.colorPrimary));
            Ttab.setBackgroundResource(R.drawable.editbox);
            Ttab.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (valueBg.equalsIgnoreCase("2")) {
            ctab.setBackgroundResource(R.drawable.button);
            ctab.setTextColor(getResources().getColor(R.color.colorWhite));
            alltab.setBackgroundResource(R.drawable.editbox);
            alltab.setTextColor(getResources().getColor(R.color.colorPrimary));
            cotab.setBackgroundResource(R.drawable.editbox);
            cotab.setTextColor(getResources().getColor(R.color.colorPrimary));
            Ttab.setBackgroundResource(R.drawable.editbox);
            Ttab.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (valueBg.equalsIgnoreCase("4")) {
            Ttab.setBackgroundResource(R.drawable.button);
            Ttab.setTextColor(getResources().getColor(R.color.colorWhite));
            alltab.setBackgroundResource(R.drawable.editbox);
            alltab.setTextColor(getResources().getColor(R.color.colorPrimary));
            cotab.setBackgroundResource(R.drawable.editbox);
            cotab.setTextColor(getResources().getColor(R.color.colorPrimary));
            ctab.setBackgroundResource(R.drawable.editbox);
            ctab.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (valueBg.equalsIgnoreCase("3")) {
            cotab.setBackgroundResource(R.drawable.button);
            cotab.setTextColor(getResources().getColor(R.color.colorWhite));
            alltab.setBackgroundResource(R.drawable.editbox);
            alltab.setTextColor(getResources().getColor(R.color.colorPrimary));
            ctab.setBackgroundResource(R.drawable.editbox);
            ctab.setTextColor(getResources().getColor(R.color.colorPrimary));
            Ttab.setBackgroundResource(R.drawable.editbox);
            Ttab.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void filterDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.filterdialog);
        AppCompatSeekBar minFilter = dialog.findViewById(R.id.minFilter);
        AppCompatSeekBar maxFilter = dialog.findViewById(R.id.maxFilter);
        TextView mintxt = dialog.findViewById(R.id.mintxt);
        TextView maxtxt = dialog.findViewById(R.id.maxtxt);
        Button filterbtn = dialog.findViewById(R.id.filterbtn);

        int step = 1;
        int max = 100000;
        int min = 500;
        minFilter.setMax((max - min) / step);
        maxFilter.setMax(max - min / step);

        minFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int value = min + (progress * step);
                String minvalue = String.valueOf(value);
                mintxt.setText("\u20B9 " + minvalue);
                // Toast.makeText(getActivity(), ""+value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        maxFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int value = min + (progress * step);
                String maxvalue = String.valueOf(value);
                maxtxt.setText("\u20B9 " + maxvalue);
                // Toast.makeText(getActivity(), ""+value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maximum = maxtxt.getText().toString().trim();
                minimum = mintxt.getText().toString().trim();
                dialog.dismiss();
                productList(property_type, location, maximum, minimum);
            }
        });

        dialog.show();
    }

    private void productList(String property_type, String location, String max, String min) {
        //progressDialog.show();
        RequestQueue requestQueuebottom = Volley.newRequestQueue(getActivity());
        StringRequest stringRequestbotom = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/getproperty",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        Log.e("bottomres", response.toString());
                        if (response != null) {
                            try {
                                JSONObject objectBottom = new JSONObject(response);
                                if (objectBottom.getString("status").equals("success")) {
                                    JSONArray arrayBottom = objectBottom.getJSONArray("data");
                                    progressDialog.hide();
                                    prolistData.clear();

                                    for (int i = 0; i < arrayBottom.length(); i++) {
                                        JSONObject jsonObjectBottom = arrayBottom.getJSONObject(i);

                                        ProductListPojo bd = new ProductListPojo();
                                        propertyNot.setVisibility(View.GONE);
                                        ourRecy.setVisibility(View.VISIBLE);
                                        //  Double dprice = jsonObjectBottom.getDouble("price");
                                        bd.setName(jsonObjectBottom.getString("property_name"));
                                        bd.setImage(jsonObjectBottom.getString("image"));
                                        bd.setId(jsonObjectBottom.getString("id"));
                                        // String pri = String.valueOf(String.format("%.2f", dprice));

                                        // bd.setPrice(pri.substring(0, pri.length() - 3));
                                        bd.setDiscount(jsonObjectBottom.getString("property_address"));
                                        bd.setPropertyType(jsonObjectBottom.getString("property_type"));
                                        bd.setPerson_limit(jsonObjectBottom.getString("person_limit"));

                                        prolistData.add(bd);
                                    }
                                    ourAdapter = new OurProductAdapter(getActivity(), prolistData, HomeFragment.this);
                                    ourRecy.setAdapter(ourAdapter);
                                } else {
                                    progressDialog.hide();
                                    ourRecy.setVisibility(View.GONE);
                                    propertyNot.setVisibility(View.VISIBLE);
                                    //   Toast.makeText(getActivity(), "No data found !", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("error", e.toString());
                                progressDialog.hide();
                            }

                        } else {
                            progressDialog.hide();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Log.e("bottomerror", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("property_type", property_type);
                params.put("location", location);
                params.put("maximum", max);
                params.put("minimum", min);
                return params;
            }
        };
        requestQueuebottom.add(stringRequestbotom);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);


        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSION_REQUEST_CODE);

    }

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


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void init() {

        RequestQueue sliderQue = Volley.newRequestQueue(getActivity());

        StringRequest requestSlider = new StringRequest(Request.Method.GET, "https://chhotacabin.com/Apicontrollers/getbanner",
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
                                    myCustomPagerAdapter = new Custom_View_Pager_Adapter(getActivity(), listImage, HomeFragment.this);
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
        });
        sliderQue.add(requestSlider);

    }

    public void cityListImg() {

        //  progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chhotacabin.com/Apicontrollers/category",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();

                        if (response != null) {
                            try {
                                JSONObject obj = new JSONObject(response);

                                String msg = obj.getString("message");
                                if (obj.getString("status").equalsIgnoreCase("success")) {
                                    JSONArray array = obj.getJSONArray("data");

                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject object = array.getJSONObject(i);
                                        CityData listData = new CityData();

                                        listData.setCityName(object.getString("name"));
                                        listData.setCityImg(object.getString("image"));
                                        listData.setCityId(object.getString("id"));

                                        cityImgList.add(listData);
                                    }
                                    adapter = new CityNameAdapter(getActivity(), cityImgList, HomeFragment.this);
                                    cataRecy.setAdapter(adapter);
                                   /* if (cityImgList.isEmpty()) {
                                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                    } else {
                                        cityAdapter = new CityNameAdapter(getActivity(), cityImgList, HomeFragment.this);
                                        cityRecycler.setAdapter(cityAdapter);
                                    }
*/
                                } else {
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                // params.put("latitude", "28.576190");//latitude.toString());
                //params.put("longitude", "77.345790");//longitude.toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void clickCity(String cityName) {

        if (!cityName.equalsIgnoreCase("Near me")) {
           /* Intent in = new Intent(getActivity(), SelectTypeforUser.class);
            in.putExtra("cityName", cityName);
            startActivity(in);*/
            location = cityName;
            productList(property_type, location, maximum, minimum);
        } else {
             if (!checkPermission()) {
                requestPermission();

                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    GprsTrackers gprsTrackers = new GprsTrackers(getActivity());
                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addresses.size() < 1) {
                        getDialog();
                        Log.e("address Size", "" + addresses.size());
                    } else {
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        location = city;
                        productList(property_type, location, maximum, minimum);
                    }
                } else {
                    statusCheck();
                }
            }
        }
    }

    private void getDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
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
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(R.string.dialog_message);
        alert.show();
    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public void openDialog() {

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialogmobile_singin);
        dialog.setCancelable(false);
        dialog.getContext().getResources().getColor(R.color.colorWhite);
        cancelDialog = dialog.findViewById(R.id.cancelDialog);
        btnsignin = dialog.findViewById(R.id.btnsignin);
        edit_mobile = dialog.findViewById(R.id.edit_mobile);


        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getMobile = edit_mobile.getText().toString().trim();
                dialog.dismiss();
                openOtp();
            }
        });
        dialog.show();
    }

    public void openOtp() {

        dialogOtp = new Dialog(getActivity());
        dialogOtp.setContentView(R.layout.dialog_otp);
        dialogOtp.setCancelable(false);
        dialog.getContext().getResources().getColor(R.color.colorWhite);
        cancelDialog = dialogOtp.findViewById(R.id.cancelDialog);
        btnsignin = dialogOtp.findViewById(R.id.btnsubmit);
        mobileText = dialogOtp.findViewById(R.id.mobileText);

        mobileText.setText(getMobile);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOtp.dismiss();
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        dialogOtp.show();
    }

    @Override
    public void productClick(String id, String name, String propertyType, String pro_assress, String pers_limit) {
       /* if (MyPreferences.getActiveInstance(getActivity()).getUserId().equalsIgnoreCase("")) {

        } else {*/
        Intent in = new Intent(getActivity(), ProductDetail.class);
        in.putExtra("proName", name);
        in.putExtra("proId", id);
        in.putExtra("propertyType", propertyType);
        in.putExtra("pro_assress", pro_assress);
        in.putExtra("person", pers_limit);
        startActivity(in);
        //  }
    }

    @Override
    public void itemData(String imageId) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (!checkPermission()) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        }
    }
}


