package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.Observable;

import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class HomeViewModel extends Observable
{
    private Context context;
    private ObservableInt bottomVisible = new ObservableInt(View.GONE);
    public ObservableInt progress = new ObservableInt(View.GONE);
    private String language;
    private SharedPreferences settings;


    public ObservableInt getBottomVisible()
    {
        return bottomVisible;
    }

    public int getServerErrorText()
    {
        if (language.equals(Constant.KZ))
            return R.string.tryAgainTextKz;
        return R.string.tryAgainTextRu;
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
        settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
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
