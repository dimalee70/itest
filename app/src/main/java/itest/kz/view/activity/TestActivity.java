package itest.kz.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
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

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityTestBinding;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.CustomViewPager;
import itest.kz.util.TestViewModelFactory;
import itest.kz.util.TestsUtils;
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
    private String language;
    private String accessToken;
    private Long testIdMain;

    public Tests getTests()
    {
        return tests;
    }
    private Toolbar navigationToolbar;


    private void fetchFullTestQuestionsGenerate(Long testId)
    {
//        testViewModel.setProgress(true);
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getQuestions(Constant.ACCEPT,
                language,
                "Bearer " + accessToken, testId)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

//                                   System.out.println("json");
//                                   System.out.println(jsonObject.toString());
                                   Tests questions =
                                           TestsUtils.deserializeFromJsonToTests(jsonObject);
                                   updateTestDataList(questions);
//                                   testViewModel.setProgress(false);

//
//                                   setArraListArrayListQuestions(questions);
////
//                                   Tests arrayList = questions.get(0);
//
//
//////
//                                   setFragment(arrayList);

                               }
                           }
//                        new Consumer<JSONObject>() {
//                    @Override
//                    public void accept(JSONObject jsonObject) throws Exception
//                    {
//                        System.out.println("json");
//                        System.out.println(jsonObject.toString());
//                    }
//                }
                );

        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.SELECTED_TEST_POSITION_ID = extras.getInt(Constant.SELECTED_TEST_POSITION_ID, 0);
            this.isStartedFirst = extras.getBoolean(Constant.IS_STARTED_FIRST, true);
            this.tests = (Tests) extras.getSerializable("list");
            this.resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, null);
            typeTest = getIntent().getExtras().getString(Constant.TYPE);

            this.selectedSubject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
            this.testIdMain = getIntent().getLongExtra(Constant.TEST_MAIN_ID, 0);


//            System.out.println("selected pos");
//            System.out.println(SELECTED_TEST_POSITION_ID);
//            System.out.println("test");
//            System.out.println(tests);
        }

        getAccessToken();
        getLanguage();

        if (tests == null && testIdMain != 0)
        {
//            System.out.println("null");
            fetchFullTestQuestionsGenerate(testIdMain);

        } else {


//        getAccessToken();


//        testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
//                selectedSubject, typeTest, isStartedFirst, tests)).get(TestViewModel.class);


//        System.out.println("resultl");
//        System.out.println(resultTag);

//        if (resultTag != null && resultTag.equals(Constant.RESULT_TAG))
//        {
////            System.out.println("test");
////            System.out.println(testIdMain);
////            fetchFullTestQuestionsGenerate(testIdMain);


//            testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
//                    selectedSubject, typeTest, isStartedFirst, testIdMain)).get(TestViewModel.class);
//        }
//        else
//        {
//            System.out.println("test2");
            testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
                    selectedSubject, typeTest, isStartedFirst, tests)).get(TestViewModel.class);
//            fetchFullTestQuestionsGenerate(testIdMain);
//        }

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
                    testViewModel.setProgress(true);
                    if (!isStartedFirst) {
                        tests = getTests();
                    }
                    if (tests != null && tests.getQuestions().size() > 0) {

                        numbersOFpages = tests.getQuestions().size();
                        mPager = activityTestBinding.pager;
                        mPager.setOffscreenPageLimit(2);

                        mPager.setAdapter(new MyAdapter(getSupportFragmentManager(), tests, selectedSubject, resultTag, typeTest));

                        PageListener listener = new PageListener();
                        mPager.addOnPageChangeListener(listener);

                        navigationToolbar = activityTestBinding.toolbar;
                        buttonForward = activityTestBinding.buttonForward;
                        buttonBack = activityTestBinding.buttonBack;

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

                        buttonBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (SELECTED_TEST_POSITION_ID != 0)
                                    mPager.setCurrentItem(--SELECTED_TEST_POSITION_ID, true);
                            }
                        });


                        TextView tv = activityTestBinding.textNumberPager;
                        tv.setText((SELECTED_TEST_POSITION_ID + 1) + " / " + numbersOFpages);

