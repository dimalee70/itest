package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;

import static android.content.Context.MODE_PRIVATE;

public class SplashScreensViewModel extends BaseObservable
{
    private Context context;
    private String language;
    public Action logIn;

    public SplashScreensViewModel(Context context)
    {
        this.context = context;
        logIn = () ->
        {
            Intent intent = new Intent(context, AuthActivity.class);
            context.startActivity(intent);
        };

        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }

    public int getPassText()
    {
        return R.string.passText;
    }

    public int getBeginText()
    {
        return R.string.begin;
    }


}
