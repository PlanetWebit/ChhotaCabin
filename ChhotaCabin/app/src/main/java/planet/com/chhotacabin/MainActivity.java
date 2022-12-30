package planet.com.chhotacabin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.chhotacabin.fragment.HomeFragment;
import planet.com.chhotacabin.utils.MyPreferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    Dialog dialog;
    BottomSheetDialog dialogOtp;
    ImageView cancelDialog;
    Button btnsignin;
    EditText edit_mobile;
    TextView mobileText, textName, referPoint,count_notification;
    String getMobile = "";
    ImageView searchImg,notification, checkDeep;
    CircleImageView profileImage;
    String joinBounus = "";
    ImageView darkMode,lightMode;
    LinearLayout home_image, booking_image, earn_image, help_image,top_round;
    private static final String AUTH_KEY = "AAAARljv6II:APA91bFvo8m4QoNuMK4gwaC2GsTI2X7SImSJLl_J-qwenQleIXGc8Az6b9EItf1MA-YSgdH4itDVeB0VHLxl_DJvXAjvRDNW6zmV4EJZtw0rXZRcD1VB601e5vP_TJmAEfoedvuTqPuR";
    private TextView mTextView;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        searchImg = findViewById(R.id.searchImg);
        notification = findViewById(R.id.notification);
        checkDeep = findViewById(R.id.checkDeep);
        top_round = findViewById(R.id.top_round);
        count_notification = findViewById(R.id.count_notification);


        get_count();
        home_image = findViewById(R.id.home_image);
        booking_image = findViewById(R.id.booking_image);
        earn_image = findViewById(R.id.earn_image);
        help_image = findViewById(R.id.help_image);


        home_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);*/

            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotficationContest.class);
                startActivity(intent);

            }
        });
        booking_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyPreferences.getActiveInstance(MainActivity.this).getCheckloginorNot() == false) {
                    Intent in = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(in);
                } else {
                    Intent intent = new Intent(MainActivity.this, BookingHistory.class);
                    startActivity(intent);
                }
            }
        });
        earn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyPreferences.getActiveInstance(MainActivity.this).getCheckloginorNot() == false) {
                    Intent in = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(in);
                } else {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID + "\n" + "\n"
                            +"Hii ,"+"\n"+ "I'm inviting you to use chhota cabin, a simple and secure Cabin , Conference Hall , Training Hall booking app, Here's my code (" + MyPreferences.getActiveInstance(MainActivity.this).getRefer_point()
                            + " )- if you use my code , you will earn a reward !";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                }
            }
        });

        help_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyPreferences.getActiveInstance(MainActivity.this).getCheckloginorNot() == false) {
                    Intent in = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(in);
                }else {
                    Intent in = new Intent(MainActivity.this, NeedHelp.class);
                    startActivity(in);
                }

            }
        });

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // if (MyPreferences.getActiveInstance(MainActivity.this).getUserId().equalsIgnoreCase("")) {
        if (MyPreferences.getActiveInstance(MainActivity.this).getCheckloginorNot() == false) {
            customerLogout();
        } else {
            customerLogIn();
        }
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SearchFilter.class);
                startActivity(in);
            }
        });
        checkDeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*PackageManager packageManager= getPackageManager();
                ComponentName componentName = new ComponentName(MainActivity.this,SplashActivity.class);
                packageManager.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                ,PackageManager.DONT_KILL_APP);
                Toast.makeText(MainActivity.this, "App Hidden!", Toast.LENGTH_SHORT).show();
