package itest.kz.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.Answer;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.TestGenerate;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.TestsUtils;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.fragments.TestFragment;

import static android.content.Context.MODE_PRIVATE;

public class TestViewModel extends AndroidViewModel
{
    static final int ITEMS = 20;

    private MutableLiveData<Tests> listMutableLiveData;

    private Subject subject;
    private String language;
    private String accessToken;
    public Tests testsList;
//    private  List<Question> testList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private String typeTest;
    private boolean isStartedFirst;
    public Action onClickForward;

    public LiveData<Tests> getTests() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<Tests>();
            if (testsList == null)
                fetchTestList();
            else
                fetchFullTestQuestionsGenerate(testsList.getTestId());
        }
        return listMutableLiveData;
    }

    public TestViewModel(Application application, Subject subject, String typeTest, boolean isStartedFirst, Tests testsList)
    {
        super(application);
        this.testsList = testsList;
        this.subject = subject;
        this.context = application;
        this.isStartedFirst = isStartedFirst;
        SharedPreferences settings = application.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);
        SharedPreferences lang = application.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");
        this.typeTest = typeTest;
//        this.onClickForward = () ->
//        {
//            System.out.println("Dfvdf");
//        };
//        System.out.println("MV");
//        System.out.println(subject);
//        fetchTestList();

    }
//
//    public TestViewModel(Context context, Subject subject)
//    {
//        this.subject = subject;
//        this.context = context;
//        this.testList = new ArrayList<>();
////        testList.add(new Test(1,"2121","323232",111,12,1,2,2, 1, 1, Arrays.asList(new Answer[]{new Answer(1, 1, "2", 1, 1),})));
//        fetchTestList();
//    }

    public void setTestList(  Tests testList)
    {
        this.testsList = testList;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public String getSubjectId()
    {
        return String.valueOf(subject.getId());
    }

//    public String getTitle()
//    {
//        if (typeTest.equals(Constant.TYPEFULLTEST))
//            return testsList.getTitle();
//        return testsList.getSubject().getTitle();
//    }



    public void fetchTestList()
    {
//        System.out.println("subject");
//        System.out.println(subject);
        TestGenerateCredentials credentials = new TestGenerateCredentials("ent", typeTest, subject.getId().toString());
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getTestGenerate(Constant.ACCEPT,
                language, "Bearer " + accessToken, credentials)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestGenerateResponse>()
                           {
                               @Override
                               public void accept(TestGenerateResponse testGenerateResponse) throws Exception
                               {
//                                   System.out.println("testGenerate");
//                                   System.out.println(testGenerateResponse.getTestGenerate());
                                   fetchFullTestQuestionsGenerate(testGenerateResponse.getTestGenerate().getTestId());
                               }
                           }
//                        new Consumer<List<Question>>() {
//                               @Override
//                               public void accept(List<Question> tests) throws Exception {
////                                   System.out.println("Test list");
////                                   System.out.println(tests);
//                                   updateTestDataList(tests);
//                               }
//                           }
                );
//      id=58756, question='<p><meta charset="utf-8" /></p>
  //              <p dir="ltr">Вычислите: cos <span class="math-tex">\(cos\)</span>&nbsp;78<span class="math-tex">\(^{\circ}\)</span>&nbsp;cos&nbsp;<span class="math-tex">\(cos\)</span> 18<span class="math-tex">\(^{\circ}\)</span>&nbsp;+ sin&nbsp;<span class="math-tex">\(sin\)</span> 78<span class="math-tex">\(^{\circ}\)</span> sin&nbsp;<span class="math-tex">\(sin\)</span>&nbsp;18<span class="math-tex">\(^{\circ}\)</span>.</p>', description='<p>cos&nbsp;<span class="math-tex">\(cos\)</span> 78<span class="math-tex">\(^{\circ}\)</span> cos&nbsp;<span class="math-tex">\(cos\)</span> 18<span class="math-tex">\(^{\circ}\)</span>&nbsp;+ sin&nbsp;<span class="math-tex">\(sin\)</span> 78<span class="math-tex">\(^{\circ}\)</span> sin&nbsp;<span class="math-tex">\(sin\)</span> 18<span class="math-tex">\(^{\circ}\)</span> = cos&nbsp;<span class="math-tex">\(cos\)</span> (78<span class="math-tex">\(^{\circ}\)</span>-18<span class="math-tex">\(^{\circ}\)</span>)= cos&nbsp;<span class="math-tex">\(cos\)</span> 60<span class="math-tex">\(^{\circ}\)</span> = <span class="math-tex">\(\frac12\)</span> = 0,5.</p>
//    <p><meta charset="utf-8" /></p>', nodeId=249, subjectId=1, langId=1, examSubjectId=0, difficultyLevel=1, checked=0, answerType=8, answers=[Answer{id=309695, questionId=58756, answer='<p><span class="math-tex">\(-\frac12\)</span></p>', correct=0, letter=null}, Answer{id=309696, questionId=58756, answer='<p><span class="math-tex">\(\frac12\)</span></p>', correct=1, letter=null}, Answer{id=309697, questionId=58756, answer='<p>1</p>', correct=0, letter=null}, Answer{id=309698, questionId=58756, answer='<p>0</p>', correct=0, letter=null}, Answer{id=309699, questionId=58756, answer='<p><span class="math-tex">\(\sqrt3\)</span></p>', correct=0, letter=null}, Answer{id=309700, questionId=58756, answer='<p>0,5</p>', correct=1, letter=null}, Answer{id=309701, questionId=58756, answer='<p><span class="math-tex">\(-\sqrt3\)</span></p>', correct=0, letter=null}, Answer{id=309702, questionId=58756, answer='<p>&ndash; 0,5</p>', correct=0, letter=null}]}

        compositeDisposable.add(disposable);
    }

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

    private void updateTestDataList(Tests tests)
    {
//        testsList.getQuestions().clear();
//        testsList.getQuestions().addAll(tests.getQuestions());
        testsList = new Tests();
        testsList.setQuestions(tests.getQuestions());
        testsList.setTestId(tests.getTestId());
        listMutableLiveData.setValue(testsList);
//        setTestList(testList);

//        System.out.println("hello world");
//        setChanged();
//        notifyObservers();
    }

    public  Tests getTestList()
    {
        return testsList;
    }

    private void unSubscribeFromObservable()
    {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset()
    {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }


}

