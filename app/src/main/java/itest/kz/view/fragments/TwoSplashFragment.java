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
import itest.kz.databinding.FragmentTwoSplashBinding;
import itest.kz.viewmodel.TwoSplashViewModel;

public class TwoSplashFragment extends Fragment
{
    private FragmentTwoSplashBinding fragmentTwoSplashBinding;
    private TwoSplashViewModel twoSplashViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentTwoSplashBinding = DataBindingUtil.inflate
                (inflater, R.layout.fragment_two_splash,
                        container, false);
        twoSplashViewModel = new TwoSplashViewModel(getContext());
        fragmentTwoSplashBinding.setTwo(twoSplashViewModel);
        return fragmentTwoSplashBinding.getRoot();
    }
}
