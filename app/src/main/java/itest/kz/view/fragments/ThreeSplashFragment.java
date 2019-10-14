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
import itest.kz.databinding.FragmentThreeSplashBinding;
import itest.kz.viewmodel.ThreeSplashViewModel;

public class ThreeSplashFragment extends Fragment
{
    private FragmentThreeSplashBinding fragmentThreeSplashBinding;
    private ThreeSplashViewModel threeSplashViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentThreeSplashBinding = DataBindingUtil.inflate
                (inflater, R.layout.fragment_three_splash, container, false);
        threeSplashViewModel = new ThreeSplashViewModel(getContext());
        fragmentThreeSplashBinding.setThree(threeSplashViewModel);

        return fragmentThreeSplashBinding.getRoot();
    }
}
