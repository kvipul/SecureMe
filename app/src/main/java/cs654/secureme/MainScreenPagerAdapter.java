package cs654.secureme;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by sunil on 3/4/16.
 */
public class MainScreenPagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;
    CharSequence mTitles[];

    public MainScreenPagerAdapter(FragmentManager fm, CharSequence titles[], int numberOfTabs) {
        super(fm);

        this.mTitles = titles;
        this.mNumberOfTabs = numberOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 1: return new F2();
            case 2: return new F3();
//            case 3: return new F3();
            default: return new F1();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
