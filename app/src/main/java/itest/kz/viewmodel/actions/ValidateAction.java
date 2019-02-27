package itest.kz.viewmodel.actions;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.RegisterResponse;
import itest.kz.network.UserService;

public class ValidateAction implements Action
{

    private Context context;
    private ObservableField<String> login;
    private ObservableField<String> password;
    private ObservableField<String> confirmPassword;

    public ValidateAction(ObservableField<String> login, ObservableField<String> password,
                  ObservableField<String> confirmPassword, Context context)
    {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.context = context;
    }
    @Override
    public void run() throws Exception
    {
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        UserService userService = appController.getUserService();

        Disposable disposable = userService.register(login.get(), password.get(),
                confirmPassword.get())
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception
                    {
                        Toast toast;
                        toast = Toast.makeText(context.getApplicationContext(),
                        registerResponse.getMessage(),
                        Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

        compositeDisposable.add(disposable);
    }
}
