package tr.com.aliok.meetingroomkiosk.android;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import tr.com.aliok.meetingroomkiosk.android.fragments.NowAndTodayOverviewFragment;
import tr.com.aliok.meetingroomkiosk.android.fragments.WeekCalendarFragment;
import tr.com.aliok.meetingroomkiosk.android.model.Event;
import tr.com.aliok.meetingroomkiosk.android.model.PeriodSchedule;
import tr.com.aliok.meetingroomkiosk.android.model.ScheduleInformation;
import tr.com.aliok.meetingroomkiosk.android.service.ServiceContext;
import tr.com.aliok.meetingroomkiosk.android.util.AppUtils;
import tr.com.aliok.meetingroomkiosk.android.util.CommonUtils;
import tr.com.aliok.meetingroomkiosk.android.util.DeLorean;
import tr.com.aliok.meetingroomkiosk.android.util.SharedPrefsUtils;
import tr.com.aliok.meetingroomkiosk.model.api.PeriodType;

import static tr.com.aliok.meetingroomkiosk.android.Constants.TAG;

/**
 * Default activity of the application. Displays an overview of the events.
 */
public class OverviewActivity extends FragmentActivity implements
        NowAndTodayOverviewFragment.ActivityContract,
        WeekCalendarFragment.ActivityContract {

    private static final int DEFAULT_THEME = FlatUI.GRASS;

    // ---- Services ----------------------- //
    private ServiceContext serviceContext;

    // ------------ data ------------------//
    private ScheduleInformation scheduleInformation;
    private Event currentEvent;
    private List<Event> upcomingEvents;
    private PeriodSchedule weekPeriodSchedule;

    // ---- UI Components ------ //
    private ViewPager mViewPager;
    private WeekCalendarFragment mWeekCalendarFragment;
    private NowAndTodayOverviewFragment mNowAndTodayOverviewFragment;

    //TODO check internet connection on resume

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Creating OverviewActivity");

        // this is required for displaying the "loading circle" on action bar
        // see http://stackoverflow.com/questions/20280843/android-progress-bar-under-actionbar-and-remove-circle-progress
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        // these flags allows activity to run without passing the key lock
        // see http://stackoverflow.com/questions/2993146/android-wakelock-and-keyguard
        // they're not sent in SettingsActivity to make sure settings are only changeable by device owner
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.activity_overview);

        // Converts the default values (radius, size, border) to dp to be compatible with different
        // screen sizes. If you skip this there may be problem with different screen densities
        FlatUI.initDefaultValues(this);

        // Setting default theme to avoid to add the attribute "theme" to XML
        // and to be able to change the whole theme at once
        FlatUI.setDefaultTheme(DEFAULT_THEME);

        // Getting action bar drawable and setting it.
        // Sometimes weird problems may occur while changing action bar drawable at runtime.
        // You can try to set title of the action bar to invalidate it after setting background.
        //noinspection ConstantConditions
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, DEFAULT_THEME, false));

        // Check device for Play Services APK.
        CommonUtils.checkPlayServices(this);

        // override application fonts (typefaces)
        AppUtils.setDefaultFont(this, "DEFAULT", "opensans_regular.ttf");
        AppUtils.setDefaultFont(this, "MONOSPACE", "opensans_regular.ttf");
        AppUtils.setDefaultFont(this, "SANS_SERIF", "opensans_regular.ttf");

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        final OverviewPagerAdapter overviewPagerAdapter = new OverviewPagerAdapter(this, getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(overviewPagerAdapter);

        // on tab change, update viewPager so that it shows the correct page
        final ActionBar.TabListener tabListener = new ActionBar.TabListener() {
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

        // add tabs
        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.current_session)
                .setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.week_overview)
                .setTabListener(tabListener);
        actionBar.addTab(tab);

        // similar to above, update the selected tab when current page is changed
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        // set initial view
        mViewPager.setCurrentItem(0);


        // create service context
        if (Constants.MOCK_DATA)
            serviceContext = ServiceContext.buildMock(getAssets());
        else
            serviceContext = ServiceContext.build(SharedPrefsUtils.getServerEndpoint(getApplication()));
    }

    @Override
    protected void onResume() {
        // TODO: receive special parameter from GcmIntentService about the GCM message
        super.onResume();
        Log.d(TAG, "Resuming OverviewActivity");

        // Check device for Play Services APK.
        // need to check it again on resume
        CommonUtils.checkPlayServices(this);

        if (!checkTokens()) {
            return;
        }

        if (!checkSensor()) {
            return;
        }

        fetchScheduleInformation();
    }

    @Override
    public void onEventSelected(Event event) {
        // TODO
        return;
    }

    private void fetchScheduleInformation() {
        // fetch it in the background

        // display action bar loading circle
        setProgressBarIndeterminateVisibility(Boolean.TRUE);


        final String serverToken = SharedPrefsUtils.getServerToken(getApplication());
        new AsyncTask<Void, Void, ScheduleInformation>() {
            @Override
            protected ScheduleInformation doInBackground(Void... voids) {
                try {
                    return serviceContext.getScheduleService().getSchedule(serverToken);
                } catch (RuntimeException e) {
                    Toast.makeText(OverviewActivity.this, R.string.unable_to_fetch_schedule, Toast.LENGTH_LONG).show();
                    Log.e(Constants.TAG, "Unable to fetch schedule", e);
                    // hide action bar loading circle
                    setProgressBarIndeterminateVisibility(Boolean.FALSE);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ScheduleInformation scheduleInformation) {
                if (scheduleInformation == null)
                    return;

                OverviewActivity.this.scheduleInformation = scheduleInformation;
                extractDataFromScheduleInformation();

                // TODO: go to week tab if there is nothing today (no upcoming events and no current even)

                notifyFragmentWithDataArrivalIfTheyAreAttached();
                // hide action bar loading circle
                setProgressBarIndeterminateVisibility(Boolean.FALSE);

            }
        }.execute(null, null, null);

    }

    private void extractDataFromScheduleInformation() {
        if (scheduleInformation == null) {
            // don't clear existing data
            return;
        }

        for (PeriodSchedule periodSchedule : scheduleInformation.getPeriodSchedules()) {
            // extract week schedule

            if (PeriodType.WEEK.equals(periodSchedule.getPeriod().getPeriodType())) {
                weekPeriodSchedule = periodSchedule;

            } else if (PeriodType.DAY.equals(periodSchedule.getPeriod().getPeriodType())) {
                // extract day schedule

                final Date now = DeLorean.now();
                final List<Event> eventsOfToday = periodSchedule.getSchedule().getEvents();
                final List<Event> eventsStartAfterNow = Lists.newArrayList(Iterables.filter(eventsOfToday, new Predicate<Event>() {
                    @Override
                    public boolean apply(Event input) {
                        return !now.after(input.getEventStart()); // means : now is before or equal to input.eventStart
                    }
                }));

                for (Event eventOfToday : eventsOfToday) {
                    if (eventOfToday.getEventStart().before(now) && eventOfToday.getEventEnd().after(now)) {
                        currentEvent = eventOfToday;
                        break;
                    }
                }

                eventsStartAfterNow.remove(currentEvent);
                upcomingEvents = eventsStartAfterNow;
                Collections.sort(upcomingEvents);
            } else {
                throw new IllegalStateException();
            }
        }


    }

    private void notifyFragmentWithDataArrivalIfTheyAreAttached() {
        // perhaps the data request was too quick and it returned before the fragments are created!
        // in that case, fragments will check the data upon creation anyway
        // so, don't worry about notifying them if they're not attached yet

        if (mNowAndTodayOverviewFragment != null)
            mNowAndTodayOverviewFragment.dataArrived();
        if (mWeekCalendarFragment != null)
            mWeekCalendarFragment.dataArrived();
    }

    /**
     * Check if required tokens are there.
     *
     * @return true iff both GCM and server tokens are there
     */
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

    private boolean checkSensor() {
        //TODO : fetch data from server and see if there is an assigned sensor!
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview, menu);
        if (Constants.MOCK_DATA)
            menu.findItem(R.id.action_bttf).setVisible(true);
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
        } else if (id == R.id.action_bttf) {
            if (!Constants.MOCK_DATA){
                Toast.makeText(this, "This button is for development purposes only!", Toast.LENGTH_SHORT).show();
                return true;
            }


            refresh();
            Toast.makeText(this, "Advanced time to " + DeLorean.now(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        if (Constants.MOCK_DATA)
            DeLorean.advanceTime();

        // refresh views with current scheduleInformation. do not fetch the data again immediately. do it in the background.

        // do following to extract current event and upcoming events from the current schedule information
        extractDataFromScheduleInformation();

        // update views with extracted data
        notifyFragmentWithDataArrivalIfTheyAreAttached();

        // then, go fetch the data and do the same again
        fetchScheduleInformation();
    }

    @Override
    public List<Event> getUpcomingEvents() {
        return upcomingEvents;
    }

    @Override
    public PeriodSchedule getWeekSchedule() {
        return weekPeriodSchedule;
    }

    @Override
    public Event getCurrentEvent() {
        return currentEvent;
    }

    @Override
    public void setNowAndTodayOverviewFragment(NowAndTodayOverviewFragment nowAndTodayOverviewFragment) {
        this.mNowAndTodayOverviewFragment = nowAndTodayOverviewFragment;
    }

    @Override
    public void setWeekCalendarFragment(WeekCalendarFragment mWeekCalendarFragment) {
        this.mWeekCalendarFragment = mWeekCalendarFragment;
    }
}
