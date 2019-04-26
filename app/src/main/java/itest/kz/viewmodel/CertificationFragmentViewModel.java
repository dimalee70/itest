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
import itest.kz.model.RegisterResponse;
import itest.kz.model.Subject;
import itest.kz.model.SubjectResponce;
import itest.kz.network.SubjectService;
import itest.kz.network.UserService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.fragments.CertificationFragment;
import itest.kz.view.fragments.SubjectFragment;

import static android.content.Context.MODE_PRIVATE;

public class CertificationFragmentViewModel extends Observable
{
    public Context context;
    public ObservableInt subjectRecycler;
    private List<Subject> subjectList;
    public Action showHideButtons;
    private String language;
    private String accessToken;
    public ObservableInt cardViewVisibility = new ObservableInt(View.VISIBLE);
    public ObservableInt imageButtonVisibility = new ObservableInt(View.GONE);
    public ObservableInt progress = new ObservableInt(View.GONE);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public Action hideShow;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;
    public Action clickTryAgain;
    private TextView dialogTextError;
    private Button buttonYesError;
    private Button buttonNoError;

    public CertificationFragmentViewModel(Context context)
    {
        this.context = context;
        this.subjectRecycler = new ObservableInt(View.GONE);
        this.subjectList = new ArrayList<>();
        clickTryAgain = () ->
        {
            fetchSubjectList();
        };
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");

//        showHideButtons = () ->
//                {
//                    System.out.println("Hello World");
//                };
        fetchSubjectList();
    }

    public ObservableInt getCardViewVisibility()
    {
        return cardViewVisibility;
    }

    public void setCardViewVisibility(boolean isVisibility)
    {
        if (isVisibility)
            cardViewVisibility.set(View.VISIBLE);
        else
            cardViewVisibility.set(View.GONE);
        notifyObservers();
    }

    public ObservableInt getImageButtonVisibility() {
        return imageButtonVisibility;
    }

    public void setImageButtonVisibility(boolean isVisibility)
    {
        if (isVisibility)
            imageButtonVisibility.set(View.VISIBLE);
        else
            imageButtonVisibility.set(View.GONE);
        notifyObservers();
    }

    private void fetchSubjectList()
    {
        progress.set(View.VISIBLE);
        if (CheckUtility.isNetworkConnected(context))
        {
            setImageButtonVisibility(false);
            setCardViewVisibility(true);
            AppController appController = new AppController();
            SubjectService subjectService = appController.getSubjectService();


            Disposable disposable = subjectService.getSubjects(Constant.ENT,
                    "Bearer " + accessToken, Constant.ACCEPT, language)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SubjectResponce>() {
                                   @Override
                                   public void accept(SubjectResponce subjectResponce) throws Exception {
                                       updateSubjectDataList(subjectResponce.getList());
                                       subjectRecycler.set(View.VISIBLE);
                                       progress.set(View.GONE);
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (throwable.getMessage().contains("401")) {
                                        showToastUnauthorized();
                                        progress.set(View.GONE);
                                    }
                                }
                            }
                    );

            compositeDisposable.add(disposable);
        }
        else
        {



            setCardViewVisibility(false);
            setImageButtonVisibility(true);
            String text = "";
            if (language.equals(Constant.KZ))
                text = Constant.SERVER_ERROR_ALERT_KZ;
            else
                text = Constant.SERVER_ERROR_ALERT_RU;
//            Toast toast = Toast.makeText(context,
//                    text, Toast.LENGTH_SHORT);
//            toast.show();

            //        public void showFinishTimeDialog()
//        {
            Dialog dialog = new Dialog(context);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog)
                {
//                    openAuthActivity();
//                    finishTest(testIdMain);
                    //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogTextError = dialog.findViewById(R.id.dialog_text);
            buttonYesError = dialog.findViewById(R.id.buttonOk);
            buttonNoError = dialog.findViewById(R.id.buttonCancel);
            buttonNoError.setVisibility(View.GONE);
            buttonYesError.setText(R.string.ok);
//            if(language.equals(Constant.KZ))
//            {
//            buttonNo.setText(R.string.noKz);

                dialogTextError.setText(text);

//            }
//            else
//            {
////            buttonNo.setText(R.string.noRu);
////            buttonYes.setText(R.string.yesRu);
//                dialogTextAuth.setText(R.string.sessionErrorRu);
//            }
            buttonYesError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    buttonYesError.setEnabled(false);
                    dialog.dismiss();
//                    openAuthActivity();
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


            progress.set(View.GONE);
        }
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

    private void updateSubjectDataList(List<Subject> subjects) {

        subjectList.addAll(subjects);
        setChanged();
        notifyObservers();
    }

    public List<Subject> getSubjectList()
    {
        return subjectList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }

    public int getServerErrorText()
    {
        if (language.equals(Constant.KZ))
            return R.string.tryAgainTextKz;
        return R.string.tryAgainTextRu;
    }


}
