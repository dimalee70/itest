package itest.kz.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ankushgrover.hourglass.Hourglass;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityFullTestBinding;
import itest.kz.model.ProfileResponse;
import itest.kz.model.Question;
import itest.kz.model.QuestionResponce;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.TestGenerate;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.util.CustomViewPager;
import itest.kz.util.DataHolder;
import itest.kz.util.PageListener;
import itest.kz.util.TestsUtils;
import itest.kz.view.adapters.FullTestAdapter;
import itest.kz.view.adapters.FullTestSubjectAdapter;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.view.adapters.ResultAdapter;
import itest.kz.view.fragments.TestFragment;
import itest.kz.viewmodel.FullTestViewModel;
import itest.kz.viewmodel.HomeViewModel;
import itest.kz.viewmodel.ResultViewModel;
import okhttp3.Response;

public class FullTestActivity extends AppCompatActivity
{
    private ActivityFullTestBinding activityFullTestBinding;
    private FullTestViewModel fullTestViewModel;
    private List<Subject> subjectList;
    private String accessToken;
    public CustomViewPager mPager;
    private String language;
    public Long testIdMain;
    private List<Tests> arrayListArrayListQuestions;
    private FullTestSubjectAdapter fullTestSubjectAdapter;
    private boolean isStartedFirst;
    private TestGenerate testGenerate;
    private Integer currentPosition;
    private Integer selectedSubjectPosition = 0;
    private  Integer selectedTestPosition = 0;
    private int numbersOFpages;
    public int currentPage = 0;
    private TextView timer;
    private long maxTimeInMilliseconds;// in your case
    private Hourglass hourglass;
    private  ImageButton buttonForward;
    private ImageButton buttonBack;
    private  CountDownTimer t;
    private String resultTag;
    private RecyclerView subjectResycleView;
    private boolean hasActiveTest = false;
    private TextView dialogText;
    private Button buttonYes;
    private Button buttonNo;
    private TextView dialogTextFinish;
    private Button buttonYesFinish;
    private Button buttonNoFinish;
    private String statisticTag;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {


//        try {
//
//        }
//        System.out.println("Dfsdcds");
//        catch (Exception)
//        {}



        super.onCreate(savedInstanceState);

        subjectList = getIntent().getParcelableArrayListExtra(Constant.SUBJECT_LIST);


        SharedPreferences settings = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);

        this.selectedTestPosition = getIntent().getExtras().getInt(Constant.SELECTED_TEST_POSITION_ID, 0);
        this.selectedSubjectPosition = getIntent().getExtras().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);
        this.isStartedFirst = getIntent().getExtras().getBoolean(Constant.IS_STARTED_FIRST, true);
        this.resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, null);
        this.hasActiveTest = getIntent().getBooleanExtra(Constant.hasActiveTest, false);
        this.statisticTag = getIntent().getExtras().getString(Constant.STATISTIC_TAG, null);

        language = settings.getString(Constant.LANG, "kz");
        getAccessToken();


        this.currentPosition = getIntent().getExtras().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);
        this.testIdMain = getIntent().getExtras().getLong(Constant.TEST_MAIN_ID);
        subjectList.get(currentPosition).setOnClickedRecycle(1);

        activityFullTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_full_test);
        setR(activityFullTestBinding.listSubjects);

        if (isStartedFirst)
        {
//            maxTimeInMilliseconds = 10800000;
            fetchFullTestGenerate();
        }
        else
        {
            maxTimeInMilliseconds = (Long) getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE)
                    .getLong(Constant.CURRENT_TIME, 10800000);
            fetchFullTestQuestionsGenerate(testIdMain);
        }
//        System.out.println(maxTimeInMilliseconds);

