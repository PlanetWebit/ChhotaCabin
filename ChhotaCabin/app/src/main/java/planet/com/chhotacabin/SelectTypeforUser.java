package planet.com.chhotacabin;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.Map;

import planet.com.chhotacabin.adapter.SelectTypeforUserAdapter;
import planet.com.chhotacabin.pojo.SelectTypeUserPojo;

public class SelectTypeforUser extends AppCompatActivity implements SelectTypeforUserAdapter.OnClickChosse {
    Button nextBtn;
    Toolbar toolbar;
    RecyclerView recy;
    RecyclerView.Adapter adapter;
    ArrayList<SelectTypeUserPojo> data = new ArrayList<>();
    String selectType = "";
    String cityName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.select_type_for_user);
        nextBtn = findViewById(R.id.nextBtn);
        toolbar = findViewById(R.id.toolbar);
        recy = findViewById(R.id.recy);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        recy.setHasFixedSize(true);
        recy.setLayoutManager(new LinearLayoutManager(SelectTypeforUser.this));
        Animation anim = AnimationUtils.loadAnimation(SelectTypeforUser.this,
                R.anim.item_animation_fall_down);
        recy.setAnimation(anim);

        cityName=getIntent().getStringExtra("cityName");
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectType.equalsIgnoreCase("")) {
                    Toast.makeText(SelectTypeforUser.this, "Select What want need you", Toast.LENGTH_SHORT).show();
                } else {
                    Intent in = new Intent(SelectTypeforUser.this, SelectTimeFromSearch.class);
                    in.putExtra("selectType", selectType);
                    startActivity(in);
                }

            }
        });
        getCabin();

    }

    public void getCabin() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://chhotacabin.com/Apicontrollers/getcabintype"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        JSONObject object = new JSONObject(response);

                        String message = object.getString("message");
                        if (object.getString("status").equalsIgnoreCase("success")) {

                            JSONArray array = object.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                SelectTypeUserPojo pojo = new SelectTypeUserPojo();

                                pojo.setName(obj.getString("name"));
                                pojo.setId(obj.getString("id"));

                                data.add(pojo);
                            }
                            adapter = new SelectTypeforUserAdapter(SelectTypeforUser.this, data, SelectTypeforUser.this);
                            recy.setAdapter(adapter);
                        } else {
                            Toast.makeText(SelectTypeforUser.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SelectTypeforUser.this, "Server error", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();

                return parms;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SelectTypeforUser.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnClickCabin(ArrayList<SelectTypeUserPojo> data, int position) {
        Intent in = new Intent(SelectTypeforUser.this, SelectTimeFromSearch.class);
        in.putExtra("selectType", data.get(position).getName());
        in.putExtra("cabinSelId", data.get(position).getId());
        in.putExtra("cityName",cityName);
        //  Toast.makeText(this, id + name, Toast.LENGTH_SHORT).show();
        startActivity(in);
        finish();
    }
}
