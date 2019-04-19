package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;

import io.reactivex.disposables.CompositeDisposable;
import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class PasswordChangeFragmentViewModel extends BaseObservable
{
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String language;
    public PasswordChangeFragmentViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }

    public int getTitleText()
    {
        if (language.equals(Constant.KZ))
            return R.string.passwordChangedKz;
        return R.string.passwordChangedRu;
    }

    public int getSaveText()
    {
        if (language.equals(Constant.KZ))
            return R.string.saveTitleKz;
        return R.string.saveTitleRu;
    }

    public int getHintConfirmPasswordText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintConfirmNewPassKz;
        return R.string.hintConfirmNewPassRu;
    }

    public int getConfirmPasswordText()
    {
        if (language.equals(Constant.KZ))
            return R.string.confirmNewPassKz;
        return R.string.confirmNewPassRu;
    }

    public int getHintNewPasswordText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintNewPassTextKz;
        return R.string.hintNewPassTextRu;
    }

    public int getNewPasswordText()
    {
        if (language.equals(Constant.KZ))
            return R.string.newPassTextKz;
        return R.string.newPassTextRu;
    }

    public int getHintOldPasswordText()
    {
        if (language.equals(Constant.KZ))
            return R.string.hintOldPassTextKz;
        return R.string.hintOldPassTextRu;
    }

    public int getOldPassText()
    {
        if (language.equals(Constant.KZ))
            return R.string.oldPassTextKz;
        return R.string.oldPassTextRu;
    }

    public String getPassWordText()
    {
        if (language.equals(Constant.KZ))
            return context
                    .getResources()
                    .getString( R.string.passwordChangedKz) + ": ";
        return context
                .getResources()
                .getString( R.string.passwordChangedRu) + ": ";
    }

}
