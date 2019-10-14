package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import itest.kz.R;
import itest.kz.model.Answer;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.Tests;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class ResultViewModel extends Observable
{
    public Context context;
    public ObservableInt answersRecycler;
    private Tests answersList;
    private List<Question> testList;
    private String language;

    public List<Question> getTestList() {
        return testList;
    }

    public void setTestList(List<Question> testList) {
        this.testList = testList;
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

//    public ResultViewModel(Context context, List<Answer> answersList)
//    {
//        this.context = context;
//        this.answersRecycler = new ObservableInt(View.VISIBLE);
//        this.answersList = answersList;
//    }

    public ResultViewModel(Context context, Tests answersList)
    {
        this.context = context;
        this.answersRecycler = new ObservableInt(View.VISIBLE);
        this.answersList = answersList;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
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

    public String getTitle()
    {
        return context.getResources().getString(R.string.toolbarAnswersText);
//        return String.valueOf(R.string.agreementRu);
    }
//
//    public int getToolbarTitle()
//    {
////        if (language.equals(Constant.KZ))
////            return R.string.toolbarAnswersTextKz;
//        return R.string.toolbarAnswersTextRu;
//    }

    public Tests getAnswersList()
    {
        return answersList;
    }

    public void setAnswersList(Tests answersList)
    {
        this.answersList = answersList;
    }

}
