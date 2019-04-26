package itest.kz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import itest.kz.R;
import itest.kz.databinding.ActivityStatisticBinding;
import itest.kz.util.Constant;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.fragments.FullTestStatisticFragment;
import itest.kz.view.fragments.LectureStatisticFragment;
import itest.kz.view.fragments.SubjectStatisticFragment;
import itest.kz.viewmodel.StatisticViewModel;

public class StatisticActivity extends AppCompatActivity
{
    private ActivityStatisticBinding activityStatisticBinding;
    private StatisticViewModel statisticViewModel;
    private List<Fragment> mFragments;
    private BottomNavigationView bottomNavigationView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private SharedPreferences sharedPreferences;
    private String typeTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.typeTest = Constant.TYPEFULLTEST;
        activityStatisticBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_statistic);
        statisticViewModel = new StatisticViewModel(this);
        activityStatisticBinding.setStatistic(statisticViewModel);
        mFragments = new ArrayList<>();
        mFragments.add(new SubjectStatisticFragment());
        mFragments.add(new LectureStatisticFragment());
        mFragments.add(FullTestStatisticFragment.newInstance(typeTest));
        mViewPager = (ViewPager) activityStatisticBinding.vpFragmentsContainer;
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));

        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText((statisticViewModel
                .getLanguage().equals(Constant.KZ)) ? R.string.subjectKz
                : R.string.subjetcRu);
        mTabLayout.getTabAt(1).setText((statisticViewModel
                .getLanguage().equals(Constant.KZ)) ? R.string.lectureKz
                : R.string.lectureRu);
        mTabLayout.getTabAt(2).setText((statisticViewModel
                .getLanguage().equals(Constant.KZ)) ? R.string.fullKz
                : R.string.fullRu);

        sharedPreferences = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();

        setMyToolbar();




//        mFragments.add(new SubjectFragment());
//        mFragments.add(new CertificationFragment());

//        mViewPager = (ViewPager) findViewById(R.id.vp_fragments_container);
//        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
//
//
//        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.getTabAt(0).setText((subjectViewModel
//                .getLanguage().equals(Constant.KZ)) ? R.string.fullEntKz
//                : R.string.fullEntRu);
//        mTabLayout.getTabAt(1).setText((subjectViewModel
//                .getLanguage().equals(Constant.KZ)) ? R.string.forSubjectKz
//                : R.string.forSubjectRu);




        bottomNavigationView = (BottomNavigationView) activityStatisticBinding.bottomNavigationView;

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
                                finish();
                                intent = new Intent(StatisticActivity.this,SubjectActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
                                break;
                            case R.id.item_statistic:
//                                finish();
//                                intent = new Intent(StatisticActivity.this,StatisticActivity.class);
//                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                startActivity(intent);
                                break;
                            case R.id.item_user:
//                                fetchProfileInfo();
                                finish();
                                intent = new Intent(StatisticActivity.this,HomeActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
//                                fragmentManager.beginTransaction().replace(R.id.viewpager, fragment).commit();
                                break;


                        }


                        return true;
                    }
                }
        );

        bottomNavigationView.setSelectedItemId(R.id.item_statistic);


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

    public void setMyToolbar() {
//        myToolbar = (Toolbar) activitySubjectBinding.getRoot().findViewById(R.id.toolbar_subject_menu);
        myToolbar = (Toolbar) activityStatisticBinding
                .toolbarSubjectMenu;
        mainToolbarText = (TextView) activityStatisticBinding
                .toolbarTitle;
//        mainToolbarText.setText("Пәндер");
//        mainToolbarText.setTextColor(Color.WHITE);
        myToolbar.setTitle("");


        setSupportActionBar(myToolbar);
    }

    @Override
    public void onBackPressed()
    {
        finishAffinity();
        System.exit(0);
    }

}

