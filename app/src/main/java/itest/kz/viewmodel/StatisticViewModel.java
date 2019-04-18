package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;

import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class StatisticViewModel extends BaseObservable
{
    private Context context;
    private String language;

    public StatisticViewModel(Context context)
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

    public int getToolbarTitle()
    {
        return R.string.statisticKzRu;
    }

}
