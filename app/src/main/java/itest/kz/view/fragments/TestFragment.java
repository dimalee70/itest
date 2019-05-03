package itest.kz.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import at.blogc.android.views.ExpandableTextView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.FragmentTestBinding;
import itest.kz.model.Answer;
import itest.kz.model.NodeResponse;
import itest.kz.model.Question;
import itest.kz.model.SaveAnswerResponse;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.FulltestResultActivity;
import itest.kz.view.activity.MainActivity;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.ResultsActivity;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.adapters.AnswerAdapter;
import itest.kz.view.adapters.TestAdapter;
import itest.kz.viewmodel.TestFragmentViewModel;

import static android.content.Context.MODE_PRIVATE;

public class TestFragment extends Fragment
{
    private boolean isStarted = false;
    private int mLastSelectedIndex = 0;
    private FragmentTestBinding fragmentTestBinding;
    private TestFragmentViewModel testFragmentViewModel;
    public  Integer currentPosition;
    private ExpandableTextView expandableTextView;
    private  List<Answer> answers;
    private AnswerAdapter answerAdapter;
//    private List<Test> testList;
    private Subject selectedSubject;
    private ArrayList<Tests> arrayListArrayListQuestions;
    private String typeTest;
//    private Test test;

    private TextView numberPager;
    private Tests testList;
    private Tests tests;
    private Long testIdMain;
    private Question test;
    private String language;
    private String accessToken;
    public boolean isStartedRecycle = false;
    private List<Subject> subjectList;
    private  int selectedSubjectPosition = 0;
    private ImageButton titleButton;
    private Toolbar myToolbar;
    private ImageButton titleButtonClose;
    private Toolbar navigationToolbar;
    private String resultTag;
    private RecyclerView answerListRecycle;
    private Button buttonYes;
    private Button buttonNo;
    private TextView dialogText;

    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }





//    public Test getTest()
//    {
//        return test;
//    }

//    public void setTest(Test test)
//    {
//        this.test = test;
//    }


    public void setTest(Question test)
{
    this.test = test;
}

    public Question getTest()
    {
        return test;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        SharedPreferences settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = getContext().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        subjectList = getActivity().getIntent().getParcelableArrayListExtra(Constant.SUBJECT_LIST);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");

//        getActivity().setSupportActionBar(myToolbar);


        this.test = (Question) getArguments().getSerializable("test");
        this.selectedSubject = (Subject) getArguments().getSerializable(Constant.SELECTED_SUBJECT);
        this.typeTest = getArguments().getString(Constant.TYPE);
        this.resultTag = getArguments().getString(Constant.RESULT_TAG);
//        if (typeTest.equals(Constant.TYPEFULLTEST))
//        {
            this.testIdMain = getArguments().getLong(Constant.TEST_MAIN_ID);
//        }
//            this.arrayListArrayListQuestions = (ArrayList<Tests>) getArguments().getSerializable(Constant.ARRAYLISTTEST);

//        System.out.println("tests");
//        System.out.println(arrayListArrayListQuestions);

//        tests = savedInstanceState.getParcelableArrayList("tests");


        this.tests = (Tests) getArguments().getSerializable("tests");
        this.currentPosition = getArguments().getInt(Constant.CURRENT_POSITION_SUBJECT, 0);

                this.testList = tests;
        fragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test,
                container, false);
        this.answers = test.getAnswers();

        if (resultTag == null)
            testFragmentViewModel = new TestFragmentViewModel(getContext(), test);
        else
        {
            testFragmentViewModel = new TestFragmentViewModel(getContext(), test, getActivity());
        }
//        for (tests.getQuestions() )
//        TextView textView = fragmentTestBinding.showAnswerText;
//        textView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                BottomAnswerDialogFragment bottomSheetFragment = BottomAnswerDialogFragment.newInstance();
//                bottomSheetFragment.show(getActivity().getSupportFragmentManager(), "test");
//            }
//        });

        fragmentTestBinding.setTest(testFragmentViewModel);

