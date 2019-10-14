package itest.kz.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ankushgrover.hourglass.Hourglass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.TestGenerate;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.AppUtils;
import itest.kz.util.CenterLayoutManager;
import itest.kz.util.Constant;
import itest.kz.util.CustomViewPager;
import itest.kz.util.TestsUtils;
import itest.kz.view.adapters.FullTestAdapter;
import itest.kz.view.adapters.FullTestSubjectAdapter;
import itest.kz.viewmodel.FullTestViewModel;

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
    private Test testResult;

    public  boolean changed = false;
    public boolean isSetFirstItem = false;
    private boolean isLastPageSwiped = false;
    private boolean isLastPageSwipedEnd = false;

    private FullTestAdapter fullTestAdapter;
    private int count = 0;
    private  boolean isShowFinishDialog = false;
    private boolean isRight = false;
    private  boolean isLeft = false;
//    private Queue<Boolean> changedQueue = new LinkedList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

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

        try {
            setR(activityFullTestBinding.listSubjects);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if (isStartedFirst)
        {
            fetchFullTestGenerate();

        }
        else
        {
            maxTimeInMilliseconds = (Long) getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE)
                    .getLong(Constant.CURRENT_TIME, 10800000);
            fetchFullTestQuestionsGenerate(testIdMain);

        }



        activityFullTestBinding.buttonFinishTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickOnButtonClose();
            }
        });
    }

    public void clickOnButtonClose()
    {
        try {


            if (resultTag == null)
            {
                AppUtils.setLocale((language.equals(Constant.KZ))?"en": Constant.RU, this);
                Dialog dialog = new Dialog(this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogText = dialog.findViewById(R.id.dialog_text);
                buttonYes = dialog.findViewById(R.id.buttonOk);
                buttonNo = dialog.findViewById(R.id.buttonCancel);
                buttonNo.setText(R.string.no);
                buttonYes.setText(R.string.yes);
                dialogText.setText(R.string.finishTestDialog);

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishTest(testIdMain);
                        dialog.dismiss();

                    }
                });

                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else
            {
                openResultFragment();
            }
        }catch (Exception e)
        {

        }
    }

    public void openResultFragment()
    {
        TestFinishResponse testFinishResponse = new TestFinishResponse
                ("true", true, testResult.getResult());
        Intent intent = new Intent(FullTestActivity.this, ResultsActivity.class);
        intent.putExtra(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
        intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
        intent.putExtra(Constant.TYPE, Constant.TYPEFULLTEST);
//                                       intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
        intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);
        intent.putExtra(Constant.STATISTIC_TAG, statisticTag);

        startActivity(intent);
    }


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
                cancel();
            }
        }.start();
    }

    public void showFinishTimeDialog()
    {
        Dialog dialog = new Dialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                finishTest(testIdMain);
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
        dialogText.setText(R.string.timeIsUpAlert);
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
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.finishTest(
                language, testIdMain)
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

    public void setR(RecyclerView recyclerView) throws CloneNotSupportedException {
        this.subjectResycleView = recyclerView;
        fullTestViewModel = new FullTestViewModel(this,subjectList, resultTag );
        activityFullTestBinding.setFull(fullTestViewModel);

        timer = activityFullTestBinding.timer;

        fullTestSubjectAdapter =
                new FullTestSubjectAdapter( this, subjectList);


        recyclerView.setAdapter(fullTestSubjectAdapter);

        recyclerView.setLayoutManager(new CenterLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//

        fullTestSubjectAdapter.setOnItemListener(new FullTestSubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subject item, int position) throws CloneNotSupportedException
            {
                initSubjectTest(position);

            }
        });
        try {
            activityFullTestBinding.listSubjects.smoothScrollToPosition(currentPosition);
        }
        catch (Exception e)
        {
        }



    }

    private void initSubjectTest(int position) throws CloneNotSupportedException
    {
        try {
            currentPosition = position;
            View v = activityFullTestBinding.listSubjects
                    .findViewHolderForAdapterPosition(position)
                    .itemView;
            int itemToScroll = activityFullTestBinding.listSubjects.getChildLayoutPosition(v);
            int centerOfScreen = activityFullTestBinding.listSubjects.getWidth() / 2 - v.getWidth() / 2;
//                ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(itemToScroll, centerOfScreen);
            activityFullTestBinding.listSubjects.smoothScrollToPosition(currentPosition);

            changePricesInTheList(position);
            setFragment(arrayListArrayListQuestions.get(position), 0);

        }catch (Exception e)
        {

        }

    }

    private void changePricesInTheList(int position) throws CloneNotSupportedException {

        ArrayList<Subject> models = new ArrayList<>();
        int previousPosition = 0;

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
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.getTestGenerate(language,
                 credentials)
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

            dialogText.setText(R.string.sessionError);
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

            dialog.show();
        }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(this, AuthActivity.class);
        ((Activity)this).startActivity(intent);

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
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.getQuestions(
                language,
                 id)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

                                   Gson gson = new GsonBuilder()
                                           .setDateFormat("yyyy-mm-dd HH:MM:SS")
                                           .create();
