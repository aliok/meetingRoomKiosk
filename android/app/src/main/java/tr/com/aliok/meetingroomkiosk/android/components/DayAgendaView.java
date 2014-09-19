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

import java.util.SortedSet;

import tr.com.aliok.meetingroomkiosk.android.R;
import tr.com.aliok.meetingroomkiosk.android.model.Event;
import tr.com.aliok.meetingroomkiosk.android.util.DateTimeUtils;

/**
 * Displays events in agenda like way.
 */
public class DayAgendaView extends FrameLayout {

    // ------ listener  --------------------------
    private EventSelectionListener eventSelectionListener;

    // ------ component bindings -----------------
    private GridLayout mEventsGridLayout;

    public DayAgendaView(Context context) {
        super(context);
        initizalize(context);
    }

    public DayAgendaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initizalize(context);
    }

    public DayAgendaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initizalize(context);
    }

    private void initizalize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_day_agenda, null);

        // this is the moment to bind components, not before
        bindComponents(view);

        addView(view);
    }

    private void bindComponents(View view) {
        this.mEventsGridLayout = (GridLayout) view.findViewById(R.id.eventsGridLayout);
    }

    public void updateViewWithEvents(SortedSet<Event> events) {
        //TODO
        // TODO: show 2 lines for each event
        // 1st line : time and title
        // 2nd line : organizer
        // TODO: on title click, trigger listener
        this.mEventsGridLayout.removeAllViews();
        this.mEventsGridLayout.setRowCount(events.size() * 2);
        this.mEventsGridLayout.setColumnCount(2);

        int i = 0;
        for (Event event : events) {
            // add the time column
            {
                final FlatTextView attendeeNameTextView = createFlatTextView(15, FlatUI.GRASS);
                attendeeNameTextView.setText(DateTimeUtils.getTimeRangeStr(event.getEventStart(), event.getEventEnd()));
                attendeeNameTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i*2), GridLayout.spec(0)));
                this.mEventsGridLayout.addView(attendeeNameTextView, 0);
            }
            // add the title column
            {
                final FlatTextView attendeeNameTextView = createFlatTextView(20, FlatUI.SEA);
                attendeeNameTextView.setText(event.getEventTitle());
                attendeeNameTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i*2), GridLayout.spec(1)));
                this.mEventsGridLayout.addView(attendeeNameTextView, 0);
            }
            // add the organizer column
            {
                final FlatTextView attendeeNameTextView = createFlatTextView(15, FlatUI.SEA);
                attendeeNameTextView.setText(event.getOrganizer().getFirstName() + " " + event.getOrganizer().getLastName());
                attendeeNameTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i*2 + 1), GridLayout.spec(1)));
                this.mEventsGridLayout.addView(attendeeNameTextView, 0);
            }

            i++;
        }
    }

    private FlatTextView createFlatTextView(int textSizeInDp, int theme) {
        final FlatTextView textView = new FlatTextView(getContext());

        textView.getAttributes().setTheme(theme, getResources());
        textView.getAttributes().setBorderWidth(0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInDp);

        textView.onThemeChange();       // need to trigger this one to make sure FlatTextView reads attributes set!
        return textView;
    }

    public interface EventSelectionListener {
        void onEventSelected(Event event);
    }

}
