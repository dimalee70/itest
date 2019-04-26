package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;

import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class ThreeSplashViewModel extends BaseObservable
{
    private Context context;
    private String language;
    private SharedPreferences settings;

    public ThreeSplashViewModel(Context context)
    {
        this.context = context;
        settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }

    public int getText()
    {
        if (language.equals(Constant.KZ))
            return R.string.threeSplashKz;
        return R.string.threeSplashRu;
    }
}
