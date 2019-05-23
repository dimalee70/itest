package itest.kz.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityMainHomeBinding;
import itest.kz.model.ProfileResponse;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.adapters.AuthFragmentPagerAdapter;
import itest.kz.view.adapters.MainHomeActivityPagerAdapter;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.fragments.CertificationFragment;
import itest.kz.view.fragments.ProfileFragment;
import itest.kz.view.fragments.SubjectFragment;
import itest.kz.viewmodel.MainHomeViewModel;

public class MainHomeActivity extends AppCompatActivity
{
    private ActivityMainHomeBinding activityMainHomeBinding;
    private MainHomeViewModel mainHomeViewModel;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView bottomNavigationView;
    private String accessToken;
    private ProfileResponse profileResponse;
    private Toolbar toolbar;
    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private String language;


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu)
//    {
//        if(Build.VERSION.SDK_INT > 11)
//        {
//            menu.findItem(R.id.menu_github).setVisible(false);
//            menu.findItem(R.id.menu_github1).setVisible(false);
//            menu.findItem(R.id.menu_github2).setVisible(false);
//            menu.findItem(R.id.menu_save).setVisible(false);
//            menu.findItem(R.id.menu_logout).setVisible(true);
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                closefragment();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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

//    public void fetchProfileInfo()
//    {
////        System.out.println(accessToken);
//        AppController appController = new AppController();
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
////        AppController appController = AppController.create(context);
//        UserService userService = appController.getUserService();
//
//        Disposable disposable = userService.getProfile("ru", Constant.ACCEPT,
//                "Bearer " + accessToken)
//                .subscribeOn(appController.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ProfileResponse>()
//                {
//                    @Override
//                    public void accept(ProfileResponse profileResponse) throws Exception
//                    {
//                        openFragment(profileResponse);
//
//                    }
//                });
//
//        compositeDisposable.add(disposable);
//    }
//
//    private void setProfileResponce(ProfileResponse profileResponse)
//    {
//        this.profileResponse = profileResponse;
//    }


//    private void openFragment(ProfileResponse profileResponse)
//    {
////        System.out.println(profileResponse.getProfile());
////        FragmentHelper.openFragment(this,
////                R.id.frame_layout,
////                ProfileFragment.newInstance(profileResponse.getProfile()));
//
//        ProfileFragment nextFrag= ProfileFragment.newInstance(profileResponse.getProfile());
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.viewpager, nextFrag, "findThisFragment")
//                .addToBackStack(null)
//                .commit();
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final FragmentManager fragmentManager = getSupportFragmentManager();
//        final Fragment testFragment = new SubjectActivity();
//        final Fragment statisticFragment = new StatisticActivity();
//        final Fragment userFragment = new HomeActivity();
        SharedPreferences lang = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");
        activityMainHomeBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main_home);
        mainHomeViewModel = new MainHomeViewModel(this);
        activityMainHomeBinding.setMainHome(mainHomeViewModel);

        sharedPreferences = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();

//        toolbar = (Toolbar) activityMainHomeBinding.toolbarProfile;
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);




//        mFragments = new ArrayList<>();
//
//        mFragments.add(testFragment);
//        mFragments.add(statisticFragment);
//        mFragments.add(userFragment);
//
//        // do we need to implement databinding on each layout? I don't think so. but feel free to bind the layout if you want to.
//        mViewPager = activityMainHomeBinding
//                .viewpager;
////                activitySubjectBinding.vpFragmentsContainer;
////        (R.id.vp_fragments_container);
//        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));

//        SubjectActivity.PageListener listener = new SubjectActivity.PageListener();
//        mViewPager.addOnPageChangeListener(listener);

//        ViewPager viewPager =
//                activityMainHomeBinding.viewpager;
//        MainHomeActivityPagerAdapter mainHomeActivityPagerAdapter =
//                new MainHomeActivityPagerAdapter(getSupportFragmentManager(), this);
//        viewPager.setAdapter(mainHomeActivityPagerAdapter);

        bottomNavigationView = activityMainHomeBinding.bottomNavigationView;

        if (language.equals(Constant.KZ))
        {
            bottomNavigationView.getMenu().getItem(0).setTitle(R.string.mainNavigationKz);
        }
        else
        {
            bottomNavigationView.getMenu().getItem(0).setTitle(R.string.mainNavigationRu);
        }
        bottomNavigationView.getMenu().getItem(1).setTitle(R.string.statisticNavigation);
        bottomNavigationView.getMenu().getItem(2).setTitle(R.string.profileNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                    {
                        Intent intent = null;
                        switch (menuItem.getItemId())
                        {
                            case R.id.item_test:
//                                if (testFragment.isAdded())
//                                {
//                                    fragmentManager.beginTransaction().remove(testFragment).commit();
////                                    System.out.println("Added");
//                                }
//
//                                else
//                                    fragmentManager
//                                            .beginTransaction()
//                                            .replace(R.id.viewpager, testFragment)
//                                            .commit();
                                intent = new Intent(getBaseContext(),SubjectActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
                                break;

                            case R.id.item_statistic:
//                                if (statisticFragment.isAdded())
//                                {
//                                    fragmentManager.beginTransaction().remove(statisticFragment).commit();
////                                    System.out.println("Added");
//                                }
//
//                                else
//                                    fragmentManager
//                                            .beginTransaction()
//                                            .replace(R.id.viewpager, statisticFragment)
//                                            .commit();
                                intent = new Intent(getBaseContext(),SubjectActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
                                break;

                            case R.id.item_user:
//                                if (statisticFragment.isAdded())
//                                {
//                                    fragmentManager.beginTransaction().remove(userFragment).commit();
////                                    System.out.println("Added");
//                                }
//
//                                else
//                                    fragmentManager
//                                            .beginTransaction()
//                                            .replace(R.id.viewpager, userFragment)
//                                            .commit();
//                                fetchProfileInfo();
                                intent = new Intent(getBaseContext(),HomeActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);

//                                fragmentManager.beginTransaction().replace(R.id.viewpager, fragment).commit();
                                break;

                        }


                        return false;
                    }
                }
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment fragment;
//                        switch (item.getItemId()) {
//                            case R.id.action_favorites:
//                                fragment = fragment1;
//                                break;
//                            case R.id.action_schedules:
//                                fragment = fragment2;
//                                break;
//                            case R.id.action_music:
//                            default:
//                                fragment = fragment3;
//                                break;
//                        }
//                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
//                        return true;
//                    }
//                }
        );
//         Set default selection
        bottomNavigationView.setSelectedItemId(R.id.item_test);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
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
//            System.out.println("Access ");
//            System.out.println(accessToken);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString(Constant.ACCESS_TOKEN, accessToken);
            editor.apply();
            editor.commit();

//            System.out.println(sharedPreferences.getString(Constant.ACCESS_TOKEN, null));

        }
    }
}
