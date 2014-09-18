package tr.com.aliok.meetingroomkiosk.android.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatTextView;

import tr.com.aliok.meetingroomkiosk.android.R;
import tr.com.aliok.meetingroomkiosk.android.model.Event;

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
        init();
    }

    public EventDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EventDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.setWillNotDraw(false);     // this will allow us to override onDraw(Canvas) method
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.event_detail_view, null);

        // this is the moment to bind components, not before
        bindComponents(view);

        addView(view);
    }

    private void bindComponents(View view) {
        this.mTimeRangeTextView = (FlatTextView) view.findViewById(R.id.timeRangeTextView);
        this.mTitleTextView = (FlatTextView) view.findViewById(R.id.titleTextView);
        this.mDescriptionTextView = (FlatTextView) view.findViewById(R.id.descriptionTextView);
        this.mOrganizerTextView = (FlatTextView) view.findViewById(R.id.organizerLabelTextView);
        this.mAttendeesGridLayout = (GridLayout) view.findViewById(R.id.attendeesGridLayout);
    }

    public void updateFragmentWithEvent(Event currentEvent) {
        this.mTimeRangeTextView.setText(currentEvent.getEventStart().getTime() + "");
    }
}
