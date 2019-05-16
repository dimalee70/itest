package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.ahmadrosid.svgloader.SvgLoader;

import itest.kz.R;
import itest.kz.model.Subject;

public class ItemEntViewModel  extends BaseObservable
{
    private Drawable myDrawabble;
    private Context context;
    private Subject subject;
    public ObservableInt backgroundColor;

    public ItemEntViewModel(Context context, Subject subject)
    {
        this.subject = subject;
        this.context = context;
        backgroundColor =  new ObservableInt(Color.parseColor(subject.getColorBg()));



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.myDrawabble = (subject.isMain || subject.getIsSelected() == 1)? context.getDrawable( R.drawable.ic_check) :
                    context.getDrawable(R.drawable.ic_plus);
        }
        else
            this.myDrawabble = (subject.isMain || subject.getIsSelected() == 1)? context.getResources()
                    .getDrawable( R.drawable.ic_check) :
                    context.getResources().getDrawable(R.drawable.ic_plus);
    }

    public void setMyDrawabble(Drawable myDrawabble)
    {
        this.myDrawabble = myDrawabble;
    }

    public String getName()
    {
        return subject.getTitle();
    }

    public String getSvg()
    {
        return subject.getIcon();
    }
    public void setSubject(Subject subject)
    {
        this.subject = subject;
//        this.subjectList = new ArrayList<>();
        notifyChange();
    }

//    public Drawable getIcon()
//    {
//        if (subject.getIsSelected() == 1 || subject.isMain()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                return context.getDrawable(R.drawable.ic_check);
//            }
//            return context.getResources().getDrawable(R.drawable.ic_plus);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            return context.getDrawable(R.drawable.ic_plus);
//        }
//        return context.getResources().getDrawable(R.drawable.ic_plus);
//    }
}
