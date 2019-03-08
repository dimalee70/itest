package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import itest.kz.model.Answer;
import itest.kz.model.Test;

public class ItemResultViewModel extends BaseObservable
{
    private Answer answer;
    private Context context;
    private Test test;

    public Test getTest()
    {
        return test;
    }

    public void setTest(Test test)
    {
        this.test = test;
        notifyChange();
    }

//    public ItemResultViewModel(Answer answer, Context context)
//    {
//        this.answer = answer;
//        this.context = context;
//    }

    public ItemResultViewModel(Test test, Context context)
    {
        this.test = test;
        this.context = context;
    }

//    public Answer getAnswer()
//    {
//        return answer;
//    }
//
//    public void setAnswer(Answer answer)
//    {
//        this.answer = answer;
//        notifyChange();
//    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
}
