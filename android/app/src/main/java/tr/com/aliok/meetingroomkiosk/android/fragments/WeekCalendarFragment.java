package tr.com.aliok.meetingroomkiosk.android.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatTextView;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import tr.com.aliok.meetingroomkiosk.android.R;
import tr.com.aliok.meetingroomkiosk.android.model.Event;
import tr.com.aliok.meetingroomkiosk.android.model.PeriodSchedule;
import tr.com.aliok.meetingroomkiosk.android.util.DateTimeUtils;
import tr.com.aliok.meetingroomkiosk.model.api.PeriodType;

/**
 * A fragment to show a weekly calendar.
 * <p/>
 * Use newInstance method to initialize it.
 */
public class WeekCalendarFragment extends Fragment {

    // ------ component ids to be used in bindings -----------------
    private final static List<Integer> DAY_COLUMN_IDS = Arrays.asList(
            R.id.day0Column,
            R.id.day1Column,
            R.id.day2Column,
            R.id.day3Column,
            R.id.day4Column
    );
    private final static List<Integer> DAY_COLUMN_HEADER_IDS = Arrays.asList(
            R.id.day0ColumnHeader,
            R.id.day1ColumnHeader,
            R.id.day2ColumnHeader,
            R.id.day3ColumnHeader,
            R.id.day4ColumnHeader
    );

    // ------ activity and metrics ----------------
    private ActivityContract activityContract;
    private WeekCalendarMetrics mWeekCalendarMetrics;

    // ------ component bindings -----------------
    private RelativeLayout mTimeColumn;
    private TextView mTimeColumnHeaderTextView;
    private List<RelativeLayout> mDayColumns;
    private List<FlatTextView> mDayColumHeaders;

    public static WeekCalendarFragment newInstance() {
        WeekCalendarFragment fragment = new WeekCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public WeekCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentRoot = inflater.inflate(R.layout.fragment_week_calendar, container, false);

        final WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);


        bindComponents(fragmentRoot);
        createFields(fragmentRoot, windowManager);

        // initialize fragment view with empty data
        createHourColumn();
        adjustDayColumnHeadersStyles();

        doRefreshViewWithDataFromActivity();

