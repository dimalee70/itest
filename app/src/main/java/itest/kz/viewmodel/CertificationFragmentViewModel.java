package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.RegisterResponse;
import itest.kz.model.Subject;
import itest.kz.model.SubjectResponce;
import itest.kz.network.SubjectService;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
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


    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public Action hideShow;

    public CertificationFragmentViewModel(Context context)
    {
        this.context = context;
        this.subjectRecycler = new ObservableInt(View.GONE);
        this.subjectList = new ArrayList<>();
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
//        showHideButtons = () ->
//                {
//                    System.out.println("Hello World");
//                };
        fetchSubjectList();
    }

    private void fetchSubjectList()
    {

        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


        Disposable disposable = subjectService.getSubjects(Constant.ATTESTATION,
                "Bearer " + Constant.ACCESSTOKEN, Constant.ACCEPT, language)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SubjectResponce>() {
                    @Override
                    public void accept(SubjectResponce subjectResponce) throws Exception
                    {
                        updateSubjectDataList(subjectResponce.getList());
                        subjectRecycler.set(View.VISIBLE);
                    }
                });

        compositeDisposable.add(disposable);
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


}