//        myToolbar = fragmentTestBinding.myToolbar;
//        titleButton = fragmentTestBinding.imageButtonTitle;
//        titleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("Hello From Fragment");
//            }
//        });
//        myToolbar =
//          ((AppCompatActivity)getActivity()).getSupportActionBar();
//                ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        myToolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
        titleButton = (ImageButton) getActivity().findViewById(R.id.imageButtonTitle);
        titleButtonClose = (ImageButton) getActivity().findViewById(R.id.buttonFinishTest);

//        navigationToolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
//        numberPager = (TextView) getActivity().findViewById(R.id.text_number_pager);

//        if (currentPosition == )
//        int temp = 0;
//
//        for (int i = 0; i < tests.getQuestions().size(); i++)
//        {
//            if (tests.getQuestions().get(i) == test) {
//                temp = --i;
//                break;
//            }
//        }
//        numberPager.setText(currentPosition + "/" + tests.getQuestions().size());



        titleButtonClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                try {


                    if (resultTag == null) {
                        Dialog dialog = new Dialog(getContext());

                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogText = dialog.findViewById(R.id.dialog_text);
                        buttonYes = dialog.findViewById(R.id.buttonOk);
                        buttonNo = dialog.findViewById(R.id.buttonCancel);
                        if (language.equals(Constant.KZ)) {
                            buttonNo.setText(R.string.noKz);
                            buttonYes.setText(R.string.yesKz);
                            dialogText.setText(R.string.finishTestDialogKz);

                        } else {
                            buttonNo.setText(R.string.noRu);
                            buttonYes.setText(R.string.yesRu);
                            dialogText.setText(R.string.finishTestDialogRu);
                        }
                        buttonYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finishTest(testIdMain);


                                //System.out.println(testIdMain);//103080954

                            }
                        });

                        buttonNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        getActivity().finish();
                    }
                }catch (Exception e)
                {

                }
            }
        });
        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = null;
                switch (typeTest)
                {
                    case Constant.TYPESUBJECTTEST:
                        if (getActivity() != null)
                        {
                            intent = new Intent(getActivity(), ResultActivity.class);
                            intent.putExtra("tests", (Tests) testList);
                            intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                            intent.putExtra(Constant.RESULT_TAG, resultTag);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
////                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constant.TYPE, typeTest);
//                        startActivity(intent);
                        }

                        break;
                    case Constant.TYPEFULLTEST:
                        if (getActivity() != null)
                        {
                            intent = new Intent(getActivity(), FulltestResultActivity.class);
                            intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
                            intent.putExtra(Constant.CURRENT_POSITION_SUBJECT, currentPosition);
                            intent.putExtra(Constant.RESULT_TAG, resultTag);
                            intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);
                            intent.putExtra(Constant.TYPE, typeTest);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        args.putSerializable(Constant.SUBJECT_LIST, (ArrayList<Subject>) selectedSubects);
                        }

                        break;

                    case Constant.TYPELECTURETEST:
                        if (getActivity() != null)
                        {
                            intent = new Intent(getActivity(), ResultActivity.class);
                            intent.putExtra("tests", (Tests) testList);
                            intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                            intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
                            intent.putExtra(Constant.RESULT_TAG, resultTag);
                            intent.putExtra(Constant.TYPE, typeTest);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivity(intent);
                        }


                }
                if (intent != null)
                {
                    startActivity(intent);
                }

            }
        });

        expandableTextView = fragmentTestBinding.expandableTextView;

        // set animation duration via code, but preferable in your layout files by using the animation_duration attribute
        expandableTextView.setAnimationDuration(750L);

        // set interpolators for both expanding and collapsing animations
        expandableTextView.setInterpolator(new OvershootInterpolator());

        fragmentTestBinding.expandedIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (expandableTextView.isExpanded())
                {
                    expandableTextView.collapse();
                    fragmentTestBinding.expandedIcon.setImageResource(R.drawable.ic_icon_down_double);
                    testFragmentViewModel.setExpandText(false);
//                        buttonToggle.setText(R.string.expand);
                }
                else
                {
                    expandableTextView.expand();
                    fragmentTestBinding.expandedIcon.setImageResource(R.drawable.ic_icon_up_double);

                    testFragmentViewModel.setExpandText(true);
//                        buttonToggle.setText(R.string.collapse);
                }
            }
        });

