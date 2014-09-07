package tr.com.aliok.meetingroomkiosk.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tr.com.aliok.meetingroomkiosk.android.fragments.CurrentSessionFragment;
import tr.com.aliok.meetingroomkiosk.android.fragments.WeekCalendarFragment;

public class OverviewPagerAdapter extends FragmentPagerAdapter {

    public OverviewPagerAdapter(OverviewActivity overviewActivity, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // do not cache instances of the fragments:
        // see http://stackoverflow.com/questions/6976027/reusing-fragments-in-a-fragmentpageradapter
        // and http://stackoverflow.com/questions/9727173/support-fragmentpageradapter-holds-reference-to-old-fragments

        switch (position) {
            case 0:
                return CurrentSessionFragment.newInstance();
            case 1:
                return WeekCalendarFragment.newInstance();
        }

        throw new IllegalStateException();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
