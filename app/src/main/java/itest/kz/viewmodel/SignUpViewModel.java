package itest.kz.viewmodel;


import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.BoringLayout;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import itest.kz.util.RxUtils;
import io.reactivex.functions.Function3;


public class SignUpViewModel
{
    public ObservableField<String> login = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<Boolean> enableSignUp;

    public Action signUp;

    public SignUpViewModel()
    {
        Observable <Boolean> result = Observable.combineLatest
                (RxUtils.toObservable(login),
                        RxUtils.toObservable(password),
                        new BiFunction<String, String, Boolean>() {
                            @Override
                            public Boolean apply(String s, String s2) throws Exception
                            {
                                int failcount = 0;
                                if (!isValidLogin(s))
                                    ++failcount;
                                if (!isValidPassword(s2))
                                    ++failcount;

                                System.out.println("failcount " + failcount );


                                return failcount == 0;
                            }
                        });
        enableSignUp = RxUtils.toField(result);

        System.out.println("enable " + enableSignUp.get());
        signUp = new Action()
        {
            @Override
            public void run() throws Exception {
                System.out.println("login " + login.get());
            }
        };
    }
    public Boolean isValidPassword(String password)
    {
        return true;
    }

    public Boolean isValidLogin(String login)
    {
        return true;
    }










//    private Context context;
//    public final ObservableField<String> login = new ObservableField<>("");
//    public final ObservableField<String> password = new ObservableField<>("");
//    public Observable<String> loginObservable = RxUtils.toObservable(login);
//    public  Observable<String> passwordObservable = RxUtils.toObservable(password);
//
//    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    public Observable<Boolean> result;
//
//
//
//
//
//
//
//    public SignUpViewModel()
//    {
//
//        init();
//    }
//
//    public void init()
//    {
//
//        Observable<Boolean> result = Observable
//                .combineLatest(loginObservable,
//                        passwordObservable,
//                        new BiFunction<String, String, Boolean>() {
//                            @Override
//                            public Boolean apply(String s, String s2) throws Exception {
//                                return isValidPassword(s) && isValidPassword(s2);
//                            }
//                        });
//
//        this.result = result;
//        Disposable disposable = result
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        System.out.println("changed");
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        System.out.println("Error");
//                    }
//                });
//
//        compositeDisposable.add(disposable);
//    }
//
//
//
////    private Boolean isValidForm(String s, String s2)
////    {
////    }
//
//    private Boolean isValidPassword(String password)
//    {
//        return password != null && !password.isEmpty();
//    }
//
//    private Boolean isValidLogin(String login)
//    {
//        return login != null && !login.isEmpty();
//    }
//
//
    public void onLoginButtonClick()
    {
//        login = new ObservableField<>();
//        password = new ObservableField<>();
//        init();
//        System.out.println("result " + result.get());
        System.out.println("My Login " + login.get());
//        System.out.println("My Password " + password.get());
    }




//    public Context getContext()
//    {
//        return context;
//    }

//    public void setContext(Context context)
//    {
//        this.context = context;
//    }
//
//    public ObservableField<String> getLogin()
//    {
//        return login;
//    }

//    public void setLogin(ObservableField<String> login)
//    {
//        this.login = login;
//    }

//    public ObservableField<String> getPassword()
//    {
//        return password;
//    }
//
//    public void setPassword(ObservableField<String> password)
//    {
//        this.password = password;
//    }


}