// or set them separately
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        if (test.getText() == null || test.getText().equals(""))
        {
//                System.out.println("null");
        }
        else
        {
            fragmentTestBinding.textText.setVisibility(View.VISIBLE);
            fragmentTestBinding.cadviewText.setVisibility(View.VISIBLE);
            expandableTextView.setText(test.getText());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                expandableTextView.setText(Html.fromHtml(test.getText(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                expandableTextView.setText(Html.fromHtml(test.getText()));
            }

        }

        answerListRecycle = fragmentTestBinding.answerList;
        setUpListOfAnswersView(answerListRecycle);
//        setUpObserver(testFragmentViewModel);
        return fragmentTestBinding.getRoot();

    }

    private void setUpListOfAnswersView(RecyclerView listAnswers)
    {
            testFragmentViewModel.setProgress(true);


            answerAdapter = new AnswerAdapter(answers, resultTag);
//            answers = test.getAnswers();
//            answerAdapter.setAnswerList(answers);

            answerAdapter.setHasStableIds(true);

            listAnswers.setAdapter(answerAdapter);
//            listAnswers.
//        int index = mList.getFirstVisiblePosition();

            listAnswers.setLayoutManager(new LinearLayoutManager(getContext()));
            testFragmentViewModel.setProgress(false);

            if (resultTag == null) {

                answerAdapter.setOnItemListener(new AnswerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Answer item, int position) throws CloneNotSupportedException
                    {

                        changePricesInTheList(position);
                    }
                });
            }

    }

    private void changePricesInTheList(int position) throws CloneNotSupportedException
    {
        ArrayList<Answer> models = new ArrayList<>();
        Answer temp;
        int positionUnselect = 0;

//        int position = -1;
        String answersId = "";
        int answersRsponseCount = 0;
        for (Answer model : answers)
        {
            if (model.getUserAnswer() == 1)
            {
                answersId += model.getAnswerId().toString() + ",";
                answersRsponseCount++;
            }
            models.add(model.clone());

        }

        if (models.size() == 8)
        {
            if (models.get(position).getUserAnswer() == 1)
            {
                models.get(position).setUserAnswer(0);
            }
            else
            {
                if (answersRsponseCount < 3)
                {
                    models.get(position).setUserAnswer(1);
                }
            }

            String answerIds = "";
            for (Answer m : models)
            {
                if (m.getUserAnswer() == 1)
                {
                    answerIds += m.getAnswerId() + ",";
                }
            }

//            System.out.println(answerIds);
            if(!answerIds.equals("")  && answerIds.endsWith(","))
            {
                answerIds = answerIds.substring(0,answerIds.length() - 1);
            }

            saveAnswerTest(test.getQuestionId(), answerIds);

        }
        else
        {
            for (Answer m : models)
            {
                m.setUserAnswer(0);
            }
            models.get(position).setUserAnswer(1);
            saveAnswerTest(test.getQuestionId(), models.get(position).getAnswerId().toString());
        }

        for (int i = 0; i < models.size(); i++)
        {
            if (models.get(i).getUserAnswer() == 1)
            {
                RecyclerView.ViewHolder view = answerListRecycle.findViewHolderForLayoutPosition(i);
                CardView cardView =  view.itemView.findViewById(R.id.cardview1);
                TextView textView = view.itemView.findViewById(R.id.textview1);
                cardView
                        .setCardBackgroundColor(
                                        Color.parseColor("#ff2daafc")
//                                Color.WHITE
                        );
                textView.setTextColor(Color.WHITE);
            }
            else
            {
                RecyclerView.ViewHolder view = answerListRecycle.findViewHolderForLayoutPosition(i);
                CardView cardView =  view.itemView.findViewById(R.id.cardview1);
                TextView textView = view.itemView.findViewById(R.id.textview1);
                cardView
                        .setCardBackgroundColor(
//                                Color.parseColor("#ff2daafc")
                                Color.WHITE
                        );
                textView.setTextColor(Color.BLACK);
            }
        }

        answerAdapter.setData(models);
//        answerAdapter.setAnswerList(models);

    }

    public void finishTest(Long testIdMain)
    {
        testFragmentViewModel.setProgress(true);
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


//        System.out.println(accessToken);
        Disposable disposable = subjectService.finishTest(Constant.ACCEPT,
                language, "Bearer "+ accessToken, testIdMain)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestFinishResponse>()
                           {
                               @Override
                               public void accept(TestFinishResponse testFinishResponse) throws Exception
                               {

                                   if (getActivity() != null) {
//                                       if (resultTag == null) {
                                           Intent intent = new Intent(getContext(), ResultsActivity.class);
                                           intent.putExtra(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
                                           intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
                                           intent.putExtra(Constant.TYPE, typeTest);
                                           intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                                           intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);

    //                                       System.out.println("testrespone");
    //                                       System.out.println(testFinishResponse.getSuccess());

                                           startActivity(intent);

                                           testFragmentViewModel.setProgress(false);
//                                   }


//                            Intent intent = new Intent(this, MainActivity.class);
//                            startActivity(intent);
                                   }
//                                   Toast toast = Toast.makeText(getContext(),
////                                    jsonObject.toString(),
//                                           jsonObject.toString(),
//                                           Toast.LENGTH_SHORT);
//
//                                   toast.show();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401")) {
                                    showToastUnauthorized();
                                    testFragmentViewModel.setProgress(false);
                                }
//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                            }
                        }

                );
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
        if(language.equals(Constant.KZ))
        {
//            buttonNo.setText(R.string.noKz);

            dialogTextAuth.setText(R.string.sessionErrorKz);

        }
        else
        {
//            buttonNo.setText(R.string.noRu);
//            buttonYes.setText(R.string.yesRu);
            dialogTextAuth.setText(R.string.sessionErrorRu);
        }
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


    public void saveAnswerTest(Long questionId, String answerId)
    {
//        System.out.println("question");
//        System.out.println(questionId);
//        System.out.println(answerId);
//        System.out.println(tests.getTestId());
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


//        System.out.println(accessToken);
        Disposable disposable = subjectService.saveAnswer(Constant.ACCEPT,
                language, "Bearer "+ accessToken, tests.getTestId(), questionId, answerId)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SaveAnswerResponse>() {
                               @Override
                               public void accept(SaveAnswerResponse saveAnswerResponse) throws Exception {
//                                   Toast toast = Toast.makeText(getContext(),
////                                    jsonObject.toString(),
//                                    saveAnswerResponse.getMessage(),
//                                    Toast.LENGTH_SHORT);
//
//                                    toast.show();
                               }
                           }
//                        new Consumer<JsonObject>()
//                           {
//                               @Override
//                               public void accept(JsonObject jsonObject) throws Exception {
////                                   Toast toast = Toast.makeText(getContext(),
////                                    jsonObject.toString(),
////                                    Toast.LENGTH_SHORT);
////
////                                    toast.show();
//                               }
//                           }
//                        new Consumer<NodeResponse>() {
//                               @Override
//                               public void accept(NodeResponse nodeResponse) throws Exception
//                               {
//                                   updateNodeBySubjectData(nodeResponse.getData().getLectures());
//                                   lectures_list.set(View.VISIBLE);
//                               }
//                           }
//                        new Consumer<NodesByNode>()
//                {
//                    @Override
//                    public void accept(NodesByNode nodesByNode) throws Exception
//                    {
//                        updateNodeBySubjectData(nodesByNode.getLectures());
//                        lectures_list.set(View.VISIBLE);
//                    }
//                }
                );
    }
