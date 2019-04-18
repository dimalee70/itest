package itest.kz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivitySubjectBinding;
import itest.kz.model.PasswordChangeResponce;
import itest.kz.model.ProfileResponse;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.fragments.CertificationFragment;
import itest.kz.view.fragments.ProfileFragment;
import itest.kz.view.fragments.SubjectFragment;
import itest.kz.viewmodel.SubjectViewModel;

public class SubjectActivity extends AppCompatActivity
{
    private SubjectViewModel subjectViewModel;
    private ActivitySubjectBinding activitySubjectBinding;
    List<Fragment> mFragments;
    private BottomNavigationView bottomNavigationView;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private SharedPreferences sharedPreferences;


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        activitySubjectBinding =
//                DataBindingUtil.inflate(inflater, R.layout.activity_subject,
//                        container, false);
//        subjectViewModel = new SubjectViewModel(getContext());
//        activitySubjectBinding.setSubject(subjectViewModel);
//
//        mFragments = new ArrayList<>();
//
//        mFragments.add(new SubjectFragment());
//        mFragments.add(new CertificationFragment());
//
//        // do we need to implement databinding on each layout? I don't think so. but feel free to bind the layout if you want to.
////        mViewPager = (ViewPager) findViewById(R.id.vp_fragments_container);
//        mViewPager = activitySubjectBinding.vpFragmentsContainer;
//        mViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), mFragments));
//
//        mTabLayout = activitySubjectBinding.tlTabsContainer;
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.getTabAt(0).setText("ENT");
//        mTabLayout.getTabAt(1).setText("Subjects");
//
//        return activitySubjectBinding.getRoot();
////        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activitySubjectBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_subject);

        subjectViewModel = new SubjectViewModel(this);
        activitySubjectBinding.setSubject(subjectViewModel);


        mFragments = new ArrayList<>();

        mFragments.add(new SubjectFragment());
        mFragments.add(new CertificationFragment());

        // do we need to implement databinding on each layout? I don't think so. but feel free to bind the layout if you want to.
        mViewPager = (ViewPager) findViewById(R.id.vp_fragments_container);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));

        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText((subjectViewModel
                .getLanguage().equals(Constant.KZ)) ? R.string.fullEntKz
                : R.string.fullEntRu);
        mTabLayout.getTabAt(1).setText((subjectViewModel
                .getLanguage().equals(Constant.KZ)) ? R.string.forSubjectKz
                : R.string.forSubjectRu);

//        setCustomFont();

        sharedPreferences = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();

        setMyToolbar();



        bottomNavigationView = (BottomNavigationView) activitySubjectBinding.bottomNavigationView;

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
//                                    System.out.println("Added");
//                                }

//                                else
//                                fragmentManager.beginTransaction().replace(R.id.viewpager, testFragment)
//                                        .commit();
//                                finish();
//                                intent = new Intent(SubjectActivity.this,SubjectActivity.class);
//                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                startActivity(intent);
                                break;
                            case R.id.item_statistic:
                                finish();
                                intent = new Intent(SubjectActivity.this,StatisticActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
                                break;
                            case R.id.item_user:
//                                fetchProfileInfo();
                                finish();
                                intent = new Intent(SubjectActivity.this,HomeActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
//                                fragmentManager.beginTransaction().replace(R.id.viewpager, fragment).commit();
                                break;

                        }


                        return true;
                    }
                }
        );
        bottomNavigationView.setSelectedItemId(R.id.item_test);
    }
//
//    public void setCustomFont() {
//
//        ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
//        int tabsCount = vg.getChildCount();
//
//        for (int j = 0; j < tabsCount; j++) {
//            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
//
//            int tabChildsCount = vgTab.getChildCount();
//
//            for (int i = 0; i < tabChildsCount; i++) {
//                View tabViewChild = vgTab.getChildAt(i);
//                if (tabViewChild instanceof TextView) {
//                    //Put your font in assests folder
//                    //assign name of the font here (Must be case sensitive)
//                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "res/font/opensans_semibold.ttf"));
//                }
//            }
//        }
//    }
//    res/font/opensans_semibold.ttf

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

            System.out.println(accessToken);
//            System.out.println(sharedPreferences.getString(Constant.ACCESS_TOKEN, null));

        }
    }

    public void setMyToolbar() {
//        myToolbar = (Toolbar) activitySubjectBinding.getRoot().findViewById(R.id.toolbar_subject_menu);
        myToolbar = (Toolbar) activitySubjectBinding
                .toolbarSubjectMenu;
        mainToolbarText = (TextView) activitySubjectBinding
                .toolbarTitle;
//        mainToolbarText.setText("Пәндер");
//        mainToolbarText.setTextColor(Color.WHITE);
        myToolbar.setTitle("");


        setSupportActionBar(myToolbar);
    }

    public void fetchProfileInfo()
    {
//        System.out.println(accessToken);
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        UserService userService = appController.getUserService();

        Disposable disposable = userService.getProfile("ru", Constant.ACCEPT,
                "Bearer " + accessToken)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProfileResponse>()
                {
                    @Override
                    public void accept(ProfileResponse profileResponse) throws Exception
                    {
                        openFragment(profileResponse);

                    }
                });

        compositeDisposable.add(disposable);
    }

    private void openFragment(ProfileResponse profileResponse)
    {
//        System.out.println(profileResponse.getProfile());
//        FragmentHelper.openFragment(this,
//                R.id.frame_layout,
//                ProfileFragment.newInstance(profileResponse.getProfile()));


        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.viewpager);
//        activitySubjectBinding.viewpager.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);

        ProfileFragment nextFrag= ProfileFragment.newInstance(profileResponse.getProfile());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.viewpager, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
