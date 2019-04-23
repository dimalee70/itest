package itest.kz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityResultBinding;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.TestsUtils;
import itest.kz.view.adapters.ResultAdapter;
import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.viewmodel.ResultViewModel;

public class ResultActivity extends AppCompatActivity implements Observer
{

    public ResultViewModel resultViewModel;
    public ActivityResultBinding activityResultBinding;
    private Tests tests;
    private Test test;
    private String resultTag;
    private Subject selectedSubject;
    private Long testIdMain;
    private String language;
    private String accessToken;
    private Toolbar toolbar;
    private ImageButton buttonCloseResult;
    private String typeTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        SharedPreferences acessTok = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = acessTok.getString(Constant.ACCESS_TOKEN, "");
//        test = (Test)getIntent().getSerializableExtra("test");
        typeTest = getIntent().getExtras().getString(Constant.TYPE);
        tests = (Tests)getIntent().getSerializableExtra("tests");
        testIdMain = getIntent().getExtras().getLong(Constant.TEST_MAIN_ID);
        selectedSubject =(Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);

//        System.out.println(selectedSubject);
        resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, null);



        activityResultBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_result);
        if (tests != null)
        {
            setR(activityResultBinding.listAnswers);
        }
        else
        {
            fetchFullTestQuestionsGenerate(testIdMain);
        }


//
    }

//    public void fetchTestList()
//    {
////        System.out.println("subject");
////        System.out.println(subject);
//        TestGenerateCredentials credentials = new TestGenerateCredentials("ent", "subject", subject.getId().toString());
//        AppController appController = new AppController();
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
////        AppController appController = AppController.create(context);
//        SubjectService subjectService = appController.getSubjectService();
//
//        Disposable disposable = subjectService.getTestGenerate(Constant.ACCEPT,
//                language, "Bearer " + accessToken, credentials)
//                .subscribeOn(appController.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<TestGenerateResponse>()
//                           {
//                               @Override
//                               public void accept(TestGenerateResponse testGenerateResponse) throws Exception
//                               {
//                                   fetchFullTestQuestionsGenerate(testGenerateResponse.getTestGenerate().getTestId());
//                               }
//                           }
////                        new Consumer<List<Question>>() {
////                               @Override
////                               public void accept(List<Question> tests) throws Exception {
//////                                   System.out.println("Test list");
//////                                   System.out.println(tests);
////                                   updateTestDataList(tests);
////                               }
////                           }
//                );
////      id=58756, question='<p><meta charset="utf-8" /></p>
//        //              <p dir="ltr">Вычислите: cos <span class="math-tex">\(cos\)</span>&nbsp;78<span class="math-tex">\(^{\circ}\)</span>&nbsp;cos&nbsp;<span class="math-tex">\(cos\)</span> 18<span class="math-tex">\(^{\circ}\)</span>&nbsp;+ sin&nbsp;<span class="math-tex">\(sin\)</span> 78<span class="math-tex">\(^{\circ}\)</span> sin&nbsp;<span class="math-tex">\(sin\)</span>&nbsp;18<span class="math-tex">\(^{\circ}\)</span>.</p>', description='<p>cos&nbsp;<span class="math-tex">\(cos\)</span> 78<span class="math-tex">\(^{\circ}\)</span> cos&nbsp;<span class="math-tex">\(cos\)</span> 18<span class="math-tex">\(^{\circ}\)</span>&nbsp;+ sin&nbsp;<span class="math-tex">\(sin\)</span> 78<span class="math-tex">\(^{\circ}\)</span> sin&nbsp;<span class="math-tex">\(sin\)</span> 18<span class="math-tex">\(^{\circ}\)</span> = cos&nbsp;<span class="math-tex">\(cos\)</span> (78<span class="math-tex">\(^{\circ}\)</span>-18<span class="math-tex">\(^{\circ}\)</span>)= cos&nbsp;<span class="math-tex">\(cos\)</span> 60<span class="math-tex">\(^{\circ}\)</span> = <span class="math-tex">\(\frac12\)</span> = 0,5.</p>
////    <p><meta charset="utf-8" /></p>', nodeId=249, subjectId=1, langId=1, examSubjectId=0, difficultyLevel=1, checked=0, answerType=8, answers=[Answer{id=309695, questionId=58756, answer='<p><span class="math-tex">\(-\frac12\)</span></p>', correct=0, letter=null}, Answer{id=309696, questionId=58756, answer='<p><span class="math-tex">\(\frac12\)</span></p>', correct=1, letter=null}, Answer{id=309697, questionId=58756, answer='<p>1</p>', correct=0, letter=null}, Answer{id=309698, questionId=58756, answer='<p>0</p>', correct=0, letter=null}, Answer{id=309699, questionId=58756, answer='<p><span class="math-tex">\(\sqrt3\)</span></p>', correct=0, letter=null}, Answer{id=309700, questionId=58756, answer='<p>0,5</p>', correct=1, letter=null}, Answer{id=309701, questionId=58756, answer='<p><span class="math-tex">\(-\sqrt3\)</span></p>', correct=0, letter=null}, Answer{id=309702, questionId=58756, answer='<p>&ndash; 0,5</p>', correct=0, letter=null}]}
//
//        compositeDisposable.add(disposable);
//    }

    private void fetchFullTestQuestionsGenerate(Long testId)
    {
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

//                                   System.out.println(jsonObject.toString());
                                   Tests questions =
                                           TestsUtils.deserializeFromJsonToTests(jsonObject);
                                   updateTestDataList(questions);
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

    private void updateTestDataList(Tests questions)
    {
        this.tests = questions;
        setR(activityResultBinding.listAnswers);
    }

    public void setR(RecyclerView recyclerView)
    {
        resultViewModel = new ResultViewModel(this,tests);
        activityResultBinding.setResult(resultViewModel);

        toolbar = activityResultBinding.myToolbar;
//                findViewById(R.id.my_toolbar);
        buttonCloseResult = activityResultBinding.buttonCloseResult;
//                findViewById(R.id.buttonCloseResult);
        buttonCloseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                System.out.println("Close");
//                finish();
                ResultActivity.super.onBackPressed();
            }
        });

        if (selectedSubject == null)
        {
            selectedSubject = tests.getSubject();
        }
        ResultAdapter resultAdapter = new ResultAdapter(tests, this,
                selectedSubject, testIdMain,null,
                null,null,0, resultTag, typeTest);

//        recyclerView.setOnClickListener();
//
//        resultAdapter.setOnItemListener(new ResultAdapter.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(Test test,r )
//            {
//
//            }
//        });
//        resultAdapter.setOnItemListener(new ResultAdapter.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(Test test, List<Test> testList)
//            {
//                tests = testList;
//                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
//////        intent.putExtra()
//                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, num );
//////        context.startActivity(TestActivity.fillSelectedSubject(view.etContext(), subject));
//////                StartActivityForResult(intent, 0);
////                context.startActivity(intent);
//            }
//        });
        recyclerView.setAdapter(resultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void update(Observable o, Object arg)
    {

    }

    @Override
    public void onBackPressed()
    {
//        Intent parentIntent = NavUtils.getParentActivityIntent(this);
////        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(parentIntent);
        finish();
//        super.onBackPressed();
    }
}
