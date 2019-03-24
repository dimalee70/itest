package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

public class MainHomeViewModel extends BaseObservable
{
    private Context context;

    public MainHomeViewModel(Context context)
    {
        this.context = context;
    }
}
