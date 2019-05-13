package itest.kz.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityTestBinding;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
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
    private Button buttonYes;
    private Button buttonNo;
    private TextView dialogText;
    public Tests getTests()
    {
        return tests;
    }
    private Toolbar navigationToolbar;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;
    private Context context;
    private boolean lastPageSwipe = false;
    public  String statisticTag;

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
                           },
                        new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable jsonObject) throws Exception
                    {
//                        System.out.println("json");
//                        System.out.println(jsonObject.toString());
                    }
                }
                );

        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.SELECTED_TEST_POSITION_ID = extras.getInt(Constant.SELECTED_TEST_POSITION_ID, 0);
            this.isStartedFirst = extras.getBoolean(Constant.IS_STARTED_FIRST, true);
            this.tests = (Tests) extras.getSerializable("list");
            this.resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, null);
            typeTest = getIntent().getExtras().getString(Constant.TYPE);

            this.selectedSubject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
            this.testIdMain = getIntent().getLongExtra(Constant.TEST_MAIN_ID, 0);
            this.statisticTag = getIntent().getExtras().getString(Constant.STATISTIC_TAG, null);


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

                        testIdMain = tests.getTestId();
                        numbersOFpages = tests.getQuestions().size();
                        mPager = activityTestBinding.pager;
                        mPager.setOffscreenPageLimit(2);

                        mPager.setAdapter(new MyAdapter(getSupportFragmentManager(),
                                tests, selectedSubject,
                                resultTag, typeTest,
                                statisticTag));

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
                                else if (SELECTED_TEST_POSITION_ID == numbersOFpages - 1)
                                {
                                    showFinishDialog();
                                }

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
                    testViewModel.setProgress(false);
                    mPager.setCurrentItem(SELECTED_TEST_POSITION_ID);


                }


            };

            testViewModel.getTests().observe(this, listObserver);


            myToolbar = activityTestBinding.myToolbar;
            myToolbar.setTitle("");
            setSupportActionBar(myToolbar);
        }
    }

    private void showFinishDialog() {
        if (resultTag == null) {
            Dialog dialog = new Dialog(this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogText = dialog.findViewById(R.id.dialog_text);
            buttonYes = dialog.findViewById(R.id.buttonOk);
            buttonNo = dialog.findViewById(R.id.buttonCancel);
            if (language.equals(Constant.KZ)) {
                buttonNo.setText(R.string.noKz);
                buttonYes.setText(R.string.yesKz);
                dialogText.setText(R.string.finishTestDialogKz);

            } else {
                buttonNo.setText(R.string.noRu);
                buttonYes.setText(R.string.yesRu);
                dialogText.setText(R.string.finishTestDialogRu);
            }
            buttonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishTest(testIdMain);


                    //System.out.println(testIdMain);//103080954

                }
            });

            buttonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void finishTest(Long testIdMain)
    {
        testViewModel.setProgress(true);
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


//        System.out.println(accessToken);
        Disposable disposable = subjectService.finishTest(Constant.ACCEPT,
                language, "Bearer "+ accessToken, testIdMain)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestFinishResponse>()
                           {
                               @Override
                               public void accept(TestFinishResponse testFinishResponse) throws Exception
                               {

                                   if (this != null) {
//                                       if (resultTag == null) {
                                       Intent intent = new Intent(context, ResultsActivity.class);
                                       intent.putExtra(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
                                       intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
                                       intent.putExtra(Constant.TYPE, typeTest);
                                       intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);

//                                       intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);

                                       //                                       System.out.println("testrespone");
                                       //                                       System.out.println(testFinishResponse.getSuccess());

                                       startActivity(intent);

                                       testViewModel.setProgress(false);
//                                   }


//                            Intent intent = new Intent(this, MainActivity.class);
//                            startActivity(intent);
                                   }
//                                   Toast toast = Toast.makeText(getContext(),
////                                    jsonObject.toString(),
//                                           jsonObject.toString(),
//                                           Toast.LENGTH_SHORT);
//
//                                   toast.show();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401")) {
                                    showToastUnauthorized();
                                    testViewModel.setProgress(false);
                                }
//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                            }
                        }

                );
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
//        System.out.println(accessToken);
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

                    mPager.setAdapter(new MyAdapter(getSupportFragmentManager(), tests, selectedSubject, resultTag, typeTest,
                            statisticTag));

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
                            else
                            {
                                showFinishDialog();
                            }
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
//            if (SELECTED_TEST_POSITION_ID == numbersOFpages - 1)
//            {
//                showFinishDialog();
//            }
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

    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(context);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTextAuth = dialog.findViewById(R.id.dialog_text);
        buttonYesAuth = dialog.findViewById(R.id.buttonOk);
        buttonNoAuth = dialog.findViewById(R.id.buttonCancel);
        buttonNoAuth.setVisibility(View.GONE);
        buttonYesAuth.setText(R.string.ok);
        if(language.equals(Constant.KZ))
        {
//            buttonNo.setText(R.string.noKz);

            dialogTextAuth.setText(R.string.sessionErrorKz);

        }
        else
        {
//            buttonNo.setText(R.string.noRu);
//            buttonYes.setText(R.string.yesRu);
            dialogTextAuth.setText(R.string.sessionErrorRu);
        }
        buttonYesAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYesAuth.setEnabled(false);
                dialog.dismiss();
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954

            }
        });

//        buttonNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(context, AuthActivity.class);
        startActivity(intent);
//        if (language.equals(Constant.KZ))
//
//            Toast.makeText(this,
//                    R.string.sessionErrorKz,
//                    Toast.LENGTH_SHORT).show();
//        else
//        {
//            Toast.makeText(this,
//                    R.string.sessionErrorRu,
//                    Toast.LENGTH_SHORT).show();
//        }
    }
}

