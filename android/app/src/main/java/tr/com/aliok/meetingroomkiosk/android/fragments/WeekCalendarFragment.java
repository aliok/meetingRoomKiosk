package tr.com.aliok.meetingroomkiosk.android.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatTextView;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

import tr.com.aliok.meetingroomkiosk.android.R;


public class WeekCalendarFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private WeekCalendarMetrics mWeekCalendarMetrics;

    private RelativeLayout mTimeColumn;
    private TextView mTimeColumnHeaderTextView;

    private final List<Integer> dayColumnHeaderIds = Arrays.asList(
            R.id.day0ColumnHeader,
            R.id.day1ColumnHeader,
            R.id.day2ColumnHeader,
            R.id.day3ColumnHeader,
            R.id.day4ColumnHeader
    );

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentRoot = inflater.inflate(R.layout.fragment_week_calendar, container, false);

        mTimeColumn = (RelativeLayout) fragmentRoot.findViewById(R.id.timeColumn);

        final WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        mWeekCalendarMetrics = new WeekCalendarMetrics(fragmentRoot, windowManager);

        mTimeColumnHeaderTextView = (TextView) fragmentRoot.findViewById(R.id.timeColumnHeaderTextView);

        mDayColumHeaders = Lists.transform(this.dayColumnHeaderIds, new Function<Integer, FlatTextView>() {
            @Override
            public FlatTextView apply(Integer input) {
                return (FlatTextView) fragmentRoot.findViewById(input);
            }
        });

        createHourColumn();

        adjustDayColumnHeadersStyles();

        return fragmentRoot;
    }

    private void adjustDayColumnHeadersStyles() {
        for (FlatTextView columnHeader : mDayColumHeaders) {
            columnHeader.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mWeekCalendarMetrics.heightOfHeader));
        }
    }

    private void createHourColumn() {
        mTimeColumn.setLayoutParams(new LinearLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, ViewGroup.LayoutParams.WRAP_CONTENT));

        mTimeColumnHeaderTextView.setLayoutParams(new RelativeLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, mWeekCalendarMetrics.heightOfHeader));

        for (int i = mWeekCalendarMetrics.DAY_START_HOUR; i < mWeekCalendarMetrics.DAY_END_HOUR; i++) {
            {
                final FlatTextView textView = createHourLabelTextView();

                textView.setText(String.format("%02d:00", i));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, Math.round(mWeekCalendarMetrics.heightOfEachHourPart));
                params.leftMargin = 0;
                params.topMargin = Math.round(mWeekCalendarMetrics.heightOfEachHour * (i - mWeekCalendarMetrics.DAY_START_HOUR) + mWeekCalendarMetrics.heightOfHeader);

                mTimeColumn.addView(textView, params);
            }
            {
                final FlatTextView textView = createHourLabelTextView();

                textView.setText(String.format("%02d:30", i));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mWeekCalendarMetrics.widthOfTimeColumn, Math.round(mWeekCalendarMetrics.heightOfEachHourPart));
                params.leftMargin = 0;
                params.topMargin = Math.round(mWeekCalendarMetrics.heightOfEachHour * (i - mWeekCalendarMetrics.DAY_START_HOUR) + mWeekCalendarMetrics.heightOfEachHourPart + mWeekCalendarMetrics.heightOfHeader);

                mTimeColumn.addView(textView, params);
            }
        }
    }

    private FlatTextView createHourLabelTextView() {
        final FlatTextView textView = new FlatTextView(getActivity());

        textView.getAttributes().setTheme(FlatUI.SEA, getResources());
        textView.getAttributes().setBorderWidth(0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(mWeekCalendarMetrics.sizeOfHourText));

        textView.onThemeChange();       // need to trigger this one to make sure FlatTextView reads attributes set!
        return textView;
    }

    private static class WeekCalendarMetrics {
        private static final int HEIGHT_OF_HEADER_IN_DP = 30;         // in dp units
        private static final int HEIGHT_OF_EACH_HOUR_IN_DP = 45;      // in dp units

        private static final int WIDTH_OF_TIME_COLUMN_IN_DP = 70;     // in dp units

        private static final int DAY_START_HOUR = 7;
        private static final int DAY_END_HOUR = 19;

        // 8:00 - 18:00 will be shown by default. I mean scroll of calendar will be set like that
        private static final int INITIAL_HOURS_TO_SHOW_ON_CALENDAR = 18 - 8;


        // now real pixel values
        private int heightOfHeader;
        private float heightOfEachHour;
        private float heightOfEachHourPart;

        private int widthOfTimeColumn;

        private float offsetOf8Oclock;

        private float sizeOfHourText;

        private DisplayMetrics displayMetrics = new DisplayMetrics();

        private WeekCalendarMetrics(View fragmentView, WindowManager windowManager) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            float density = displayMetrics.density;

            heightOfHeader = Math.round(HEIGHT_OF_HEADER_IN_DP * density);
            heightOfEachHour = HEIGHT_OF_EACH_HOUR_IN_DP * density;             // do not round these as alignment will be broken
            heightOfEachHourPart = heightOfEachHour / 2;                        // do not round these as alignment will be broken

            widthOfTimeColumn = Math.round(WIDTH_OF_TIME_COLUMN_IN_DP * density);

            offsetOf8Oclock = heightOfEachHour * (8 - DAY_START_HOUR);

            // %90 of each hourPart
            sizeOfHourText = (float) (heightOfEachHourPart - (HEIGHT_OF_EACH_HOUR_IN_DP / 2) * 0.1 * density);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onWeekCalendarFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onWeekCalendarFragmentInteraction(Uri uri);
    }

}
