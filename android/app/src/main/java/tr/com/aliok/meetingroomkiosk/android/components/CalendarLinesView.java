package tr.com.aliok.meetingroomkiosk.android.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import tr.com.aliok.meetingroomkiosk.android.R;

/**
 * Draws the lines for the calendar view.
 * <p/>
 * It draws a horizontal line for each hour and a vertical line for each day.
 * This view should be added behind the events, so that it doesn't go over the events.
 */
public class CalendarLinesView extends RelativeLayout {

    private static final int NUMBER_OF_DAY_COLUMNS = 5;
    private Paint mPaint;       // paint settings to draw lines

    public CalendarLinesView(Context context) {
        super(context);
        initialize();
    }

    public CalendarLinesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CalendarLinesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        this.setWillNotDraw(false);     // this will allow us to override onDraw(Canvas) method

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(com.cengalabs.flatui.R.color.grass_light));
        mPaint.setStyle(Paint.Style.STROKE);
        // mPaint.setStrokeJoin(Paint.Join.ROUND);     // no join round
        // mPaint.setStrokeCap(Paint.Cap.ROUND);    // no cap
        mPaint.setStrokeWidth(getResources().getInteger(R.integer.calendar_lines_view_stroke_width));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        final View parent = (View) getParent();

        final int heightOfEachHour = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getInteger(R.integer.week_calendar_height_of_each_hour_part_in_dp) * 2, displayMetrics));
        final int heightOfHeader = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getInteger(R.integer.week_calendar_height_of_header_in_dp), displayMetrics));
        final int widthOfTimeColumn = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getInteger(R.integer.week_calendar_width_of_time_column_in_dp), displayMetrics));
        final float widthOfEachDayColumn = (parent.getWidth() - widthOfTimeColumn) / NUMBER_OF_DAY_COLUMNS;

        final int hours = getResources().getInteger(R.integer.week_calendar_day_end_hour) - getResources().getInteger(R.integer.week_calendar_day_start_hour);

        // draw horizontal lines
        for (int i = 0; i < hours; i++) {
            final int marginTop = i * heightOfEachHour + heightOfHeader;
            canvas.drawLine(0, marginTop, parent.getWidth(), marginTop, mPaint);
        }

        // draw vertical lines for each day
        for (int i = 0; i < 5; i++) {
            // minus some arbitrary number to make the alignment work
            // all the math done fails in this case! :(
            final float marginLeft = parent.getWidth() - ((i + 1.0f) * widthOfEachDayColumn) - 3.0f;
            canvas.drawLine(marginLeft, 0, marginLeft, parent.getHeight(), mPaint);
        }
    }
}
