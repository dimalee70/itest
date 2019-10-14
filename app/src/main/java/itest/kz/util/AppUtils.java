package itest.kz.util;

import android.content.Context;
import android.content.res.Configuration;
import android.support.design.widget.BottomNavigationView;
import android.util.DisplayMetrics;
import android.view.Menu;

import java.util.Locale;

public class AppUtils
{
    private static Locale locale;

    public static void setLocale(String lang, Context context){
        Locale jaLocale = new Locale(lang);
        AppUtils.setLocale(jaLocale);
        AppUtils.setConfigChange(context);
    }
    public static void setLocale(Locale localeIn) {
        locale = localeIn;
        if(locale != null) {
            Locale.setDefault(locale);
        }
    }
    public static void setConfigChange(Context ctx){
        if(locale != null){
            Locale.setDefault(locale);

            Configuration configuration = ctx.getResources().getConfiguration();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            configuration.locale=locale;

            ctx.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }
    public static void setCheckable(BottomNavigationView view, boolean checkable, int index) {
        final Menu menu = view.getMenu();
        for(int i = 0; i < menu.size(); i++) {
            if (i == index)
            {
                menu.getItem(i).setChecked(true);
            }
            else
                menu.getItem(i).setCheckable(checkable);
        }
    }
}