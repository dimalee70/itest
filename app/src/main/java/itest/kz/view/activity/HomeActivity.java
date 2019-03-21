package itest.kz.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityHomeBinding;
import itest.kz.model.ProfileResponse;
import itest.kz.model.RegisterResponse;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.fragments.LoginFragment;
import itest.kz.view.fragments.ProfileFragment;
import itest.kz.viewmodel.HomeViewModel;

public class HomeActivity extends AppCompatActivity
{
    private ActivityHomeBinding activityHomeBinding;
    private HomeViewModel homeViewModel;
    private SharedPreferences sharedPreferences;
    private String accessToken;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        homeViewModel = new HomeViewModel(this);
        activityHomeBinding.setHome(homeViewModel);
        sharedPreferences = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();
        fetchProfileInfo();




    }

    public void setAccessToken()
    {
        this.accessToken = getIntent().getStringExtra(Constant.ACCESS_TOKEN);
        if (accessToken == null || accessToken.equals(""))
        {
            finish();

        }
        else
        {
//            System.out.println("Access ");
//            System.out.println(accessToken);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString(Constant.ACCESS_TOKEN, accessToken);
            editor.apply();
            editor.commit();

//            System.out.println(sharedPreferences.getString(Constant.ACCESS_TOKEN, null));

        }
    }
//    eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk2M2NhNTNhYzlmODNiODVmNDg5MjZhNTAyOTFlZDgyYmM4YjJmOWRiYjI2YTMwNzA5ZTZhYjVjMDZlOTY3M2M4ZjhhNGJkODBiOTIwZDNkIn0.eyJhdWQiOiIxIiwianRpIjoiOTYzY2E1M2FjOWY4M2I4NWY0ODkyNmE1MDI5MWVkODJiYzhiMmY5ZGJiMjZhMzA3MDllNmFiNWMwNmU5NjczYzhmOGE0YmQ4MGI5MjBkM2QiLCJpYXQiOjE1NTI2NjUzNjIsIm5iZiI6MTU1MjY2NTM2MiwiZXhwIjoxNTg0Mjg3NzYyLCJzdWIiOiIxMjEyNTIwIiwic2NvcGVzIjpbXX0.b5CtuVLp84AR3BJ9bTGSwdsW2fkDRbXGwuNMa57g0pM7sTXCx4SMb_6Xj8-tn4TcQ3vOJz1NMU0EdVfMuqv7k6CFoVXzA85PlJAjmbHPA5f8fE8F2RTRoVD3xQ_HKtAqt5KdaSmbw_JXdAAHK6JJ7uwgUe66-c7fuzAGw2Jdl_WZ53NLDok30qqYGxPxNi_3wYTD6ydn5aPu19SZEbsv2EcuLAOP47XAUQbSMegGH56Moz8fWs_XF4-zIa6XNyCVQ-TIZhytsCjh_yuqFuq5tXc6XfSg4rgs8JHG192YQwtdMQm_1SxYzWEN_J_24oR55g94jtlnuHuOZ_v6fvj8pC_EQtaotNgAVSC9fYJ5MO_tfb2hMkItNG2p1ZVemJJQlg3_Z9pWlSOVff1KTrl8L5HHW8oZOxnccmMGM7cel0rBC8ViTKSQSnxBMu4tZpw86ajPtML7GY_oIX97-0wwb7Ilp_xp4l1ElUbwYtebaiPME6GqoaR7wntEn-v_y4NIxCzJ3KrLC-4pvGIS0wExb_UPwvdI65XLYGPhl4lB02Azrr3qtQRNvIJjCT8J5nV3vKKBZHMgMS4U0IOe8dOpC5WRWyc9a0tZia9aekSF7cczKhT3hQylkOs1osh8ZDzvv8x_Eu5qJv9lMrUfWZYOpIdMG4LLiy4bR1MkzSz4AcU

    public void fetchProfileInfo()
    {
//        System.out.println(accessToken);
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        UserService userService = appController.getUserService();

        Disposable disposable = userService.getProfile("ru", Constant.ACCEPT,
                "Bearer " + accessToken)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProfileResponse>()
                {
                    @Override
                    public void accept(ProfileResponse profileResponse) throws Exception
                    {
                        openFragment(profileResponse);

                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openFragment(ProfileResponse profileResponse)
    {
//        System.out.println(profileResponse.getProfile());
        FragmentHelper.openFragment(this,
                R.id.frame_layout,
                ProfileFragment.newInstance(profileResponse.getProfile()));
    }

    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        finish();
    }
}


//    public void accept(RegisterResponse registerResponse) throws Exception
//    {
//        Toast toast;
//        toast = Toast.makeText(context.getApplicationContext(),
//                registerResponse.getMessage(),
//                Toast.LENGTH_LONG);
//        toast.show();
//    }