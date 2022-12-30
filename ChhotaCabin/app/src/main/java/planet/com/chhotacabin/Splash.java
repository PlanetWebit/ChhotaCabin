package planet.com.chhotacabin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import planet.com.chhotacabin.utils.MyPreferences;

public class Splash extends AppCompatActivity {

    private final static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MyPreferences.getActiveInstance(Splash.this).getIsLoggedIn() == true) {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    intent.putExtra("viaLog", "dirLogin");
                    intent.putExtra("proName", "splproName");
                    intent.putExtra("proId", "splIf");
                    intent.putExtra("proDetail", "spproDetail");
                    startActivity(intent);

                    finish();
                }

               // startActivity(new Intent(Splash.this,LoginActivity.class));
            }
        }, SPLASH_TIME_OUT);
    }
}