package itest.kz.view.fragments;

import android.app.Activity;
import android.app.Dialog;
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
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import itest.kz.databinding.FragmentCheckResultsBinding;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.CustomViewPager;
import itest.kz.util.TestsUtils;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.FulltestResultActivity;
import itest.kz.view.adapters.FullTestResultAdapter;
import itest.kz.view.adapters.ResultAdapter;
import itest.kz.viewmodel.CheckResultViewMoel;

import static android.content.Context.MODE_PRIVATE;

public class CheckResultFragment extends Fragment
{
    public CustomViewPager mPager;
    private String accessToken;
    private Integer currentPosition;
    private String language;
    private ArrayList<Tests> arrayListArrayListQuestions;
    private FragmentCheckResultsBinding fragmentCheckResultsBinding;
    private CheckResultViewMoel checkResultViewMoel;
    private FullTestResultAdapter fullTestResultAdapter;
    private ResultAdapter resultAdapter;
    private ArrayList<Subject> subjectsList;
    private Long testIdMain;
    private String resultTag;
    private String typeTest;
    private Subject selectedSubject;
    private TextView subjectTitleText;
    private ImageButton buttonNext;
    private ImageButton buttonPrevious;
    private CardView subjectCardview;
    private String statisticTag;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;


    public static CheckResultFragment newInstance(Long testIdMain, List<Subject> subjectList, Subject selectedSubject,
                                                  String tag, String typeTest, String statisticTag)
    {

        Bundle args = new Bundle();
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        args.putSerializable(Constant.SUBJECT_LIST, (Serializable) subjectList);
        args.putString(Constant.RESULT_TAG, tag);
        args.putString(Constant.TYPE, typeTest);
        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
        args.putString(Constant.STATISTIC_TAG, statisticTag);
//        System.out.println("tag");
//        System.out.println(tag);
        CheckResultFragment fragment = new CheckResultFragment();
        fragment.setArguments(args);
        return fragment;
    }



