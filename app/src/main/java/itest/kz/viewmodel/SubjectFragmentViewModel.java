package itest.kz.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import id.co.flipbox.sosoito.LoadingLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.SubjectResponce;
import itest.kz.model.Test;
import itest.kz.network.SubjectService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.MainHomeActivity;

import static android.content.Context.MODE_PRIVATE;

public class SubjectFragmentViewModel extends Observable
{
    public ObservableInt imageButtonVisibility = new ObservableInt(View.GONE);
    public ObservableInt relativeVisibility = new ObservableInt(View.VISIBLE);
    private ObservableInt subjectRecycler;
    private List<Subject> subjectListMain;
    private List<Subject> subjectList;
    private boolean isStartedFirst = false;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private ObservableInt entVisibleBtn;
    private String language;
    private String accessToken;
    private LoadingLayout loadingLayout;
    public ObservableInt progress = new ObservableInt(View.GONE);
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;
    private TextView dialogTextError;
    private Button buttonYesError;
    private Button buttonNoError;
    public Action clickTryAgain;
    private boolean firstTimeDialog = true;
    public ObservableInt cancelCardView = new ObservableInt(View.GONE);

    public void onClickCancel()
    {
        subjectListMain = new ArrayList<>();
        subjectList = new ArrayList<>();
        fetchSubjectList();
    }

    public ObservableInt getImageButtonVisibility()
    {
        return imageButtonVisibility;
    }

    public ObservableInt getCancelCardView() {
        return cancelCardView;
    }

    public void setCancelCardView(boolean isVisible)
    {
        if (isVisible)
            cancelCardView.set(View.VISIBLE);
        else
        {
            cancelCardView.set(View.GONE);
        }

        notifyObservers();
    }

    public void setImageButtonVisibility(boolean isVisibility)
    {
        if (isVisibility)
            imageButtonVisibility.set(View.VISIBLE);
        else
            imageButtonVisibility.set(View.GONE);
        notifyObservers();
    }

    public ObservableInt getRelativeVisibility() {
        return relativeVisibility;
    }

    public void setRelativeVisibility(boolean isVisibility)
    {
        if (isVisibility)
            relativeVisibility.set(View.VISIBLE);
        else
            relativeVisibility.set(View.GONE);
        notifyObservers();
    }

    private MutableLiveData<List<Subject>> listMutableLiveData;

    public LiveData<List<Subject>> getTests()
    {
//        this.loadingLayout = ((Activity) context).findViewById(R.id.loading);
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<List<Subject>>();


            fetchSubjectList();

        }

//        clickTryAgain = () ->
//        {
//            System.out.println("click");
//            fetchSubjectList();
//        };
        return listMutableLiveData;
    }

    public void setSubjectListMain(List<Subject> subjectListMain)
    {
        this.subjectListMain = subjectListMain;
    }

    public void setSubjectList(List<Subject> subjectList)
    {
        this.subjectList = subjectList;
        notifyObservers();
//        notifyChange();
    }

    public SubjectFragmentViewModel(Context context)
    {
        this.context = context;
        this.loadingLayout = ((Activity) context).findViewById(R.id.loading);
        this.subjectListMain = new ArrayList<>();
        this.subjectList = new ArrayList<>();
        this.subjectRecycler = new ObservableInt(View.GONE);
        this.entVisibleBtn = new ObservableInt(View.VISIBLE);

        clickTryAgain = () ->
        {
//            System.out.println("click");
            fetchSubjectList();
        };



//        fetchSubjectList();
        isStartedFirst = true;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");
    }

    public ObservableInt getEntVisibleBtn()
    {
        return entVisibleBtn;
    }

    public void setVisibilityGone()
    {
        entVisibleBtn.set(View.GONE);
    }

    public void setVisibilityVisible()
    {
        entVisibleBtn.set(View.VISIBLE);
    }


    public void fetchSubjectList()
    {
//        if (loadingLayout != null) {
//            loadingLayout.showCustomLoading(true, R.drawable.ic_itest_logo_larger);
//        }

        progress.set(View.VISIBLE);
        if (CheckUtility.isNetworkConnected(context))
        {
            setImageButtonVisibility(false);
            setRelativeVisibility(true);
            AppController appController = new AppController();
            SubjectService subjectService = appController.getSubjectService(accessToken);


            Disposable disposable = subjectService.getSubjects(Constant.ENT,
                    language)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SubjectResponce>() {
                                   @Override
                                   public void accept(SubjectResponce subjectResponce) throws Exception {
                                       updateSubjectDataList(subjectResponce.getList());
                                       subjectRecycler.set(View.VISIBLE);
                                       progress.set(View.GONE);
//                                        loadingLayout.showCustomLoading(false);
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (throwable.getMessage().contains("401"))
                                    {
                                        showToastUnauthorized();
                                        progress.set(View.GONE);
//                                        loadingLayout.showCustomLoading(false);
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
            setRelativeVisibility(false);
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
//            if (!firstTimeDialog)
//            {
                Dialog dialog = new Dialog(context);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
//                    openAuthActivity();
                        MainHomeActivity.isServerErrorDialogShown = false;
//                    finishTest(testIdMain);
                        //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
                    }
                });
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
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
                        MainHomeActivity.isServerErrorDialogShown = false;
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
            if (!MainHomeActivity.isServerErrorDialogShown) {
                dialog.show();
                MainHomeActivity.isServerErrorDialogShown = true;
            }
//            }


//            firstTimeDialog = false;


            progress.set(View.GONE);
        }
    }

//        private TextView dialogTextAuth;
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

    public ObservableInt getSubjectRecycler()
    {
        return subjectRecycler;
    }

    public void updateSubjectDataList(List<Subject> list)
    {

        for (Subject s : list)
        {
            if (s.isMain)
                subjectListMain.add(s);
            else
            {
                if(language.equals(Constant.RU) && s.getId() == 19)
                {
                    continue;
                }
                else if(language.equals(Constant.KZ) && s.getId() == 29)
                    continue;
                subjectList.add(s);
            }
        }
//        System.out.println(subjectList.toString());
//        subjectList.addAll(list);
        setChanged();
        notifyObservers();

    }

    public List<Subject> getSubjectListMain()
    {
        return subjectListMain;
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

    public int getRequiredSubjects()
    {
        return R.string.requiredSubjects;
    }

    public int getChosedSubjects()
    {
        return R.string.chosedSubjects;
    }

    public int getStartFullTest()
    {
        return R.string.fullTestStart;
    }

    public int getCancel()
    {
        return R.string.cancel;
    }

    public int getServerErrorText()
    {
        return R.string.tryAgainText;
    }

//    public void clickTryAgain(View view)
//    {
//        System.out.println("hello");
//    }
}
