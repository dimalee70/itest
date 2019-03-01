package itest.kz.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import itest.kz.model.Test;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.fragments.TestFragment;
import itest.kz.viewmodel.TestViewModel;

public class MyAdapter extends FragmentStatePagerAdapter
{
    public String question;
    public List<Test> test;
    public MyAdapter(FragmentManager fm, List<Test> test)
    {
        super(fm);
        this.test = test;
//            this.question = question;
    }

    @Override
    public Fragment getItem(int i)
    {

//            return ArrayListFragment.init(i);

        if (i >= 0 && i < 25)
        {
            return TestFragment.newInstance(i, test);
        }
        return null;
//            switch (i)
//            {
//                case 0:
//                    return  TestFragment.init(i);
////                    break;
//                case 1:
//                    return  TestFragment.init(i);
////                    break;
//            }
//            return null;
    }

    @Override
    public int getCount() {
        return 25;
    }
}