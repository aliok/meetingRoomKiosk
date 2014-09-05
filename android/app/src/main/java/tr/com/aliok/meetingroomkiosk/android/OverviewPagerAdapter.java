package tr.com.aliok.meetingroomkiosk.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tr.com.aliok.meetingroomkiosk.android.fragments.CurrentSessionFragment;
import tr.com.aliok.meetingroomkiosk.android.fragments.DayCalendarFragment;
import tr.com.aliok.meetingroomkiosk.android.fragments.WeekCalendarFragment;

public class OverviewPagerAdapter extends FragmentPagerAdapter {

    private CurrentSessionFragment mCurrentSessionFragment;
    private DayCalendarFragment mDayCalendarFragment;
    private WeekCalendarFragment mWeekCalendarFragment;

    public OverviewPagerAdapter(FragmentManager fm) {
        super(fm);

        this.mCurrentSessionFragment = CurrentSessionFragment.newInstance(null, null);
        this.mDayCalendarFragment = DayCalendarFragment.newInstance(null, null);
        this.mWeekCalendarFragment = WeekCalendarFragment.newInstance(null, null);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mCurrentSessionFragment;
            case 1:
                return mDayCalendarFragment;
            case 2:
                return mWeekCalendarFragment;
        }

        throw new IllegalStateException();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
