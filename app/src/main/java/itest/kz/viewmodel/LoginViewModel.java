package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import itest.kz.util.InputValidator;
import itest.kz.util.RxUtils;
import itest.kz.viewmodel.actions.LoginAction;

public class LoginViewModel
{
    private Context context;
    public ObservableField<String> login = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    public ObservableField<String> loginErr = new ObservableField<>();
    public ObservableField<String> passwordErr = new ObservableField<>();
    public ObservableField<Boolean> enableLogin;

    public Action logIn;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LoginViewModel(Context context)
    {
        this.context = context;
        Observable result = Observable.combineLatest(RxUtils.toObservable(login),
                RxUtils.toObservable(password), new BiFunction<String, String, Boolean>() {
                    @Override
                    public Boolean apply(String login, String password) throws Exception {
                        int failCount = 0;
                        if (!InputValidator.validateUserName(login)) {
                            ++failCount;
                            loginErr.set("Username format not correct");
                        } else {
                            loginErr.set("");
                        }

                        if (!InputValidator.validatePassword(password)) {
                            ++failCount;
                            passwordErr.set("Password format not correct");
                        } else {
                            passwordErr.set("");
                        }
                        return failCount == 0;
                    }
                });

        enableLogin = RxUtils.toField(result);

        checkLogin();

    }

    private void checkLogin()
    {
        LoginAction loginAction = new LoginAction(context, login, password);
        logIn = loginAction;
    }

}