//    public void setUpObserver(Observable observable)
//    {
//        observable.addObserver(this);
//    }

//    public static TestFragment newInstance(int val, List<Test> tests)
//    {
//
//        Bundle args = new Bundle();
//        args.putInt("val", val);
//        TestFragment fragment = new TestFragment();
//        fragment.setArguments(args);
//        args.putSerializable("test", tests.get(val));
//        args.putParcelableArrayList("tests", (ArrayList<Test>) tests);
//        fragment.setArguments(args);
//        return fragment;
//    }


//    eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQyOGQ0MzZhNjU0YTdhM2ExZDU5MGY4ZGM1NmU3MTZkYWFmODhkYmJjY2U4YWVhM2M4YTk2ZGRkNjk0MjIwNzMyYzIxY2NkZTIyNDBlMGFhIn0.eyJhdWQiOiIxIiwianRpIjoiZDI4ZDQzNmE2NTRhN2EzYTFkNTkwZjhkYzU2ZTcxNmRhYWY4OGRiYmNjZThhZWEzYzhhOTZkZGQ2OTQyMjA3MzJjMjFjY2RlMjI0MGUwYWEiLCJpYXQiOjE1NTM2MDE4NjksIm5iZiI6MTU1MzYwMTg2OSwiZXhwIjoxNTg1MjI0MjY5LCJzdWIiOiIxMjEyNTIwIiwic2NvcGVzIjpbXX0.Cb7j5a0pRWTON0TYQj_ikhIKhiYS-AnTIsvfpckIfdhtyQv3oCPR7XyPgbBklVqg5UgtD67eaZby_9QE-_0Rjd422QbVe_sY8NzhtQUOCrByDW1GGw1rDxQqtly350Skznd6d82hS_vZJ0LBypMCoa-5p5D6lf54tkEOFzJZk3vv0p_ad3p_OIy3um8_jguAPTHK8EY0IwRVvWwifuLaNNa4M2Vg7Dpki5usbCaPO5KAP7iX5y3XEi4N_6v3GAnukyNkhVimqhEDbWxJ-Al50piPhYKAruE9z15XCyJ-UdKWM2YPR10CGz3lifeDAZmcpfD2KFeYNJlYiiXJ1lPEEda3RASnFXr0dOtVwoxEOP7ABzc6g1nzYVKlVPWqwslGdsd9mZT1iuOBXWDI30huvhRac1-G1t2GRhcm8Vpl_AzB_WMjUHd-0k4sGC6If9LWsHcrOL-ySv7ViGhK5i-9Oe1dYsBOHNszKmhOGjKz3Edbx77MOjs54kKLvFUF88ifEGa12kEPTWyDvZ7hHSGfr72SbiXu5KKUXjkhdtjK7VIGN1noz3BOD6m75NP39xG0xLqjJWKk9_8NV04jPM-fWmW4oFfsA8T5GBBwZj33aneUmCN-IwcOEZOG_YPZAn4hzk0gfZlbWUlSxIat0Iz-0O_N5FsPnYrAgiRbf3NDVmc

    public static TestFragment newInstance(int val,
                                           Tests tests,
                                           Subject selectedSubject,
                                           String type,
                                           Long testIdMain,
                                           List<Subject> selectedSubects,
                                           int currentPosition)
    {

        Bundle args = new Bundle();
        args.putInt("val", val);
//        currentPosition = val;
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        args.putString(Constant.TYPE, type);
        args.putInt(Constant.CURRENT_POSITION_SUBJECT, currentPosition);
        args.putSerializable(Constant.SUBJECT_LIST, (ArrayList<Subject>) selectedSubects);
        args.putSerializable("test", tests.getQuestions().get(val));
        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
        args.putSerializable("tests", tests);
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        fragment.setArguments(args);

        return fragment;
    }

    public static TestFragment newInstance(int val,
                                           Tests tests,
                                           Subject selectedSubject,
                                           String type,
                                           Long testIdMain,
                                           List<Subject> selectedSubects,
                                           int currentPosition, String resultTag)
    {

        Bundle args = new Bundle();
        args.putInt("val", val);
//        currentPosition = val;
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        args.putString(Constant.TYPE, type);
        args.putInt(Constant.CURRENT_POSITION_SUBJECT, currentPosition);
        args.putSerializable(Constant.SUBJECT_LIST, (ArrayList<Subject>) selectedSubects);
        args.putSerializable("test", tests.getQuestions().get(val));
        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
        args.putSerializable("tests", tests);
        args.putString(Constant.RESULT_TAG, resultTag);
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        fragment.setArguments(args);

        return fragment;
    }

    public static TestFragment newInstance(int val, Tests tests, Subject selectedSubject, String type, Long testIdMain, String resultTag)
    {

        Bundle args = new Bundle();
        args.putInt("val", val);
//        currentPosition = val;
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        args.putString(Constant.TYPE, type);
        args.putSerializable("test", tests.getQuestions().get(val));
        args.putSerializable(Constant.SELECTED_SUBJECT, selectedSubject);
        args.putSerializable("tests", tests);
        args.putLong(Constant.TEST_MAIN_ID, testIdMain);
        args.putString(Constant.RESULT_TAG, resultTag);
        fragment.setArguments(args);
        return fragment;
    }
