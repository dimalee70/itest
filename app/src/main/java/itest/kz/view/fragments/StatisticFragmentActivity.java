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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.StatisticFragmentActivityBinding;
import itest.kz.util.Constant;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.viewmodel.StatisticFragmentActivityViewModel;
import itest.kz.viewmodel.StatisticViewModel;
import itest.kz.viewmodel.SubjectFragmentActivityViewModel;

import static android.content.Context.MODE_PRIVATE;

public class StatisticFragmentActivity extends Fragment  implements TabLayout.OnTabSelectedListener
{
    public StatisticFragmentActivityBinding statisticFragmentActivityBinding;
    public StatisticFragmentActivityViewModel statisticFragmentActivityViewModel;
    private String typeTest;
    private String language;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        statisticFragmentActivityBinding = DataBindingUtil.inflate(inflater, R.layout.statistic_fragment_activity, container, false);
        statisticFragmentActivityViewModel = new StatisticFragmentActivityViewModel(getContext());
        statisticFragmentActivityBinding.setStatistic(statisticFragmentActivityViewModel);

        this.typeTest = Constant.TYPEFULLTEST;

        SharedPreferences lang = getActivity().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");

        mFragments = new ArrayList<>();
        mFragments.add(new SubjectStatisticFragment());
        mFragments.add(new LectureStatisticFragment());
        mFragments.add(FullTestStatisticFragment.newInstance(typeTest));
        mViewPager = (ViewPager) statisticFragmentActivityBinding.vpFragmentsContainer;
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mFragments));

        mTabLayout = statisticFragmentActivityBinding.tlTabsContainer;
        mTabLayout.setupWithViewPager(mViewPager);


        mTabLayout.getTabAt(0).setText(R.string.subject);
        mTabLayout.getTabAt(1).setText(R.string.lecture);
        mTabLayout.getTabAt(2).setText(R.string.full);

        View firstTab = ((ViewGroup)
                mTabLayout.getChildAt(0)).getChildAt(0);
        View centerTab = ((ViewGroup)
                mTabLayout.getChildAt(0)).getChildAt(1);

        View secondTab = ((ViewGroup)
                mTabLayout.getChildAt(0)).getChildAt(2);

        for (int i = 0; i < mTabLayout.getTabCount(); i++)
        {

            TabLayout.Tab tab = mTabLayout.getTabAt(i);

            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getContext()).inflate(R.layout.tab_layout, mTabLayout, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            if(i == 1)
            {
                View view = (View)relativeLayout.findViewById(R.id.divider);
                view.setVisibility(View.GONE);


            }else if(i == 0)
            {
                tabTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            mTabLayout.getTabAt(i).setCustomView(relativeLayout);


        }


//        mTabLayout.getTabAt(2).getCustomView().setVisibility(View.GONE);
//        mTabLayout.getTabAt(2).select();

        mTabLayout.addOnTabSelectedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            firstTab.setBackground(getActivity().getDrawable(R.drawable.selected_tab_first));
            centerTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_center_statistic));
            secondTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_second_statistic));

        }

        sharedPreferences = getActivity().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        return statisticFragmentActivityBinding.getRoot();
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
                View firstTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(0);
                View secondTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(2);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
                firstTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_first));
                selectedTab.setBackground(getActivity().getDrawable(R.drawable.selected_tab_center));
                secondTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_second));

                TextView secondTextView = selectedTab.findViewById(R.id.tab_title);
                secondTextView.setTextColor(getResources().getColor(R.color.colorPrimary));

                TextView firstTextView = firstTab.findViewById(R.id.tab_title);
                firstTextView.setTextColor(getResources().getColor(R.color.colorWhite));

                TextView thirdTextView = secondTab.findViewById(R.id.tab_title);
                thirdTextView.setTextColor(getResources().getColor(R.color.colorWhite));

                View secondDivider = selectedTab.findViewById(R.id.divider);
                secondDivider.setVisibility(View.GONE);

                View firstDivider = firstTab.findViewById(R.id.divider);
                firstDivider.setVisibility(View.GONE);

                View thirdDivider = secondTab.findViewById(R.id.divider);
                thirdDivider.setVisibility(View.GONE);



//                View secondTab = ((ViewGroup)
//                        mTabLayout.getChildAt(0)).getChildAt(1);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
            }
            else if(tab.getPosition() == 2)
            {
                View firstTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(0);
                View centerTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(1);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
                firstTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_first_statistic));
                selectedTab.setBackground(getActivity().getDrawable(R.drawable.selected_tab_second));
                centerTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_center_statistic_left));

                TextView secondTextView = selectedTab.findViewById(R.id.tab_title);
                secondTextView.setTextColor(getResources().getColor(R.color.colorPrimary));

                TextView firstTextView = firstTab.findViewById(R.id.tab_title);
                firstTextView.setTextColor(getResources().getColor(R.color.colorWhite));

                TextView thirdTextView = centerTab.findViewById(R.id.tab_title);
                thirdTextView.setTextColor(getResources().getColor(R.color.colorWhite));

                View secondDivider = selectedTab.findViewById(R.id.divider);
                secondDivider.setVisibility(View.GONE);

                View firstDivider = centerTab.findViewById(R.id.divider);
                firstDivider.setVisibility(View.VISIBLE);

                View thirdDivider = firstTab.findViewById(R.id.divider);
                thirdDivider.setVisibility(View.GONE);
            }
            else
            {
                View centerTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(1);
                View secondTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(2);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
                centerTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_center_right));
                selectedTab.setBackground(getActivity().getDrawable(R.drawable.selected_tab_first));
                secondTab.setBackground(getActivity().getDrawable(R.drawable.unselected_tab_second_left));

                TextView secondTextView = selectedTab.findViewById(R.id.tab_title);
                secondTextView.setTextColor(getResources().getColor(R.color.colorPrimary));

                TextView firstTextView = centerTab.findViewById(R.id.tab_title);
                firstTextView.setTextColor(getResources().getColor(R.color.colorWhite));

                TextView thirdTextView = secondTab.findViewById(R.id.tab_title);
                thirdTextView.setTextColor(getResources().getColor(R.color.colorWhite));

                View secondDivider = selectedTab.findViewById(R.id.divider);
                secondDivider.setVisibility(View.GONE);

                View firstDivider = centerTab.findViewById(R.id.divider);
                firstDivider.setVisibility(View.GONE);

                View thirdDivider = secondTab.findViewById(R.id.divider);
                thirdDivider.setVisibility(View.VISIBLE);
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
