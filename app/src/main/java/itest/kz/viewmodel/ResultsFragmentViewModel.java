package itest.kz.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.TestsUtils;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.ResultsActivity;
import itest.kz.view.activity.TestActivity;

import static android.content.Context.MODE_PRIVATE;

public class ResultsFragmentViewModel extends Observable
{
    private Context context;
    private TestFinishResponse testFinishResponse;
    public Action clickAgainTest;
    private Subject selectedSubject;
    private List<Subject> subjectList;
    private String typeTest;
    private String language;
    private SharedPreferences settings;
    public ObservableInt progress = new ObservableInt(View.VISIBLE);
    public ObservableInt nestedVisible = new ObservableInt(View.GONE);
    private String accessToken;
    private String statisticTag;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;


    public ObservableInt getProgress()
    {
        return progress;
    }

    public void setProgress(boolean isProgress)
    {
        if (isProgress)
            progress.set(View.VISIBLE);
        else
            {
                progress.set(View.GONE);
                nestedVisible.set(View.VISIBLE);
            }

        notifyObservers();
    }

    //    private String text = "This is <font color='red'>red</font>. This is <font color='blue'>blue</font>.";

    public ResultsFragmentViewModel(Context context, TestFinishResponse testFinishResponse,
                                    List<Subject> subjectList, Subject selectedSubject,
                                    String typeTest, String statisticTag)
    {
        this.context = context;
        this.testFinishResponse = testFinishResponse;
        this.subjectList = subjectList;
        this.selectedSubject = selectedSubject;
        this.typeTest = typeTest;
        settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        this.statisticTag = statisticTag;

        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");

        this.clickAgainTest = ()->
        {
            if (typeTest.equals(Constant.TYPEFULLTEST))
            {
                if (statisticTag.equals(Constant.STATISTIC_TAG))
                {
                    fetchFullTestEntQuestionsGenerate(selectedSubject.getId());
                }

                else
                {
                    Intent intent = new Intent(context, FullTestActivity.class);
                    intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);
                    intent.putExtra(Constant.TYPE, typeTest);
                    context.startActivity(intent);
                }

            }
            else if (typeTest.equals(Constant.TYPESUBJECTTEST))
            {


//                Intent intent = new Intent((Activity) context, TestActivity.class);
//                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
//                intent.putExtra(Constant.TYPE, typeTest);



                if (statisticTag.equals(Constant.STATISTIC_TAG))
                {
                    fetchFullTestQuestionsGenerate(selectedSubject.getId());
                }
                else
                {
                    Intent intent = new Intent(context, TestActivity.class);
                    intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                    intent.putExtra(Constant.TYPE, typeTest);
                    context.startActivity(intent);
                }


//                System.out.println(acc);
//                System.out.println(selectedSubject);
//                System.out.println(typeTest);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(intent);
            }

            else if (typeTest.equals(Constant.TYPELECTURETEST))
            {

                if (statisticTag.equals(Constant.STATISTIC_TAG))
                {
                    fetchFullTestQuestionsGenerate(selectedSubject.getId());
                }
                else {
                    Intent intent = new Intent(context, TestActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                    intent.putExtra(Constant.TYPE, typeTest);
                    context.startActivity(intent);
                }
            }
        };
    }

    public void fetchFullTestEntQuestionsGenerate(Long id)
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
//                                   JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
//                                   JSONObject config = jsonObject1.getJSONObject("config");
//                                   int limit = config.getInt("time_limit");
//                                   int remaining = config.getInt("time_remaining");
                                   ArrayList<Tests> questions =
                                           TestsUtils.deserializeFromJson(jsonObject);

                                   ArrayList<Subject> arrayList = new ArrayList<>();
                                   for (int i = 0; i < questions.size(); i++)
                                   {
                                       arrayList.add(questions.get(i).getSubject());
                                   }
                                   Intent intent = new Intent(context, FullTestActivity.class);
                                   intent.putExtra(Constant.SUBJECT_LIST, (Serializable) arrayList);
                                   intent.putExtra(Constant.TYPE, typeTest);
                                   context.startActivity(intent);


//                                   System.out.println("questions");
//                                   System.out.println(questions);
//
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
//                                   fullTestViewModel.setProgress(false);

                               }
                           }
                           ,
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
                                   Intent intent = new Intent((Activity) context, TestActivity.class);
                                   intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) questions.getSubject());
                                   intent.putExtra(Constant.TYPE, typeTest);
                                   context.startActivity(intent);

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
                                if (jsonObject.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
//                                    fullTestViewModel.setProgress(false);
                                }
//                        System.out.println("json");
//                        System.out.println(jsonObject.toString());
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }

    public int getShowResultText()
    {
        if (language.equals(Constant.KZ))
            return R.string.resultShowKz;
        return R.string.resultShowRu;
    }
    public Spanned getHtmlText()
    {
        if (testFinishResponse != null)
        {
            int all = 0;
            int points = 0;
            if(testFinishResponse.getResult()!= null)
            {
                all = testFinishResponse.getResult().getAll();
                points = testFinishResponse.getResult().getPoints();//style="color:#000000"
            }

            String text = "<font color=#68DA78>" + points + "</font>"
                    + "/<font color='black'>" + all + "</font>";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
            }
            else
            {
                return Html.fromHtml(text);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml("", HtmlCompat.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml("");
    }

    public  int getPercent()
    {
        if (testFinishResponse.getResult() != null)
        {
            return  (int )testFinishResponse.getResult().getPercent();
        }
        return  0;
    }

    public int getAgainTestText()
    {
        if (language.equals(Constant.KZ))
            return R.string.againTestKz;
        return R.string.againTestRu;
    }
}
