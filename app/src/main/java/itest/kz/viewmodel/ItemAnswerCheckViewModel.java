package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import java.util.Observable;

import itest.kz.model.Answer;
import itest.kz.util.Constant;

public class ItemAnswerCheckViewModel extends BaseObservable
{
    private Context context;
    private Answer answer;
    public ObservableField<String> answerText = new ObservableField<>();

    public ItemAnswerCheckViewModel (Context context, Answer answer)
    {
        this.context = context;
        this.answer = answer;
        answerText.set(Constant.MATHJAX + "<font color='white'>" + answer.getAnswer()+ "</font>");
    }
    public void setAnswer(Answer answer)
    {
        this.answer = answer;
        notifyChange();
    }

}
