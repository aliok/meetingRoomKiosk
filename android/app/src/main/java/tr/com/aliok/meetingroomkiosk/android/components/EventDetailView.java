package tr.com.aliok.meetingroomkiosk.android.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatTextView;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;

import tr.com.aliok.meetingroomkiosk.android.R;
import tr.com.aliok.meetingroomkiosk.android.model.Attendee;
import tr.com.aliok.meetingroomkiosk.android.model.Event;
import tr.com.aliok.meetingroomkiosk.android.util.DateTimeUtils;

/**
 * A fragment to show details of an event.
 */
public class EventDetailView extends FrameLayout {

    // ------ component bindings -----------------
    private FlatTextView mTimeRangeTextView;
    private FlatTextView mTitleTextView;
    private FlatTextView mDescriptionTextView;
    private FlatTextView mOrganizerTextView;
    private GridLayout mAttendeesGridLayout;

    public EventDetailView(Context context) {
        super(context);
        init(context);
    }

    public EventDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EventDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.event_detail_view, null);

        // this is the moment to bind components, not before
        bindComponents(view);

        addView(view);
    }


    private void bindComponents(View view) {
        this.mTimeRangeTextView = (FlatTextView) view.findViewById(R.id.timeRangeTextView);
        this.mTitleTextView = (FlatTextView) view.findViewById(R.id.titleTextView);
        this.mDescriptionTextView = (FlatTextView) view.findViewById(R.id.descriptionTextView);
        this.mOrganizerTextView = (FlatTextView) view.findViewById(R.id.organizerTextView);
        this.mAttendeesGridLayout = (GridLayout) view.findViewById(R.id.attendeesGridLayout);
    }

    public void updateFragmentWithEvent(Event event) {
        this.mTimeRangeTextView.setText(DateTimeUtils.getTimeRangeStr(event.getEventStart(), event.getEventEnd()));

        this.mTitleTextView.setText(event.getEventTitle());

        this.mDescriptionTextView.setText(event.getEventDescription());

        this.mOrganizerTextView.setText(getOrganizerStr(event.getOrganizer()));

        this.mAttendeesGridLayout.removeAllViews();
        if (CollectionUtils.isNotEmpty(event.getAttendees())) {

            List<Attendee> attendees = event.getAttendees();
            for (int i = 0; i < attendees.size(); i++) {
                Attendee attendee = attendees.get(i);

                // add the name column
                {
                    final FlatTextView attendeeNameTextView = createFlatTextView(20, FlatUI.SEA);
                    attendeeNameTextView.setText(attendee.getFirstName() + " " + attendee.getLastName());
                    attendeeNameTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(0)));
                    this.mAttendeesGridLayout.addView(attendeeNameTextView, 0);
                }

                // add the name column
                {
                    final FlatTextView attendeeEmailTextView = createFlatTextView(20, FlatUI.SEA);
                    attendeeEmailTextView.setText(attendee.getFirstName() + " " + attendee.getLastName());
                    attendeeEmailTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(0)));
                    this.mAttendeesGridLayout.addView(attendeeEmailTextView);
                }
                // add the email column
                {
                    final FlatTextView attendeeStatusTextView = createFlatTextView(15, FlatUI.SEA);
                    attendeeStatusTextView.setText("<" + attendee.getEmail() + ">");
                    attendeeStatusTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(1)));
                    this.mAttendeesGridLayout.addView(attendeeStatusTextView);
                }
                // add the status column
                {
                    int themeForAttendeeStatus = FlatUI.SEA;
                    switch (attendee.getAttendeeStatus()) {
                        case ACCEPTED:
                            themeForAttendeeStatus = FlatUI.GRASS;
                            break;
                        case REJECTED:
                            themeForAttendeeStatus = FlatUI.BLOOD;
                            break;
                        case OPTIONAL:
                            themeForAttendeeStatus = FlatUI.SAND;
                            break;
                    }

                    final FlatTextView attendeeNameTextView = createFlatTextView(20, themeForAttendeeStatus);
                    attendeeNameTextView.setText(" - " + StringUtils.capitalize(attendee.getAttendeeStatus().name().toLowerCase(Locale.ENGLISH)));
                    attendeeNameTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(2)));
                    this.mAttendeesGridLayout.addView(attendeeNameTextView);
                }
            }
        }
    }

    private String getOrganizerStr(Attendee organizer) {
        final StringBuilder builder = new StringBuilder();
        builder.append(organizer.getFirstName()).append(" ").append(organizer.getLastName());
        if (StringUtils.isNotBlank(organizer.getEmail()))
            builder.append(" <").append(organizer.getEmail()).append(">");

        return builder.toString();
    }

    private FlatTextView createFlatTextView(int textSizeInDp, int theme) {
        final FlatTextView textView = new FlatTextView(getContext());

        textView.getAttributes().setTheme(theme, getResources());
        textView.getAttributes().setBorderWidth(0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInDp);

        textView.onThemeChange();       // need to trigger this one to make sure FlatTextView reads attributes set!
        return textView;
    }
}
