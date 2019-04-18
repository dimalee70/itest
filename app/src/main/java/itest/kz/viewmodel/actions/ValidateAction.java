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
import itest.kz.util.CheckUtility;

public class ValidateAction implements Action
{

    private Context context;
    private ObservableField<String> login;
    private ObservableField<String> password;
    private ObservableField<String> confirmPassword;
    private String language;

    public ValidateAction(String language, ObservableField<String> login, ObservableField<String> password,
                  ObservableField<String> confirmPassword, Context context)
    {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.context = context;
        this.language = language;
    }
    @Override
    public void run() throws Exception
    {
        if (CheckUtility.isNetworkConnected(context))
        {
            AppController appController = new AppController();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
            UserService userService = appController.getUserService();

            Disposable disposable = userService.register(language,login.get(), password.get(),
                    confirmPassword.get())
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<RegisterResponse>() {
                        @Override
                        public void accept(RegisterResponse registerResponse) throws Exception
                        {
                            String mess;
                            if (registerResponse.getSuccess())
                            {
                                mess = registerResponse.getMessage();
                                login.set("");
                                password.set("");
                                confirmPassword.set("");
                            }
                            else
                            {
                                mess = registerResponse.getError();
                            }

                            Toast toast;
                            toast = Toast.makeText(context,
                                    mess,
                                    Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });

            compositeDisposable.add(disposable);
        }
        else
        {
            Toast toast;
            toast = Toast.makeText(context,
                    "No internet",
                    Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
