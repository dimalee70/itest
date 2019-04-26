package itest.kz.view.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itest.kz.R;
import itest.kz.databinding.FragmentOneSplashBinding;
import itest.kz.viewmodel.OneSplashViewModel;

public class OneSplashFragment extends Fragment
{
    private FragmentOneSplashBinding fragmentOneSplashBinding;
    private OneSplashViewModel oneSplashViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentOneSplashBinding = DataBindingUtil
                .inflate
                        (inflater,
                                R.layout.fragment_one_splash,
                                container, false);
        oneSplashViewModel = new OneSplashViewModel(getContext());
        fragmentOneSplashBinding.setOne(oneSplashViewModel);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.one);
//        fragmentOneSplashBinding.image.setImageResource();R.drawable.1);

//                iv2.setImageResource(R.drawable.android_image2);
        return fragmentOneSplashBinding.getRoot();
    }
}
