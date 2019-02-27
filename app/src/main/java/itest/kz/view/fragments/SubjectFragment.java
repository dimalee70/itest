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
import itest.kz.databinding.FragmentEntBinding;
import itest.kz.viewmodel.SubjectFragmentViewModel;

public class SubjectFragment extends Fragment
{
    public SubjectFragmentViewModel subjectFragmentViewModel;
    public FragmentEntBinding fragmentEntBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentEntBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ent, container, false);
        subjectFragmentViewModel = new SubjectFragmentViewModel(getContext());
        fragmentEntBinding.setEnt(subjectFragmentViewModel);
        return fragmentEntBinding.getRoot();
    }

    //    private
}
