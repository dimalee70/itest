package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.view.View;

import io.reactivex.disposables.CompositeDisposable;
import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class PasswordChangeFragmentViewModel extends BaseObservable
{
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String language;
    public ObservableInt progress = new ObservableInt(View.GONE);

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
        }
    }

    public PasswordChangeFragmentViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }

    public int getTitleText()
    {
        return R.string.passwordChanged;
    }

    public int getSaveText()
    {
        return R.string.saveTitle;
    }

    public int getHintConfirmPasswordText()
    {
        return R.string.hintConfirmNewPass;
    }

    public int getConfirmPasswordText()
    {
        return R.string.confirmNewPass;
    }

    public int getHintNewPasswordText()
    {
        return R.string.hintNewPassText;
    }

    public int getNewPasswordText()
    {
        return R.string.newPassText;
    }

    public int getHintOldPasswordText()
    {
        return R.string.hintOldPassText;
    }

    public int getOldPassText()
    {
        return R.string.oldPassText;
    }

    public String getPassWordText()
    {
        return context
                .getResources()
                .getString( R.string.passwordChanged) + ": ";
    }

}
