package itest.kz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.databinding.ActivityAuthBinding;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.adapters.AuthFragmentPagerAdapter;
import itest.kz.view.fragments.LoginFragment;
import itest.kz.view.fragments.SignUpFragment;
import itest.kz.viewmodel.AuthViewModel;

public class AuthActivity extends AppCompatActivity
{
    private AuthViewModel authViewModel;
    public TextView tabTextFirst;
    public TextView tabTextSecond;

//    private SignUpFragment signUpFragment = new SignUpFragment();


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
//        finishAffinity();
//        System.exit(0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ActivityAuthBinding activityAuthBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_auth);
        authViewModel = new AuthViewModel(this);
        activityAuthBinding.setAuth(authViewModel);

        tabTextFirst = activityAuthBinding.tabTextFirst;
        tabTextSecond = activityAuthBinding.tabTextSecond;
//        FragmentHelper.openFragment(this, R.id.fl_fragment_container, signUpFragment);

        ViewPager viewPager =
                activityAuthBinding.viewpager;
        AuthFragmentPagerAdapter authFragmentPagerAdapter =
                new AuthFragmentPagerAdapter(this,getSupportFragmentManager());

        PageListener listener = new PageListener();
        viewPager.addOnPageChangeListener(listener);
        viewPager.setAdapter(authFragmentPagerAdapter);

//        TabLayout tabLayout = activityAuthBinding.slidingTabs;
//        tabLayout.setupWithViewPager(viewPager);
//
        tabTextFirst.setText(R.string.logIn);
        tabTextSecond.setText(R.string.signUp);

        tabTextFirst.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                viewPager.setCurrentItem(0);
            }
        });

        tabTextSecond.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                viewPager.setCurrentItem(1);
            }
        });
    }

    public class PageListener extends ViewPager.SimpleOnPageChangeListener
    {

        public void onPageSelected(int position)
        {
            if (position == 0)
            {
                tabTextFirst.setTextColor(Color.parseColor("#2DAAFC"));
                tabTextSecond.setTextColor(Color.parseColor("#DADADA"));
            }
            else
            {
                tabTextFirst.setTextColor(Color.parseColor("#DADADA"));
                tabTextSecond.setTextColor(Color.parseColor("#2DAAFC"));
            }

        }
    }

}
