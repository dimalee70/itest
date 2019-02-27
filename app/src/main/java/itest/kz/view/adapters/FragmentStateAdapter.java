package itest.kz.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

import itest.kz.view.fragments.TestFragment;

public class FragmentStateAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<Integer> page_indexer;
    private int numbersOfPages;

    public FragmentStateAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public FragmentStateAdapter (FragmentManager fm, int numbersOfPages)
    {
        super(fm);
        this.numbersOfPages = numbersOfPages;
        page_indexer = new ArrayList<>();
        for (int i = 1; i <= numbersOfPages; i++)
        {
            page_indexer.add(i);
        }

    }


    @Override
    public Fragment getItem(int i) {
        Integer index = page_indexer.get(i);
        return TestFragment.newInstance(index);
    }

    @Override
    public int getCount()
    {
        return page_indexer.size();
    }
    @Override
    public int getItemPosition(Object object)
    {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }
}
