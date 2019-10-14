package itest.kz.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

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
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.FulltestResultActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.ResultsActivity;
import itest.kz.view.activity.StatisticActivity;
import itest.kz.view.activity.SubjectActivity;
import itest.kz.view.adapters.AnswerAdapter;
import itest.kz.view.adapters.ResultsSubjectAdapter;
import itest.kz.view.adapters.ViewPagerAdapter;
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
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;
    private String statisticTag;
    private ImageButton closeFragment;


    public static ResultsFragment newInstance(TestFinishResponse testFinishResponse,
                                              Long testIdMain, List<Subject> subjectList, Subject selectedSubject,
                                              String typeTest, String statisticTag)
    {

        Bundle args = new Bundle();
        args.putSerializable(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
        args.putSerializable(Constant.SUBJECT_LIST, (Serializable) subjectList);
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        args.putString(Constant.TYPE, typeTest);
        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
        args.putString(Constant.STATISTIC_TAG, statisticTag);
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        setHasOptionsMenu(true);
        SharedPreferences settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = settings.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = getContext().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
        testFinishResponse = (TestFinishResponse) getArguments().getSerializable(Constant.TEST_FINISH_RESPONSE);
        testIdMain = getArguments().getLong(Constant.TEST_MAIN_ID, 0);
        subjectList = getArguments().getParcelableArrayList(Constant.SUBJECT_LIST);
        selectedSubject = (Subject) getArguments().getSerializable(Constant.SELECTED_SUBJECT);
        statisticTag = getArguments().getString(Constant.STATISTIC_TAG, "");
        typeTest = getArguments().getString(Constant.TYPE);
        fragmentResultsBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_results, container, false);
        resultsFragmentViewModel = new ResultsFragmentViewModel(getContext(), testFinishResponse,
                subjectList, selectedSubject, typeTest, statisticTag, testIdMain);
        fragmentResultsBinding.setResults(resultsFragmentViewModel);
        resultsFragmentViewModel.setProgress(true);
//        resultsFragmentViewModel.setProgress(true);
        fetchFullTestQuestionsGenerate(testIdMain);

        closeFragment = fragmentResultsBinding.buttonCloseFragment;


        closeFragment.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                            openNewActivity();
                    }
                });
//        resultsFragmentViewModel.setProgress(false);
        progressBar = fragmentResultsBinding.statsProgressbar;

        return fragmentResultsBinding.getRoot();
    }

    private void openNewActivity()
    {
//        System.out.println("Close method");

        if (statisticTag.equals(Constant.STATISTIC_TAG) )
        {
            Intent intent = new Intent(getContext(), MainHomeActivity.class);
            intent.putExtra(Constant.STATISTIC_TAG, Constant.STATISTIC_TAG);
            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getContext(), MainHomeActivity.class);
            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
            startActivity(intent);
        }

//        finish();
    }



    public void fetchFullTestQuestionsGenerate(Long id)
    {


//        System.out.println("start");
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.getQuestions(
                language, id)
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

                                   else  if (typeTest.equals(Constant.TYPELECTURETEST))
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
                           },
                        new Consumer<Throwable>()
                        {
                            @Override
                            public void accept(Throwable throwable) throws Exception
                            {
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
                                    resultsFragmentViewModel.setProgress(false);
                                }
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

//    private TextView dialogTextAuth;
//    private Button buttonYesAuth;
//    private Button buttonNoAuth;
//        private TextView dialogTextAuth;
//    private Button buttonYesAuth;
//    private Button buttonNoAuth;

    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(getContext());
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTextAuth = dialog.findViewById(R.id.dialog_text);
        buttonYesAuth = dialog.findViewById(R.id.buttonOk);
        buttonNoAuth = dialog.findViewById(R.id.buttonCancel);
        buttonNoAuth.setVisibility(View.GONE);
        buttonYesAuth.setText(R.string.ok);
        dialogTextAuth.setText(R.string.sessionError);
        buttonYesAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYesAuth.setEnabled(false);
                dialog.dismiss();
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954

            }
        });

//        buttonNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(getContext(), AuthActivity.class);
        ((Activity)getContext()).startActivity(intent);
//        if (language.equals(Constant.KZ))
//
//            Toast.makeText(this,
//                    R.string.sessionErrorKz,
//                    Toast.LENGTH_SHORT).show();
//        else
//        {
//            Toast.makeText(this,
//                    R.string.sessionErrorRu,
//                    Toast.LENGTH_SHORT).show();
//        }
    }

    public ResultsSubjectAdapter getResultsSubjectAdapter()
    {
        return resultsSubjectAdapter;
    }

    private void setUpListOfSubjectsView(RecyclerView listSubjects)
    {
        if (testsList != null && testsList.size() == 5)
        {
            resultsSubjectAdapter =
                    new ResultsSubjectAdapter(testsList);
            listSubjects.setAdapter(resultsSubjectAdapter);
            listSubjects.setLayoutManager(new LinearLayoutManager(getContext()));

//
//            resultsSubjectAdapter.setOnItemListener(new ResultsSubjectAdapter.OnItemClickListener()
//            {
//                @Override
//                public void onItemClick(Tests item, int position) throws CloneNotSupportedException
//                {
//                    if (typeTest.equals(Constant.TYPEFULLTEST))
//                    {
//
//
//
//                        ResultsActivity r = ((ResultsActivity)getActivity());
//
//                        ViewPagerAdapter v = (ViewPagerAdapter)r.getmViewPager().getAdapter();
//                        CheckResultFragment c = (CheckResultFragment) v.getItem(1);
//                        c.setCurrentPosition(position);
//
//                        ((ResultsActivity)getActivity()).getmViewPager().setCurrentItem(1);
////
//                    }
//
//                }
//            });
        }

        resultsFragmentViewModel.setProgress(false);

    }
}
