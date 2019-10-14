package itest.kz.view.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import itest.kz.R;
import itest.kz.view.activity.SubjectActivity;

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
        switch (i)
        {
            case 0:
                return  null;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return context.getString(R.string.logIn);
//            case 1:
//                return context.getString(R.string.signUpKaz);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
