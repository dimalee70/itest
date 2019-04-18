package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Observable;

import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class ResultsViewModel extends Observable
{
    private Context context;
    private String language;

    public ResultsViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }

    public String getLanguage()
    {
        return language;
    }
}
