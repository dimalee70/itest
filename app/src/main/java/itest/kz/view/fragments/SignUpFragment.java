package itest.kz.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itest.kz.R;
import itest.kz.databinding.FragmentSignUpBinding;
import itest.kz.viewmodel.SignUpViewModel;

public class SignUpFragment extends Fragment
{
    //    private EditText etLogin, etPassword;
//    private Button btnLogin;
//
//    private Observable<Boolean> observable;
    private SignUpViewModel signUpViewModel;
    private FragmentSignUpBinding fragmentSignUpBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentSignUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        signUpViewModel = new SignUpViewModel(getContext());
        fragmentSignUpBinding.setSighUp(signUpViewModel);
//        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        return fragmentSignUpBinding.getRoot();
    }

}
