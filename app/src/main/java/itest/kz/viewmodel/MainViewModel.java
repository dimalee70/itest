package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.support.v4.app.FragmentManager;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.SubjectActivity;
import itest.kz.view.fragments.LoginFragment;

import static android.content.Context.MODE_PRIVATE;

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
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        String value = settings.getString(Constant.ACCESS_TOKEN, null);
        if (value != null && !value.equals(""))
        {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.putExtra(Constant.ACCESS_TOKEN, value);
            context.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(context, AuthActivity.class);
            ((Activity)context).startActivity(intent);
        }


//        System.out.println("clickOnSignUp");
    }
}
