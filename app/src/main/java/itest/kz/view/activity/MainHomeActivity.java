package itest.kz.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import itest.kz.R;
import itest.kz.databinding.ActivityMainHomeBinding;
import itest.kz.view.adapters.AuthFragmentPagerAdapter;
import itest.kz.viewmodel.MainHomeViewModel;

public class MainHomeActivity extends AppCompatActivity
{
    private ActivityMainHomeBinding activityMainHomeBinding;
    private MainHomeViewModel mainHomeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityMainHomeBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main_home);
        mainHomeViewModel = new MainHomeViewModel(this);
        activityMainHomeBinding.setMainHome(mainHomeViewModel);

        ViewPager viewPager =
                activityMainHomeBinding.viewpager;
        AuthFragmentPagerAdapter authFragmentPagerAdapter =
                new AuthFragmentPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(authFragmentPagerAdapter);

        TabLayout tabLayout = activityMainHomeBinding.slidingTabs;
        tabLayout.setupWithViewPager(viewPager);
    }
}
