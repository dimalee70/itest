package itest.kz.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import itest.kz.model.Question;
import itest.kz.model.Test;
import itest.kz.model.Tests;
import itest.kz.view.fragments.TestFragment;

public class FullTestAdapter extends FragmentStatePagerAdapter
{

    public String question;
    public Tests test;
    public int NUM;
    public FullTestAdapter(FragmentManager fm, Tests test)
    {
        super(fm);
        this.test = test;
        this.NUM = test.getQuestions().size();

    }

    @Override
    public Fragment getItem(int i)
    {
        if (i >= 0 && i < NUM)
        {

            return TestFragment.newInstance(i, test.getQuestions());

        }
        return null;
    }

    @Override
    public int getCount()
    {
        if (test != null)
            return test.getQuestions().size();
        return 0;
    }
}