//                                   System.out.println(jsonObject.toString());
                                   JSONObject jsonObject1 = new JSONObject(jsonObject.toString());

                                   if (jsonObject1.has("test"))
                                       testResult =
                                               gson.fromJson(jsonObject1.getJSONObject("test").toString(), Test.class);
                                   JSONObject config = jsonObject1.getJSONObject("config");
                                   int limit = config.getInt("time_limit");
                                   int remaining = config.getInt("time_remaining");
                                   if (isStartedFirst || hasActiveTest)
                                   {
                                       maxTimeInMilliseconds = TestsUtils.getTimeRemaining(limit, remaining);
                                   }
                                   ArrayList<Tests> questions =
                                           TestsUtils.deserializeFromJson(jsonObject);

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

    }

    public void setFragment(Tests arrayList)
    {

        fullTestViewModel.setProgress(true);
        this.numbersOFpages = arrayList.getQuestions().size();
        mPager = activityFullTestBinding.pager;
        fullTestAdapter = new FullTestAdapter(getSupportFragmentManager(),arrayList, testIdMain,
                subjectList, currentPosition,
                statisticTag, resultTag);
        mPager.setAdapter(fullTestAdapter);

        PageListener listener = new PageListener();
        mPager.addOnPageChangeListener(listener);
        setPageNumberToFragment();
    }

    public void setFragment(Tests arrayList, int pos)
    {

        this.numbersOFpages = arrayList.getQuestions().size();
        mPager = activityFullTestBinding.pager;
        fullTestAdapter = new FullTestAdapter(getSupportFragmentManager(),arrayList, testIdMain,
                subjectList, currentPosition, statisticTag, resultTag);
        mPager.setAdapter(fullTestAdapter);
        mPager.setOffscreenPageLimit(1);

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
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.logVisitTest(
                 testIdMain)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {
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

    public  void setPageNumberToFragment()
    {
        TextView tv = findViewById(R.id.text_number_pager);
        tv.setText(1 + " / " + numbersOFpages);

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

                    showFinishDialog(false);
                }
//                else if (resultTag != null
//                        && selectedTestPosition == numbersOFpages - 1
//                        && currentPosition == subjectList.size() - 1
//                )
//                {
//                    openResultFragment();
//                }
                else if (selectedTestPosition == numbersOFpages - 1
                    && currentPosition != subjectList.size() - 1)
                {
                    try {
                        initSubjectTest(++currentPosition);

                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
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
                else if (selectedTestPosition == 0 && currentPosition != 0)
                {
                    try {
                        initSubjectTest(--currentPosition);
                        mPager.setCurrentItem(arrayListArrayListQuestions.get(currentPosition).getQuestions().size() - 1);

                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        fullTestViewModel.setProgress(false);
    }

    public class PageListener extends ViewPager.SimpleOnPageChangeListener {

        private static final float thresholdOffset = 0.5f;
        private boolean scrollStarted, checkDirection;



        public void onPageSelected(int position) {

//            if(selectedTestPosition > position)
//            {//User Move to left
////                System.out.println("left");
//                left = false;
//                right = true;
////                isRight = false;
//            }
//            else if (selectedTestPosition < position)
//            {
//                right = false;
//                left = true;
////                System.out.println("right");
////                isRight = true;
//            }

            selectedTestPosition = position;

            TextView tv = findViewById(R.id.text_number_pager);
            if (isSetFirstItem) {
//                selectedTestPosition = 0;
//                ++count;
//                tv.setText((1) + " / " + numbersOFpages);
                mPager.setCurrentItem(0);
                mPager.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onPageSelected(0);
                    }
                });
                isSetFirstItem = false;

            } else {
                if (selectedTestPosition == 0) {
                    tv.setText((1) + " / " + numbersOFpages);
                } else {

                    tv.setText((selectedTestPosition + 1) + " / " + numbersOFpages);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            int lastIdx = fullTestAdapter.getCount() - 1;
            int firstIdx = 0;


            if(selectedTestPosition==lastIdx  && state==1 && currentPosition != arrayListArrayListQuestions.size() - 1){
                isLastPageSwiped = true;
                isSetFirstItem = true;
                isRight = true;
                try {

                    initSubjectTest(++currentPosition);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                // i put this here since onPageScroll gets called a couple of times.
//                finish();
            }
//            else if (selectedTestPosition == firstIdx && state == 1 && currentPosition != 0)
//            {
//                try {
//                    initSubjectTest(--currentPosition);
//                    mPager.setCurrentItem(arrayListArrayListQuestions.get(currentPosition).getQuestions().size() - 1);
//                    mPager.post(new Runnable()
//                    {
//                        @Override
//                        public void run()
//                        {
//                            onPageSelected(arrayListArrayListQuestions.get(currentPosition).getQuestions().size() - 1);
//                        }
//                    });
//                } catch (CloneNotSupportedException e) {
//                    e.printStackTrace();
//                }
//            }

        }



        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            if (selectedTestPosition == arrayListArrayListQuestions
            .get(currentPosition).getQuestions().size() - 1
            && positionOffset == 0 )
            {

                if (currentPosition == arrayListArrayListQuestions.size() - 1 && !isShowFinishDialog)
                {
                    if (isLastPageSwipedEnd)
                    {
                        if(isRight) {
                            isLastPageSwipedEnd = false;

                            isShowFinishDialog = true;
                            showFinishDialog(true);
                            isRight = false;

                        }
                        isRight = true;
                    } else {
                        isRight = false;
                        isLastPageSwipedEnd = true;
                    }
                }
                if(selectedTestPosition == arrayListArrayListQuestions
                        .get(currentPosition).getQuestions().size() - 2
                        && positionOffset == 0 )
                {
                    isLastPageSwipedEnd = false;
                    isRight = false;
                    isShowFinishDialog =false;
                }

            }

        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Long time =  savedInstanceState.getLong(Constant.CURRENT_TIME, 10800000);
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

    }

    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
    }



    private void showFinishDialog(boolean fromSwipe) {
        if (resultTag == null) {
            Dialog dialog = new Dialog(this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogTextFinish = dialog.findViewById(R.id.dialog_text);
            buttonYesFinish = dialog.findViewById(R.id.buttonOk);
            buttonNoFinish = dialog.findViewById(R.id.buttonCancel);
            buttonNoFinish.setText(R.string.no);
            buttonYesFinish.setText(R.string.yes);
            dialogTextFinish.setText(R.string.finishTestDialog);
            buttonYesFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishTest(testIdMain);
                    if (fromSwipe)
                        changeTags();

                    //System.out.println(testIdMain);//103080954

                }
            });

            buttonNoFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (fromSwipe)
                        changeTags();

                }
            });
            dialog.show();
        }
        else
            openResultFragment();
    }
    private void changeTags ()
    {
        isShowFinishDialog = false;
        isRight = true;
    }

}
