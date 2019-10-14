package itest.kz.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityMainHomeBinding;
import itest.kz.model.Lecture;
import itest.kz.model.Profile;
import itest.kz.model.ProfileResponse;
import itest.kz.network.UserService;
import itest.kz.util.AppUtils;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.adapters.AuthFragmentPagerAdapter;
import itest.kz.view.adapters.MainHomeActivityPagerAdapter;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.customviews.BottomBarAdapter;
import itest.kz.view.customviews.NoSwipePager;
import itest.kz.view.fragments.AgreementFragment;
import itest.kz.view.fragments.CertificationFragment;
import itest.kz.view.fragments.LanguageChangeFragment;
import itest.kz.view.fragments.PasswordChangeFragment;
import itest.kz.view.fragments.ProfileFragment;
import itest.kz.view.fragments.ProfileInfoFragment;
import itest.kz.view.fragments.StatisticFragmentActivity;
import itest.kz.view.fragments.SubjectFragment;
import itest.kz.view.fragments.SubjectFragmentActivity;
import itest.kz.viewmodel.MainHomeViewModel;

public class MainHomeActivity extends AppCompatActivity
{
    private ActivityMainHomeBinding activityMainHomeBinding;
    private MainHomeViewModel mainHomeViewModel;
    private SharedPreferences sharedPreferences;
    private BottomNavigationViewEx bottomNavigationView;
    private String accessToken;
    private ProfileResponse profileResponse;
    private Toolbar toolbar;
    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private String language;
    private NoSwipePager viewPager;
    private BottomBarAdapter pagerAdapter;
    private String statisticTag;
    public static boolean shouldAllowBack = true;
    public static boolean isServerErrorDialogShown = false;

    SubjectFragmentActivity frag1 = new SubjectFragmentActivity();
    StatisticFragmentActivity frag2 = new StatisticFragmentActivity();
    HomeActivity frag3 = new HomeActivity();


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(Build.VERSION.SDK_INT > 11)
        {
            menu.findItem(R.id.menu_github).setVisible(false);
            menu.findItem(R.id.menu_github1).setVisible(false);
            menu.findItem(R.id.menu_github2).setVisible(false);
            menu.findItem(R.id.menu_save).setVisible(false);
            menu.findItem(R.id.menu_logout).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        final FragmentManager fragmentManager = getSupportFragmentManager();
        SharedPreferences lang = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        statisticTag = getIntent().getExtras().getString(Constant.STATISTIC_TAG, "");
        language = lang.getString(Constant.LANG, "kz");

        AppUtils.setLocale((language.equals(Constant.KZ)) ? "en":"ru",this);
        activityMainHomeBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main_home);

        setAccessToken();
        mainHomeViewModel = new MainHomeViewModel(this, accessToken);
        activityMainHomeBinding.setMainHome(mainHomeViewModel);

        sharedPreferences = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);


        bottomNavigationView = activityMainHomeBinding.bottomNavigationView;

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        final View iconView = menuView.getChildAt(0).findViewById(android.support.design.R.id.icon);
        final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
        iconView.setLayoutParams(layoutParams);

        final View iconView1 = menuView.getChildAt(2).findViewById(android.support.design.R.id.icon);
        final ViewGroup.LayoutParams layoutParams1 = iconView.getLayoutParams();
        final DisplayMetrics displayMetrics1 = getResources().getDisplayMetrics();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics1);
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics1);
        iconView1.setLayoutParams(layoutParams1);

        viewPager = activityMainHomeBinding.viewpager;
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPagingEnabled(false);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(frag1);
        pagerAdapter.addFragments(frag2);
        pagerAdapter.addFragments(frag3);

        viewPager.setAdapter(pagerAdapter);
        if(statisticTag != null && statisticTag.equals(Constant.STATISTIC_TAG))
            viewPager.setCurrentItem(1);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                    {

//                        Fragment fragment = null;
//                        Intent intent = null;
                        switch (menuItem.getItemId())
                        {
                            case R.id.item_test:

                                viewPager.setCurrentItem(0, false);
                                return true;
//                                fragment = new SubjectFragmentActivity();
//                                intent = new Intent(getBaseContext(),SubjectActivity.class);
//                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                startActivity(intent);
//                                break;

                            case R.id.item_statistic:
                                viewPager.setCurrentItem(1, false);

                                return true;
//                                fragment = new StatisticFragmentActivity();
//                                intent = new Intent(getBaseContext(),StatisticActivity.class);
//                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                startActivity(intent);
//                                break;

                            case R.id.item_user:
                                viewPager.setCurrentItem(2, false);
                                return true;
//                                fragment = new HomeActivity();
//                                intent = new Intent(getBaseContext(),HomeActivity.class);
//                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                startActivity(intent);
//                                break;

                        }


                        return false;
//                        return loadFragment(fragment);
                    }
                }
        );
//         Set default selection
        bottomNavigationView.setSelectedItemId(R.id.item_test);
    }

