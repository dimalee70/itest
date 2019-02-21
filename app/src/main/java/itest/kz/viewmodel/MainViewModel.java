package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;

import itest.kz.view.activity.SignUpActivity;

public class MainViewModel
{
    private Context context;

    public MainViewModel (Context context)
    {
        this.context = context;
    }
    public ObservableBoolean helloButtonEnabled = new ObservableBoolean(false);
//
//    public void buttonClicked() {
//        helloText.set(String.format("Hello %s %s !", firstName.get(), lastName.get()));
//    }
    public void buttonClicked()
    {
        System.out.println("Hello World");
    }

    public void clickOnSignUp()
    {
        Intent intent = new Intent(context, SignUpActivity.class);
        ((Activity)context).startActivity(intent);

//        System.out.println("clickOnSignUp");
    }
}
