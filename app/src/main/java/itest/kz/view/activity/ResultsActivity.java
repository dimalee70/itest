package itest.kz.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ActivityResultsBinding;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.adapters.ResultsSubjectAdapter;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.fragments.CheckResultFragment;
import itest.kz.view.fragments.ResultsFragment;
import itest.kz.viewmodel.ResultsViewModel;

public class ResultsActivity extends AppCompatActivity
//        implements View.OnClickListener
{
    private ActivityResultsBinding activityResultsBinding;
    private ResultsViewModel resultsViewModel;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar resultToolbar;
    private TextView mainToolbarText;
    private List<Fragment> mFragments;
    private TestFinishResponse testFinishResponse;
    private Long testIdMain;
    private List<Subject> subjectList;
    private String typeTest;
    private Subject selectedSubject;
    private ImageButton closeFragment;
    private Context context;
    private String accessToken;
    private String resultTag;
    private  String statisticTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences accessTok = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");
        context = this;
        resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, "");
        testFinishResponse = (TestFinishResponse) getIntent().getExtras().getSerializable(Constant.TEST_FINISH_RESPONSE);
        testIdMain = getIntent().getExtras().getLong(Constant.TEST_MAIN_ID, 0);
        subjectList = getIntent().getParcelableArrayListExtra(Constant.SUBJECT_LIST);
        selectedSubject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
        activityResultsBinding = DataBindingUtil.setContentView(this, R.layout.activity_results);
        statisticTag = getIntent().getExtras().getString(Constant.STATISTIC_TAG, null);
        typeTest = getIntent().getExtras().getString(Constant.TYPE);
        resultsViewModel = new ResultsViewModel(this);
        activityResultsBinding.setResults(resultsViewModel);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackgroundResults));
        }

        setMyToolbar();
        setFragments();

    }

    public ViewPager getmViewPager()
    {
        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager)
    {
        this.mViewPager = mViewPager;
    }

    public void  setFragments()
    {
        mFragments = new ArrayList<>();
        ResultsFragment resultsFragment = ResultsFragment.newInstance(testFinishResponse, testIdMain, subjectList,
                selectedSubject,
                typeTest, statisticTag);

        mFragments.add(resultsFragment);

//        resultsFragment.getResultsSubjectAdapter().setOnItemListener(new View);




//        mFragments.add(CheckResultFragment.newInstance(testIdMain, subjectList, selectedSubject, Constant.RESULT_TAG, typeTest, statisticTag));
//        mFragments.add(CheckResultFragment)
        mViewPager = (ViewPager) activityResultsBinding.vpFragmentsContainer;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(viewPagerAdapter);

        setmViewPager(mViewPager);




//        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.getTabAt(0).setText((resultsViewModel
//                .getLanguage().equals(Constant.KZ)) ? R.string.checkInFullKz
//                : R.string.checkInFullRu);
//        mTabLayout.getTabAt(1).setText((resultsViewModel
//                .getLanguage().equals(Constant.KZ)) ? R.string.checkInKz
//                : R.string.checkInRu);

    }

    public void setMyToolbar() {
//        resultToolbar = (Toolbar) activityResultsBinding
//                .resultsToolbar;
//        mainToolbarText = (TextView) activityResultsBinding
//                .textViewTitle;
//        resultToolbar.setTitle("");
//        setSupportActionBar(resultToolbar);

        closeFragment = activityResultsBinding.buttonCloseFragment;
//
//
        closeFragment.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                            openNewActivity();
                    }
                });
//


    }



    private void openNewActivity()
    {
//        System.out.println("Close method");

        if (statisticTag != null && statisticTag.equals(Constant.STATISTIC_TAG) )
        {
            Intent intent = new Intent(getBaseContext(), StatisticActivity.class);
            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getBaseContext(), SubjectActivity.class);
            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
            startActivity(intent);
        }

//        finish();
    }

    @Override
    public void onBackPressed()
    {
    }

    //    @Override
//    public void onClick(View v)
//    {
//        System.out.println("Click on Close");
//        Intent intent = new Intent(this, SubjectActivity.class);
//        startActivity(intent);
////        finish();
////        closeActivity();
//    }

//    public void closeActivity()
//    {
////        finish();
//
//    }


}
