package itest.kz.viewmodel;

import android.content.Context;

import java.util.Observable;

public class HomeViewModel extends Observable
{
    private Context context;

    public HomeViewModel(Context context)
    {
        this.context = context;
    }
}
