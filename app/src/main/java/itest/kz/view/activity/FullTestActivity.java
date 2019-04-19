package itest.kz.view.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
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

        language = settings.getString(Constant.LANG, "kz");
        getAccessToken();


        this.currentPosition = getIntent().getExtras().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);
        this.testIdMain = getIntent().getExtras().getLong(Constant.TEST_MAIN_ID);
        subjectList.get(currentPosition).setOnClickedRecycle(1);

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
        activityFullTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_full_test);
        setR(activityFullTestBinding.listSubjects);
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
                Toast.makeText(FullTestActivity.this, "Finish", Toast.LENGTH_SHORT).show();

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
                        fetchFullTestQuestionsGenerate(testGenerateResponse.getTestGenerate().getTestId());

                    }
                });

        compositeDisposable.add(disposable);
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
                                   activityFullTestBinding.textViewTitle.setText(arrayList.getTitle());
                                   if (resultTag == null)
                                       startTimer(maxTimeInMilliseconds, 1000);
                                   setFragment(arrayList);
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
        setPageNumberToFragment();
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

//            FullTestAdapter fullTestAdapter = (FullTestAdapter) mPager.getAdapter();
//            TestFragment testFragment = (TestFragment) fullTestAdapter.getItem(position);
            TextView tv = findViewById(R.id.text_number_pager);
            tv.setText((currentPage + 1) + " / " + numbersOFpages);
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



}
