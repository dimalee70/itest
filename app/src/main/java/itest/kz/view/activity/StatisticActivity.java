package itest.kz.view.activity;

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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

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

import static android.content.Context.MODE_PRIVATE;

public class StatisticActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener
{
    private ActivityStatisticBinding activityStatisticBinding;
    private StatisticViewModel statisticViewModel;
    private List<Fragment> mFragments;
    private BottomNavigationViewEx bottomNavigationView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private SharedPreferences sharedPreferences;
    private String typeTest;
    private String language;
    private String prefiousActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.prefiousActivity = getIntent().getStringExtra(Constant.FROM_ACTIVITY);
        this.typeTest = Constant.TYPEFULLTEST;
        activityStatisticBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_statistic);
        statisticViewModel = new StatisticViewModel(this);
        activityStatisticBinding.setStatistic(statisticViewModel);

        SharedPreferences lang = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");

        mFragments = new ArrayList<>();
        mFragments.add(new SubjectStatisticFragment());
        mFragments.add(new LectureStatisticFragment());
        mFragments.add(FullTestStatisticFragment.newInstance(typeTest));
        mViewPager = (ViewPager) activityStatisticBinding.vpFragmentsContainer;
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));

        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
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
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, mTabLayout, false);
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

        sharedPreferences = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();

        setMyToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            firstTab.setBackground(getDrawable(R.drawable.selected_tab_first));
            centerTab.setBackground(getDrawable(R.drawable.unselected_tab_center_statistic));
            secondTab.setBackground(getDrawable(R.drawable.unselected_tab_second_statistic));

        }


        bottomNavigationView =  activityStatisticBinding.bottomNavigationView;
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.setLabelVisibilityMode(1);
        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
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
//                                if (prefiousActivity != null && prefiousActivity.equals(Constant.SUBJECT_ACTIVITY))
//                                {
//                                    finish();
//                                    break;
//                                }
//                                else
//                                {
                                    intent = new Intent(StatisticActivity.this,SubjectActivity.class);
                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.STATISTIC_ACTIVITY);
                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                    startActivity(intent);
                                    break;
//                                }


                            case R.id.item_statistic:
                                break;
                            case R.id.item_user:
//                                fetchProfileInfo();
//                                finish();
//                                if (prefiousActivity != null && prefiousActivity.equals(Constant.PROFILE_ACTIVITY))
//                                {
//                                    StatisticActivity.super.onBackPressed();
//                                }
//                                else
//                                {
                                    intent = new Intent(StatisticActivity.this,HomeActivity.class);
                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.STATISTIC_ACTIVITY);
                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                    startActivity(intent);
//                                }

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
        myToolbar = (Toolbar) activityStatisticBinding
                .toolbarSubjectMenu;
        mainToolbarText = (TextView) activityStatisticBinding
                .toolbarTitle;
        myToolbar.setTitle("");


        setSupportActionBar(myToolbar);
    }

    @Override
    public void onBackPressed()
    {
        finishAffinity();
//        System.exit(0);
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
                firstTab.setBackground(getDrawable(R.drawable.unselected_tab_first));
                selectedTab.setBackground(getDrawable(R.drawable.selected_tab_center));
                secondTab.setBackground(getDrawable(R.drawable.unselected_tab_second));

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
                firstTab.setBackground(getDrawable(R.drawable.unselected_tab_first_statistic));
                selectedTab.setBackground(getDrawable(R.drawable.selected_tab_second));
                centerTab.setBackground(getDrawable(R.drawable.unselected_tab_center_statistic_left));

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
                centerTab.setBackground(getDrawable(R.drawable.unselected_tab_center_right));
                selectedTab.setBackground(getDrawable(R.drawable.selected_tab_first));
                secondTab.setBackground(getDrawable(R.drawable.unselected_tab_second_left));

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