//                    }


                    }
                    mPager.setCurrentItem(SELECTED_TEST_POSITION_ID);

                    testViewModel.setProgress(false);
                }


            };

            testViewModel.getTests().observe(this, listObserver);


            myToolbar = activityTestBinding.myToolbar;
            myToolbar.setTitle("");
            setSupportActionBar(myToolbar);
        }
    }

    public void getLanguage()
    {
        SharedPreferences settings = this.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = settings.getString(Constant.LANG, "kz");
    }


    public void getAccessToken()
    {
        SharedPreferences settings = this.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);
    }

//    private void fetchFullTestQuestionsGenerate(Long testId)
//    {
//        AppController appController = new AppController();
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
////        AppController appController = AppController.create(context);
//        SubjectService subjectService = appController.getSubjectService();
//
//        Disposable disposable = subjectService.getQuestions(Constant.ACCEPT,
//                language,
//                "Bearer " + accessToken, testId)
//                .subscribeOn(appController.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<JsonObject>()
//                           {
//                               @Override
//                               public void accept(JsonObject jsonObject) throws Exception
//                               {
//
////                                   System.out.println(jsonObject.toString());
//                                   Tests questions =
//                                           TestsUtils.deserializeFromJsonToTests(jsonObject);
//                                   updateTestDataList(questions);
////
////                                   setArraListArrayListQuestions(questions);
//////
////                                   Tests arrayList = questions.get(0);
////
////
////////
////                                   setFragment(arrayList);
//
//                               }
//                           }
////                        new Consumer<JSONObject>() {
////                    @Override
////                    public void accept(JSONObject jsonObject) throws Exception
////                    {
////                        System.out.println("json");
////                        System.out.println(jsonObject.toString());
////                    }
////                }
//                );
//
//        compositeDisposable.add(disposable);
//    }

    private void updateTestDataList(Tests questions)
    {
        this.tests = questions;

        //        getAccessToken();


//        testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
//                selectedSubject, typeTest, isStartedFirst, tests)).get(TestViewModel.class);


//        System.out.println("resultl");
//        System.out.println(resultTag);

//        if (resultTag != null && resultTag.equals(Constant.RESULT_TAG))
//        {
////            System.out.println("test");
////            System.out.println(testIdMain);
////            fetchFullTestQuestionsGenerate(testIdMain);


//            testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
//                    selectedSubject, typeTest, isStartedFirst, testIdMain)).get(TestViewModel.class);
//        }
//        else
//        {
//            System.out.println("test2");
        testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
                selectedSubject, typeTest, isStartedFirst, tests)).get(TestViewModel.class);
//            fetchFullTestQuestionsGenerate(testIdMain);
//        }

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
            public void onChanged(@Nullable Tests tests) {
                testViewModel.setProgress(true);
                if (!isStartedFirst) {
                    tests = getTests();
                }
                if (tests != null && tests.getQuestions().size() > 0) {
                    numbersOFpages = tests.getQuestions().size();
                    mPager = activityTestBinding.pager;
                    mPager.setOffscreenPageLimit(2);

                    mPager.setAdapter(new MyAdapter(getSupportFragmentManager(), tests, selectedSubject, resultTag, typeTest));

                    PageListener listener = new PageListener();
                    mPager.addOnPageChangeListener(listener);

                    navigationToolbar = activityTestBinding.toolbar;
                    buttonForward = activityTestBinding.buttonForward;
                    buttonBack = activityTestBinding.buttonBack;

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

                    buttonBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (SELECTED_TEST_POSITION_ID != 0)
                                mPager.setCurrentItem(--SELECTED_TEST_POSITION_ID, true);
                        }
                    });


                    TextView tv = activityTestBinding.textNumberPager;
                    tv.setText((SELECTED_TEST_POSITION_ID + 1) + " / " + numbersOFpages);

//                    }


                }
                mPager.setCurrentItem(SELECTED_TEST_POSITION_ID);
                testViewModel.setProgress(false);

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
            SELECTED_TEST_POSITION_ID = position;
            TextView tv = activityTestBinding.textNumberPager;
            tv.setText((SELECTED_TEST_POSITION_ID + 1) + " / " + numbersOFpages);
        }
    }


    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
//        finish();
    }
}

