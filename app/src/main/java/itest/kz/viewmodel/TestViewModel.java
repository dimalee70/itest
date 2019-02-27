package itest.kz.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;

public class TestViewModel extends Observable
{
    private Subject subject;
    private List<Test> testList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;

    public TestViewModel(Context context, Subject subject)
    {
        this.subject = subject;
        this.context = context;
//        this.testList = new ArrayList<>();
        fetchTestList();
    }

    public void setTestList(List<Test> testList)
    {
        this.testList = testList;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public String getSubjectId()
    {
        return String.valueOf(subject.getId());
    }

    public void fetchTestList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


        Disposable disposable = subjectService.getTests(Constant.ACCEPT)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Test>>() {
                               @Override
                               public void accept(List<Test> tests) throws Exception {
//                                   System.out.println("Test list");
//                                   System.out.println(tests);
                                   updateTestDataList(tests);
                               }
                           }
                );

        compositeDisposable.add(disposable);
    }

    private void updateTestDataList(List<Test> tests) {

        testList.addAll(tests);

//        System.out.println("hello world");
        setChanged();
        notifyObservers();
    }

    public List<Test> getTestList()
    {
        return testList;
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
}