//
//    @Override
//    public void update(Observable o, Object arg)
//    {
//        if (o instanceof TestFragmentViewModel)
//        {
//            TestAdapter testAdapter = (TestAdapter)
//                fragmentTestBinding.answerList.getAdapter();
//
//            TestFragmentViewModel testFragmentViewModel =
//                    (TestFragmentViewModel) o;
//            testAdapter.setAnswerList(testFragmentViewModel.getAnswers());
//
//        }
//
//    }

//    public Integer getCurrentPosition()
//    {
//        return (Integer) getArguments().getInt("val") - 1;
//    }



    @Override
    public void onDestroyView()
    {

        super.onDestroyView();
    }



//    public  int

























//    private FragmentTestBinding fragmentTestBinding;
//    private TestFragmentViewModel testFragmentViewModel;
//    private List<Test> tests;
//    private ViewPager viewPager;
////    private TestAdapter testAdapter;
//
//    public int fragVal;
//    Test test;
//    MathView formula_two;
//    String tex = "This come from string. You can insert inline formula:" +
//            " \\(ax^2 + bx + c = 0\\) " +
//            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$";
//
//    public TestFragment()
//    {
//
//    }
////    public TestFragment(ViewPager viewPager)
////    {
////        this.viewPager = viewPager;
////    }
//
//
//    public static TestFragment newInstance(int val, List<Test> tests)
//    {
//
//        Bundle args = new Bundle();
//        args.putInt("val", val);
//        TestFragment fragment = new TestFragment();
//        fragment.setArguments(args);
//        args.putSerializable("test", tests.get(val));
//        args.putParcelableArrayList("tests", (ArrayList<Test>) tests);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        fragVal = getArguments().get("val") != null ? getArguments().getInt("val") : 1;
//        test = (Test) getArguments().getSerializable("test");
////        tests = savedInstanceState.getParcelableArrayList("tests");
//
//        tests = getArguments().getParcelableArrayList("tests");
////        books = savedInstanceState.getParcelableArrayList(“books”);
//
//
//
////        final
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//
//
//        fragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test,container, false);
//        testFragmentViewModel = new TestFragmentViewModel(getContext(), test);
//        fragmentTestBinding.setTest(testFragmentViewModel);
//
////        setUpListOfAnswersView(fragmentTestBinding.answerList);
//        setUpObserver(testFragmentViewModel);
//
//
//
//        // Construct the data source
////        List<Answer> arrayOfAnswers = new test.getAnswers();
//// Create the adapter to convert the array to views
//
//
//
//
//
//
//
//        AnswerAdapter adapter = new AnswerAdapter(this.getContext(), test.getAnswers());
//// Attach the adapter to a ListView








