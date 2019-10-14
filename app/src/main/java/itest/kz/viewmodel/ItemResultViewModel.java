package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import io.reactivex.functions.Action;
import itest.kz.model.Answer;
import itest.kz.model.Question;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.fragments.TestFragment;

public class ItemResultViewModel extends BaseObservable
{
    private Answer answer;
    private Context context;
    private Question test;
    private int num;
    public Action clickAnswer;

    public Question getTest()
    {
        return test;
    }

    public void setTest(Question test)
    {
        this.test = test;
        notifyChange();
    }

    public ItemResultViewModel(Question test, Context context, int num)
    {
        this.test = test;
        this.context = context;
        this.num = num+1;

    }


    public String getNum()
    {
        return String.valueOf(num);
    }
    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
}
