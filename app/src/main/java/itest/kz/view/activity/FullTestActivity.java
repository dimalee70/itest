package itest.kz.view.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
import itest.kz.view.adapters.FullTestAdapter;
import itest.kz.view.adapters.FullTestSubjectAdapter;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.view.adapters.ResultAdapter;
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
    private Toolbar myToolbar;
    private ArrayList<Tests> arrayListArrayListQuestions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getAccessToken();
        subjectList = getIntent().getParcelableArrayListExtra("subjects");
        fetchFullTestGenerate();
//        System.out.println(subjectList.toString());
        activityFullTestBinding = DataBindingUtil.setContentView(this, R.layout.activity_full_test);
        myToolbar = activityFullTestBinding.myToolbar;
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        setR(activityFullTestBinding.listSubjects);



    }

    public void getAccessToken()
    {
        SharedPreferences settings = this.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);
    }

    public void setR(RecyclerView recyclerView)
    {
        fullTestViewModel = new FullTestViewModel(this,subjectList);
        activityFullTestBinding.setFull(fullTestViewModel);

        FullTestSubjectAdapter fullTestSubjectAdapter =
                new FullTestSubjectAdapter( this, subjectList);
        recyclerView.setAdapter(fullTestSubjectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fullTestSubjectAdapter.setOnItemListener(new FullTestSubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subject item, int position)
            {
//                System.out.println("Position");
//                System.out.println(position);
                setFragment(arrayListArrayListQuestions.get(position));
            }
        });

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

        Disposable disposable = subjectService.getTestGenerate(Constant.ACCEPT, "ru",
                "Bearer " + accessToken, credentials)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestGenerateResponse>()
                {
                    @Override
                    public void accept(TestGenerateResponse testGenerateResponse) throws Exception
                    {
                        fetchFullTestQuestionsGenerate(testGenerateResponse.getTestGenerate().getTestId(),
                                testGenerateResponse.getTestGenerate());
                    }
                });

        compositeDisposable.add(disposable);
    }

    public  ArrayList<Tests> deserializeFromJson(TestGenerate testGenerate, JsonObject jsonObject) throws JSONException
    {
//        ArrayList<ArrayList<Question>> arrayListsQuestions =
//                new ArrayList<>();

//        System.out.println(accessToken);
        ArrayList<Tests> testsArrayList = new ArrayList<>();
        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
        JSONObject data = jsonObject1.getJSONObject("data");
        JSONArray tests = data.getJSONArray("tests");

        Gson gson = new Gson();
        for (int i=0; i < tests.length(); i++)
        {
            JSONObject testItem = tests.getJSONObject(i);

            JSONArray questions = testItem.getJSONArray("questions");
            JSONObject test = testItem.getJSONObject("test");
//            JSONObject subject = testItem.getJSONObject("subject");
            Subject subject = gson.fromJson(testItem.getJSONObject("subject").toString(), Subject.class);
            Long testId = test.getLong("id");

            ArrayList<Question> questionsList = new ArrayList<>();
            for (int j = 0; j < questions.length(); j++) {
                Question obj = gson.fromJson(questions.getJSONObject(j).toString(),Question.class);
                if (testItem.has("texts")) {
                    JSONObject texts = testItem.getJSONObject("texts");

                    if (obj.getTextId() != null) {

                        if (texts.has(obj.getTextId().toString())) {

                            JSONObject textsId = texts.getJSONObject
                                    (obj.getTextId().toString());
                            String t = textsId.getString("text");
                            obj.setText(t);
                        }
                    }

                }
                questionsList.add(obj);
            }

            testsArrayList.add(new Tests(questionsList, testId, subject));

//            if (testItem.has("texts"))
//            {
//                JSONObject texts = testItem.getJSONObject("texts");
//
//            }




//            System.out.println(test.toString());
//            JSONArray questions = tests.getJSONArray(i);

//            JSONObject test = tests.getJSONObject(i);
//            System.out.println(test.toString());
//
//            System.out.println();
//            System.out.println(i);
//            System.out.println();
//            Question obj = gson.fromJson(questions.getJSONObject(i).toString(),Question.class);
//            if (testId.has("texts"))
//            {
//                JSONObject texts = testId.getJSONObject("texts");
//                if (texts.has(obj.getQuestionId().toString()))
//                {
//                    JSONObject textsId = texts.getJSONObject
//                            (obj.getQuestionId().toString());
//                    String t = textsId.getString("text");
//                    obj.setText(t);
//                }
//
//            }
//            questionsList.add(obj);
        }

        return testsArrayList;
//        arrayListsQuestions.add(questionsList);
//
//        System.out.println(tests.toJSONObject());





//        JSONObject tests = jsonObject1.getJSONObject("tests");
//        for (Long id : testGenerate.getOwners())
//        {
////            System.out.println("id ");
////            System.out.println(id);
//            JSONObject testId = tests.getJSONObject(id.toString());
//            JSONObject data = testId.getJSONObject("data");
//            JSONArray questions = data.getJSONArray("questions");
//
//            Gson gson = new Gson();
//            ArrayList<Question> questionsList = new ArrayList<>();
//            for (int i=0; i < questions.length(); i++) {
//                Question obj = gson.fromJson(questions.getJSONObject(i).toString(),Question.class);
//                if (testId.has("texts"))
//                {
//                    JSONObject texts = testId.getJSONObject("texts");
//                    if (texts.has(obj.getQuestionId().toString()))
//                    {
//                        JSONObject textsId = texts.getJSONObject
//                                (obj.getQuestionId().toString());
//                        String t = textsId.getString("text");
//                        obj.setText(t);
//                    }
//
//                }
//                questionsList.add(obj);
//            }
//            arrayListsQuestions.add(questionsList);
//
//        }
//
//        System.out.println("q");
//        System.out.println(arrayListsQuestions);

//        return arrayListsQuestions;


    }

    public void fetchFullTestQuestionsGenerate(Long id, TestGenerate testGenerate)
    {
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getQuestions(Constant.ACCEPT,
                "ru",
                "Bearer " + accessToken, id)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

//                                   System.out.println(jsonObject.toString());
                                   ArrayList<Tests> questions =
                                   deserializeFromJson(testGenerate, jsonObject);
//
                                   setArraListArrayListQuestions(questions);
//
                                   Tests arrayList = questions.get(0);

//                                   for (Tests t : questions)
//                                   {
//                                       for (Question q : t.getQuestions())
//                                       {
//                                           System.out.println(q.getText());
//                                       }
//                                   }
////
                                   setFragment(arrayList);

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

    private void setArraListArrayListQuestions(ArrayList<Tests> questions)
    {
        this.arrayListArrayListQuestions = questions;
    }

    public void setFragment(Tests arrayList)
    {
        mPager = activityFullTestBinding.pager;
        mPager.setOffscreenPageLimit(2);

//                    if (isStartedFirst)
//                    {

//                                   mPager.setAdapter( new MyAdapter(getSupportFragmentManager(), questions.get(0)));u
        mPager.setAdapter(new FullTestAdapter(getSupportFragmentManager(),arrayList));



    }



}
