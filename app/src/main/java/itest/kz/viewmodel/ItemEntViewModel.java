package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;

import itest.kz.R;
import itest.kz.model.Subject;

public class ItemEntViewModel  extends BaseObservable
{
    private Drawable myDrawabble;
    private Context context;
    private Subject subject;

    public ItemEntViewModel(Context context, Subject subject)
    {
        this.subject = subject;
        this.context = context;
        this.myDrawabble = (subject.isMain || subject.getIsSelected() == 1)? context.getDrawable( R.drawable.ic_check) :
                context.getDrawable(R.drawable.ic_plus);
    }

    public void setMyDrawabble(Drawable myDrawabble)
    {
        this.myDrawabble = myDrawabble;
    }

    public String getName()
    {
        return subject.getTitle();
    }


    public void setSubject(Subject subject)
    {
        this.subject = subject;
//        this.subjectList = new ArrayList<>();
        notifyChange();
    }

    public Drawable getIcon()
    {
       return myDrawabble;
    }
}
