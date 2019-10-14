package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Observable;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.util.Constant;
import itest.kz.view.activity.ResultsActivity;
import itest.kz.view.activity.SubjectActivity;

import static android.content.Context.MODE_PRIVATE;

public class ResultsViewModel extends Observable
{
    private Context context;
    private String language;
    public Action onClickClose;

    public ResultsViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        onClickClose = () ->
        {
            Intent intent = new Intent(context.getApplicationContext(), SubjectActivity.class);
            context.startActivity(intent);
        };
    }

    public String getLanguage()
    {
        return language;
    }

    public int getTitle()
    {
        return R.string.result;
    }
}
