package planet.com.chhotacabin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import planet.com.chhotacabin.utils.MyPreferences;

public class NeedHelp extends AppCompatActivity {
    TextView description;
    Button postButton;
    ProgressDialog dialog;
    Toolbar toolbar;
    String rating = "";
    RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.need_help);
        description = findViewById(R.id.description);
        ratingBar = findViewById(R.id.ratingBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        postButton = findViewById(R.id.postButton);
        dialog = new ProgressDialog(NeedHelp.this);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(NeedHelp.this, "Please write something here...", Toast.LENGTH_SHORT).show();
                } else if (ratingBar.getRating()==0) {
                    Toast.makeText(NeedHelp.this, "Please give rating", Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(NeedHelp.this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
                    sendSuggestion();
                }

            }
        });

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = String.valueOf(ratingBar.getRating());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void sendSuggestion() {

        dialog.setMessage("Please wait..");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/user_suggestion",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        Log.e("login", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            String message = obj.getString("msg");
                            if (obj.getString("status").equals("success")) {

                                Intent in = new Intent(NeedHelp.this, MainActivity.class);
                                startActivity(in);

                            } else {

                                Toast.makeText(NeedHelp.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(NeedHelp.this, "error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.hide();

                        Toast.makeText(NeedHelp.this, "error occurred", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", MyPreferences.getActiveInstance(NeedHelp.this).getUserId());
                params.put("rating",String.valueOf(ratingBar.getRating()));
                params.put("msg", description.getText().toString());


                Log.e("ParmaValuess", ">>>>" + params);
                return params;
            }
        };
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(NeedHelp.this);
        requestQueue.add(stringRequest);
    }
}
