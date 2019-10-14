package itest.kz.viewmodel;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.RegisterResponse;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.viewmodel.actions.ValidateAction;
import itest.kz.util.InputValidator;
import itest.kz.util.RxUtils;

import static android.content.Context.MODE_PRIVATE;


public class SignUpViewModel
{



/**
 * Created by jacksvarghese on 4/10/18.
 */


    private static final String TAG = "LoginViewModel";

    public ObservableField<String> login = new ObservableField<>();
    public ObservableField<String> password  = new ObservableField<>();
    public ObservableField<String> confirmPassword  = new ObservableField<>();
    public ObservableField<Boolean> enableLogin;
    public Action signIn;
    private Context context;
    private String language;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SignUpViewModel(Context context)
    {

        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        Observable result = Observable.combineLatest(RxUtils.toObservable(login),
                RxUtils.toObservable(password),
                RxUtils.toObservable(confirmPassword), new Function3<String, String, String, Boolean>() {
                    @Override
                    public Boolean apply(String userName, String password, String email) throws Exception {
                        int failCount = 0;
                        if (!InputValidator.validateUserName(userName)) {
                            ++failCount;
//                            loginErr.set("Username format not correct");
                        }
//                        else {
//                            loginErr.set("");
//                        }

                        if (!InputValidator.validatePassword(password)) {
                            ++failCount;
//                            passwordErr.set("Password format not correct");
                        }
//                        else {
//                            passwordErr.set("");
//                        }

                        if (!InputValidator.validatePassword(email)) {
                            ++failCount;

//                            confirmPasswordErr.set("Email format not correct");
                        }
//                        else
//                            {
//                            confirmPasswordErr.set("");
//                        }
                        return failCount == 0;
                    }});

        enableLogin = RxUtils.toField(result);

        ValidateAction validateAction = new ValidateAction(language, login, password, confirmPassword, context);

        signIn = validateAction;
    }

    public int getLoginHint()
    {
        return R.string.emailPlaceholder;
    }

    public int getPasswordHint()
    {
        return R.string.passwordPlaceholder;
    }

    public int getPasswordConfirmHint()
    {
        return R.string.passwordConfirmHint;
    }

    public int getSignInButtonText()
    {
        return R.string.signUp;
    }

    public Spanned getText()
    {
        String text;

        if (language.equals(Constant.KZ))
        {
            text = "<p>Тіркелу арқылы сіз  " +
                    "<a href=\"https://itest.kz/"+language+"/terms-of-use\">Қолданушылық</a> " +
                    "келісімі мен <a href=\"https://itest.kz/"+language+"/privacy-policy\"> Құпиялылық саясатының " +
                    "</a> шарттарын автоматты түрде қабылдайсыз</p>";
        }
        else
        {
            text = "<p>Проходя регистрацию вы автоматически принимаете условия " +
                "<a href=\"https://itest.kz/"+language+"/terms-of-use\">Пользовательского соглашения </a> " +
                "и <a href=\"https://itest.kz/"+language+"/privacy-policy\"> Политики конфиденциальности ";
        }

//        String text = "<a href=\"https://vk.com\">Google</a>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml(text);
    }

//     мен  шарттарын автоматты түрде қабылдайсыз
    public void destroy()
    {

    }
}