//        WebView browser = (WebView) fragmentTestBinding.getRoot().findViewById(R.id.webview);
//
//        browser.loadData(Constant.MATHJAX + test.getQuestion(), "text/html; charset=utf-8", "UTF-8");







//
//        ListView listView = (ListView) fragmentTestBinding.getRoot().findViewById(R.id.answer_list);
//        listView.setClickable(true);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                TestActivity testActivity = ((TestActivity)getActivity());
//                Answer a = (Answer) parent.getItemAtPosition(position);
//
//                if (testActivity.getPosition() == 4)
//                {
////                    TestActivity.newAnswers.add(a);
//                    a.setAnswerResponce( a.getId());
//
////                    Intent parentIntent = NavUtils.getParentActivityIntent(getActivity());
////                    parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////
////                    startActivity(parentIntent);
////                    finish();
//
//                    Intent intent = new Intent(getContext(), ResultActivity.class);
//                    intent.putExtra("test",(Serializable) test);
//                    intent.putExtra("tests", (ArrayList<Test>) tests);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
////                    getActivity().finish();
//
////
//                }
//
//
//                else
//                {
////                    TestActivity.newAnswers.add(a);
//                    a.setAnswerResponce(a.getId());
//                    System.out.println(a.getAnswerResponce());
////                    Intent intent = new Intent(getContext(), ResultActivity.class);
////                    intent.putExtra("test",(Serializable) test);
////                    intent.putExtra("tests", (ArrayList<Test>) tests);
////                    startActivity(intent);
//
//
//
//
////                    System.out.println("click!");
//                }


















