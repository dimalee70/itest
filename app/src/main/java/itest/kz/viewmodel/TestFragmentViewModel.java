package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

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

public class TestFragmentViewModel extends Observable
{
//    public ObservableField<String> url = new ObservableField<>();
    private Subject subject;
    private List<Test> testList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private Test test;

    public TestFragmentViewModel(Context context, Test test)
    {

        this.context = context;

        this.test = test;
    }

    public String getData()
    {
        return  test.getQuestion();
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

