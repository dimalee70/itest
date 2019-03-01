package itest.kz.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;

import java.util.List;

import itest.kz.model.Answer;
import itest.kz.model.Test;

public class ItemAnswerViewModel
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
//    }

    public  ItemAnswerViewModel (Context context, Answer answer)
    {
//        super(application);
        this.context = context;
        this.answer = answer;
//        System.out.println(answer.getAnswer());
        answerText.set(answer.getAnswer());

    }
//    public String getAnswerText()
//    {
//        return  answer.getAnswer();
//    }
}
