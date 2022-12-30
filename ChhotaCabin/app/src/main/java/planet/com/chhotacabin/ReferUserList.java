package planet.com.chhotacabin;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

import planet.com.chhotacabin.adapter.ReferUserListAdapter;
import planet.com.chhotacabin.pojo.ReferListPojo;
import planet.com.chhotacabin.utils.MyPreferences;

public class ReferUserList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ReferListPojo> data = new ArrayList<>();
    RecyclerView.Adapter adapter;
    Toolbar toolbar;
TextView refer_point;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_user_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        refer_point = findViewById(R.id.refer_point);
        recyclerView = findViewById(R.id.recy_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReferUserList.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getReferUserList();
        getReferLink();

        for (int i = 0; i <= 2; i++) {
           
        }


    }
    public void getReferLink() {
        StringRequest request;
        RequestQueue queue;
        queue = Volley.newRequestQueue(ReferUserList.this);
        request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/get_userpoint", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response!=null){
                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getString("status").equalsIgnoreCase("success")){

                            int point = object.getInt("data");

                            refer_point.setText(String.valueOf(point));
                            //               referPoint.setText(object.getInt("data"));
                        }else {
                            Toast.makeText(ReferUserList.this, "error occord", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(ReferUserList.this, "Server error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                Log.e("user_id",MyPreferences.getActiveInstance(ReferUserList.this).getUserId());
                params.put("user_id", MyPreferences.getActiveInstance(ReferUserList.this).getUserId());
                return params;
            }

        };
        queue.add(request);
    }
    public void getReferUserList() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/mydownline_user"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getString("status").equalsIgnoreCase("success")) {
                            JSONArray array = object.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                ReferListPojo pojo = new ReferListPojo();
                                JSONObject obj = array.getJSONObject(i);

                                pojo.setName(obj.getString("name"));
                                pojo.setDate(obj.getString("date"));
                                pojo.setPoint(obj.getString("point"));
                                pojo.setMobile(obj.getString("msg"));

                                data.add(pojo);
                            }

                            adapter = new ReferUserListAdapter(ReferUserList.this, data);
                            recyclerView.setAdapter(adapter);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(ReferUserList.this, "server error!", Toast.LENGTH_SHORT).show();
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
                params.put("user_id", MyPreferences.getActiveInstance(ReferUserList.this).getUserId());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ReferUserList.this);
        queue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull  MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
