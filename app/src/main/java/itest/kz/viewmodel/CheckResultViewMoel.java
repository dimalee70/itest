package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.Observable;

public class CheckResultViewMoel extends Observable
{
    private Context contex;
    public ObservableInt progress = new ObservableInt(View.GONE);
    public ObservableInt cardView = new ObservableInt(View.GONE);

    public ObservableInt getProgress()
    {
        return progress;
    }

    public void setProgress(boolean isProgress)
    {
        if (isProgress)
        {
            progress.set(View.VISIBLE);
        }
        else
        {
            progress.set(View.GONE);
//            cardView.set(View.VISIBLE);
        }
        notifyObservers();
    }


    public CheckResultViewMoel(Context contex)
    {
        this.contex = contex;
    }

    public void setCardView(boolean isCardView)
    {
        if (isCardView)
            cardView.set(View.VISIBLE);
        else
        {
            cardView.set(View.GONE);
        }
//        this.cardView = cardView;
    }
}
