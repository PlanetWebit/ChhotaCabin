package planet.com.chhotacabin;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

public class About_Us extends AppCompatActivity {

    Toolbar toolbar;
    WebView webView;
    TextView aboutUs;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        webView = findViewById(R.id.webView);
        toolbar = findViewById(R.id.toolbar);

        aboutUs = findViewById(R.id.aboutUs);

        String htmlString = "<h1>Our Story</h1> <p style=\"font-family: arial; padding: 10px;\">Chhota Cabin is a cabin services company that is passionate about creating innovative solutions for our clients. Our services and solutions are designed with a focus on secure, scalable, expandable and reliable business systems..\n" +
                "\n" +
                "We list classiest budget cabins on our site. Each cabin on our site gives you a memorable staying experience. Along with deluxe, budget and luxury cabins, enjoy a seamless and convenient process to book your Cabin stay with great deals.\n" +
                "\n" +
                "Booking cabins by hour is the finest option to retain your energy. Our cabins are available for your official meeting and micro-stay. For the lowest price and wide availability, you can save upto 50% by booking our first-rate cabins on hourly basis every time.</p>\n" +
                "\t\n" +
                "\n" +
                "\t<h2>Pay And Stay Hourly</h12>\n" +
                "\t\t<h3>Flexible Time Check-In</h3>\n" +
                "\t\t<p style=\"font-family: arial; padding: 10px;\">How often do we crib about the fixed 12PM check-in? Almost, Everytime! Not with us; we provide you the complete flexibility of check-in.</p>\n" +
                "\n" +
                "\n" +
                "\t\t<h3>Great Deals</h3>\n" +
                "\t\t<p style=\"font-family: arial; padding: 10px;\">Also grab attractive offers on holiday packages, Cabin, Training Hall, Confrence Hall and Events.</p>\n" +
                "\n" +
                "\n" +
                "\t\t\n" +
                "\t\t<h3>Best Price Ever</h3>\n" +
                "\t\t<p style=\"font-family: arial; padding: 10px;\">How often do we crib about the fixed 12PM check-in? Almost, Everytime! Not with us; we provide you the complete flexibility of check-in.</p>\n";

        Spanned spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT);

        aboutUs.setText(spanned);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.loadUrl("https://chhotacabin.com/about_us");
        // webView.loadUrl("");
        //   webView.loadUrl("javascript:foobar('https://chhotacabin.com/Home/property')");


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public class MyWebChromeClient extends WebChromeClient {
        // reference to activity instance. May be unnecessary if your web chrome client is member class.


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            // make sure there is no existing message
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            Intent intent = fileChooserParams.createIntent();
            try {
                startActivityForResult(intent, PostProperty.REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                Toast.makeText(About_Us.this, "Cannot open file chooser", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_FILE) {
            if (uploadMessage == null) return;
            uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            uploadMessage = null;
        }
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            // Toast.makeText(Webview.this, "Oh aaya jee! " , Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            // Toast.makeText(Webview.this, "Oh aaya! " , Toast.LENGTH_LONG).show();
            return true;

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            // Toast.makeText(GenealogyActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);


        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
            return true;
        }
    }
}