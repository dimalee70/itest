package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.Observable;

public class HomeViewModel extends Observable
{
    private Context context;
    private ObservableInt bottomVisible = new ObservableInt(View.GONE);
    public ObservableInt progress = new ObservableInt(View.GONE);


    public ObservableInt getBottomVisible()
    {
        return bottomVisible;
    }

    public void setBottomVisible(boolean bottom)
    {
        if (bottom)
            bottomVisible.set(View.VISIBLE);
        else
            bottomVisible.set(View.GONE);
        notifyObservers();
    }

    public HomeViewModel(Context context)
    {
        this.context = context;
    }

    public void setProgress(boolean isProgres)
    {
        if (isProgres)
            progress.set(View.VISIBLE);
        else
            progress.set(View.GONE);
        notifyObservers();
    }
}
