package tr.com.aliok.meetingroomkiosk.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tr.com.aliok.meetingroomkiosk.android.fragments.CurrentSessionFragment;
import tr.com.aliok.meetingroomkiosk.android.fragments.WeekCalendarFragment;

public class OverviewPagerAdapter extends FragmentPagerAdapter {

    private CurrentSessionFragment mCurrentSessionFragment;
    private WeekCalendarFragment mWeekCalendarFragment;

    public OverviewPagerAdapter(FragmentManager fm) {
        super(fm);

        // TODO : is creating them in advance good?

        this.mCurrentSessionFragment = CurrentSessionFragment.newInstance();
        this.mWeekCalendarFragment = WeekCalendarFragment.newInstance();
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mCurrentSessionFragment;
            case 1:
                return mWeekCalendarFragment;
        }

        throw new IllegalStateException();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
