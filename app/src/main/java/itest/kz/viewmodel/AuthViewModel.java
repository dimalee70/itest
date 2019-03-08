package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;


import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import itest.kz.R;
import itest.kz.util.FragmentHelper;
import itest.kz.util.InputValidator;
import itest.kz.util.RxUtils;
import itest.kz.view.fragments.LoginFragment;
import itest.kz.view.fragments.SignUpFragment;

public class AuthViewModel extends Observable
{
    private Context context;
    public Action logIn;
    public Action signUp;
    public ObservableInt buttonColor ;


    public AuthViewModel(Context context)
    {
        this.context = context;
        this.buttonColor = new ObservableInt(Color.red(0));
        logIn = () ->
        {
            FragmentHelper.openFragment(context, R.id.fl_fragment_container, new LoginFragment());
        };

        signUp = () ->
        {
            FragmentHelper.openFragment(context, R.id.fl_fragment_container, new SignUpFragment());

        };
    }

}
