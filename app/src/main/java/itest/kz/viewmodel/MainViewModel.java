package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.support.v4.app.FragmentManager;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.util.FragmentHelper;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.SubjectActivity;
import itest.kz.view.fragments.LoginFragment;

public class MainViewModel
{
    private LoginFragment loginFragment;
    private FragmentManager fragmentManager;
    private Context context;
    public Action buttonKaz;

    public MainViewModel (Context context)
    {
        this.context = context;
        this.loginFragment = new LoginFragment();
        fragmentManager = FragmentHelper.getFragmentManager(context);

        buttonKaz = () ->
        {
            Intent intent = new Intent(context, SubjectActivity.class);
            ((Activity)context).startActivity(intent);
        };

    }
    public ObservableBoolean helloButtonEnabled = new ObservableBoolean(false);
//
//    public void buttonClicked() {
//        helloText.set(String.format("Hello %s %s !", firstName.get(), lastName.get()));
//    }
//    public void buttonClicked()
//    {
//        FragmentHelper.openFragment(context,R.id.fl_fragment_container, new LoginFragment());
//        System.out.println("Hello World");
//    }

    public void clickOnSignUp()
    {
        Intent intent = new Intent(context, AuthActivity.class);
        ((Activity)context).startActivity(intent);

//        System.out.println("clickOnSignUp");
    }
}