//        startTimer(maxTimeInMilliseconds, 1000);



    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    public void startTimer(Long maxTime, int interval)
    {

        t = new CountDownTimer(maxTimeInMilliseconds, 1000) {


            public void onTick(long millisUntilFinished) {
                long remainedSecs = millisUntilFinished / 1000;
                maxTimeInMilliseconds = millisUntilFinished;
                timer.setText((remainedSecs/60 )/60+ ":" + (remainedSecs / 60)%60 + ":" + (remainedSecs % 60));// manage it accordign to you
            }

            public void onFinish() {
                timer.setText("00:00:00");
                showFinishTimeDialog();
//                Toast.makeText(FullTestActivity.this, "Finish", Toast.LENGTH_SHORT).show();





                cancel();
            }
        }.start();




//        hourglass = new Hourglass(maxTime, interval) {
//            @Override
//            public void onTimerTick(long timeRemaining)
//            {
////                long remainedSecs = timeRemaining / 1000;
//                maxTimeInMilliseconds = timeRemaining;
//                timer.setText(((timeRemaining/1000)/60 )/60+ ":" + ((timeRemaining/1000) / 60)%60 + ":" + ((timeRemaining/1000) % 60));
////                System.out.println(remainedSecs);// manage it accordign to you
//            }
//
//            @Override
//            public void onTimerFinish()
//            {
//                timer.setText("00:00:00");
//                Toast.makeText(FullTestActivity.this, "Finish", Toast.LENGTH_SHORT).show();
//            }
//        };




//        t = new CountDownTimer(maxTimeInMilliseconds, 1000) {
//
//
//            public void onTick(long millisUntilFinished) {
//                long remainedSecs = millisUntilFinished / 1000;
//                maxTimeInMilliseconds = millisUntilFinished;
//                timer.setText((remainedSecs/60 )/60+ ":" + (remainedSecs / 60)%60 + ":" + (remainedSecs % 60));// manage it accordign to you
//            }
//
//            public void onFinish() {
//                timer.setText("00:00:00");
//                Toast.makeText(FullTestActivity.this, "Finish", Toast.LENGTH_SHORT).show();
//
//                cancel();
//            }
//        }.start();
    }

    public void showFinishTimeDialog()
    {
        Dialog dialog = new Dialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogText = dialog.findViewById(R.id.dialog_text);
        buttonYes = dialog.findViewById(R.id.buttonOk);
        buttonNo = dialog.findViewById(R.id.buttonCancel);
        buttonNo.setVisibility(View.GONE);
        buttonYes.setText(R.string.ok);
        if(language.equals(Constant.KZ))
        {
//            buttonNo.setText(R.string.noKz);

            dialogText.setText(R.string.timeIsUpAlertKz);

        }
        else
        {
//            buttonNo.setText(R.string.noRu);
//            buttonYes.setText(R.string.yesRu);
            dialogText.setText(R.string.timeIsUpAlertRu);
        }
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYes.setEnabled(false);
                dialog.dismiss();
                finishTest(testIdMain);
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
    public void finishTest(Long testIdMain)
    {
        //        System.out.println("question");
//        System.out.println(questionId);
//        System.out.println(answerId);
//        System.out.println(tests.getTestId());
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
                                       Intent intent = new Intent(FullTestActivity.this, ResultsActivity.class);
                                       intent.putExtra(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
                                       intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
                                       intent.putExtra(Constant.TYPE, Constant.TYPEFULLTEST);
//                                       intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                                       intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);

                                       startActivity(intent);
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401")) {
                                    showToastUnauthorized();
                                }
//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                            }
                        }

                );
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SharedPreferences d = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE);
        d.edit().clear().apply();
        d.edit().commit();

    }


    public void getAccessToken()
    {
        SharedPreferences settings = this.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);
    }

    public void setR(RecyclerView recyclerView)
    {
        this.subjectResycleView = recyclerView;
        fullTestViewModel = new FullTestViewModel(this,subjectList, resultTag );
        activityFullTestBinding.setFull(fullTestViewModel);

        timer = activityFullTestBinding.timer;

        fullTestSubjectAdapter =
                new FullTestSubjectAdapter( this, subjectList);
//        fullTestSubjectAdapter.setHasStableIds(true);
        recyclerView.setAdapter(fullTestSubjectAdapter);
//        recyclerView.setItemAnimator(null);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        {
//            @Override
//            public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate)
//            {
//                return false;
//            }
//            @Override
//            public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
//                return false;
//            }
//        }
        );

        fullTestSubjectAdapter.setOnItemListener(new FullTestSubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subject item, int position) throws CloneNotSupportedException
            {
//                selectedSubjectPosition = position;
                currentPosition = position;
                changePricesInTheList(position);
                setFragment(arrayListArrayListQuestions.get(position), 0);
            }
        });


    }

    private void changePricesInTheList(int position) throws CloneNotSupportedException {

        ArrayList<Subject> models = new ArrayList<>();
        int previousPosition = 0;
//        int nextPosition;

        for (Subject model : subjectList) {
            models.add(model.clone());
        }

        for (int i = 0; i < models.size(); i++)
        {
            if (models.get(i).getOnClickedRecycle() == 1)
            {
                previousPosition = i;
                models.get(i).setOnClickedRecycle(0);


            }
        }
        models.get(position).setOnClickedRecycle(1);




        fullTestSubjectAdapter.setSubjectList(models);
//        setData(models);
    }


    public String getOwnersId()
    {
        ArrayList<Long> owners = new ArrayList<>();
        String str = "";
        for (int i = 0; i < subjectList.size(); i++)
        {
            if (i == subjectList.size() - 1)
                str += subjectList.get(i).getId();
            else
                str += subjectList.get(i).getId() + ", ";
        }
//        for (Subject s : subjectList)
//        {
////
//            str += s.getId()+", ";
//        }
        return str;
    }

    public void fetchFullTestGenerate()
    {
//        System.out.println(getOwnersId());

        fullTestViewModel.setProgress(true);

        TestGenerateCredentials credentials = new TestGenerateCredentials("ent", "full", getOwnersId());
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getTestGenerate(Constant.ACCEPT, language,
                "Bearer " + accessToken, credentials)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestGenerateResponse>()
                {
                    @Override
                    public void accept(TestGenerateResponse testGenerateResponse) throws Exception
                    {
                        setTestIdMain(testGenerateResponse.getTestGenerate().getTestId());
                        setTestGenerateResponse(testGenerateResponse);
                        testIdMain = testGenerateResponse.getTestGenerate().getTestId();
                        fetchFullTestQuestionsGenerate(testGenerateResponse.getTestGenerate().getTestId());
//                        fullTestViewModel.setProgress(false);

                    }

                }
                ,
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable.getMessage().contains("401"))
                        {
                            showToastUnauthorized();
//
                        }
                        fullTestViewModel.setProgress(false);
                    }
                }
                );

        compositeDisposable.add(disposable);
    }


    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
            Dialog dialog = new Dialog(this);
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
            dialogText = dialog.findViewById(R.id.dialog_text);
            buttonYes = dialog.findViewById(R.id.buttonOk);
            buttonNo = dialog.findViewById(R.id.buttonCancel);
            buttonNo.setVisibility(View.GONE);
            buttonYes.setText(R.string.ok);
            if(language.equals(Constant.KZ))
            {
//            buttonNo.setText(R.string.noKz);

                dialogText.setText(R.string.sessionErrorKz);

            }
            else
            {
//            buttonNo.setText(R.string.noRu);
//            buttonYes.setText(R.string.yesRu);
                dialogText.setText(R.string.sessionErrorRu);
            }
            buttonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    buttonYes.setEnabled(false);
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
        Intent intent = new Intent(this, AuthActivity.class);
        ((Activity)this).startActivity(intent);
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


    private void setTestGenerateResponse(TestGenerateResponse testGenerateResponse)
    {
        this.testGenerate = testGenerateResponse.getTestGenerate();
    }

    private void setTestIdMain(Long testId)
    {
        this.testIdMain = testId;
    }



    public void fetchFullTestQuestionsGenerate(Long id)
    {

        fullTestViewModel.setProgress(true);
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getQuestions(Constant.ACCEPT,
                language,
                "Bearer " + accessToken, id)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

//                                   System.out.println(jsonObject.toString());
                                   JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                   JSONObject config = jsonObject1.getJSONObject("config");
                                   int limit = config.getInt("time_limit");
                                   int remaining = config.getInt("time_remaining");
                                   if (isStartedFirst || hasActiveTest)
                                   {
                                       maxTimeInMilliseconds = TestsUtils.getTimeRemaining(limit, remaining);
                                   }
                                   ArrayList<Tests> questions =
                                           TestsUtils.deserializeFromJson(jsonObject);

//                                   System.out.println("questions");
//                                   System.out.println(questions);
//
                                   setArraListArrayListQuestions(questions);
//
                                   Tests arrayList = questions.get(currentPosition);
                                   String titleText = "ҰБТ";
                                   if (language.equals(Constant.RU))
                                   {
                                       titleText = "ЕНТ";
                                   }
                                   activityFullTestBinding.textViewTitle.setText(titleText);
                                   if (resultTag == null)
                                       startTimer(maxTimeInMilliseconds, 1000);

                                   setLogTest(arrayList.getTestId());


                                   setFragment(arrayList);
//                                   fullTestViewModel.setProgress(false);

                               }
                           },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
