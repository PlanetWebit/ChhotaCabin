package planet.com.chhotacabin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
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

import planet.com.chhotacabin.adapter.NotificationConsAdapter;
import planet.com.chhotacabin.pojo.VideoListTopPojo;
import planet.com.chhotacabin.utils.MyPreferences;

public class NotficationContest extends AppCompatActivity implements NotificationConsAdapter.OnQuizClick,NotificationConsAdapter.OnWinnerList{

    RecyclerView my_contest_list;
    RecyclerView.Adapter adapter;
    ArrayList<VideoListTopPojo> data = new ArrayList<>();
    Toolbar toolbar;
    NestedScrollView notificationSS;
    LinearLayout notification_no_data_foundImg;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        my_contest_list = findViewById(R.id.my_contest_list);

        notificationSS = findViewById(R.id.notificationSS);
        notification_no_data_foundImg = findViewById(R.id.notification_no_data_foundImg);

        my_contest_list.setHasFixedSize(true);
        my_contest_list.setLayoutManager(new LinearLayoutManager(NotficationContest.this));
        my_contest_list.setItemAnimator(new DefaultItemAnimator());
        getContestList();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent in = new Intent(NotficationContest.this, MainActivity.class);
        startActivity(in);
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void getContestList() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/get_notification", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Log.e("contest_response", ">>>" + response);
                        if (object.getString("status").equalsIgnoreCase("success")) {
                            data.clear();
                            JSONArray array = object.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                notificationSS.setVisibility(View.VISIBLE);
                                JSONObject obj = array.getJSONObject(i);
                                VideoListTopPojo pojo = new VideoListTopPojo();
                               pojo.setUser_id(obj.getString("user_id"));
                               pojo.setTitle(obj.getString("title"));
                               pojo.setBody(obj.getString("body"));
                                data.add(pojo);
                            }
                            adapter = new NotificationConsAdapter(NotficationContest.this, data, NotficationContest.this, NotficationContest.this);
                            my_contest_list.setAdapter(adapter);
                        } else {
                            notification_no_data_foundImg.setVisibility(View.VISIBLE);
                            data.clear();
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
                    params.put("user_id", MyPreferences.getActiveInstance(NotficationContest.this).getUserId());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(NotficationContest.this);
        queue.add(request);
    }

    @Override
    public void DailyQuizClick(String id, String quizname, String totlQuetion, String cataId) {

    }

    @Override
    public void checkWinnerList(String id, String quizname, String totlQuetion, String cataId) {
       /* Intent in = new Intent(Notfication.this, WinnerList.class);
        in.putExtra("quetId", id);
        in.putExtra("qutionName", quizname);
        in.putExtra("totalQ", totlQuetion);
        startActivity(in);*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(NotficationContest.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
