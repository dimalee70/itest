package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.SubjectResponce;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;

public class SubjectFragmentViewModel extends Observable
{
    private ObservableInt subjectRecycler;
    private List<Subject> subjectListMain;
    private List<Subject> subjectList;
    private boolean isStartedFirst = false;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;


    public void onClickCancel()
    {
        subjectListMain = new ArrayList<>();
        subjectList = new ArrayList<>();
        fetchSubjectList();
    }

    public SubjectFragmentViewModel(Context context)
    {
        this.context = context;
        this.subjectListMain = new ArrayList<>();
        this.subjectList = new ArrayList<>();
        this.subjectRecycler = new ObservableInt(View.GONE);
        fetchSubjectList();
        isStartedFirst = true;
    }




    private void fetchSubjectList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


        Disposable disposable = subjectService.getSubjects(Constant.ENT,
                "Bearer " + Constant.ACCESSTOKEN, Constant.ACCEPT, "ru")
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

    public ObservableInt getSubjectRecycler()
    {
        return subjectRecycler;
    }

    private void updateSubjectDataList(List<Subject> list)
    {

        for (Subject s : list)
        {
            if (s.isMain)
                subjectListMain.add(s);
            else
                subjectList.add(s);
        }
        System.out.println(subjectList.toString());
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
}
