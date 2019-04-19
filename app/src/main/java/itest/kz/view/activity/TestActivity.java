package itest.kz.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import itest.kz.R;
import itest.kz.databinding.ActivityTestBinding;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.util.CustomViewPager;
import itest.kz.util.TestViewModelFactory;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.viewmodel.TestViewModel;

public class TestActivity extends AppCompatActivity
{
    public ActivityTestBinding activityTestBinding;
    private TestViewModel testViewModel;
    private Subject selectedSubject;
    public Tests tests;
    private List<Tests> testsList;
    private Toolbar myToolbar;
    private Integer SELECTED_TEST_POSITION_ID = 0;
    private boolean isStartedFirst= true;
    private String resultTag;
    private String typeTest;
    private int numbersOFpages;
    public int currentPage = 0;
    private ImageButton buttonForward;
    private ImageButton buttonBack;
    MyAdapter mAdapter;
    public CustomViewPager mPager;
    public ViewPager getmPager()
    {
        return mPager;
    }
    public Tests getTests()
    {
        return tests;
    }
    private Toolbar navigationToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            this.SELECTED_TEST_POSITION_ID = extras.getInt(Constant.SELECTED_TEST_POSITION_ID, 0);
            this.isStartedFirst = extras.getBoolean(Constant.IS_STARTED_FIRST, true);
            this.tests = (Tests) extras.getSerializable("list");
            this.resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, null);
            typeTest = getIntent().getExtras().getString(Constant.TYPE);

            this.selectedSubject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
        }

        testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
                selectedSubject, typeTest, isStartedFirst, tests)).get(TestViewModel.class);

        activityTestBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_test);

        activityTestBinding.setTest(testViewModel);

        if (typeTest.equals(Constant.TYPESUBJECTTEST) || typeTest.equals(Constant.TYPELECTURETEST))
            activityTestBinding
                    .textViewTitle.setText(selectedSubject.getTitle());

//
//        if (SELECTED_TEST_POSITION_ID == null || !(isStartedFirst))
//        {
//        }






        final Observer<Tests> listObserver = new Observer<Tests>() {

            @Override
            public void onChanged(@Nullable Tests tests)
            {
                if (!isStartedFirst)
                {
                    tests = getTests();
                }
                if (tests != null && tests.getQuestions().size() > 0)
                {
                    numbersOFpages = tests.getQuestions().size();
                    mPager = activityTestBinding.pager;
                    mPager.setOffscreenPageLimit(2);

                    mPager.setAdapter( new MyAdapter(getSupportFragmentManager(), tests, selectedSubject, resultTag, typeTest));

                    PageListener listener = new PageListener();
                    mPager.addOnPageChangeListener(listener);

                    navigationToolbar = activityTestBinding.toolbar;
                    buttonForward = activityTestBinding.buttonForwardTest;
                    buttonBack = activityTestBinding.buttonBackTest;

//                    activityTestBinding.buttonForwardTestTest.setOnClickListener(new View.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(View v)
//                        {
//                            System.out.println("Hello");
//                        }
//                    });

                    buttonForward.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SELECTED_TEST_POSITION_ID != numbersOFpages - 1)
                                mPager.setCurrentItem(++SELECTED_TEST_POSITION_ID, true);
                        }
                    });

                    buttonBack.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (SELECTED_TEST_POSITION_ID != 0)
                                mPager.setCurrentItem(--SELECTED_TEST_POSITION_ID, true);
                        }
                    });


                    TextView tv = activityTestBinding.textNumberPager;
                    tv.setText((SELECTED_TEST_POSITION_ID+1) + " / " + numbersOFpages);

//                    }


                }
                    mPager.setCurrentItem(SELECTED_TEST_POSITION_ID);

            }


        };

        testViewModel.getTests().observe(this, listObserver);


        myToolbar = activityTestBinding.myToolbar;
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
    }


    public int getPosition()
    {
        return mPager.getCurrentItem();
    }


    public void setPosition (int pos)
    {
        mPager.setCurrentItem(pos);
    }

    public class PageListener extends ViewPager.SimpleOnPageChangeListener {

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPageArg) {
            currentPage = currentPageArg;
        }

        public void onPageSelected(int position)
        {
            currentPage = position;
            TextView tv = activityTestBinding.textNumberPager;
            tv.setText((currentPage + 1) + " / " + numbersOFpages);
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}

