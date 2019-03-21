package itest.kz.viewmodel.actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.LoginResponse;
import itest.kz.model.RegisterResponse;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.view.activity.HomeActivity;

public class LoginAction implements Action
{
    private Context context;
    private ObservableField<String> login;
    private  ObservableField<String> password;

    public LoginAction(Context context,
                       ObservableField<String> login,
                       ObservableField<String> password)
    {
        this.context = context;
        this.login = login;
        this.password = password;
    }
//    AppController appController = new AppController();
//    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    //        AppController appController = AppController.create(context);
//    UserService userService = appController.getUserService();
//
//    Disposable disposable = userService.register(login.get(), password.get(),
//            confirmPassword.get())
//            .subscribeOn(appController.subscribeScheduler())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Consumer<RegisterResponse>() {
//                @Override
//                public void accept(RegisterResponse registerResponse) throws Exception
//                {
//                    Toast toast;
//                    toast = Toast.makeText(context.getApplicationContext(),
//                            registerResponse.getMessage(),
//                            Toast.LENGTH_LONG);
//                    toast.show();
//                }
//            });
//
//        compositeDisposable.add(disposable);
    @Override
    public void run() throws Exception
    {
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        UserService userService = appController.getUserService();
        Disposable disposable = userService.login("ru",
                                                  Constant.ACCEPT,
                                                  login.get(),
                                                  password.get())
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception
                    {
//                        SharedPreferences sharedPref = (Activity)context.getPreferences()
////                                context..getPreferences(Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
//                        editor.commit();
                        if (loginResponse.getAccessToken() == null
                                || loginResponse.getAccessToken() == "")
                        {
//                            System.out.println("error");
                        }
                        else
                        {
//                            System.out.println("Access_token");
//                            System.out.println(loginResponse.getAccessToken());
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.putExtra(Constant.ACCESS_TOKEN, loginResponse.getAccessToken());
                            context.startActivity(intent);
                        }



                    }

                });
        compositeDisposable.add(disposable);

    }
}
