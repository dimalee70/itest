package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.Observable;

import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class FulltestResultViewModel extends Observable
{
    private Context context;
    private String language;
    public ObservableInt progress = new ObservableInt(View.GONE);
    public ObservableInt scroll = new ObservableInt(View.GONE);

    public ObservableInt getProgress()
    {
        return progress;
    }

    public void setProgress(boolean isProgress)
    {
        if (isProgress)
            progress.set(View.VISIBLE);
        else
        {
            progress.set(View.GONE);
            scroll.set(View.VISIBLE);
        }
        notifyObservers();
    }

    public  FulltestResultViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }

    public String getTitle()
    {
        if (language.equals(Constant.KZ))
            return context.getResources().getString(R.string.toolbarAnswersTextKz);
        return context.getResources().getString(R.string.toolbarAnswersTextRu);
    }
}
