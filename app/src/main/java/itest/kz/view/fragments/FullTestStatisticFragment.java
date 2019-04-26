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
import itest.kz.databinding.FragmentFullTestStatisticBinding;
import itest.kz.databinding.FragmentSubjectStatisticBinding;
import itest.kz.util.Constant;
import itest.kz.view.adapters.SubjectStatisticAdapter;
import itest.kz.viewmodel.FullTestStatisticViewModel;
import itest.kz.viewmodel.SubjectStatisticViewModel;

public class FullTestStatisticFragment extends Fragment implements Observer
{
    private FragmentFullTestStatisticBinding fragmentFullTestStatisticBinding;
    private FullTestStatisticViewModel fullTestStatisticViewModel;
    private String typeTest;

    public static FullTestStatisticFragment newInstance(String typeTest) {

        Bundle args = new Bundle();
        args.putString(Constant.TYPE, typeTest);
        FullTestStatisticFragment fragment = new FullTestStatisticFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        this.typeTest = getArguments().getString(Constant.TYPE);
        fragmentFullTestStatisticBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_full_test_statistic, container, false);
        fullTestStatisticViewModel = new FullTestStatisticViewModel(getContext());
        fragmentFullTestStatisticBinding.setFull(fullTestStatisticViewModel);
        setUpListOfSbjectsView(fragmentFullTestStatisticBinding.listSubjectStatistic);
        setUpObserver(fullTestStatisticViewModel);
        return fragmentFullTestStatisticBinding.getRoot();
    }

    private void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    private void setUpListOfSbjectsView(RecyclerView listSubjectStatistic)
    {
        SubjectStatisticAdapter subjectStatisticAdapter =
                new SubjectStatisticAdapter(typeTest);
//        subjectStatisticAdapter.setHasStableIds(true);
        listSubjectStatistic.setAdapter(subjectStatisticAdapter);
        listSubjectStatistic.setLayoutManager(new LinearLayoutManager(getContext()));
//        fullTestStatisticViewModel.setProgress(false);

    }


    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof  FullTestStatisticViewModel)
        {
            SubjectStatisticAdapter subjectStatisticAdapter =
                    (SubjectStatisticAdapter) fragmentFullTestStatisticBinding
                            .listSubjectStatistic.getAdapter();
            FullTestStatisticViewModel fullTestStatisticViewModel =
                    (FullTestStatisticViewModel) o;
            subjectStatisticAdapter.setStatisticSubjectList
                    (fullTestStatisticViewModel.getStatisticSubjectList());
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        fullTestStatisticViewModel.reset();
    }

}
