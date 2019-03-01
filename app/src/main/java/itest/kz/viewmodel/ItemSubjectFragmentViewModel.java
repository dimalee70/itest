package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.SubjectResponce;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.activity.TestActivity;

public class ItemSubjectFragmentViewModel extends BaseObservable
{
    private Subject subject;
    private Context context;
    public  Action clickMat;
//    private List<Subject> subjectList;
//    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    public Action clickTest;


    public ItemSubjectFragmentViewModel(Subject subject, Context context)
    {
        this.subject = subject;
        this.context = context;

//        clickTest =
    }



    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
//        this.subjectList = new ArrayList<>();
        notifyChange();
    }

    public String getTitle()
    {
        return subject.getTitle();
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void onItemClick(View view)
    {
        Log.d("dcsd","On Item Click");
    }

//    private void fetchSubjectList()
//    {
//
//        AppController appController = new AppController();
//        SubjectService subjectService = appController.getSubjectService();
//
//
//        Disposable disposable = subjectService.getSubjects("Bearer " + Constant.ACCESSTOKEN, Constant.ACCEPT, "ru")
//                .subscribeOn(appController.subscribeScheduler())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<SubjectResponce>() {
//                    @Override
//                    public void accept(SubjectResponce subjectResponce) throws Exception
//                    {
//                        updateSubjectDataList(subjectResponce.getList());
//                        subjectRecycler.set(View.VISIBLE);
//                    }
//                });
//
//
//        compositeDisposable.add(disposable);
//    }

//    private void updateSubjectDataList(List<Subject> subjects) {
//
//        subjectList.addAll(subjects);
//        setChanged();
//        notifyObservers();
//    }

    public void clickTest(View view)
    {

        Intent intent = new Intent(getContext().getApplicationContext(), TestActivity.class);
//        context.startActivity(TestActivity.fillSelectedSubject(view.getContext(), subject));
        context.startActivity(intent);
    }

}
