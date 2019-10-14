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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
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
import itest.kz.model.Lecture;
import itest.kz.model.LectureResponse;
import itest.kz.model.Node;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.TestActivity;

import static android.content.Context.MODE_PRIVATE;

public class LectureViewModel extends Observable
{
    private LectureResponse lectureResponse;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    public ObservableInt visibleHeader = new ObservableInt(View.GONE);
    public ObservableInt visibleFooter = new ObservableInt(View.GONE);
    public Action onClickTest;
    private String accessToken;
    private String language;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;

    public LectureViewModel(Context context, LectureResponse lectureResponse)
    {
        this.context = context;
        this.lectureResponse = lectureResponse;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
        onClickTest = () ->
        {

            Intent intent = new Intent(context, TestActivity.class);
//            intent.putExtra(Constant.TEST_MAIN_ID, testGenerateResponse.getTestGenerate().getTestId());
            intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
            Subject subject = new Subject(Long.parseLong(String.valueOf(lectureResponse.getLecture().getId())),
                    lectureResponse.getLecture().getTitle());
            intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) subject);
            intent.putExtra(Constant.IS_STARTED_FIRST, true);
//            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
            context.startActivity(intent);

//            fetchTestList();
//            Intent intent = new Intent(context, ResultActivity.class);
//            intent.putExtra(Constant.TEST_MAIN_ID, lectureResponse.getLecture().getId());
////            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//            context.startActivity(intent);
        };
        setVisibilityHeader();

    }


    private void setVisibilityHeader()
    {
        if (lectureResponse.getLecture().isHasQuestions())
        {
            visibleHeader.set(View.GONE);
            visibleFooter.set(View.VISIBLE);
        }
        else
        {
            visibleHeader.set(View.VISIBLE);
            visibleFooter.set(View.GONE);
        }
    }

    public void fetchTestList()
    {
//        System.out.println("subject");
//        System.out.println(subject);
        TestGenerateCredentials credentials = new TestGenerateCredentials("ent", "lecture",
                String.valueOf(lectureResponse.getLecture().getId()));
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.getTestGenerate(
                language, credentials)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestGenerateResponse>()
                           {
                               @Override
                               public void accept(TestGenerateResponse testGenerateResponse) throws Exception
                               {
                                   Intent intent = new Intent(context, TestActivity.class);
                                   intent.putExtra(Constant.TEST_MAIN_ID, testGenerateResponse.getTestGenerate().getTestId());
                                   intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
                                   Subject subject = new Subject(Long.parseLong(String.valueOf(lectureResponse.getLecture().getId())),
                                           lectureResponse.getLecture().getTitle());
                                   intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) subject);
//            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                                   context.startActivity(intent);
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


        dialogTextAuth.setText(R.string.sessionError);

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

    public LectureResponse getLectureResponse()
    {
        return lectureResponse;
    }

    public void setLectureResponse(LectureResponse lectureResponse)
    {
        this.lectureResponse = lectureResponse;
    }

    public CompositeDisposable getCompositeDisposable()
    {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable)
    {
        this.compositeDisposable = compositeDisposable;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public String getData()
    {
        return lectureResponse.getLecture().getDescription();
    }

    public String getTitle()
    {
        return lectureResponse.getLecture().getTitle();
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

    public int getPassTestText()
    {
        return R.string.chapterTest;
    }

    public int getHaventTestText()
    {
        return R.string.haventTest;
    }

}
