package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import io.reactivex.functions.Action;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.fragments.TestFragment;

public class ItemResultViewModel extends BaseObservable
{
    private Answer answer;
    private Context context;
    private Test test;
    private int num;
    public Action clickAnswer;

    public Test getTest()
    {
        return test;
    }

    public void setTest(Test test)
    {
        this.test = test;
        notifyChange();
    }

//    public void clickAnswer(View view)
//    {
////        System.out.println("Click Answer");
//
//
//
//
//    }

//    public ItemResultViewModel(Answer answer, Context context)
//    {
//        this.answer = answer;
//        this.context = context;
//    }

    public ItemResultViewModel(Test test, Context context, int num)
    {
        this.test = test;
        this.context = context;
        this.num = num+1;
//        clickAnswer = new Action()
//        {
//            @Override
//            public void run() throws Exception
//            {
//
////        System.out.println(num - 1);
//                Intent intent = new Intent(context, TestActivity.class);
////        intent.putExtra()
//                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, num );
////        context.startActivity(TestActivity.fillSelectedSubject(view.getContext(), subject));
////                StartActivityForResult(intent, 0);
//                context.startActivity(intent);
////        TestFragment.newInstance(num-1, test);
//            }
//        };
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
