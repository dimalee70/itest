package itest.kz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

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

import static android.content.Context.MODE_PRIVATE;
import static itest.kz.util.AppUtils.setCheckable;

public class SubjectActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener
{
    private SubjectViewModel subjectViewModel;
    private ActivitySubjectBinding activitySubjectBinding;
    List<Fragment> mFragments;
    private BottomNavigationViewEx bottomNavigationView;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private SharedPreferences sharedPreferences;
    private String language;
    private String previousActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        System.out.println("Start");
        this.previousActivity = getIntent().getStringExtra(Constant.FROM_ACTIVITY);
        activitySubjectBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_subject);

        subjectViewModel = new SubjectViewModel(this);
        activitySubjectBinding.setSubject(subjectViewModel);

        SharedPreferences lang = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");

        mFragments = new ArrayList<>();

        mFragments.add(new CertificationFragment());
        mFragments.add(new SubjectFragment());


        // do we need to implement databinding on each layout? I don't think so. but feel free to bind the layout if you want to.
        mViewPager = (ViewPager) activitySubjectBinding.vpFragmentsContainer;
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));




        mTabLayout = activitySubjectBinding.tlTabsContainer;
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(1).setText(R.string.fullEnt);
        mTabLayout.getTabAt(0).setText(R.string.forSubject);

//        setCustomFont();
        View firstTab = ((ViewGroup)
                mTabLayout.getChildAt(0)).getChildAt(0);
        View secondTab = ((ViewGroup)
            mTabLayout.getChildAt(0)).getChildAt(1);

        mTabLayout.addOnTabSelectedListener(this);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            firstTab.setBackground(getDrawable(R.drawable.selected_tab_first));
            secondTab.setBackground(getDrawable(R.drawable.unselected_tab_second));
        }
//
        sharedPreferences =
                getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();

        setMyToolbar();



        bottomNavigationView = activitySubjectBinding.bottomNavigationView;
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
        setCheckable(bottomNavigationView, false,0);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                    {
                        Intent intent = null;
                        switch (menuItem.getItemId())
                        {
                            case R.id.item_test:
                                break;
                            case R.id.item_statistic:
//                                finish();
//                                if (previousActivity != null && previousActivity.equals(Constant.STATISTIC_ACTIVITY))
//                                {
//                                    SubjectActivity.super.onBackPressed();
//                                }
//                                else {
                                    intent = new Intent(SubjectActivity.this, StatisticActivity.class);
                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.SUBJECT_ACTIVITY);
                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                    startActivity(intent);
//                                }
                                break;
                            case R.id.item_user:
//                                fetchProfileInfo();
//                                finish();
//                                if (previousActivity != null && previousActivity.equals(Constant.PROFILE_ACTIVITY))
//                                {
//                                    SubjectActivity.super.onBackPressed();
//                                }
//                                else {
                                    intent = new Intent(SubjectActivity.this, HomeActivity.class);
                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.SUBJECT_ACTIVITY);
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
        bottomNavigationView.setSelectedItemId(R.id.item_test);
    }


    public void setAccessToken()
    {
        this.accessToken = getIntent()
                .getStringExtra(Constant.ACCESS_TOKEN);
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

//            System.out.println(accessToken);
//            System.out.println(sharedPreferences.getString(Constant.ACCESS_TOKEN, null));

        }
    }

    public void setMyToolbar() {
//        myToolbar = (Toolbar) activitySubjectBinding.getRoot().findViewById(R.id.toolbar_subject_menu);
        myToolbar = (Toolbar) activitySubjectBinding
                .toolbarSubjectMenu;
//        mainToolbarText = (TextView) activitySubjectBinding
//                .toolbarTitle;
//        mainToolbarText.setText("Пәндер");
//        mainToolbarText.setTextColor(Color.WHITE);
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
                View secondTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(0);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
                secondTab.setBackground(getDrawable(R.drawable.unselected_tab_first));
                selectedTab.setBackground(getDrawable(R.drawable.selected_tab_second));
//                View secondTab = ((ViewGroup)
//                        mTabLayout.getChildAt(0)).getChildAt(1);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
            }
            else
            {
                View firstTab = ((ViewGroup)
                        mTabLayout.getChildAt(0)).getChildAt(1);
//                secondTab.setBackground(getDrawable(R.drawable.selected_tab_altername));
                firstTab.setBackground(getDrawable(R.drawable.unselected_tab_second));
                selectedTab.setBackground(getDrawable(R.drawable.selected_tab_first));
            }

        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        if (requestCode == 1) {
//            if(resultCode == RESULT_OK) {
//                previousActivity=data.getStringExtra(Constant.FROM_ACTIVITY);
//            }
//        }
//    }

    @Override
    protected void onResume()
    {
        setCheckable(bottomNavigationView, false,0);

        super.onResume();
//
//        bottomNavigationView.getMenu().getItem(0).setChecked(true);
//        bottomNavigationView.getMenu().getItem(1).setChecked(false);
//        bottomNavigationView.getMenu().getItem(2).setChecked(false);

    }

    //    public class PageListener extends ViewPager.SimpleOnPageChangeListener {
//
//        public void onPageSelected(int position)
//        {
////            currentPage = position;
//            mainToolbarText.setText(mTabLayout.getTabAt(position).getText());
//        }
//    }

}
