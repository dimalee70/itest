package itest.kz.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import itest.kz.databinding.ItemAnswerBinding;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.adapters.TestAdapter;

public class ItemAnswerViewModel extends BaseObservable
{
    Context context;
    Answer answer;
    ObservableInt color ;
    List<Answer> answerList;

//    public ItemAnswerViewModel()
//    {
//        super();
//        if (answer.getAnswerResponce() != null)
//        {
//            color.set(Color.GREEN);
//        }
//        else
//            color.set(Color.WHITE);
//
//    }

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
//        this.answerList = answerList;
//        color = new ObservableInt((answer.getAnswerResponce())?
//                Color.GREEN : Color.WHITE);
//        notifyChange();
//        System.out.println(answer.getAnswer());
        answerText.set(Constant.MATHJAX + answer.getAnswer());

    }

    public String getLetter()
    {
        return answer.getLetter();
    }

    public ObservableInt getColor()
    {
        return color;
    }
//
//    public void setColor(ObservableInt color)
//    {
//        this.color = color;
//    }

//    public void onClick()
//    {
////        System.out.println(answer.getAnswerResponce());
//        if (color.get() != Color.WHITE)
//        {
//            color.set( Color.WHITE);
//            answer.setAnswerResponce(false);
//        }
//        else
//        {
////            answer.setAnswerResponce(new ArrayList<>());
////            answer.getAnswerResponce().add(answer.getId());
//            for (Answer a: answerList
//                 ) {
//                a.setAnswerResponce(false);
//            }
//            answer.setAnswerResponce(false);
//            answer.setAnswerResponce(true );
//            color.set(Color.GREEN);
//
//
////            notifyChange();
////            notifyChange();
//
//
//
//        }
////        System.out.println("On Click ");
//        TestActivity testActivity =
//                (TestActivity)getA
//    }
//    public String getAnswerText()
//    {
//        return  answer.getAnswer();
//    }
}
