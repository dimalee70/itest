package itest.kz.viewmodel;


import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import itest.kz.app.AppController;
import itest.kz.model.RegisterResponse;
import itest.kz.network.UserService;
import itest.kz.viewmodel.actions.ValidateAction;
import itest.kz.util.InputValidator;
import itest.kz.util.RxUtils;


public class SignUpViewModel
{



/**
 * Created by jacksvarghese on 4/10/18.
 */


    private static final String TAG = "LoginViewModel";

    public ObservableField<String> login = new ObservableField<>();
    public ObservableField<String> password  = new ObservableField<>();
    public ObservableField<String> confirmPassword  = new ObservableField<>();
    public ObservableField<String> loginErr  = new ObservableField<>();
    public ObservableField<String> passwordErr  = new ObservableField<>();
    public ObservableField<String> confirmPasswordErr  = new ObservableField<>();
    public ObservableField<Boolean> enableLogin;
    public Action signIn;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SignUpViewModel(Context context)
    {

        this.context = context;
        Observable result = Observable.combineLatest(RxUtils.toObservable(login),
                RxUtils.toObservable(password),
                RxUtils.toObservable(confirmPassword), new Function3<String, String, String, Boolean>() {
                    @Override
                    public Boolean apply(String userName, String password, String email) throws Exception {
                        int failCount = 0;
                        if (!InputValidator.validateUserName(userName)) {
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

                        if (!InputValidator.validateEmail(email)) {
                            ++failCount;
                            confirmPasswordErr.set("Email format not correct");
                        } else {
                            confirmPasswordErr.set("");
                        }
                        return failCount == 0;
                    }});

        enableLogin = RxUtils.toField(result);

        ValidateAction validateAction = new ValidateAction(login, password, confirmPassword, context);

        signIn = validateAction;
    }

    public void destroy()
    {

    }
}
