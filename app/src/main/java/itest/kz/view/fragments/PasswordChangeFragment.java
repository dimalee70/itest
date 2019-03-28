package itest.kz.view.fragments;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import itest.kz.util.Constant;
import itest.kz.view.activity.HomeActivity;
import itest.kz.viewmodel.PasswordChangeFragmentViewModel;
import itest.kz.viewmodel.ProfileInfoViewModel;

import static android.content.Context.MODE_PRIVATE;

public class PasswordChangeFragment extends Fragment
{
    private FragmentPasswordChangedBinding fragmentPasswordChangedBinding;
    private PasswordChangeFragmentViewModel passwordChangeFragmentViewModel;
    private Toolbar myToolbar;
    private TextView mainToolbarText;
    private String accessToken;
    private String language;

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
        ((HomeActivity)getActivity()).setNavigationVisibility(false);
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

        TextView saveBtn = (TextView) fragmentPasswordChangedBinding.toolbarTextRight;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView password = fragmentPasswordChangedBinding.passwordInput;
                TextView newPassword = fragmentPasswordChangedBinding.newPasswordInput;
                TextView confirmPassword = fragmentPasswordChangedBinding.confirmNewPasswordInput;


                System.out.println(accessToken);

                AppController appController = new AppController();
                CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
                UserService userService = appController.getUserService();

                Disposable disposable = userService.changePassword(Constant.ACCEPT,
                        language, "Bearer " + accessToken,
//                        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjI1NTU0MTEzYjcwM2EzNzgzMGFlYjFjYjZkMWI3YzI3NzQwY2MyMTNmZTk2YmYyMTc5ZTY4ZGQ1MTdhNWY3NzYxMDIyYzI2ZmM3NzQ3MzExIn0.eyJhdWQiOiIxIiwianRpIjoiMjU1NTQxMTNiNzAzYTM3ODMwYWViMWNiNmQxYjdjMjc3NDBjYzIxM2ZlOTZiZjIxNzllNjhkZDUxN2E1Zjc3NjEwMjJjMjZmYzc3NDczMTEiLCJpYXQiOjE1NTM1MDI4MTEsIm5iZiI6MTU1MzUwMjgxMSwiZXhwIjoxNTg1MTI1MjExLCJzdWIiOiIxMjEyNTIwIiwic2NvcGVzIjpbXX0.o_AqAxMWQf2h7BHDRH1QFzUvJ5jrVYA8PuLtlGXxIjHSygf1pKmuiifzxHwYshPHTqdZEUmRvgCFokAM349RoFDgkekI2RPA_mGsQ_UaCCuyMCN5xBIRObE_YBl8CDMX6rGhJwHQsmuVQXwnC9BnwFl_7OdjFo7AZM_05ZdjEweLX-gcFYAlOpdi0qfsueN2LUNTEseHPwgTFFxPCNpL8JPh98hSGSmo0OsKaJUHRy1ggYXarAsYoO5rS-vK_zVo_MLDm0ADyV-yVuVfjisNn0EVaXd3pC1q3GC76YAuJlmv20Cme0XIpgBF33lWICswnehpPRkqqo_OUTmwvUgJwT2d2cQlTFO59IHeFoHi2JwrH4kE07E-HHLXhiOnnL0McsqX-l_ofF5Nal8KK5xKmFg9ha2W8-tSmsyYMTtNYgXQw2OX2OgmeC1fVNIHHmdw9-hjwyJrm_ojjc9owbb53FQNaE6YTBSSHv5cDJlebCr0Zz2u1cKScujLH9Zq3V_eDkvLq_G-wQxhuRbSjo_Sc9T2no5KaHAP96Kiv9GOhkxOpdvem3LyolKcKeL3QIMvAU9cj3kidnlLY_WyMEg8um4Msam8k9fJ6JryIbKkTQsTc0Wh3BExgf3pNDayDvkTV-3NHRCv9XDHIIsSMQg_JgSiR8A3SQJuxmXWtnkhFJA",
//                        "1234","123", "10.10.1010",
//                            "test123@gmail.com", "test123@gmail.com"
                        password.getText().toString(), newPassword.getText().toString(),
                        confirmPassword.getText().toString()
                )
                        .subscribeOn(appController.subscribeScheduler())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<PasswordChangeResponce>() {
                                       @Override
                                       public void accept(PasswordChangeResponce passwordChangeResponce) throws Exception
                                       {
                                           Toast toast;
                                           toast = Toast.makeText(getContext(),
                                                   passwordChangeResponce.getMessage(),
                                                   Toast.LENGTH_LONG);
                                           toast.show();
                                       }
                                   }
//                                new Consumer<RegisterResponse>() {
//                            @Override
//                            public void accept(RegisterResponse registerResponse) throws Exception
//                            {
//                                Toast toast;
//                                toast = Toast.makeText(context.getApplicationContext(),
//                                        registerResponse.getMessage(),
//                                        Toast.LENGTH_LONG);
//                                toast.show();
//                            }
//                        }
                        );

                compositeDisposable.add(disposable);

//                System.out.println(name.getText());
            }
        });
        ((AppCompatActivity)getActivity())
                .setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity())
                .getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
