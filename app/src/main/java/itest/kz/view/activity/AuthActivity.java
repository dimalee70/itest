package itest.kz.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.databinding.ActivityAuthBinding;
import itest.kz.util.FragmentHelper;
import itest.kz.view.adapters.AuthFragmentPagerAdapter;
import itest.kz.view.fragments.LoginFragment;
import itest.kz.view.fragments.SignUpFragment;
import itest.kz.viewmodel.AuthViewModel;

public class AuthActivity extends AppCompatActivity
{
    private AuthViewModel authViewModel;
//    private SignUpFragment signUpFragment = new SignUpFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityAuthBinding activityAuthBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_auth);
        authViewModel = new AuthViewModel(this);
        activityAuthBinding.setAuth(authViewModel);

//        FragmentHelper.openFragment(this, R.id.fl_fragment_container, signUpFragment);

        ViewPager viewPager =
                activityAuthBinding.viewpager;
        AuthFragmentPagerAdapter authFragmentPagerAdapter =
                new AuthFragmentPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(authFragmentPagerAdapter);

        TabLayout tabLayout = activityAuthBinding.slidingTabs;
        tabLayout.setupWithViewPager(viewPager);


//        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        mainViewModel = new MainViewModel(this);
//        activityMainBinding.setMainViewModel(mainViewModel);
    }

}
