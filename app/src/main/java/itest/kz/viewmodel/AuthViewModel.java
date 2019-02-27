package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import itest.kz.R;
import itest.kz.util.FragmentHelper;
import itest.kz.util.InputValidator;
import itest.kz.util.RxUtils;
import itest.kz.view.fragments.LoginFragment;
import itest.kz.view.fragments.SignUpFragment;

public class AuthViewModel
{
    private Context context;
    public Action logIn;
    public Action signUp;

    public AuthViewModel(Context context)
    {
        this.context = context;
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
