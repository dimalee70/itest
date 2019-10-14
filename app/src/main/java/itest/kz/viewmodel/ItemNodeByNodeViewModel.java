package itest.kz.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonObject;

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
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.LectureActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.NodeByNodeActivity;
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
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;
    private View viewDisableLayout;
    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 300;
    private boolean doubleView = false;


    public ItemNodeByNodeViewModel(Context contex, Lecture lecture)
    {
        this.context = contex;
        this.lecture = lecture;
        SharedPreferences lang = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
//        viewDisableLayout = ((Activity) context).findViewById(R.id.viewDisableLayout);
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
        ((Activity) getContext()).findViewById(R.id.viewDisableLayout123).setVisibility(View.VISIBLE);
        ((Activity) getContext()).findViewById(R.id.progressBar123).setVisibility(View.VISIBLE);
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService(accessToken);

//        if (subject != null) {
        Disposable disposable = subjectService.getLecture(Constant.ENT,
                lecture.getId(), language)
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
//                                       ((Activity) getContext()).findViewById(R.id.viewDisableLayout123).setVisibility(View.GONE);
                                       ((Activity) getContext()).findViewById(R.id.progressBar123).setVisibility(View.GONE);
                                       Dialog dialog = new Dialog(getContext());

                                       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                       dialog.setContentView(R.layout.dialog);
                                       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                       buttonYes = dialog.findViewById(R.id.buttonOk);
                                       buttonNo = dialog.findViewById(R.id.buttonCancel);
                                       dialogText = dialog.findViewById(R.id.dialog_text);

                                       dialogText.setText(R.string.textDialogCertification);
                                       buttonYes.setText(R.string.yes);
                                       buttonNo.setText(R.string.no);

                                       buttonYes.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
//                                               finishTest(testIdMain);

//                                               fetchTestList();

                                               Intent intent = new Intent(context, TestActivity.class);
//            intent.putExtra(Constant.TEST_MAIN_ID, testGenerateResponse.getTestGenerate().getTestId());
                                               intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
                                               Subject subject = new Subject(Long.parseLong(String.valueOf(lectureResponse.getLecture().getId())),
                                                       lectureResponse.getLecture().getTitle());
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
//                                        public void clickCategory (String name) {
                                        LectureActivity test = (LectureActivity) ((MainHomeActivity) getContext())
                                                .getSupportFragmentManager().findFragmentByTag(LectureActivity.class.getSimpleName());
                                        if (test != null && test.isVisible())
                                        {

                                        }
                                        else
                                        {
                                            try {
                                                FragmentManager manager = ((MainHomeActivity) getContext()).getSupportFragmentManager();
                                                manager.executePendingTransactions();
                                                FragmentTransaction transaction = manager.beginTransaction();

                                                Fragment fragment = LectureActivity.newInstance(lectureResponse);
                                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                        .replace(R.id.frame3, fragment, LectureActivity.class.getSimpleName())
                                                        .addToBackStack(NodeByNodeActivity.class.getSimpleName())
                                                        .commit();
                                                MainHomeActivity.shouldAllowBack = true;
                                            }
                                            catch (Exception e){}

                                        }




                                        
//                                    }

//                                        Fragment fragment = LectureActivity.newInstance(lectureResponse);
//                                        ((MainHomeActivity) (Activity)getContext()).loadFragment(fragment);

                                        setLogLecture(lectureResponse.getLecture().getId());
//                                        ((Activity) getContext()).findViewById(R.id.viewDisableLayout123).setVisibility(View.GONE);
                                        ((Activity) getContext()).findViewById(R.id.progressBar123).setVisibility(View.GONE);
                                    }

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception
                            {
//                                ((Activity) getContext()).findViewById(R.id.viewDisableLayout123).setVisibility(View.GONE);
//                                ((Activity) getContext()).findViewById(R.id.progressBar123).setVisibility(View.GONE);
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
                                }
                            }
                        }
                );

        compositeDisposable.add(disposable);
    }

    private void setLogLecture(int lectureId)
    {
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.logVisitLecture(
                 lectureId)
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
    }

    public void onClick(View view)
    {
//        if (!doubleView) {

            long now = System.currentTimeMillis();
            if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                return;
            }
            mLastClickTime = now;
            MainHomeActivity.shouldAllowBack = false;
            getLectureResponse();// open
//            doubleView = true;
//        }
//        else
//            doubleView = false;
    }

    private void updateLectureResponseData(LectureResponse lectureResponse)
    {
        this.lectureResponse = lectureResponse;
    }
}
