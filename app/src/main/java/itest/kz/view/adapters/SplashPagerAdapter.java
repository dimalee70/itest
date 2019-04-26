package itest.kz.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import itest.kz.view.fragments.OneSplashFragment;
import itest.kz.view.fragments.ThreeSplashFragment;
import itest.kz.view.fragments.TwoSplashFragment;

public class SplashPagerAdapter  extends FragmentPagerAdapter
{
    private static int NUM_ITEMS = 3;

    public SplashPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages.
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for a particular page.
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OneSplashFragment();
            case 1:
                return new TwoSplashFragment();
            case 2:
                return new ThreeSplashFragment();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return "Tab " + position;
//    }

}
