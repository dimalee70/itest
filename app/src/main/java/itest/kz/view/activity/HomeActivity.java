package itest.kz.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
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
    private String language;
    private BottomNavigationView bottomNavigationView;
    private TextView dialogText;
    private Button buttonYes;
    private Button buttonNo;


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.main, menu);
//    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu)
//    {
//        if(Build.VERSION.SDK_INT > 11)
//        {
//            menu.findItem(R.id.menu_github).setVisible(false);
//            menu.findItem(R.id.menu_github1).setVisible(false);
//            menu.findItem(R.id.menu_github2).setVisible(false);
//            menu.findItem(R.id.menu_save).setVisible(false);
//            menu.findItem(R.id.menu_logout).setVisible(true);
//        }
//        super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        activityHomeBinding =
//                DataBindingUtil.inflate(inflater,
//                        R.layout.activity_home, container, false);
//        homeViewModel = new HomeViewModel(getContext());
//        sharedPreferences = getActivity().getSharedPreferences
//                (Constant.MY_PREF, Context.MODE_PRIVATE);
//        setAccessToken();
//        fetchProfileInfo();
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    public void setNavigationVisibility(boolean visible) {
        if (bottomNavigationView.isShown() && !visible) {
            bottomNavigationView.setVisibility(View.GONE);
        }
        else if (!bottomNavigationView.isShown() && visible){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        homeViewModel = new HomeViewModel(this);
        activityHomeBinding.setHome(homeViewModel);
        sharedPreferences = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();
        fetchProfileInfo();

        bottomNavigationView = (BottomNavigationView) activityHomeBinding.bottomNavigationView;


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                    {
                        Intent intent = null;
                        switch (menuItem.getItemId())
                        {
                            case R.id.item_test:
//                                if (testFragment.isAdded())
//                                {
//                                    fragmentManager.beginTransaction().remove(testFragment).commit();
//                                    System.out.println("Added");
//                                }

//                                else
//                                fragmentManager.beginTransaction().replace(R.id.viewpager, testFragment)
//                                        .commit();
                                finish();
                                intent = new Intent(HomeActivity.this,SubjectActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
                                break;
                            case R.id.item_statistic:
                                finish();
                                intent = new Intent(HomeActivity.this,StatisticActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                startActivity(intent);
                            case R.id.item_user:
//                                fetchProfileInfo();
//                                finish();
//                                intent = new Intent(HomeActivity.this,HomeActivity.class);
//                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                startActivity(intent);
//                                fragmentManager.beginTransaction().replace(R.id.viewpager, fragment).commit();
                                break;

                        }
                        return true;
                    }
                }
        );

        bottomNavigationView.setSelectedItemId(R.id.item_user);



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
        homeViewModel.setProgress(true);
//        System.out.println(accessToken);
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        UserService userService = appController.getUserService();

        Disposable disposable = userService.getProfile(language, Constant.ACCEPT,
                "Bearer " + accessToken)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProfileResponse>() {
                               @Override
                               public void accept(ProfileResponse profileResponse) throws Exception {
                                   openFragment(profileResponse);
                                   homeViewModel.setBottomVisible(true);

                               }
                           },
                        new Consumer<Throwable>()
                        {
                            @Override
                            public void accept(Throwable throwable) throws Exception
                            {
                                homeViewModel.setBottomVisible(false);
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
                                    homeViewModel.setProgress(false);

                                }
//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                            }
                        });

        compositeDisposable.add(disposable);
    }

    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogText = dialog.findViewById(R.id.dialog_text);
        buttonYes = dialog.findViewById(R.id.buttonOk);
        buttonNo = dialog.findViewById(R.id.buttonCancel);
        buttonNo.setVisibility(View.GONE);
        buttonYes.setText(R.string.ok);
        if(language.equals(Constant.KZ))
        {
//            buttonNo.setText(R.string.noKz);

            dialogText.setText(R.string.sessionErrorKz);

        }
        else
        {
//            buttonNo.setText(R.string.noRu);
//            buttonYes.setText(R.string.yesRu);
            dialogText.setText(R.string.sessionErrorRu);
        }
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYes.setEnabled(false);
                dialog.dismiss();
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954

            }
        });

//        buttonNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(this, AuthActivity.class);
        ((Activity)this).startActivity(intent);
//        if (language.equals(Constant.KZ))
//
//            Toast.makeText(this,
//                    R.string.sessionErrorKz,
//                    Toast.LENGTH_SHORT).show();
//        else
//        {
//            Toast.makeText(this,
//                    R.string.sessionErrorRu,
//                    Toast.LENGTH_SHORT).show();
//        }
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
        homeViewModel.setProgress(false);


//        ProfileFragment nextFrag= ProfileFragment.newInstance(profileResponse.getProfile());
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame_layout, nextFrag, "findThisFragment")
//                .addToBackStack(null)
//                .commit();

//        ProfileFragment nextFrag= ProfileFragment.newInstance(profileResponse.getProfile());
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame_layout, nextFrag, "findThisFragment")
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public void onBackPressed()
    {
        finishAffinity();
        System.exit(0);
    }

}