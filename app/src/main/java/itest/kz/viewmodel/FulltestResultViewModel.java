package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Observable;

import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class FulltestResultViewModel extends Observable
{
    private Context context;
    private String language;

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
