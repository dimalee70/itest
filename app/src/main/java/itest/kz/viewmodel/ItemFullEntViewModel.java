package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import java.util.Observable;

import itest.kz.model.Subject;

public class ItemFullEntViewModel extends BaseObservable
{
    private Context context;
    private Subject subject;

    public ItemFullEntViewModel(Context context, Subject subject)
    {
        this.context = context;
        this.subject = subject;
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
}