//    public boolean loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.viewpager, fragment)
//                    .addToBackStack(null)
//                    .commit();
//            return true;
//        }
//        return false;
//    }

//    public boolean loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.viewpager, fragment)
//                    .addToBackStack(null)
//                    .commit();
//            return true;
//        }
//        return false;
//    }

    @Override
    public void onBackPressed()
    {
//        setNavigationVisibiltity(true);
        if (shouldAllowBack) {
            setNavigationVisibiltity(true);
            Fragment currentFragment = currentFragment(R.id.frame_layout);
            Fragment nodeFragment = currentFragment(R.id.frame2);
            Fragment lectureFragment = currentFragment(R.id.frame3);
            Fragment fragment = currentFragment(R.id.frame);

//        System.out.println(fragment);
//        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mViewPager.getCurrentItem());


            try {
                if
                ((currentFragment instanceof ProfileInfoFragment
                        || currentFragment instanceof LanguageChangeFragment
                        || currentFragment instanceof AgreementFragment
                        || currentFragment instanceof PasswordChangeFragment
                        && viewPager.getCurrentItem() == 2) ||
                        (fragment instanceof MaterialsActivity
                                || nodeFragment instanceof NodeByNodeActivity
                                || lectureFragment instanceof LectureActivity)
                                && viewPager.getCurrentItem() == 0) {
                    if (
                            (fragment instanceof MaterialsActivity
                                    || nodeFragment instanceof NodeByNodeActivity
                                    || lectureFragment instanceof LectureActivity)
                                    && viewPager.getCurrentItem() == 0
                    ) {
                        if (lectureFragment instanceof LectureActivity) {
                            try {
                                //                super.onBackPressed();
                                getSupportFragmentManager().popBackStack(NodeByNodeActivity.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                findViewById(R.id.viewDisableLayout123).setVisibility(View.GONE);
                            } catch (Exception e) {
                                System.out.println("Error");
                            }
                        } else if (nodeFragment instanceof NodeByNodeActivity) {
                            //                super.onBackPressed();
                            try {
                                getSupportFragmentManager().popBackStack(MaterialsActivity.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            } catch (Exception e) {
                            }

                        } else if (fragment instanceof MaterialsActivity) {
                            //                super.onBackPressed();
                            try {
                                getSupportFragmentManager().popBackStack(CertificationFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            } catch (Exception e) {
                            }
                        }
                    } else if (currentFragment instanceof ProfileInfoFragment
                            || currentFragment instanceof LanguageChangeFragment
                            || currentFragment instanceof AgreementFragment
                            || currentFragment instanceof PasswordChangeFragment
                            && viewPager.getCurrentItem() == 2) {
                        try {
                            getSupportFragmentManager().popBackStack();
                        } catch (Exception e) {
                        }

                    }


//            Fragment fragment1 = getSupportFragmentManager().findFragmentById();
//            Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + ViewPager.getCurrentItem());
//            // based on the current position you can then cast the page to the correct Fragment class and call some method inside that fragment to reload the data:
//            if (0 == ViewPager.getCurrentItem() && null != fragment) {
//                ((TabFragment1)fragment).reloadFragmentData();
//            }


                } else
                    finishAffinity();
//        else {
//            finishAffinity();
//        }
//
//        if(
//                (currentFragment instanceof ProfileInfoFragment
//            || currentFragment instanceof LanguageChangeFragment
//            || currentFragment instanceof AgreementFragment
//            || currentFragment instanceof PasswordChangeFragment)
//            && viewPager.getCurrentItem() == 2
//        )
//        {
////            System.out.println("PopBackStack");
//
//        }
//
////        if (currentFragment instanceof ProfileFragment && viewPager.getCurrentItem() == 2)
////        {
////
////        }
//        else
//        {
////            System.out.println("Finish");
//            finishAffinity();
//
//        }

                fragment = null;
                currentFragment = null;
                nodeFragment = null;
                lectureFragment = null;
            } catch (Exception e) {
                System.out.println("Error");
            }
        }



    }

//    frame
//    R.id.frame_layout
    public Fragment currentFragment(int id){
        return getSupportFragmentManager().findFragmentById(id);
    }

    public void setAccessToken()
    {
        this.accessToken = getIntent().getStringExtra(Constant.ACCESS_TOKEN);
        if (accessToken == null || accessToken.equals(""))
        {
            finish();

        }
        else
        {
            if (sharedPreferences != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putString(Constant.ACCESS_TOKEN, accessToken);
                editor.apply();
                editor.commit();
            }

        }
    }

    public void setNavigationVisibiltity(boolean b)
    {
        if (!b)
        {
            bottomNavigationView.setVisibility(View.GONE);
        }
        else
        {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }
//    public void hideBottonNavigation()
//    {
//        bottomNavigationView.setVisibility(View.GONE);
//    }
//
//    public void showBottonNavigation()
//    {
//        bottomNavigationView.setVisibility(View.VISIBLE);
//    }
}
