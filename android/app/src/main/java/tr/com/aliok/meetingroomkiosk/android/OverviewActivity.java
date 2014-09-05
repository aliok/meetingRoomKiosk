package tr.com.aliok.meetingroomkiosk.android;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;

import tr.com.aliok.meetingroomkiosk.android.fragments.CurrentSessionFragment;
import tr.com.aliok.meetingroomkiosk.android.fragments.DayCalendarFragment;
import tr.com.aliok.meetingroomkiosk.android.fragments.WeekCalendarFragment;
import tr.com.aliok.meetingroomkiosk.android.util.CommonUtils;
import tr.com.aliok.meetingroomkiosk.android.util.FontsOverride;
import tr.com.aliok.meetingroomkiosk.android.util.SharedPrefsUtils;


public class OverviewActivity extends FragmentActivity implements
        DayCalendarFragment.OnFragmentInteractionListener,
        CurrentSessionFragment.OnFragmentInteractionListener,
        WeekCalendarFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;

    //TODO check internet connection on resume

    private static final int DEFAULT_THEME = FlatUI.GRASS;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Converts the default values (radius, size, border) to dp to be compatible with different
        // screen sizes. If you skip this there may be problem with different screen densities
        FlatUI.initDefaultValues(this);

        // Setting default theme to avoid to add the attribute "theme" to XML
        // and to be able to change the whole theme at once
        FlatUI.setDefaultTheme(DEFAULT_THEME);
//        FlatUI.setDefaultTheme(R.array.my_custom_theme);    // for using custom theme as default

        // Getting action bar drawable and setting it.
        // Sometimes weird problems may occur while changing action bar drawable at runtime.
        // You can try to set title of the action bar to invalidate it after setting background.
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, DEFAULT_THEME, false));

        // Check device for Play Services APK.
        CommonUtils.checkPlayServices(this);

        FontsOverride.setDefaultFont(this, "DEFAULT", "opensans_regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "opensans_regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset3.ttf");

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        final OverviewPagerAdapter overviewPagerAdapter = new OverviewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(overviewPagerAdapter);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                // When the tab is selected, switch to the
                // corresponding page in the ViewPager.
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                // do nothing
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                // do nothing
            }
        };

        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.current_session)
                .setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.day_overview)
                .setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.week_overview)
                .setTabListener(tabListener);
        actionBar.addTab(tab);


        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

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

        refreshOverview();
    }

    @Override
    public void onDayCalendarFragmentInteraction(Uri uri) {
        //TODO
    }

    @Override
    public void onCurrentSessionFragmentInteraction(Uri uri) {
        //TODO
    }

    @Override
    public void onWeekCalendarFragmentInteraction(Uri uri) {
        //TODO
    }

    private void refreshOverview() {
        //TODO
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
