package itest.kz.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Lecture;
import itest.kz.model.LectureResponse;
import itest.kz.model.Subject;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.activity.LectureActivity;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.TestActivity;

import static android.content.Context.MODE_PRIVATE;

public class ItemNodeByNodeViewModel extends BaseObservable
{
    private Context context;
    private Lecture lecture;
    private LectureResponse lectureResponse = null;
    private String language;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Button buttonYes;
    private Button buttonNo;
    private TextView dialogText;
    private String accessToken;

    public ItemNodeByNodeViewModel(Context contex, Lecture lecture)
    {
        this.context = contex;
        this.lecture = lecture;
        SharedPreferences lang = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");
    }

    public String getTitle()
    {
        return lecture.getTitle();
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public Lecture getLecture()
    {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
        notifyChange();
    }

    public void getLectureResponse()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();

//        if (subject != null) {
        Disposable disposable = subjectService.getLecture(Constant.ENT,
                lecture.getId(), Constant.ACCEPT, language)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LectureResponse>()
                           {
                               @Override
                               public void accept(LectureResponse lectureResponse) throws Exception
                               {

                                   updateLectureResponseData(lectureResponse);
//                                   if (!lectureResponse.getLecture().isHasQuestions())
//                                   {
//
//                                   }

//                                   System.out.println(lecture);
                                   if (!lecture.isHasDescription())
                                   {
//                                       System.out.println(lectureResponse.getLecture().isHasDescription());
                                       Dialog dialog = new Dialog(getContext());

                                       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                       dialog.setContentView(R.layout.dialog);
                                       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                       buttonYes = dialog.findViewById(R.id.buttonOk);
                                       buttonNo = dialog.findViewById(R.id.buttonCancel);
                                       dialogText = dialog.findViewById(R.id.dialog_text);
                                       if (language.equals(Constant.KZ))
                                       {
                                           dialogText.setText(R.string.textDialogCertificationKz);
                                           buttonYes.setText(R.string.yesKz);
                                           buttonNo.setText(R.string.noKz);
                                       }

                                       else
                                       {
                                           dialogText.setText(R.string.textDialogCertificationRu);
                                           buttonYes.setText(R.string.yesRu);
                                           buttonNo.setText(R.string.noRu);
                                       }

                                       buttonYes.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
//                                               finishTest(testIdMain);

//                                               fetchTestList();

                                               Intent intent = new Intent(context, TestActivity.class);
//            intent.putExtra(Constant.TEST_MAIN_ID, testGenerateResponse.getTestGenerate().getTestId());
                                               intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
                                               Subject subject = new Subject(Long.parseLong(String.valueOf(lectureResponse.getLecture().getId())));
                                               intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) subject);
                                               intent.putExtra(Constant.IS_STARTED_FIRST, true);
//            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                                               context.startActivity(intent);


                                               //System.out.println(testIdMain);//103080954

                                           }
                                       });

                                       buttonNo.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dialog.dismiss();
                                           }
                                       });
                                       dialog.show();
                                   }
                                    else
                                    {
                                        Intent intent = new Intent(getContext(), LectureActivity.class);
                                        intent.putExtra(Constant.SELECTED_LECTURE_RESPONSE, lectureResponse);
                                        context.startActivity(intent);
                                    }

                               }
                           }
                );

        compositeDisposable.add(disposable);
    }

    public void onClick(View view)
    {
        getLectureResponse();

    }

//    public void fetchTestList()
//    {
////        System.out.println("subject");
////        System.out.println(subject);
//        TestGenerateCredentials credentials = new TestGenerateCredentials("ent", "lecture", String.valueOf(lecture.getId()));
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
//                                   Intent intent = new Intent(context, TestActivity.class);
//                                   intent.putExtra(Constant.TEST_MAIN_ID, testGenerateResponse.getTestGenerate().getTestId());
//                                   Subject subject = new Subject(testGenerateResponse.getTestGenerate().getTestId());
//                                   intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) subject);
////                                   this.selectedSubject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
//                                   intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
////            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//                                   context.startActivity(intent);
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


    private void updateLectureResponseData(LectureResponse lectureResponse)
    {
        this.lectureResponse = lectureResponse;
//        System.out.println("desc");
//        System.out.println(lectureResponse.getDescription());

    }
}
