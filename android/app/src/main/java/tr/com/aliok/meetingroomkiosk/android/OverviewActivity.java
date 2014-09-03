package tr.com.aliok.meetingroomkiosk.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import tr.com.aliok.meetingroomkiosk.android.util.CommonUtils;
import tr.com.aliok.meetingroomkiosk.android.util.SharedPrefsUtils;

import static tr.com.aliok.meetingroomkiosk.android.Constants.TAG;


public class OverviewActivity extends Activity {

    private WebView mWebView;

    //TODO check internet connection on resume

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Check device for Play Services APK.
        CommonUtils.checkPlayServices(this);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check device for Play Services APK.
        // need to check it again on resume
        CommonUtils.checkPlayServices(this);

        if (!checkTokens()) {
            return;
        }

        if (!checkSensor()) {
            return;
        }

        refreshWebView();
    }

    private boolean checkSensor() {
        //TODO : fetch data from server and see if there is an assigned sensor!
        return true;
    }

    private boolean checkTokens() {
        final String gcmRegistrationId = SharedPrefsUtils.getGcmRegistrationId(getApplication());
        if (gcmRegistrationId.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.not_registered_to_gcm, Toast.LENGTH_LONG).show();
            goToSettings();
            return false;
        }

        final String serverToken = SharedPrefsUtils.getServerToken(getApplication());
        if (serverToken.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.not_registered_to_server, Toast.LENGTH_LONG).show();
            goToSettings();
            return false;
        }

        return true;
    }

    private void refreshWebView() {
        mWebView.loadUrl("file:///android_asset/www/overview.html");

        // we cannot call a JS function from the page shown in WebView until the page is loaded.
        // thus, use progress listener to make sure page is loaded
        // then call the JS code
        mWebView.setWebChromeClient(new WebChromeClient() {
            private boolean pageInitialized = false;

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (!pageInitialized && newProgress == 100) {
                    Log.i(TAG, "Initializing page");

                    // do the JS call in a new thread instead of blocking the current UI thread
                    mWebView.post(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl("javascript:hello()");
                        }
                    });

                    pageInitialized = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            goToSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
