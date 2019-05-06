package itest.kz.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import itest.kz.R;
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
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.fragments.TestFragment;

import static android.content.Context.MODE_PRIVATE;

public class TestViewModel extends AndroidViewModel
{
    static final int ITEMS = 20;

    private MutableLiveData<Tests> listMutableLiveData;
    public ObservableInt progress = new ObservableInt(View.VISIBLE);



    private Subject subject;
    private String language;
    private String accessToken;
    public Tests testsList = null;
//    private  List<Question> testList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private String typeTest;
    private boolean isStartedFirst;
    public Action onClickForward;
    private Long testIdMain;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;



    public LiveData<Tests> getTests() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<Tests>();
//
//            System.out.println("testIdMain");
//            System.out.println(testIdMain);
//            if (testIdMain <= 0)
//            {
            if (testsList == null)
                fetchTestList();
            else
                fetchFullTestQuestionsGenerate(testsList.getTestId());
//            }
//            else
//            {
//                fetchFullTestQuestionsGenerate(testIdMain);
//            }

        }
        return listMutableLiveData;
    }

    public ObservableInt getProgress()
    {
        return progress;
    }

    public void setProgress(boolean isProgress) {
        if (isProgress)
        {
            progress.set(View.VISIBLE);
        }
        else
        {
            progress.set(View.GONE);
        }
//        notify();
    }

    public TestViewModel(Application application, Subject subject, String typeTest, boolean isStartedFirst, Long testIdMain)
    {
        super(application);
        this.testIdMain = testIdMain;
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
//        System.out.println(subject);
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getTestGenerate(Constant.ACCEPT,
                language, "Bearer " + accessToken, credentials)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestGenerateResponse>() {
                               @Override
                               public void accept(TestGenerateResponse testGenerateResponse) throws Exception {
//                                   System.out.println("testGenerate");
//                                   System.out.println(testGenerateResponse.getTestGenerate());
                                   fetchFullTestQuestionsGenerate(testGenerateResponse.getTestGenerate().getTestId());
                                   setLogTest(testGenerateResponse.getTestGenerate().getTestId());
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                System.out.println("error");
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

//                                   System.out.println("json");
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
                           },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
                                }
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


//    private TextView dialogTextAuth;
//    private Button buttonYesAuth;
//    private Button buttonNoAuth;

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
        ((Activity)context).startActivity(intent);
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
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }




}

