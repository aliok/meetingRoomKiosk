package tr.com.aliok.meetingroomkiosk.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import tr.com.aliok.meetingroomkiosk.android.components.EventDetailView;
import tr.com.aliok.meetingroomkiosk.android.model.Event;


public class EventDetailActivity extends Activity {

    private EventDetailView mEventDetailView;

    static final String EXTRA_EVENT = "tr.com.aliok.meetingroomkiosk.android.EXTRA_EVENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // these flags allows activity to run without passing the key lock
        // see http://stackoverflow.com/questions/2993146/android-wakelock-and-keyguard
        // they're not sent in SettingsActivity to make sure settings are only changeable by device owner
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.activity_event_detail);

        this.mEventDetailView = (EventDetailView) findViewById(R.id.eventDetailView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final Event event = getIntent().getExtras().getParcelable(EXTRA_EVENT);
        this.mEventDetailView.updateViewWithEvent(event);
    }
}