//                                    fullTestViewModel.setProgress(false);
                                }
                            }
                        }
                );

        compositeDisposable.add(disposable);


    }

    private void setArraListArrayListQuestions(ArrayList<Tests> questions)
    {
        this.arrayListArrayListQuestions = questions;
//        List<Tests> ppl = DataHolder.getInstance().arrayListArrayListQuestions;
//        System.out.println("List");
//        System.out.println(ppl);
    }

    public void setFragment(Tests arrayList)
    {

        fullTestViewModel.setProgress(true);
        this.numbersOFpages = arrayList.getQuestions().size();
        mPager = activityFullTestBinding.pager;
//        mPager.setOffscreenPageLimit(1);

//                    if (isStartedFirst)
//                    {

//                                   mPager.setAdapter( new MyAdapter(getSupportFragmentManager(), questions.get(0)));u
        mPager.setAdapter(new FullTestAdapter(getSupportFragmentManager(),arrayList, testIdMain,
                                            subjectList, currentPosition, resultTag,
                                            statisticTag));

        PageListener listener = new PageListener();
        mPager.addOnPageChangeListener(listener);
        setPageNumberToFragment();
//        fullTestViewModel.setProgress(false);
//        mPager.setOnPageChangeListener(listener);
    }

    public void setFragment(Tests arrayList, int pos)
    {

        this.numbersOFpages = arrayList.getQuestions().size();
        mPager = activityFullTestBinding.pager;
//        mPager.setOffscreenPageLimit(1);

//                    if (isStartedFirst)
//                    {

//                                   mPager.setAdapter( new MyAdapter(getSupportFragmentManager(), questions.get(0)));u
        mPager.setAdapter(new FullTestAdapter(getSupportFragmentManager(),arrayList, testIdMain,
                subjectList, currentPosition, resultTag));



        PageListener listener = new PageListener();
        mPager.addOnPageChangeListener(listener);
        selectedTestPosition = pos;
        setPageNumberToFragment();

    }

    private void setLogTest(Long testIdMain)
    {
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.logVisitTest(
                "Bearer " + accessToken, testIdMain)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

//                                   System.out.println(jsonObject.toString());
//                                   JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
//                                   JSONObject config = jsonObject1.getJSONObject("config");
//                                   int limit = config.getInt("time_limit");
//                                   int remaining = config.getInt("time_remaining");
//                                   if (isStartedFirst || hasActiveTest)
//                                   {
//                                       maxTimeInMilliseconds = TestsUtils.getTimeRemaining(limit, remaining);
//                                   }
//                                   ArrayList<Tests> questions =
//                                           TestsUtils.deserializeFromJson(jsonObject);
//
////                                   System.out.println("questions");
////                                   System.out.println(questions);
////
//                                   setArraListArrayListQuestions(questions);
////
//                                   Tests arrayList = questions.get(currentPosition);
//                                   String titleText = "ҰБТ";
//                                   if (language.equals(Constant.RU))
//                                   {
//                                       titleText = "ЕНТ";
//                                   }
//                                   activityFullTestBinding.textViewTitle.setText(titleText);
//                                   if (resultTag == null)
//                                       startTimer(maxTimeInMilliseconds, 1000);
//                                   setFragment(arrayList);
////                                   fullTestViewModel.setProgress(false);

                               }
                           },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
