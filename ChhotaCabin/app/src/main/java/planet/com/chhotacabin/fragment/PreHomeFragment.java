package planet.com.chhotacabin.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import planet.com.chhotacabin.CataProList;
import planet.com.chhotacabin.ProductDetail;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.adapter.Categories_Custom_Adapter;
import planet.com.chhotacabin.adapter.Custom_View_Pager_Adapter;
import planet.com.chhotacabin.adapter.OurProductAdapter;
import planet.com.chhotacabin.pojo.CatagoryPojo;
import planet.com.chhotacabin.pojo.ProductListPojo;
import planet.com.chhotacabin.pojo.ViewPagerData;


public class PreHomeFragment extends Fragment implements Categories_Custom_Adapter.OnClickCata,
        Custom_View_Pager_Adapter.itemClick, OurProductAdapter.OnClickPro {

    View view;

    TextView custid, pay, point, balance;
    AppCompatButton dialogButton;
    CardView card_amazone, card_flipcart, card_shopping, card_more, card_foodpanda, card_zometo, card_uberEat,
            card_Swigy, card_ola, card_uber;

    LinearLayout withrawalclick;
    RecyclerView ourRecy;
    RecyclerView.LayoutManager ourPrLi;
    RecyclerView.Adapter ourAdapter;
    ArrayList<String> prodata = new ArrayList<>();
    ArrayList<String> prodata1 = new ArrayList<>();
    ArrayList<ViewPagerData> listImage = new ArrayList<>();
    ArrayList<CatagoryPojo> catadata = new ArrayList<>();
    Custom_View_Pager_Adapter myCustomPagerAdapter;
    public ViewPager viewPager1;
    CircleIndicator circleIndicator;
    int cunt = 0;
    RecyclerView productRecy, cataRecy;
    RecyclerView.Adapter adapter, adapter1;
    ArrayList<ProductListPojo> prolistData = new ArrayList<>();
    LinearLayout linearWallet;
    String[] cabinType = {"Cabin Type", "Classic", "Premium", "Superme"};
    String[] person = {"Person", "1 to 6", "1 to 10", "1 to 20"};
    String[] Time = {"Time", "2 hours", "4 hours", "1 day"};
    Spinner cabintypeSpinner;
    Spinner personSpiner;
    Spinner spinTime;
    TextView datefrom, dateto;
    Dialog dialogdate;
    DatePicker datePicker;
    AppCompatButton dialog_button, next_Action;
    int day, month, year;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ourRecy = view.findViewById(R.id.ourRecy);
        viewPager1 = (ViewPager) view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circleindia);


        ourRecy.setHasFixedSize(true);
        ourPrLi = new LinearLayoutManager(getActivity());
       // ourRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ourRecy.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ourRecy.setItemAnimator(new DefaultItemAnimator());
        cataRecy = view.findViewById(R.id.cataRecy);
        cataRecy.setHasFixedSize(true);
        cataRecy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        cataRecy.setItemAnimator(new DefaultItemAnimator());
        cabintypeSpinner=view.findViewById(R.id.cabintypeSpinner);
        personSpiner=view.findViewById(R.id.personSpiner);
        spinTime=view.findViewById(R.id.spinTime);
        datefrom=view.findViewById(R.id.datefrom);
        dateto=view.findViewById(R.id.dateto);
        //cabintypeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,cabinType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        cabintypeSpinner.setAdapter(aa);

        ArrayAdapter personSpiner1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, person);
        personSpiner1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        personSpiner.setAdapter(personSpiner1);
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Time);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinTime.setAdapter(adapter1);
        for (int i = 0; i <= 20; i++) {
            prodata.add("data");
        }
        adapter = new Categories_Custom_Adapter(getActivity(), prodata, PreHomeFragment.this);
        cataRecy.setAdapter(adapter);
        for (int i = 0; i <= 20; i++) {
            prodata1.add("data");
        }

        /*ourAdapter=new OurProductAdapter(getActivity(),prodata1, PreHomeFragment.this);
        ourRecy.setAdapter(ourAdapter);*/
        datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog2();
            }
        });
        init();
        /*catagoryList();
        productList();*/
        return view;
    }
    public void openDialog() {

        dialogdate = new Dialog(getActivity());
        dialogdate.setContentView(R.layout.datepickerdialog);
        datePicker = (DatePicker) dialogdate.findViewById(R.id.datePicker);
        dialog_button = (AppCompatButton) dialogdate.findViewById(R.id.dialog_button);

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                day = datePicker.getDayOfMonth();
                month = (datePicker.getMonth() + 1);
                year = datePicker.getYear();

                datefrom.setText(day + "-" + month + "-" + year);
                //dob.setText(month + "-" + day + "-" + year);
                dialogdate.dismiss();


            }
        });
        dialogdate.show();
    }
    public void openDialog2() {

        dialogdate = new Dialog(getActivity());
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
        dialogdate.show();
    }

    private void init() {

        RequestQueue sliderQue = Volley.newRequestQueue(getActivity());

        StringRequest requestSlider = new StringRequest(Request.Method.GET, "https://vucoo.club/api/get_banner_list",
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
                                /*String img1 = c.getString("image");
                                String id = c.getString("id");



                                imageShow.setImageSlid(img1);
                                imageShow.setImageSlid(id);*/
                                        Log.e("ImageoneSet", ">>>>" + imageShow);

                                        listImage.add(imageShow);
                                        Log.e("ListImage", ">>>" + listImage);
                                    }
                                    myCustomPagerAdapter = new Custom_View_Pager_Adapter(getActivity(), listImage, PreHomeFragment.this);
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


    private void catagoryList() {
        RequestQueue requestQueuebottom = Volley.newRequestQueue(getActivity());
        StringRequest stringRequestbotom = new StringRequest(Request.Method.GET, "http://pay2pal.in/api/get_category_list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("catRe", response.toString());
                if (response != null) {

                    try {
                        JSONObject objectBottom = new JSONObject(response);
                        if (objectBottom.getString("status").equals("success")) {
                            JSONArray arrayBottom = objectBottom.getJSONArray("data");

                            for (int i = 0; i < arrayBottom.length(); i++) {
                                JSONObject jsonObjectBottom = arrayBottom.getJSONObject(i);

                                CatagoryPojo bd = new CatagoryPojo();


                                bd.setName(jsonObjectBottom.getString("name"));
                                bd.setImage(jsonObjectBottom.getString("image"));

                                catadata.add(bd);
                            }
                            /*adapter = new Categories_Custom_Adapter(getActivity(), catadata, HomeFragment.this);
                            cataRecy.setAdapter(adapter);*/
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
                //  params.put("city", "noida");
                return params;
            }
        };
        requestQueuebottom.add(stringRequestbotom);
    }

    private void productList() {
        RequestQueue requestQueuebottom = Volley.newRequestQueue(getActivity());
        StringRequest stringRequestbotom = new StringRequest(Request.Method.GET, "http://pay2pal.in/api/get_product_list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("proRes", response.toString());
                        if (response != null) {

                            try {
                                JSONObject objectBottom = new JSONObject(response);
                                if (objectBottom.getString("status").equals("success")) {
                                    JSONArray arrayBottom = objectBottom.getJSONArray("data");

                                    for (int i = 0; i < arrayBottom.length(); i++) {
                                        JSONObject jsonObjectBottom = arrayBottom.getJSONObject(i);

                                        ProductListPojo bd = new ProductListPojo();


                                        bd.setName(jsonObjectBottom.getString("name"));
                                        bd.setImage(jsonObjectBottom.getString("image"));
                                        bd.setPrice(jsonObjectBottom.getString("purchase_price"));
                                        //   bd.setDiscount(jsonObjectBottom.getString("purchase_price"));
                                        bd.setId(jsonObjectBottom.getString("id"));

                                        prolistData.add(bd);
                                    }
                                  /**/ /* ourAdapter = new OurProductAdapter(getActivity(), prolistData, HomeFragment.this);
                                    ourRecy.setAdapter(ourAdapter);*/
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
                //  params.put("city", "noida");
                return params;
            }
        };
        requestQueuebottom.add(stringRequestbotom);
    }


    @Override
    public void itemData(String imageId) {
//        Toast.makeText(getActivity(), "" + imageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cataItemClick(String id, String name) {
        Intent in = new Intent(getActivity(), CataProList.class);
        in.putExtra("catId", id);
        in.putExtra("catName", name);
        startActivity(in);
    }


    @Override
    public void productClick(String id, String name,String propertyType,String pro_assress, String pers_limit) {
        Intent in = new Intent(getActivity(), ProductDetail.class);
        in.putExtra("proId", id);
        in.putExtra("proName", name);
        in.putExtra("proCart","proDe");
        startActivity(in);
    }
}