package itest.kz.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityHomeBinding;
import itest.kz.model.Profile;
import itest.kz.model.ProfileResponse;
import itest.kz.network.UserService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.fragments.ProfileFragment;
import itest.kz.viewmodel.HomeViewModel;

import static android.content.Context.MODE_PRIVATE;

public class HomeActivity extends Fragment
{
    private ActivityHomeBinding activityHomeBinding;
    private HomeViewModel homeViewModel;
    private SharedPreferences sharedPreferences;
    private String accessToken;
    private String language;
    private BottomNavigationViewEx bottomNavigationView;
    private TextView dialogText;
    private Button buttonYes;
    private Button buttonNo;
    private TextView dialogTextError;
    private Button buttonYesError;
    private Button buttonNoError;
    private String previousActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        activityHomeBinding = DataBindingUtil.inflate(inflater, R.layout.activity_home, container, false);
        homeViewModel = new HomeViewModel(getContext());
        activityHomeBinding.setHome(homeViewModel);
        sharedPreferences = getActivity().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        language = sharedPreferences.getString(Constant.LANG, "kz");
        setAccessToken();
        fetchProfileInfo();
        return activityHomeBinding.getRoot();
    }

    public void setAccessToken()
    {
        this.accessToken = getActivity().getIntent().getStringExtra(Constant.ACCESS_TOKEN);
        if (accessToken == null || accessToken.equals(""))
        {
           getActivity().finish();
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

        if (CheckUtility.isNetworkConnected(getContext()))
        {
            activityHomeBinding.buttonReflesh.setVisibility(View.GONE);
//        System.out.println(accessToken);
            AppController appController = new AppController();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
            UserService userService = appController.getUserService(accessToken);

            Disposable disposable = userService.getProfile(language)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ProfileResponse>() {
                                   @Override
                                   public void accept(ProfileResponse profileResponse) throws Exception {
                                       openFragment(profileResponse);
                                       homeViewModel.setBottomVisible(true);

                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    homeViewModel.setBottomVisible(false);
                                    if (throwable.getMessage().contains("401")) {
                                        showToastUnauthorized();
                                        homeViewModel.setProgress(false);

                                    }
//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                                }
                            });

            compositeDisposable.add(disposable);
        }
        else
        {
            homeViewModel.setProgress(false);
            activityHomeBinding
                    .buttonReflesh.setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            fetchProfileInfo();
                        }
                    });
            homeViewModel.setBottomVisible(true);
            activityHomeBinding.buttonReflesh.setVisibility(View.VISIBLE);
//            String text = "";
//
//            text = Constant.SERVER_ERROR_ALERT_RU;

            Dialog dialog = new Dialog(getContext());
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog)
                {
                    MainHomeActivity.isServerErrorDialogShown = false;
//                    openAuthActivity();
//                    finishTest(testIdMain);
                    //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogTextError = dialog.findViewById(R.id.dialog_text);
            buttonYesError = dialog.findViewById(R.id.buttonOk);
            buttonNoError = dialog.findViewById(R.id.buttonCancel);
            buttonNoError.setVisibility(View.GONE);
            buttonYesError.setText(R.string.ok);
//            if(language.equals(Constant.KZ))
//            {
//            buttonNo.setText(R.string.noKz);

            dialogTextError.setText(R.string.server_error_alert);

//            }
//            else
//            {
////            buttonNo.setText(R.string.noRu);
////            buttonYes.setText(R.string.yesRu);
//                dialogTextAuth.setText(R.string.sessionErrorRu);
//            }
            buttonYesError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonYesError.setEnabled(false);
                    MainHomeActivity.isServerErrorDialogShown = false;
                    dialog.dismiss();
//                    openAuthActivity();
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
                if (!MainHomeActivity.isServerErrorDialogShown) {
                    dialog.show();
                    MainHomeActivity.isServerErrorDialogShown = true;
                }

        }
    }

    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(getContext());
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
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogText = dialog.findViewById(R.id.dialog_text);
        buttonYes = dialog.findViewById(R.id.buttonOk);
        buttonNo = dialog.findViewById(R.id.buttonCancel);
        buttonNo.setVisibility(View.GONE);
        buttonYes.setText(R.string.ok);
        dialogText.setText(R.string.sessionError);
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
        if (!dialog.isShowing())
            dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(getContext(), AuthActivity.class);
        startActivity(intent);
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
        FragmentHelper.openFragment(getContext(),
                R.id.frame_layout,
                ProfileFragment.newInstance(profileResponse.getProfile()));
        homeViewModel.setProgress(false);

    }

//    @Override
//    public void onBackPressed()
//    {
//
//        if (currentFragment() instanceof ProfileFragment){
//            finishAffinity();
////            System.exit(0);
//        }
//        else
//        {
//            super.onBackPressed();
//        }

//        Fragment f = getFragmentManager().findFragmentById(R.id.frame_layout);
//        if(f instanceof ProfileFragment) {
//            // do something with f
//
//            finishAffinity();
//            System.exit(0);
//        }
//    }

//    public Fragment currentFragment(){
//        return getSupportFragmentManager().findFragmentById(R.id.frame_layout);
//    }

}