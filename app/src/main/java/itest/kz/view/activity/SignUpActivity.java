package itest.kz.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import itest.kz.R;
import itest.kz.databinding.ActivityMainBinding;
import itest.kz.databinding.ActivitySignUpBinding;
import itest.kz.databinding.ActivitySignUpBindingImpl;
import itest.kz.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity
{
//    private EditText etLogin, etPassword;
//    private Button btnLogin;
//
//    private Observable<Boolean> observable;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
//        init();

        ActivitySignUpBinding activitySignUpBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = new SignUpViewModel();
        activitySignUpBinding.setSignUpViewModel(signUpViewModel);

//        observable = Observable.combineLatest()


    }

//    private void init()
//    {
//        etLogin = findViewById(R.id.et_login);
//        etPassword = findViewById(R.id.et_password);
//        btnLogin = findViewById(R.id.btn_login);
//
//        Observable<String> loginObservable = RxTextView.textChanges(etLogin)
//                .skip(1)
//                .map(new Function<CharSequence, String>()
//                {
//                    @Override
//                    public String apply(CharSequence charSequence) throws Exception {
//                        return charSequence.toString();
//                    }
//                });
//
//        Observable<String> passwordObservable = RxTextView.textChanges(etPassword)
//                .skip(1)
//                .map(new Function<CharSequence, String>()
//                {
//                    @Override
//                    public String apply(CharSequence charSequence) throws Exception {
//                        return charSequence.toString();
//                    }
//                });
//
//        observable = Observable.combineLatest(loginObservable, passwordObservable,
//                new BiFunction<String, String, Boolean>() {
//                    @Override
//                    public Boolean apply(String s, String s2) throws Exception {
//                        return isValidForm(s, s2);
//
//                    }
//                });
//
//        observable.subscribe(new DisposableObserver<Boolean>() {
//            @Override
//            public void onNext(Boolean aBoolean)
//            {
//                System.out.println("onNext");
//                btnLogin.setEnabled(true);
//            }
//
//            @Override
//            public void onError(Throwable e)
//            {
//                System.out.println("onError");
//            }
//
//            @Override
//            public void onComplete()
//            {
//                System.out.println("onComplete");
//            }
//        });
//
//    }

//    public boolean isValidForm(String name, String password) {
//        boolean validName = !name.isEmpty();
//
//        if (!validName) {
//            etLogin.setError("Please enter valid name");
//        }
//
//        boolean validPass = !password.isEmpty();
//        if (!validPass) {
//            etPassword.setError("Incorrect password");
//        }
//        return validName && validPass;
//    }
}
