package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import itest.kz.R;
import itest.kz.util.Constant;
import itest.kz.util.InputValidator;
import itest.kz.util.RxUtils;
import itest.kz.view.fragments.BottomForgotPasswordDialogFragment;
import itest.kz.viewmodel.actions.LoginAction;

import static android.content.Context.MODE_PRIVATE;

public class LoginViewModel
{
    private String language;
    private Context context;
    public ObservableField<String> login = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
//    public ObservableBoolean mIsLoading = new ObservableBoolean();

//    public ObservableField<String> loginErr = new ObservableField<>();
//    public ObservableField<String> passwordErr = new ObservableField<>();
    public ObservableField<Boolean> enableLogin;
    public Action clickForgot;
    public Action logIn;
    private FragmentActivity fragmentActivity;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LoginViewModel(Context context, FragmentActivity fragmentActivity)
    {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        clickForgot = () ->
        {
            BottomForgotPasswordDialogFragment bottomForgotPasswordDialogFragment =
                    new BottomForgotPasswordDialogFragment();
            bottomForgotPasswordDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);


//            bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog);
//            bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Dialog);
//            bottomSheetFragment.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomForgotPasswordDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "test");
        };

        Observable result = Observable.combineLatest(RxUtils.toObservable(login),
                RxUtils.toObservable(password), new BiFunction<String, String, Boolean>() {
                    @Override
                    public Boolean apply(String login, String password) throws Exception {
                        int failCount = 0;
                        if (!InputValidator.validateUserName(login)) {
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
                        return failCount == 0;
                    }
                });

        enableLogin = RxUtils.toField(result);

        checkLogin();

    }

    public int getLoginHint()
    {
        if (language.equals(Constant.KZ))
            return R.string.emailPlaceholderKz;
        return R.string.emailPlaceholderRu;
    }

    public int getPasswordHint()
    {
        if (language.equals(Constant.KZ))
            return R.string.passwordPlaceholderKz;
        return R.string.passwordPlaceholderRu;
    }

    public int getLogInButtonText()
    {
        if (language.equals(Constant.KZ))
            return R.string.logInKaz;
        return R.string.logInRu;
    }

    private void checkLogin()
    {
//        setIsLoading(true);
        LoginAction loginAction = new LoginAction(context, login, password);
        logIn = loginAction;
//        setIsLoading(false);
    }

    public Spanned getText()
    {
        String text;
        if (language.equals(Constant.KZ))
            text = "<p>Авторизациядан өту кезінде сіз " +
                    "<a href=\"https://itest.kz/"+language+"/terms-of-use\">Қолданушылық</a> " +
                    "келісімі мен <a href=\"https://itest.kz/"+language+"/privacy-policy\">Құпиялылық" +
                    " саясатының</a> шарттарын автоматты түрде қабылдайсыз</p>";
        else
            text = "<p>Проходя авторизацию через Социальную сеть вы автоматически принимаете условия " +
                    "<a href=\"https://itest.kz/"+language+"/terms-of-use\">Пользовательского соглашения </a> " +
                    "и <a href=\"https://itest.kz/"+language+"/privacy-policy\">Политики конфиденциальности</p>";
//        String text = "<a href=\"https://vk.com\">Google</a>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml(text);
    }

    public int getForgotPassword()
    {
        if (language.equals(Constant.KZ))
            return R.string.forgotPasswordKz;
        return R.string.forgotPasswordRu;
    }

//    public void setIsLoading(boolean isLoading)
//    {
//        mIsLoading.set(isLoading);
//    }
//
//    public ObservableBoolean getIsLoading()
//    {
//        return mIsLoading;
//    }
}
