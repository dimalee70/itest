package itest.kz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import itest.kz.util.Constant;

public class SplashActivity extends AppCompatActivity
{
    private String accessToken;
    private SharedPreferences settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        SharedPreferences settings = this.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);

        super.onCreate(savedInstanceState);

//
        if (accessToken != null && !accessToken.equals(""))
        {
            Intent intent = new Intent(this, MainHomeActivity.class);
            startActivity(intent);
            finish();

//            Intent intent = new Intent(this, SubjectActivity.class);
//            startActivity(intent);
//            finish();
        }
        else
        {
//        System.out.println("AcessToken");
//        System.out.println(accessToken);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