//                                    fullTestViewModel.setProgress(false);
                                }

                                System.out.println("Error wws");
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }

    public  void setPageNumberToFragment()
    {
        TextView tv = findViewById(R.id.text_number_pager);
        tv.setText(1 + " / " + numbersOFpages);


//        System.out.println("Timer start");
//        tv_Number.setText("Page :" + currentPage);
        mPager.setCurrentItem(selectedTestPosition);

        buttonForward = (ImageButton) activityFullTestBinding
                .buttonForward;
        buttonBack = (ImageButton) activityFullTestBinding
                .buttonBack;
        buttonForward.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (selectedTestPosition != numbersOFpages - 1)
                    mPager.setCurrentItem(++selectedTestPosition, true);
                else if (selectedTestPosition == numbersOFpages - 1 &&
                currentPosition == subjectList.size() - 1)
                {
                    showFinishDialog();
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (selectedTestPosition != 0)
                    mPager.setCurrentItem(--selectedTestPosition, true);
            }
        });

        fullTestViewModel.setProgress(false);
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
            selectedTestPosition = position;

//            FullTestAdapter fullTestAdapter = (FullTestAdapter) mPager.getAdapter();
//            TestFragment testFragment = (TestFragment) fullTestAdapter.getItem(position);
            TextView tv = findViewById(R.id.text_number_pager);
            tv.setText((selectedTestPosition + 1) + " / " + numbersOFpages);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Long time =  savedInstanceState.getLong(Constant.CURRENT_TIME, 10800000);
//        System.out.println("Time 2 ");
//        System.out.println(time);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constant.CURRENT_TIME, maxTimeInMilliseconds);
        editor.apply();
        editor.commit();
        super.onSaveInstanceState(outState);
        // call superclass to save any view hierarchy

    }

    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
    }

    private void showFinishDialog() {
        if (resultTag == null) {
            Dialog dialog = new Dialog(this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogTextFinish = dialog.findViewById(R.id.dialog_text);
            buttonYesFinish = dialog.findViewById(R.id.buttonOk);
            buttonNoFinish = dialog.findViewById(R.id.buttonCancel);
            if (language.equals(Constant.KZ)) {
                buttonNoFinish.setText(R.string.noKz);
                buttonYesFinish.setText(R.string.yesKz);
                dialogTextFinish.setText(R.string.finishTestDialogKz);

            } else {
                buttonNoFinish.setText(R.string.noRu);
                buttonYesFinish.setText(R.string.yesRu);
                dialogTextFinish.setText(R.string.finishTestDialogRu);
            }
            buttonYesFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishTest(testIdMain);


                    //System.out.println(testIdMain);//103080954

                }
            });

            buttonNoFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
