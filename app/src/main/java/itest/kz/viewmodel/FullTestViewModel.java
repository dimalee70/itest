package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.List;
import java.util.Observable;

import itest.kz.model.Subject;

public class FullTestViewModel extends Observable
{
    private Context context;
    private List<Subject> subjectList;
    public ObservableInt timeVisibility;
    private String resultTag;

    public FullTestViewModel(Context context, List<Subject> subjectList, String resultTag)
    {
        this.context = context;
        this.subjectList = subjectList;
        this.resultTag = resultTag;
        if (resultTag == null)
            timeVisibility = new ObservableInt(View.VISIBLE);
        else
            timeVisibility = new ObservableInt(View.GONE);
    }

//    public String getTitle()
//    {
//        return
//    }
}
