package itest.kz.viewmodel;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.widget.Toast;


import com.google.gson.JsonObject;

import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.network.UserService;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class BottomForgotPasswordDialogViewModel extends Observable
{
    private Context context;
    private String language;
    private String accessToken;
    public Action clickSent;
    public ObservableField<String> login = new ObservableField<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BottomForgotPasswordDialogViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
////        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
////        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, null);
        clickSent = () ->
        {
            resetPassword();
        };
    }

    public void resetPassword()
    {

        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        UserService userService = appController.getUserService();

        Disposable disposable = userService.resetPassword(Constant.ACCEPT,
                language, login.get())
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {
                                   String answer;
                                   if (jsonObject.has("message"))
                                       answer = jsonObject.get("message").toString();
                                   else
                                       answer = jsonObject.get("error").toString();

                                   Toast toast;
                                    toast = Toast.makeText(context.getApplicationContext(),
                                            answer,
                                            Toast.LENGTH_LONG);
                                    toast.show();
                               }
                           }
                );

        compositeDisposable.add(disposable);
    }

    public int getSendButtonText()
    {
        if (language.equals(Constant.KZ))
            return R.string.forgotPasswordButtonKz;
        return R.string.forgotPasswordButoonRu;
    }

}
