package itest.kz.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.FragmentResultsBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.TestsUtils;
import itest.kz.view.activity.FulltestResultActivity;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.adapters.AnswerAdapter;
import itest.kz.view.adapters.ResultsSubjectAdapter;
import itest.kz.viewmodel.ResultsFragmentViewModel;

import static android.content.Context.MODE_PRIVATE;

public class ResultsFragment extends Fragment
{
    private FragmentResultsBinding fragmentResultsBinding;
    private ResultsFragmentViewModel resultsFragmentViewModel;
    private ProgressBar progressBar;
    private TestFinishResponse testFinishResponse;
    private ResultsSubjectAdapter resultsSubjectAdapter;
    private List<Tests> testsList;
    private String language;
    private String accessToken;
    private Long testIdMain;
    private List<Subject> subjectList;
    private String typeTest;
    private Subject selectedSubject;


    public static ResultsFragment newInstance(TestFinishResponse testFinishResponse,
                                              Long testIdMain, List<Subject> subjectList, Subject selectedSubject, String typeTest)
    {

        Bundle args = new Bundle();
        args.putSerializable(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
        args.putSerializable(Constant.SUBJECT_LIST, (Serializable) subjectList);
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        args.putString(Constant.TYPE, typeTest);
        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        SharedPreferences settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = settings.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = getContext().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
        testFinishResponse = (TestFinishResponse) getArguments().getSerializable(Constant.TEST_FINISH_RESPONSE);
        testIdMain = getArguments().getLong(Constant.TEST_MAIN_ID, 0);
        subjectList = getArguments().getParcelableArrayList(Constant.SUBJECT_LIST);
        selectedSubject = (Subject) getArguments().getSerializable(Constant.SELECTED_SUBJECT);
        this.typeTest = getArguments().getString(Constant.TYPE);
        fragmentResultsBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_results, container, false);
        resultsFragmentViewModel = new ResultsFragmentViewModel(getContext(), testFinishResponse,
                subjectList, selectedSubject, typeTest);
        fragmentResultsBinding.setResults(resultsFragmentViewModel);


        fetchFullTestQuestionsGenerate(testIdMain);
        progressBar = fragmentResultsBinding.statsProgressbar;

        return fragmentResultsBinding.getRoot();
    }

    public void fetchFullTestQuestionsGenerate(Long id)
    {

        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getQuestions(Constant.ACCEPT,
                language,
                "Bearer " + accessToken, id)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>() {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception {

//                                   System.out.println(jsonObject.toString());
                                   if (typeTest.equals(Constant.TYPEFULLTEST))
                                   testsList =
                                           TestsUtils.deserializeFromJson(jsonObject);
                                   else  if (typeTest.equals(Constant.TYPESUBJECTTEST))
                                   {
                                       testsList = new ArrayList<>();

                                       testsList.add(TestsUtils.deserializeFromJsonToTests(jsonObject));
                                   }

                                   setUpListOfSubjectsView(fragmentResultsBinding.subjectList);
//
//                                   setArraListArrayListQuestions(questions);
//
//                                   Tests arrayList = questions.get(currentPosition);

//                                   for (Tests t : questions)
//                                   {
//                                       for (Question q : t.getQuestions())
//                                       {
//                                           System.out.println(q.getText());
//                                       }
//                                   }
////
//                                   setArraListTests(arrayList);
//                                   setFragment(arrayList);


                               }
                           }
//                        new Consumer<JSONObject>() {
//                    @Override
//                    public void accept(JSONObject jsonObject) throws Exception
//                    {
//                        System.out.println("json");
//                        System.out.println(jsonObject.toString());
//                    }
//                }
                );

        compositeDisposable.add(disposable);
    }

    private void setUpListOfSubjectsView(RecyclerView listSubjects)
    {
        if (testsList.size() == 5)
        {
            resultsSubjectAdapter =
                    new ResultsSubjectAdapter(testsList);
            listSubjects.setAdapter(resultsSubjectAdapter);
            listSubjects.setLayoutManager(new LinearLayoutManager(getContext()));
            resultsSubjectAdapter.setOnItemListener(new ResultsSubjectAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(Tests item, int position) throws CloneNotSupportedException
                {
                    if (typeTest.equals(Constant.TYPEFULLTEST))
                    {
                        Intent intent = new Intent(getActivity(), FulltestResultActivity.class);
                        intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
                        intent.putExtra(Constant.CURRENT_POSITION_SUBJECT, position);
                        intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                        intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);
                        intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                        getContext().startActivity(intent);
                    }
//                else if (typeTest.equals(Constant.TYPESUBJECTTEST))
//                {
//                    Intent intent = new Intent(getActivity(), ResultActivity.class);
//                    intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
////                    intent.putExtra(Constant.CURRENT_POSITION_SUBJECT, position);
//                    intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
////                    intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);
//                    intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
//                    getContext().startActivity(intent);
//                }

                }
            });
        }

    }
}
