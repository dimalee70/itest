package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.reactivex.functions.Action;
import itest.kz.model.Subject;
import itest.kz.view.activity.TestActivity;

public class ItemSubjectFragmentViewModel extends BaseObservable
{
    private Subject subject;
    private Context context;
    public  Action clickMat;
//    public Action clickTest;


    public ItemSubjectFragmentViewModel(Subject subject, Context context)
    {
        this.subject = subject;
        this.context = context;

//        clickTest =
    }



    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
        notifyChange();
    }

    public String getTitle()
    {
        return subject.getTitle();
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void onItemClick(View view)
    {
        Log.d("dcsd","On Item Click");
    }

    public void clickTest(View view)
    {
        context.startActivity(TestActivity.fillSelectedSubject(view.getContext(), subject));
    }
}