    public static CheckResultFragment newInstance(Long testIdMain, List<Subject> subjectList)
    {

        Bundle args = new Bundle();
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        args.putSerializable(Constant.SUBJECT_LIST, (Serializable) subjectList);
        CheckResultFragment fragment = new CheckResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        Bundle extras = getActivity().getIntent().getExtras();
        SharedPreferences settings = getActivity().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = settings.getString(Constant.ACCESS_TOKEN, "");

        SharedPreferences lang = getActivity().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");
//        SharedPreferences time = getSharedPreferences(Constant.CURRENT_TIME, MODE_PRIVATE);
//        settings.edit().clear().commit();
//        maxTimeInMilliseconds = time.getLong(Constant.CURRENT_TIME, 10800000);

        statisticTag = getArguments().getString(Constant.STATISTIC_TAG, "");
        this.typeTest = getArguments().getString(Constant.TYPE);
        this.testIdMain = getActivity().getIntent().getExtras().getLong(Constant.TEST_MAIN_ID);
        this.subjectsList = getActivity().getIntent().getExtras().getParcelableArrayList(Constant.SUBJECT_LIST);
//        this.currentPosition = getIntent().getExtras().getInt(Constant.CURRENT_POSITION_TEST, 0);
        this.currentPosition = getActivity().getIntent().getExtras().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);
//        this.resultTag = getActivity().getIntent().getExtras().getString(Constant.RESULT_TAG, null);
        this.resultTag = getArguments().getString(Constant.RESULT_TAG, null);
        selectedSubject = (Subject) getArguments().getSerializable(Constant.SELECTED_SUBJECT);
//        System.out.println("ResultTag");
//        System.out.println(resultTag);
        fragmentCheckResultsBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_check_results, container, false);
        checkResultViewMoel = new CheckResultViewMoel(getContext());
        fragmentCheckResultsBinding.setCheck(checkResultViewMoel);
        if (typeTest.equals(Constant.TYPELECTURETEST) ||
                typeTest.equals(Constant.TYPESUBJECTTEST))
        {
            checkResultViewMoel.setCardView(false);
        }else
        {
            checkResultViewMoel.setCardView(true);
        }
        subjectCardview = fragmentCheckResultsBinding.subjectCardview;
        subjectTitleText = fragmentCheckResultsBinding
                .subjectTitleText;
        buttonNext = fragmentCheckResultsBinding
                .buttonNextResult;
        buttonPrevious = fragmentCheckResultsBinding
                .buttonPrefiousResult;
        setTestsResults();

        return fragmentCheckResultsBinding.getRoot();
    }

    private void fetchTestsByTestId(Long testIdMain)
    {
        checkResultViewMoel.setProgress(true);
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.getQuestions(
                language, testIdMain)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {
                                   ArrayList<Tests> questions = new ArrayList<>();
//                                   System.out.println(jsonObject.toString());
                                   if (typeTest.equals(Constant.TYPEFULLTEST))
                                        questions =
                                           TestsUtils.deserializeFromJson(jsonObject);
                                   else if (typeTest.equals(Constant.TYPESUBJECTTEST))
                                   {
//                                       questions = new ArrayList<>();
                                       questions.add(TestsUtils.deserializeFromJsonToTests(jsonObject));
                                   }
//
                                   else if (typeTest.equals(Constant.TYPELECTURETEST))
                                   {
//                                       questions = new ArrayList<>();
                                       questions.add(TestsUtils.deserializeFromJsonToTests(jsonObject));
                                   }
                                   setArraListArrayListQuestions(questions);

                                   mPager = fragmentCheckResultsBinding.pager;
                                   mPager.setOffscreenPageLimit(5);
                                   if (typeTest.equals(Constant.TYPEFULLTEST)) {

                                       fullTestResultAdapter = new FullTestResultAdapter(getActivity().getSupportFragmentManager()
                                               , questions, testIdMain,
                                               subjectsList,
                                               currentPosition, resultTag, typeTest,selectedSubject);
                                       mPager.setAdapter(fullTestResultAdapter);
                                       subjectCardview.setVisibility(View.VISIBLE);
                                   }

                                   else if (typeTest.equals(Constant.TYPESUBJECTTEST))
                                   {
                                       subjectCardview.setVisibility(View.GONE);
                                       subjectsList = new ArrayList<>();
                                       subjectsList.add(selectedSubject);
                                       fullTestResultAdapter = new FullTestResultAdapter(getActivity().getSupportFragmentManager()
                                               , questions, testIdMain,
                                               subjectsList,
                                               currentPosition, resultTag, typeTest,selectedSubject);
                                       mPager.setAdapter(fullTestResultAdapter);
//                                       resultAdapter = new ResultAdapter(questions.get(0), getContext(),
//                                               selectedSubject, null,null,
//                                               null,null,0);
                                   }

                                   else if (typeTest.equals(Constant.TYPELECTURETEST))
                                   {
                                       subjectCardview.setVisibility(View.GONE);
                                       subjectsList = new ArrayList<>();
                                       subjectsList.add(selectedSubject);
                                       fullTestResultAdapter = new FullTestResultAdapter(getActivity().getSupportFragmentManager()
                                               , questions, testIdMain,
                                               subjectsList,
                                               currentPosition, resultTag, typeTest,selectedSubject);
                                       mPager.setAdapter(fullTestResultAdapter);
//                                       resultAdapter = new ResultAdapter(questions.get(0), getContext(),
//                                               selectedSubject, null,null,
//                                               null,null,0);
                                   }

                                   PageListener listener = new PageListener();
                                   mPager.addOnPageChangeListener(listener);

                                   setmPager(mPager);

                                   mPager.setCurrentItem(currentPosition);
                                   subjectTitleText.setText(subjectsList.get(currentPosition).getTitle());

                                   buttonNext.setOnClickListener(new View.OnClickListener()
                                   {
                                       @Override
                                       public void onClick(View v)
                                       {
                                           if (currentPosition != mPager.getChildCount() - 1)
                                               mPager.setCurrentItem(++currentPosition, true);
                                       }
                                   });

                                   buttonPrevious.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v)
                                       {
                                           if (currentPosition != 0)
                                               mPager.setCurrentItem(--currentPosition, true);
                                       }
                                   });

                                   checkResultViewMoel.setProgress(false);
                               }
                           },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
                                    checkResultViewMoel.setProgress(false);
                                }
                            }
                        }

                );

        compositeDisposable.add(disposable);
    }


//    private TextView dialogTextAuth;
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

    public void setmPager(CustomViewPager mPager)
    {
        this.mPager = mPager;
    }

    public CustomViewPager getmPager()
    {
        return mPager;
    }

    public Integer getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition)
    {
        this.currentPosition = currentPosition;
        if (getmPager() != null)
            getmPager().setCurrentItem(currentPosition);
        System.out.println(currentPosition);
//        mPager.setCurrentItem(currentPosition);
    }

    public  void setTestsResults()
    {
        fetchTestsByTestId(testIdMain);
    }


    private void setArraListArrayListQuestions(ArrayList<Tests> questions)
    {
        this.arrayListArrayListQuestions = questions;
    }


    public class PageListener extends ViewPager.SimpleOnPageChangeListener {

        public int getCurrentPage() {
            return currentPosition;
        }
        private int lastPage = 0;

        public void setCurrentPage(int currentPageArg) {
            currentPosition = currentPageArg;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//            OneFromFullResultFragment f =(OneFromFullResultFragment) fullTestResultAdapter.getItem(mPager.getCurrentItem());
//            f.setCurrentPosition(position);

        }

        public void onPageSelected(int position)
        {
            if(lastPage > position)
            {
                currentPosition = position;
                lastPage = position;
                subjectTitleText
                        .setText(subjectsList.get(currentPosition).getTitle());
            }
            else
            {
                currentPosition = position;
                lastPage = position;
                subjectTitleText
                        .setText(subjectsList.get(currentPosition).getTitle());
            }


        }
    }

}
