package itest.kz.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentCertificationBinding;


import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.viewmodel.CertificationFragmentViewModel;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class CertificationFragment extends Fragment implements Observer
{
    public CertificationFragmentViewModel certificationFragmentViewModel;
    public FragmentCertificationBinding fragmentCertificationBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentCertificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_certification, container, false);
        certificationFragmentViewModel = new CertificationFragmentViewModel(getContext());
        fragmentCertificationBinding.setCertification(certificationFragmentViewModel);

        setUpListOfSbjectsView(fragmentCertificationBinding.listSubject);
        setUpObserver(certificationFragmentViewModel);
        return fragmentCertificationBinding.getRoot();
    }


    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof  CertificationFragmentViewModel) {
            SubjectAdapter subjectAdapter = (SubjectAdapter) fragmentCertificationBinding.listSubject
                    .getAdapter();
            CertificationFragmentViewModel certificationFragmentViewModel
                    = (CertificationFragmentViewModel) o;
            subjectAdapter.setSubjectList(certificationFragmentViewModel
                    .getSubjectList());
        }
    }



    // set up the list of user with recycler view
    private void setUpListOfSbjectsView(RecyclerView listSubject) {
        SubjectAdapter subjectAdapter = new SubjectAdapter();
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), HORIZONTAL);
//        itemDecor
        listSubject.addItemDecoration(itemDecor);
        listSubject.setAdapter(subjectAdapter);
        listSubject.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        certificationFragmentViewModel.reset();
    }
}
