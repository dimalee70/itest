package itest.kz.view.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ankushgrover.hourglass.Hourglass;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityFulltestResultBinding;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.CustomViewPager;
import itest.kz.util.TestViewModelFactory;
import itest.kz.util.TestsUtils;
import itest.kz.view.adapters.FullTestResultAdapter;
import itest.kz.view.fragments.OneFromFullResultFragment;
import itest.kz.viewmodel.FulltestResultViewModel;
import itest.kz.viewmodel.TestViewModel;

public class FulltestResultActivity extends AppCompatActivity
{
    private ActivityFulltestResultBinding activityFulltestResultBinding;
    private FulltestResultViewModel fulltestResultViewModel;
    private ArrayList<Tests> arrayListArrayListQuestions;
    private FullTestResultAdapter fullTestResultAdapter;
    public CustomViewPager mPager;
    private Long testIdMain;
    private String accessToken;
    private String language;
    private String resultTag;
    private ArrayList<Subject> subjectsList;
    private Integer currentPosition;
    private Integer  selectedSubjectPosition;
    private Long maxTimeInMilliseconds;
    private  CountDownTimer t;
    private ImageButton buttonCloseResult;
    private Subject selectedSubject;
    private TextView subjectTitleText;
    private ImageButton buttonNext;
    private ImageButton buttonPrevious;

//    SharedPreferences sharedPreferences = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE);
//    SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putLong(Constant.CURRENT_TIME, maxTimeInMilliseconds);
//        editor.apply();
//        editor.commit();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        SharedPreferences settings = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = settings.getString(Constant.ACCESS_TOKEN, "");

        SharedPreferences lang = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");
//        SharedPreferences time = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE);
//        settings.edit().clear().commit();
//        maxTimeInMilliseconds = time.getLong(Constant.CURRENT_TIME, 10800000);

        this.testIdMain = getIntent().getExtras().getLong(Constant.TEST_MAIN_ID);
        this.subjectsList = getIntent().getExtras().getParcelableArrayList(Constant.SUBJECT_LIST);
        this.currentPosition = getIntent().getExtras().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);

        this.resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, null);
        this.selectedSubject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
        maxTimeInMilliseconds = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE)
                .getLong(Constant.CURRENT_TIME, 10800000);
        startTimer(maxTimeInMilliseconds, 1000);




        activityFulltestResultBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_fulltest_result);
        fulltestResultViewModel = new FulltestResultViewModel(this);
        activityFulltestResultBinding.setFull(fulltestResultViewModel);
        setTestsResults();

        subjectTitleText = activityFulltestResultBinding
                .subjectTitleText;
        buttonPrevious = activityFulltestResultBinding
                .buttonPrefiousResult;
        buttonNext = activityFulltestResultBinding
                .buttonNextResult;

        buttonCloseResult = activityFulltestResultBinding
                .buttonCloseResult;
        buttonCloseResult.setOnClickListener
                (new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        finish();
                    }
                });

//        testViewModel = ViewModelProviders.of(this, new TestViewModelFactory(this.getApplication(),
//                selectedSubject)).get(TestViewModel.class);
    }

    public  void setTestsResults()
    {
        fetchTestsByTestId(testIdMain);
    }


    private void fetchTestsByTestId(Long testIdMain)
    {
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getQuestions(Constant.ACCEPT,
                language,
                "Bearer " + accessToken, testIdMain)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

//                                   System.out.println(jsonObject.toString());
                                   ArrayList<Tests> questions =
                                           TestsUtils.deserializeFromJson(jsonObject);
//
                                   setArraListArrayListQuestions(questions);

                                   mPager = activityFulltestResultBinding.pager;
                                   mPager.setOffscreenPageLimit(5);
                                   fullTestResultAdapter = new FullTestResultAdapter(getSupportFragmentManager()
                                           ,questions, testIdMain,
                                           subjectsList, currentPosition, resultTag, Constant.TYPEFULLTEST, selectedSubject);
                                   mPager.setAdapter(fullTestResultAdapter);

                                   PageListener listener = new PageListener();
                                   mPager.addOnPageChangeListener(listener);



                                   mPager.setCurrentItem(currentPosition);
                                   subjectTitleText.setText
                                           (subjectsList.get(currentPosition).getTitle());

                                   buttonPrevious.setOnClickListener(new View.OnClickListener()
                                   {
                                       @Override
                                       public void onClick(View v)
                                       {
                                           if (currentPosition != 0)
                                               mPager.setCurrentItem(--currentPosition, true);
                                       }
                                   });

                                   buttonNext.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v)
                                       {
                                           if (currentPosition != mPager.getChildCount() - 1)
                                               mPager.setCurrentItem(++currentPosition, true);
                                       }
                                   });

                               }
                           }
                );

        compositeDisposable.add(disposable);
    }

    public void startTimer(Long maxTime, int interval) {

        t = new CountDownTimer(maxTimeInMilliseconds, 1000) {


            public void onTick(long millisUntilFinished) {
                long remainedSecs = millisUntilFinished / 1000;
                maxTimeInMilliseconds = millisUntilFinished;
//                timer.setText((remainedSecs/60 )/60+ ":" + (remainedSecs / 60)%60 + ":" + (remainedSecs % 60));// manage it accordign to you
            }

            public void onFinish() {
//                timer.setText("00:00:00");
//                Toast.makeText(FullTestActivity.this, "Finish", Toast.LENGTH_SHORT).show();

                cancel();
            }
        }.start();
    }


    public class PageListener extends ViewPager.SimpleOnPageChangeListener {

        public int getCurrentPage() {
            return currentPosition;
        }
        private int lastPage = 0;

        public void setCurrentPage(int currentPageArg) {
            currentPosition = currentPageArg;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//            OneFromFullResultFragment f =(OneFromFullResultFragment) fullTestResultAdapter.getItem(mPager.getCurrentItem());
//            f.setCurrentPosition(position);

        }

        public void onPageSelected(int position)
        {
            if(lastPage > position)
            {
                currentPosition = position;
                lastPage = position;
                subjectTitleText.setText
                        (subjectsList.get(currentPosition).getTitle());
            }
            else
            {
                currentPosition = position;
                lastPage = position;
                subjectTitleText.setText
                        (subjectsList.get(currentPosition).getTitle());
            }


        }
    }


    private void setArraListArrayListQuestions(ArrayList<Tests> questions)
    {
        this.arrayListArrayListQuestions = questions;
    }

    public int getPosition()
    {
        return mPager.getCurrentItem();
    }


    public void setPosition (int pos)
    {
        mPager.setCurrentItem(pos);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    public void startTimer(Long maxTime, int interval)
//    {
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

    @Override
    public void onSaveInstanceState(Bundle outState)
    {

//        outState.putLong(Constant.CURRENT_TIME, maxTimeInMilliseconds);
//        System.out.println("Time ");
//        System.out.println(maxTimeInMilliseconds);
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constant.CURRENT_TIME, maxTimeInMilliseconds);
        editor.apply();
        editor.commit();
        super.onSaveInstanceState(outState);
        // call superclass to save any view hierarchy

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SharedPreferences d = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE);
        d.edit().clear().apply();
        d.edit().commit();

    }
}