*/

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);



        navigationView.inflateHeaderView(R.layout.nav_header_main);
        View view = navigationView.getHeaderView(0);

        profileImage = view.findViewById(R.id.profileImage);
        textName = view.findViewById(R.id.textName);
        referPoint = view.findViewById(R.id.referPoint);
        darkMode = view.findViewById(R.id.darkMode);
        lightMode = view.findViewById(R.id.lightMode);

        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                darkMode.setVisibility(View.GONE);
                lightMode.setVisibility(View.VISIBLE);
            }
        });
        lightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                lightMode.setVisibility(View.GONE);
                darkMode.setVisibility(View.VISIBLE);
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (MyPreferences.getActiveInstance(MainActivity.this).getProfileImage().equalsIgnoreCase("")) {
            profileImage.setImageResource(R.drawable.logo);
        } else {
            //profileImage.setImageResource(R.drawable.logo_white);
            Picasso.with(MainActivity.this).load(MyPreferences.getActiveInstance(MainActivity.this).getProfileImage())
                    .into(profileImage);
        }
        if (MyPreferences.getActiveInstance(MainActivity.this).getname().equalsIgnoreCase("")) {
            textName.setText("Hii , User");
        } else {
            textName.setText("Hii , " + MyPreferences.getActiveInstance(MainActivity.this).getname());
        }
        /* */

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        navigationView.setNavigationItemSelectedListener(this);
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
        getReferLink();
        getJoiningBounus();
       // sendWithOtherThread("tokens");
    }

    private void get_count() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/notification_count", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
               // Toast.makeText(MainActivity.this, ""+MyPreferences.getActiveInstance(MainActivity.this).getUserId(), Toast.LENGTH_SHORT).show();
                if (response!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("success")){
                            if (!jsonObject.getString("count").equalsIgnoreCase("0")){
                                top_round.setVisibility(View.VISIBLE);
                                count_notification.setText(jsonObject.getString("count"));
                            }else {
                                top_round.setVisibility(View.GONE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> getParams = new HashMap<>();
                getParams.put("user_id",MyPreferences.getActiveInstance(MainActivity.this).getUserId());
                Log.e("get",""+getParams);
                return getParams;
            }
        };
        queue.add(request);
    }

    public void getJoiningBounus() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/add_refer_point",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.e("response", ">>" + response);
                                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                                    joinBounus = jsonObject.getString("login_point");
                                    startDialog();
                                } else {

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
                params.put("user_id", MyPreferences.getActiveInstance(MainActivity.this).getUserId());
                return params;
            }


        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(stringRequest);
    }

    private void startDialog() {
        final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                MainActivity.this, R.style.MyDialogTheme);
        myAlertDialog.setCancelable(false);

        myAlertDialog.setTitle("Congratulations");
        myAlertDialog.setMessage("you have won Points " + joinBounus + "  as self joining bonus");

        myAlertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  planupgrade();
                        // changeFragment(new HomeFragment(), getString(R.string.home));
                        arg0.dismiss();
                    }
                });

       /* myAlertDialog.setNegativeButton("Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });*/
        myAlertDialog.show();
    }

    public void getReferLink() {
        StringRequest request;
        RequestQueue queue;
        queue = Volley.newRequestQueue(MainActivity.this);
        request = new StringRequest(Request.Method.POST, "https://chhotacabin.com/Apicontrollers/get_userpoint", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getString("status").equalsIgnoreCase("success")) {

                            int point = object.getInt("data");

                            referPoint.setText("point " + String.valueOf(point));
                            //               referPoint.setText(object.getInt("data"));
                        } else {
                            Toast.makeText(MainActivity.this, "error occord", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", MyPreferences.getActiveInstance(MainActivity.this).getUserId());
                return params;
            }

        };
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDialog() {

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialogmobile_singin);
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

    public void customerLogout() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        //   menu.findItem(R.id.nav_KYCDOC).setVisible(false);
        menu.findItem(R.id.nav_logOut).setVisible(false);
        menu.findItem(R.id.nav_bookinghis).setVisible(false);
        menu.findItem(R.id.nav_level).setVisible(false);
        menu.findItem(R.id.nav_SignIn).setVisible(true);


    }

    public void customerLogIn() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logOut).setVisible(true);
        menu.findItem(R.id.nav_bookinghis).setVisible(true);
        menu.findItem(R.id.nav_level).setVisible(true);
        menu.findItem(R.id.nav_SignIn).setVisible(false);


    }

    public void openOtp() {

        dialogOtp = new BottomSheetDialog(MainActivity.this);
        dialogOtp.setContentView(R.layout.dialog_otp);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_aboutUs) {
            Intent intent = new Intent(MainActivity.this, About_Us.class);
            startActivity(intent);
        } else if (id == R.id.nav_level) {
            Intent intent = new Intent(MainActivity.this, ReferUserList.class);
            startActivity(intent);
        }else if (id == R.id.nav_Profile) {
            if (MyPreferences.getActiveInstance(MainActivity.this).getCheckloginorNot() == false) {
                Intent in = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(in);
            } else {
                Intent in = new Intent(MainActivity.this, UpdateProfile.class);
                startActivity(in);
            }
        } else if (id == R.id.nav_referEarn) {
            if (MyPreferences.getActiveInstance(MainActivity.this).getUserId().equalsIgnoreCase("")) {
                Intent in = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(in);
            } else {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage = "https://play.google.com/store/apps/details?id=com.chota.cabin" +
                        "\n" + "\n"
                        + "I'm inviting you to use chota cabin, a simple and " +
                        "secure booking app, Here's my code " +
                        "(" + MyPreferences.getActiveInstance(MainActivity.this).getRefer_point()
                        + ")- if you use my code , you will earn a reward !";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }
        } else if (id == R.id.nav_postPro) {
            Intent in = new Intent(MainActivity.this, PostProperty.class);
            startActivity(in);
        } else if (id == R.id.nav_logOut) {
            MyPreferences.getActiveInstance(MainActivity.this).setProfileImage("");
            MyPreferences.getActiveInstance(MainActivity.this).setname("");
            MyPreferences.getActiveInstance(MainActivity.this).setCheckloginorNot(false);
            MyPreferences.getActiveInstance(MainActivity.this).setUserId("");
            Intent in = new Intent(MainActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        } else if (id == R.id.nav_SignIn) {
            Intent in = new Intent(MainActivity.this, LoginActivity.class);
            in.putExtra("proDetail", "viasing");
            startActivity(in);
            finish();
        } else if (id == R.id.nav_bookinghis) {
            Intent in = new Intent(MainActivity.this, BookingHistory.class);
            startActivity(in);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