        return fragmentRoot;
    }

    private void bindComponents(final View fragmentRoot) {
        // bind components
        mTimeColumn = (RelativeLayout) fragmentRoot.findViewById(R.id.timeColumn);

        mTimeColumnHeaderTextView = (TextView) fragmentRoot.findViewById(R.id.timeColumnHeaderTextView);

        mDayColumHeaders = Lists.transform(DAY_COLUMN_HEADER_IDS, new Function<Integer, FlatTextView>() {
            @Override
            public FlatTextView apply(Integer input) {
                return (FlatTextView) fragmentRoot.findViewById(input);
            }
        });

        mDayColumns = Lists.transform(DAY_COLUMN_IDS, new Function<Integer, RelativeLayout>() {
            @Override
            public RelativeLayout apply(Integer input) {
                return (RelativeLayout) fragmentRoot.findViewById(input);
            }
        });
    }

    private void createFields(View fragmentRoot, WindowManager windowManager) {
        // create other fields
        mWeekCalendarMetrics = new WeekCalendarMetrics(fragmentRoot, windowManager);
    }

    private void createHourColumn() {
        mTimeColumn.setLayoutParams(new LinearLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, ViewGroup.LayoutParams.WRAP_CONTENT));

        mTimeColumnHeaderTextView.setLayoutParams(new RelativeLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, mWeekCalendarMetrics.heightOfHeader));

        for (int i = mWeekCalendarMetrics.DAY_START_HOUR; i < mWeekCalendarMetrics.DAY_END_HOUR; i++) {
            {
                final FlatTextView textView = createHourLabelTextView();

                textView.setText(String.format("%02d:00", i));
                textView.setIncludeFontPadding(false);
                textView.setGravity(Gravity.TOP);
                textView.setPadding(0, -(int) (mWeekCalendarMetrics.heightOfEachHour * 0.05), 0, 0);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, mWeekCalendarMetrics.heightOfEachHourPart);
                params.leftMargin = 0;
                params.topMargin = mWeekCalendarMetrics.heightOfEachHour * (i - mWeekCalendarMetrics.DAY_START_HOUR) + mWeekCalendarMetrics.heightOfHeader;

                mTimeColumn.addView(textView, params);
            }
            {
                final FlatTextView textView = createHourLabelTextView();

                textView.setText(String.format("%02d:30", i));
                textView.setIncludeFontPadding(false);
                textView.setGravity(Gravity.TOP);
                textView.setPadding(0, -(int) (mWeekCalendarMetrics.heightOfEachHour * 0.05), 0, 0);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, mWeekCalendarMetrics.heightOfEachHourPart);
                params.leftMargin = 0;
                params.topMargin = mWeekCalendarMetrics.heightOfEachHour * (i - mWeekCalendarMetrics.DAY_START_HOUR) + mWeekCalendarMetrics.heightOfEachHourPart + mWeekCalendarMetrics.heightOfHeader;

                mTimeColumn.addView(textView, params);
            }
        }
    }

    private FlatTextView createHourLabelTextView() {
        final FlatTextView textView = new FlatTextView(getActivity());

        textView.getAttributes().setTheme(FlatUI.SEA, getResources());
        textView.getAttributes().setBorderWidth(0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mWeekCalendarMetrics.sizeOfHourText);

        textView.onThemeChange();       // need to trigger this one to make sure FlatTextView reads attributes set!
        return textView;
    }

    private void adjustDayColumnHeadersStyles() {
        for (FlatTextView columnHeader : mDayColumHeaders) {
            // adjust day column header heights with the defined one in metrics
            columnHeader.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mWeekCalendarMetrics.heightOfHeader));
        }
    }

    private void doRefreshViewWithDataFromActivity() {
        final PeriodSchedule weekSchedule = activityContract.getWeekSchedule();

        // perhaps the fragment is created but the data request of activity is not done yet!
        // so, check if activity received the data
        if (weekSchedule != null) {
            Validate.isTrue(weekSchedule.getPeriod().getPeriodType().equals(PeriodType.WEEK));

            //arrange header texts!
            final TreeSet<Date> daysInWeek = DateTimeUtils.getDaysInDateTimeRange(weekSchedule.getPeriod().getStartDateTime(), weekSchedule.getPeriod().getEndDateTime());
            Validate.isTrue(daysInWeek.size() == 5);        // we don't care about weekends. actually, we should not receive them from the server anyway!
            int i = 0;
            for (Date date : daysInWeek) {
                final String dateStr = DateTimeUtils.getLongDateStr(date);
                this.mDayColumHeaders.get(i).setText(dateStr);
                i++;
            }

            // add events
            for (Event event : weekSchedule.getSchedule().getEvents()) {
                addEvent(event);
            }
        } else {
            // TODO should we display "no events" ?
        }
    }

    private void addEvent(Event event) {
        final float eventStartHourInFloat = DateTimeUtils.getHourInFloat(event.getEventStart());
        final float eventEndHourInFloat = DateTimeUtils.getHourInFloat(event.getEventEnd());
        final int dayOfWeek = DateTimeUtils.getDayOfWeekZeroIndexed(event.getEventStart());
        if (dayOfWeek >= 5)     // we don't show weekend events
            return;

        // ---------------- button creation -----------------------//
        final FlatButton flatButton = new FlatButton(getActivity());

        flatButton.getAttributes().setTheme(FlatUI.GRASS, getResources());
        flatButton.getAttributes().setBorderWidth(0);
        // set it like that since that depends already on height of each hour
        flatButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mWeekCalendarMetrics.sizeOfEventTitleText);

        // trigger this one. otherwise attributes will not be picked up by the component
        flatButton.onThemeChange();

        flatButton.setPadding(0, 0, 0, 0);

        // ---------------- set data and the listener -----------------------//
        flatButton.setText(DateTimeUtils.getTimeRangeStr(event.getEventStart(), event.getEventEnd()) + " " + event.getEventTitle());
        // TODO listener

        // ---------------- set location and size -----------------------//

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                Math.round((eventEndHourInFloat - eventStartHourInFloat) * mWeekCalendarMetrics.heightOfEachHour) - 2 * mWeekCalendarMetrics.eventSeperatorMargin
        );
        params.leftMargin = mWeekCalendarMetrics.eventSeperatorMargin;
        params.rightMargin = mWeekCalendarMetrics.eventSeperatorMargin;
        params.topMargin = Math.round(mWeekCalendarMetrics.heightOfEachHour * (eventStartHourInFloat - mWeekCalendarMetrics.DAY_START_HOUR) + mWeekCalendarMetrics.heightOfHeader) + mWeekCalendarMetrics.eventSeperatorMargin;

        // ---------------- add button -----------------------//
        mDayColumns.get(dayOfWeek).addView(flatButton, params);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityContract = (ActivityContract) activity;
            activityContract.setWeekCalendarFragment(this);

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ActivityContract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityContract.setWeekCalendarFragment(null);
        activityContract = null;
    }

    public void dataArrived() {
        doRefreshViewWithDataFromActivity();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     */
    public interface ActivityContract {
        void setWeekCalendarFragment(WeekCalendarFragment weekCalendarFragment);

        public void onEventSelected(Event event);

        public PeriodSchedule getWeekSchedule();
    }

    private class WeekCalendarMetrics {
        private final int HEIGHT_OF_HEADER_IN_DP = getResources().getInteger(R.integer.week_calendar_height_of_header_in_dp);         // in dp units
        private final int HEIGHT_OF_EACH_HOUR_PART_IN_DP = getResources().getInteger(R.integer.week_calendar_height_of_each_hour_part_in_dp);      // in dp units

        private final int WIDTH_OF_TIME_COLUMN_IN_DP = getResources().getInteger(R.integer.week_calendar_width_of_time_column_in_dp);     // in dp units

        private final int DAY_START_HOUR = getResources().getInteger(R.integer.week_calendar_day_start_hour);
        private final int DAY_END_HOUR = getResources().getInteger(R.integer.week_calendar_day_end_hour);

        // now real pixel values
        private int heightOfHeader;
        private int heightOfEachHour;
        private int heightOfEachHourPart;

        private int widthOfTimeColumn;

        private int sizeOfHourText;
        private int sizeOfEventTitleText;

        private int eventSeperatorMargin;       // some margin to make the events separated

        private WeekCalendarMetrics(View fragmentView, WindowManager windowManager) {
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            heightOfHeader = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_OF_HEADER_IN_DP, displayMetrics));
            heightOfEachHourPart = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_OF_EACH_HOUR_PART_IN_DP, displayMetrics));
            heightOfEachHour = heightOfEachHourPart * 2;

            widthOfTimeColumn = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_OF_TIME_COLUMN_IN_DP, displayMetrics));

            // %90 of each hourPart
            sizeOfHourText = Math.round(heightOfEachHourPart * 0.9f);

            sizeOfEventTitleText = Math.round(sizeOfHourText * 0.7f);

            eventSeperatorMargin = Math.round(heightOfEachHour * 0.1f);
        }

    }

}
