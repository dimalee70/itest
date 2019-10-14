package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.net.Uri;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class AgreementViewModel extends BaseObservable
{
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String language;
    public Action onClickPolicy;
    public  Action onClickAgreement;

    public AgreementViewModel(Context context)
    {
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        this.context = context;
        onClickPolicy = () ->
        {
            String url = "https://itest.kz/"+language+"/privacy-policy";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        };

        onClickAgreement = () ->
        {
//            String url = "https://itest.kz/"+language+"/privacy-policy";
            String url = "https://itest.kz/"+language+"/terms-of-use";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        };
    }

    public int getTitleText()
    {
        return R.string.helpCenter;
    }

    public int getAgreement()
    {
        return R.string.agreement;
    }

    public int getPolicy()
    {
        return R.string.policy;
    }
}
