package itest.kz.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentOneFromFullResultBinding;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.adapters.ResultAdapter;
import itest.kz.viewmodel.OneFromFullResultViewModel;

public class OneFromFullResultFragment extends Fragment implements Observer
{
    private Tests tests;
    private FragmentOneFromFullResultBinding fragmentOneFromFullResultBinding;
    private OneFromFullResultViewModel oneFromFullResultViewModel;
    private Subject selectedSubject;
    @Nullable private  Integer currentPosition = null;
    private Long testIdMain;
    private ArrayList<Subject> subjectList;
    private  Integer selectedSubjectPosition;
    private Toolbar toolbar;
    private ImageButton buttonCloseResult;
    private ResultAdapter resultAdapter;
    private String resultTag;
    private  int i;
    private String typeTest;
    private TextView subjectTitleText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentOneFromFullResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_one_from_full_result,
                container, false);

        selectedSubjectPosition = getArguments().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);
        testIdMain = getArguments().getLong(Constant.TEST_MAIN_ID);
        typeTest = getArguments().getString(Constant.TYPE);

        if (currentPosition == null)
            currentPosition = getArguments().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);


        tests = (Tests) getArguments().getSerializable("tests");
        resultTag = getArguments().getString(Constant.RESULT_TAG, null);

        subjectList = getArguments().getParcelableArrayList(Constant.SUBJECT_LIST);

//        System.out.println(tests);
        oneFromFullResultViewModel = new OneFromFullResultViewModel(getContext(), tests);
        fragmentOneFromFullResultBinding.setOne(oneFromFullResultViewModel);

        selectedSubject = (Subject) getArguments().getSerializable(Constant.SELECTED_SUBJECT);
        setR(fragmentOneFromFullResultBinding.listAnswers);

        if (resultTag == null)
        {

            toolbar = getActivity().findViewById(R.id.my_toolbar);
            buttonCloseResult = getActivity().findViewById(R.id.buttonCloseResult);
            buttonCloseResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    getActivity().finish();
                }
            });
        }

        return fragmentOneFromFullResultBinding.getRoot();
    }

    public static OneFromFullResultFragment newInstance(Tests tests, Long testIdMain, @Nullable Integer currentPosition,
                                                        ArrayList<Subject> subjectList,
                                                        String resultTag,
                                                        String typeTest,
                                                        Subject selectedSubject
                                                        )
    {

        Bundle args = new Bundle();

        OneFromFullResultFragment fragment = new OneFromFullResultFragment();
        args.putSerializable("tests", (Tests) tests);
        args.putInt(Constant.CURRENT_POSITION_SUBJECT, currentPosition);
        args.putString(Constant.RESULT_TAG, resultTag);
        args.putSerializable(Constant.SUBJECT_LIST, subjectList);
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        args.putString(Constant.TYPE, typeTest);
        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
//        args.putInt(Constant.CURRENT_POSITION_SUBJECT, selectedSubjectPosition);
//        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
        fragment.setArguments(args);
        return fragment;
    }



    private void setR(RecyclerView listAnswers)
    {

        resultAdapter = new ResultAdapter(tests, getContext(),
                selectedSubject, testIdMain, currentPosition, subjectList, selectedSubjectPosition, i, resultTag, typeTest);
        listAnswers.setAdapter(resultAdapter);

        listAnswers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void update(Observable o, Object arg)
    {
    }

    @Override
    public String toString() {
        return "OneFromFullResultFragment{}";
    }
}