//                System.out.println("Test Click");
//                System.out.println(testActivity.getPosition());
//                System.out.println(a.toString());

//                TestActivity.newAnswers.add(a);

//
//                TestActivity testActivity = ((TestActivity)getActivity());
//                testActivity.setPosition
//                        ((testActivity.getPosition() < 0)? 0 : testActivity.getPosition()+1);
//
//                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                view.getContext().get
//                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                parent.ge
//                testFragmentViewModel
//            }

//        });
//        listView.setAdapter(adapter);







//        System.out.println(test.getQuestion());

//        <p>Укажите пары противоположных сторон четырёхугольника.</p>
//    <p><img alt="" src="/upload/images/1425288728.6612.jpeg.png" style="height:69px; width:120px" /></p>
//          <p>Стороны прямоугольника равны 15 и 20 см. На сколько процентов увеличится его площадь, если каждую сторону прямоугольника увеличить на 20%?</p>

//        return fragmentTestBinding.getRoot();





//    }



//    private void setUpListOfAnswersView(RecyclerView listAnswers) {
//        TestAdapter testAdapter = new TestAdapter();
//        listAnswers.setAdapter(testAdapter);
//        listAnswers.setLayoutManager(new LinearLayoutManager(getContext()));
//    }
//
//    public void setUpObserver(Observable observable)
//    {
//        observable.addObserver(this);
//    }
//
//    @Override
//    public void update(Observable o, Object arg)
//    {
//        if (o instanceof TestFragmentViewModel)
//        {
//            TestAdapter testAdapter = (TestAdapter) fragmentTestBinding
//                    .answerList.getAdapter();
//            TestFragmentViewModel testFragmentViewModel
//                    = (TestFragmentViewModel) o;
//            testAdapter.setAnswerList(testFragmentViewModel.getAnswers());
//        }
//
//    }
}
