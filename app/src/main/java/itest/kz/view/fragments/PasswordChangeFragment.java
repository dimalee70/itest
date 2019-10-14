package itest.kz.view.fragments;

import android.app.Activity;
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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import itest.kz.databinding.FragmentPasswordChangedBinding;
import itest.kz.model.PasswordChangeResponce;
import itest.kz.model.ProfileInfo;
import itest.kz.model.ProfileResponse;
import itest.kz.network.UserService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.viewmodel.PasswordChangeFragmentViewModel;
import itest.kz.viewmodel.ProfileInfoViewModel;

import static android.content.Context.MODE_PRIVATE;
import static itest.kz.util.InputValidator.hideKeyboard;

public class PasswordChangeFragment extends Fragment
{
    private FragmentPasswordChangedBinding fragmentPasswordChangedBinding;
    private PasswordChangeFragmentViewModel passwordChangeFragmentViewModel;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private String language;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;

    public static PasswordChangeFragment newInstance()
    {

        Bundle args = new Bundle();

        PasswordChangeFragment fragment = new PasswordChangeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                ((MainHomeActivity)(Activity)getContext()).setNavigationVisibiltity(true);
                closefragment();
                return true;
//            case R.id.menu_save:
//                System.out.println("save");
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closefragment()
    {

        getFragmentManager().popBackStackImmediate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


//        ((MainHomeActivity)(Activity)getActivity()).findViewById(R.id.bottom_navigation_view).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.bottom_navigation_view).setVisibility(View.GONE);
        setHasOptionsMenu(true);
        getAccessToken();
        SharedPreferences settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        fragmentPasswordChangedBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_password_changed,
                container, false);
        passwordChangeFragmentViewModel = new PasswordChangeFragmentViewModel(getContext());
        fragmentPasswordChangedBinding.setPassword(passwordChangeFragmentViewModel);
//        ((HomeActivity)getActivity()).setNavigationVisibility(false);
        setMyToolbar();



        return fragmentPasswordChangedBinding.getRoot();
    }

    public void getAccessToken()
    {
        accessToken = getActivity().getIntent().getStringExtra(Constant.ACCESS_TOKEN);
    }

    public void setMyToolbar() {
//        myToolbar = (Toolbar) fragmentProfileBinding.getRoot().findViewById(R.id.toolbar_profile);
        myToolbar = (Toolbar) fragmentPasswordChangedBinding
                .toolbarPassword;
        mainToolbarText = (TextView) fragmentPasswordChangedBinding
                .toolbarTitle;
        mainToolbarText.setTextColor(Color.WHITE);
        myToolbar.setTitle("");

        CardView saveBtn = fragmentPasswordChangedBinding.toolbarTextRight;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordChangeFragmentViewModel.setProgress(true);
                TextView password = fragmentPasswordChangedBinding.passwordInput;
                TextView newPassword = fragmentPasswordChangedBinding.newPasswordInput;
                TextView confirmPassword = fragmentPasswordChangedBinding.confirmNewPasswordInput;


//                System.out.println(accessToken);

                if (CheckUtility.isNetworkConnected(getContext())) {
                    AppController appController = new AppController();
                    CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
                    UserService userService = appController.getUserService(accessToken);

                    Disposable disposable = userService.changePassword(
                            language,
                            password.getText().toString(), newPassword.getText().toString(),
                            confirmPassword.getText().toString()
                    )
                            .subscribeOn(appController.subscribeScheduler())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<PasswordChangeResponce>() {
                                           @Override
                                           public void accept(PasswordChangeResponce passwordChangeResponce) throws Exception {
                                               hideKeyboard(getActivity());
                                               Toast toast;
                                               if (passwordChangeResponce.getMessage() == null || passwordChangeResponce.getMessage().equals(""))
                                                   toast = Toast.makeText(getContext(),
                                                           passwordChangeResponce.getError(),
                                                           Toast.LENGTH_LONG);
                                               else {
                                                   toast = Toast.makeText(getContext(),
                                                           passwordChangeResponce.getMessage(),
                                                           Toast.LENGTH_LONG);
                                               }
                                               toast.show();
                                               passwordChangeFragmentViewModel.setProgress(false);
                                           }
                                       },
                                    new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            if (throwable.getMessage().contains("401")) {
                                                showToastUnauthorized();
                                            }
                                            passwordChangeFragmentViewModel.setProgress(false);
                                        }
                                    }
                            );

                    compositeDisposable.add(disposable);

//                System.out.println(name.getText());
                }
                else
                {
                    passwordChangeFragmentViewModel.setProgress(false);
                }
            }
        });
        ((AppCompatActivity)getActivity())
                .setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity())
                .getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//        private TextView dialogTextAuth;
//    private Button buttonYesAuth;
//    private Button buttonNoAuth;

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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTextAuth = dialog.findViewById(R.id.dialog_text);
        buttonYesAuth = dialog.findViewById(R.id.buttonOk);
        buttonNoAuth = dialog.findViewById(R.id.buttonCancel);
        buttonNoAuth.setVisibility(View.GONE);
        buttonYesAuth.setText(R.string.ok);
        dialogTextAuth.setText(R.string.sessionError);
        buttonYesAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYesAuth.setEnabled(false);
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
        Intent intent = new Intent(getContext(), AuthActivity.class);
        ((Activity)getContext()).startActivity(intent);
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

}
