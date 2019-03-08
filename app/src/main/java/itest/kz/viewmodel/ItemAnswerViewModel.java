package itest.kz.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import java.util.List;

import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.util.Constant;

public class ItemAnswerViewModel extends BaseObservable
{
    Context context;
    Answer answer;

//    private MutableLiveData<Answer> answerMutableLiveData;
    public ObservableField<String> answerText = new ObservableField<>();

//    public LiveData<Answer> getAnswer() {
//        if (answerMutableLiveData == null) {
//            answerMutableLiveData = new MutableLiveData<Answer>();
////            fetchTestList();
//        }
//        return answerMutableLiveData;


    public Answer getAnswer()
    {
        return answer;
    }

    public void setAnswer(Answer answer)
    {
        this.answer = answer;
        notifyChange();
    }

    public  ItemAnswerViewModel (Context context, Answer answer)
    {
//        super(application);
        this.context = context;
        this.answer = answer;
//        System.out.println(answer.getAnswer());
        answerText.set(Constant.MATHJAX + answer.getAnswer());

    }

    public String getLetter()
    {
        return "fvfvd";
//        return answer.getLetter();
    }

    public void onClick(View view)
    {
        System.out.println("On Click ");
    }
//    public String getAnswerText()
//    {
//        return  answer.getAnswer();
//    }
}
