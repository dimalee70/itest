package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.model.Test;

public class ResultViewModel extends Observable
{
    public Context context;
    public ObservableInt answersRecycler;
    private List<Test> answersList;
    private List<Test> testList;

    public List<Test> getTestList() {
        return testList;
    }

    public void setTestList(List<Test> testList) {
        this.testList = testList;
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

//    public ResultViewModel(Context context, List<Answer> answersList)
//    {
//        this.context = context;
//        this.answersRecycler = new ObservableInt(View.VISIBLE);
//        this.answersList = answersList;
//    }

    public ResultViewModel(Context context, List<Test> answersList)
    {
        this.context = context;
        this.answersRecycler = new ObservableInt(View.VISIBLE);
        this.answersList = answersList;
//        this.answersList = answersList;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public ObservableInt getAnswersRecycler()
    {
        return answersRecycler;
    }

    public void setAnswersRecycler(ObservableInt answersRecycler)
    {
        this.answersRecycler = answersRecycler;
    }

    public List<Test> getAnswersList()
    {
        return answersList;
    }

    public void setAnswersList(List<Test> answersList)
    {
        this.answersList = answersList;
    }

}
