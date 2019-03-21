package itest.kz.view.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import itest.kz.R;
import itest.kz.view.fragments.LoginFragment;
import itest.kz.view.fragments.SignUpFragment;

public class AuthFragmentPagerAdapter extends FragmentPagerAdapter
{
    private Context context;

    public AuthFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return new LoginFragment();
            case 1:
                return new SignUpFragment();
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
                return context.getString(R.string.logInKaz);
            case 1:
                return context.getString(R.string.signUpKaz);
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 2;
    }
}
