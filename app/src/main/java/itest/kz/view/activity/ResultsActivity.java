package itest.kz.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ActivityResultsBinding;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
import itest.kz.util.Constant;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.fragments.CheckResultFragment;
import itest.kz.view.fragments.ResultsFragment;
import itest.kz.viewmodel.ResultsViewModel;

public class ResultsActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        testFinishResponse = (TestFinishResponse) getIntent().getExtras().getSerializable(Constant.TEST_FINISH_RESPONSE);
        testIdMain = getIntent().getExtras().getLong(Constant.TEST_MAIN_ID, 0);
        subjectList = getIntent().getParcelableArrayListExtra(Constant.SUBJECT_LIST);
        selectedSubject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
        activityResultsBinding = DataBindingUtil.setContentView(this, R.layout.activity_results);
        typeTest = getIntent().getExtras().getString(Constant.TYPE);
        resultsViewModel = new ResultsViewModel(this);
        activityResultsBinding.setResults(resultsViewModel);
        setMyToolbar();
        setFragments();

    }

    public void  setFragments()
    {
        mFragments = new ArrayList<>();
        mFragments.add(ResultsFragment.newInstance(testFinishResponse, testIdMain, subjectList,
                selectedSubject,
                typeTest));

        mFragments.add(CheckResultFragment.newInstance(testIdMain, subjectList, selectedSubject, Constant.RESULT_TAG, typeTest));
//        mFragments.add(CheckResultFragment)
        mViewPager = (ViewPager) activityResultsBinding.vpFragmentsContainer;
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText((resultsViewModel
                .getLanguage().equals(Constant.KZ)) ? R.string.checkInFullKz
                : R.string.checkInFullRu);
        mTabLayout.getTabAt(1).setText((resultsViewModel
                .getLanguage().equals(Constant.KZ)) ? R.string.checkInKz
                : R.string.checkInRu);

    }

    public void setMyToolbar() {
//        myToolbar = (Toolbar) activitySubjectBinding.getRoot().findViewById(R.id.toolbar_subject_menu);
        resultToolbar = (Toolbar) activityResultsBinding
                .resultToolbar;
        mainToolbarText = (TextView) activityResultsBinding
                .textViewTitle;
        resultToolbar.setTitle("");
        closeFragment = activityResultsBinding.buttonCloseFragment;

        setSupportActionBar(resultToolbar);

        closeFragment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                finish();

//                System.out.println("Click on Close");
                finish();
                Intent intent = new Intent(ResultsActivity.this, SubjectActivity.class);
//                intent.putExtra("tag","tag")
                startActivity(intent);
//                closeActivity();

            }
        });
//        mainToolbarText.setText("Пәндер");
//        mainToolbarText.setTextColor(Color.WHITE);

    }

//    public void closeActivity()
//    {
////        finish();
//
//    }


}
