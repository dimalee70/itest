package itest.kz.view.fragments;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.SubjectFragmentActivityBinding;
import itest.kz.util.Constant;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.viewmodel.SubjectFragmentActivityViewModel;
import itest.kz.viewmodel.SubjectFragmentViewModel;

import static android.content.Context.MODE_PRIVATE;

public class SubjectFragmentActivity extends Fragment implements TabLayout.OnTabSelectedListener
{
    public SubjectFragmentActivityBinding subjectFragmentActivityBinding;
    public SubjectFragmentActivityViewModel subjectFragmentActivityViewModel;
    private String language;
    private String accessToken;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    List<Fragment> mFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        SharedPreferences lang = getActivity().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");

        subjectFragmentActivityBinding = DataBindingUtil.inflate(inflater, R.layout.subject_fragment_activity, container, false);
        subjectFragmentActivityViewModel = new SubjectFragmentActivityViewModel(getContext());
        subjectFragmentActivityBinding.setSubitem(subjectFragmentActivityViewModel);

        mFragments = new ArrayList<>();
        mFragments.add(new CertificationFragment());
        mFragments.add(new SubjectFragment());
        // do we need to implement databinding on each layout? I don't think so. but feel free to bind the layout if you want to.
        mViewPager = (ViewPager) subjectFragmentActivityBinding.vpFragmentsContainer;
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mFragments));




        mTabLayout = subjectFragmentActivityBinding.tlTabsContainer;
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(1).setText(R.string.fullEnt);
        mTabLayout.getTabAt(0).setText(R.string.forSubject);

//        setCustomFont();
        View firstTab = ((ViewGroup)
                mTabLayout.getChildAt(0)).getChildAt(0);
        View secondTab = ((ViewGroup)
                mTabLayout.getChildAt(0)).getChildAt(1);

        mTabLayout.addOnTabSelectedListener(this);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            firstTab.setBackground(getActivity().getDrawable(R.drawable.selected_tab_first));
            secondTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_second));
        }

        return subjectFragmentActivityBinding.getRoot();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        mViewPager.setCurrentItem(tab.getPosition());
//        System.out.println(tab.getPosition());
        View selectedTab = ((ViewGroup)
                mTabLayout.getChildAt(0)).getChildAt(tab.getPosition());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if(tab.getPosition() == 1)
            {
                View secondTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(0);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
                secondTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_first));
                selectedTab.setBackground(getActivity().getDrawable(R.drawable.selected_tab_second));
//                View secondTab = ((ViewGroup)
//                        mTabLayout.getChildAt(0)).getChildAt(1);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
            }
            else
            {
                View firstTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(1);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
                firstTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_second));
                selectedTab.setBackground(getActivity().getDrawable(R.drawable.selected_tab_first));
            }

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
