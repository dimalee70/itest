package itest.kz.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.util.FragmentHelper;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.fragments.CertificationFragment;
import itest.kz.view.fragments.SubjectFragment;

public class SubjectViewModel
{
    private Context context;
    public Action ent;
    public Action certification;

    List<Fragment> mFragments;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    public SubjectViewModel(Context context)
    {
        this.context = context;


    }


}
