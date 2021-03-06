package itest.kz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import itest.kz.R;
import itest.kz.util.Constant;

public class SplashActivity extends AppCompatActivity
{
    private String accessToken;
    private SharedPreferences settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        SharedPreferences settings = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);
        if (accessToken != null && !accessToken.equals(""))
        {

            Intent intent = new Intent(this, MainHomeActivity.class);
            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
            startActivity(intent);
            finish();

        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
