package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;

import java.util.Locale;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.util.AppUtils;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.SplashScreensActivity;
import itest.kz.view.activity.SubjectActivity;
import itest.kz.view.fragments.LoginFragment;

import static android.content.Context.MODE_PRIVATE;

public class MainViewModel extends BaseObservable
{
    private LoginFragment loginFragment;
    private FragmentManager fragmentManager;
    private Context context;
    public Action buttonKaz;
    public Action buttonRus;
    private String language;

    private SharedPreferences sharedPreferences;

    public MainViewModel (Context context)
    {

        this.context = context;
        this.loginFragment = new LoginFragment();
        fragmentManager = FragmentHelper.getFragmentManager(context);

        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);

        language = settings.getString(Constant.LANG, Constant.KZ);
        AppUtils.setLocale((language.equals(Constant.KZ)?"en":"ru")
                ,context);

        buttonKaz = () ->
        {
            sharedPreferences = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.LANG, Constant.KZ);
            editor.apply();
            editor.commit();
            language = Constant.KZ;
            AppUtils.setLocale("en",context);
            notifyChange();

//            Intent intent = new Intent(context, MainActivity.class);
//            ((Activity)context).startActivity(intent);


        };

        buttonRus = () ->
        {
            sharedPreferences = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.LANG, Constant.RU);
            editor.apply();
            editor.commit();

            language = Constant.RU;
            AppUtils.setLocale(language,context);
            notifyChange();
//            Intent intent = new Intent(context, MainActivity.class);
//            ((Activity)context).startActivity(intent);
        };

    }


    public ObservableBoolean helloButtonEnabled = new ObservableBoolean(false);

    public void clickOnSignUp()
    {
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        String value = settings.getString(Constant.ACCESS_TOKEN, null);

        SharedPreferences isFirst = context
                .getSharedPreferences(Constant.MY_START, MODE_PRIVATE);
        String restoredText = isFirst.getString(Constant.MY_START, null);

        if (restoredText == null) {
            Intent intent = new Intent(context, SplashScreensActivity.class);
            context.startActivity(intent);
            isFirst
                    .edit()
                    .putString(Constant.MY_START, Constant.MY_START)
            .commit();

        }
        else {
            if (value != null && !value.equals(""))
            {
                Intent intent = new Intent(context, MainHomeActivity.class);
                intent.putExtra(Constant.ACCESS_TOKEN, value);
                context.startActivity(intent);
            } else
                {
                Intent intent = new Intent(context, AuthActivity.class);
                ((Activity) context).startActivity(intent);
            }
        }


//        System.out.println("clickOnSignUp");
    }

    public int getSelectLanguage()
    {
            return R.string.selectLanguage;
    }

    public int getContinue()
    {
        return R.string.cont;
    }

    public Drawable getColorKz()
    {
        Resources res = context.getResources();
        if (language.equals(Constant.KZ))
            return ResourcesCompat.getDrawable(res, R.drawable.button_kaz, null);
        return ResourcesCompat.getDrawable(res, R.drawable.button_rus, null);
    }

    public Drawable getColorRu()
    {
        Resources res = context.getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.button_kaz, null);
        if (language.equals(Constant.RU))
            return drawable;
        return ResourcesCompat.getDrawable(res, R.drawable.button_rus, null);
    }
}
