package itest.kz.viewmodel;

import android.content.Context;

import java.util.List;
import java.util.Observable;

import itest.kz.model.Subject;

public class FullTestViewModel extends Observable
{
    private Context context;
    private List<Subject> subjectList;

    public FullTestViewModel(Context context, List<Subject> subjectList)
    {
        this.context = context;
        this.subjectList = subjectList;
    }
}
