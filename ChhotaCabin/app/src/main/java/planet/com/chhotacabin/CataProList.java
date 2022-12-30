package planet.com.chhotacabin;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import planet.com.chhotacabin.adapter.CategoryProList;
import planet.com.chhotacabin.pojo.ProductListPojo;

public class CataProList extends AppCompatActivity {
    RecyclerView ourRecy;
    RecyclerView.LayoutManager ourPrLi;
    RecyclerView.Adapter ourAdapter;
    ArrayList<String> prodata = new ArrayList<>();
    ArrayList<ProductListPojo> prolistData = new ArrayList<>();
    String catName="";
    String catId="";
    Toolbar toolbar;
    TextView toolText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cate_pro_list);
        ourRecy=findViewById(R.id.ourRecy);
        ourRecy.setHasFixedSize(true);
       // ourPrLi = new LinearLayoutManager(CataProList.this);
        ourRecy.setLayoutManager(new GridLayoutManager(CataProList.this,2));
        ourRecy.setItemAnimator(new DefaultItemAnimator());
        catName=getIntent().getStringExtra("catName");
        catId=getIntent().getStringExtra("catId");
        toolText = findViewById(R.id.toolText);
        toolText.setText(catName);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);*/
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
    //    productList();

        for (int i=0;i<20;i++){
            prodata.add("dheeraj");
        }
        ourAdapter = new CategoryProList(CataProList.this, prodata);
        ourRecy.setAdapter(ourAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    private void productList() {
        RequestQueue requestQueuebottom = Volley.newRequestQueue(CataProList.this);
        StringRequest stringRequestbotom = new StringRequest(Request.Method.POST, "http://pay2pal.in/api/get_product_category_wise",
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
                                           bd.setDiscount(jsonObjectBottom.getString("purchase_price"));
                                        bd.setId(jsonObjectBottom.getString("id"));

                                        prolistData.add(bd);
                                    }
                                 /*   ourAdapter = new CategoryProList(CataProList.this, prolistData);
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
                 params.put("category_id", catId);
                return params;
            }
        };
        requestQueuebottom.add(stringRequestbotom);
    }
}
