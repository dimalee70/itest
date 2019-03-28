package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;

import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class LanguageChangeFragmentViewModel extends BaseObservable
{
    private Context context;
    private String language;

    public LanguageChangeFragmentViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }

    public int getTitleText()
    {
        if (language.equals(Constant.KZ))
            return R.string.langKz;
        return R.string.langRu;
    }
}
