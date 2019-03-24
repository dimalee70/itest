package itest.kz.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainHomeActivityPagerAdapter extends FragmentPagerAdapter
{
    private Context context;
    public MainHomeActivityPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i)
    {
//        switch (i)
//        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
