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
        String text = "<html><body dir=\"rtl\"; style=\"text-align:center;\">";
        text += Constant.MATHJAX + "<font color='white'>" + answer.getAnswer()+ "</font>";
        text += "</body></html>";
        text = text.trim();
        answerText.set(
                text
               );
    }
    public void setAnswer(Answer answer)
    {
        this.answer = answer;
        notifyChange();
    }

}
