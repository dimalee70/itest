package itest.kz.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itest.kz.R;
import itest.kz.databinding.FragmentLoginBinding;
import itest.kz.viewmodel.AuthViewModel;
import itest.kz.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment
{
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding fragmentLoginBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        loginViewModel = new LoginViewModel(getContext());
        fragmentLoginBinding.setAuth(loginViewModel);

        return fragmentLoginBinding.getRoot();
    }
}
