package itest.kz.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentSubjectStatisticBinding;
import itest.kz.util.Constant;
import itest.kz.view.adapters.SubjectStatisticAdapter;
import itest.kz.viewmodel.SubjectStatisticViewModel;

public class SubjectStatisticFragment  extends Fragment implements Observer
{
    private FragmentSubjectStatisticBinding fragmentSubjectStatisticBinding;
    private SubjectStatisticViewModel subjectStatisticViewModel;
    private String typeTest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        typeTest = Constant.TYPESUBJECTTEST;
        fragmentSubjectStatisticBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_subject_statistic, container, false);
        subjectStatisticViewModel = new SubjectStatisticViewModel(getContext());
        fragmentSubjectStatisticBinding.setSubject(subjectStatisticViewModel);
        setUpListOfSbjectsView(fragmentSubjectStatisticBinding.listSubjectStatistic);
        setUpObserver(subjectStatisticViewModel);
        return fragmentSubjectStatisticBinding.getRoot();
    }

    private void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    private void setUpListOfSbjectsView(RecyclerView listSubjectStatistic)
    {
        SubjectStatisticAdapter subjectStatisticAdapter =
                new SubjectStatisticAdapter(typeTest);
        listSubjectStatistic.setAdapter(subjectStatisticAdapter);
        listSubjectStatistic.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof  SubjectStatisticViewModel)
        {
            SubjectStatisticAdapter subjectStatisticAdapter =
                    (SubjectStatisticAdapter) fragmentSubjectStatisticBinding
                    .listSubjectStatistic.getAdapter();
            SubjectStatisticViewModel subjectStatisticViewModel =
                    (SubjectStatisticViewModel) o;
            subjectStatisticAdapter.setStatisticSubjectList
                    (subjectStatisticViewModel.getStatisticSubjectList());
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        subjectStatisticViewModel.reset();
    }

}
