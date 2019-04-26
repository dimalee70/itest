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
import itest.kz.model.StatisticSubject;
import itest.kz.model.StatisticSubjectResponce;
import itest.kz.network.SubjectService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;

import static android.content.Context.MODE_PRIVATE;

public class SubjectStatisticViewModel extends Observable
{
    private Context context;
    private String accessToken;
    private String language;
    public ObservableInt subjectRecycler;
    public ObservableInt imageButtonVisibility;
    private List<StatisticSubject> statisticSubjectList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ObservableInt progress = new ObservableInt(View.GONE);
    public ObservableInt relative = new ObservableInt(View.VISIBLE);
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;
    private TextView dialogTextError;
    private Button buttonYesError;
    private Button buttonNoError;
    public Action clickTryAgain;
    private boolean firstTimeDialog = true;

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
            relative.set(View.VISIBLE);
        }
        notifyObservers();
    }


    public int getServerErrorText()
    {
        if (language.equals(Constant.KZ))
            return R.string.tryAgainTextKz;
        return R.string.tryAgainTextRu;
    }

    public SubjectStatisticViewModel(Context context)
    {
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");
//        System.out.println("Access token");
//        System.out.println(accessToken);
        this.context = context;
        this.subjectRecycler = new ObservableInt(View.GONE);
        this.imageButtonVisibility = new ObservableInt(View.GONE);
        this.statisticSubjectList = new ArrayList<>();
        clickTryAgain = () ->
        {
            fetchSubjectStatisticList();
        };
        fetchSubjectStatisticList();
    }

    private void fetchSubjectStatisticList()
    {
        setProgress(true);

        if (CheckUtility.isNetworkConnected(context)) {
            AppController appController = new AppController();
            SubjectService subjectService = appController.getSubjectService();


            Disposable disposable = subjectService.getStatisticSubject(Constant.ACCEPT,
                    language,
                    "Bearer " + accessToken)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<StatisticSubjectResponce>() {
                                   @Override
                                   public void accept(StatisticSubjectResponce statisticSubjectResponce) throws Exception {
//                                   System.out.println(statisticSubjectResponce);
                                       updateSubjectDataList(statisticSubjectResponce.getData());
                                       subjectRecycler.set(View.VISIBLE);
                                       setProgress(false);
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (throwable.getMessage().contains("401")) {
                                        showToastUnauthorized();
                                        setProgress(false);
                                    }
//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                                }
                            }
                    );

            compositeDisposable.add(disposable);
        }
        else
        {
            imageButtonVisibility.set(View.VISIBLE);
            setProgress(false);

            subjectRecycler.set(View.GONE);
            imageButtonVisibility.set(View.VISIBLE);
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
            if (!firstTimeDialog) {
                Dialog dialog = new Dialog(context);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
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
                    public void onClick(View v) {
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
            }

            firstTimeDialog = false;


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


    private void updateSubjectDataList(List<StatisticSubject> data)
    {
        statisticSubjectList.clear();
        statisticSubjectList.addAll(data);
        setChanged();
        notifyObservers();
    }

    public List<StatisticSubject> getStatisticSubjectList()
    {
        return statisticSubjectList;
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
}
