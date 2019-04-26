package itest.kz.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rd.PageIndicatorView;

import itest.kz.R;
import itest.kz.databinding.ActivitySplashScreensBinding;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.view.adapters.SplashPagerAdapter;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.viewmodel.SplashScreensViewModel;

public class SplashScreensActivity extends AppCompatActivity
{
    private ActivitySplashScreensBinding activitySplashScreensBinding;
    private SplashScreensViewModel splashScreensViewModel;
    private ViewPager mPager;
    private PageIndicatorView pageIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activitySplashScreensBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_splash_screens);
        splashScreensViewModel = new SplashScreensViewModel(this);
        activitySplashScreensBinding
                .setSpash(splashScreensViewModel);
        mPager = activitySplashScreensBinding
                .vpFragmentsContainer;

//        pageIndicatorView = activitySplashScreensBinding
//                .pageIndicatorView;
//                findViewById(R.id.pageIndicatorView);
//        pageIndicatorView.setCount(3); // specify total count of indicators
//        pageIndicatorView.setSelection(1);



        mPager.setAdapter(new SplashPagerAdapter(getSupportFragmentManager()));
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position)
            {
                if (position == 2)
                {
                    activitySplashScreensBinding
                            .loginCardview
                            .setVisibility(View.VISIBLE);
//                    activitySplashScreensBinding
//                            .textPass
//                            .setVisibility(View.GONE);

//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(7,80,7,0);
//                    activitySplashScreensBinding
//                            .tabDots
//                            .setLayoutParams(params);
                }
                else
                {
                    activitySplashScreensBinding
                            .loginCardview
                            .setVisibility(View.GONE);
                    activitySplashScreensBinding
                            .textPass
                            .setVisibility(View.VISIBLE);
                }
//                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });

        TabLayout tabLayout = activitySplashScreensBinding.tabDots;
        tabLayout.setupWithViewPager(mPager, true);
//
//        for(int i=0; i < tabLayout.getTabCount(); i++) {
//            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
//            p.setMargins(0, 0, 0, 0);
//            tab.requestLayout();
//        }


//        mPager.setAdapter(new MyAdapter(getSupportFragmentManager(), tests, selectedSubject, resultTag, typeTest));


//
    }


}
