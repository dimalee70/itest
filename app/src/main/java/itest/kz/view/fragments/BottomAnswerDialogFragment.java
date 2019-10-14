package itest.kz.view.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.FragmentBottomAnswerDialogBinding;
import itest.kz.model.Answer;
import itest.kz.model.Question;
import itest.kz.util.Constant;
import itest.kz.view.adapters.AnswerCheckAdapter;
import itest.kz.viewmodel.BottomAnswerDialogViewModel;

public class BottomAnswerDialogFragment extends BottomSheetDialogFragment
{
    private FragmentBottomAnswerDialogBinding fragmentBottomAnswerDialogBinding;
    private BottomAnswerDialogViewModel bottomAnswerDialogViewModel;
    private Question test;
    private List<Answer> answerList;
    private AnswerCheckAdapter answerCheckAdapter;

    public BottomAnswerDialogFragment()
    {
    }

//    @SuppressLint("RestrictedApi")
//    @Override
//    public void setupDialog(Dialog dialog, int style)
//    {
//        super.setupDialog(dialog, style);
//        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bottom_answer_dialog,null,false);
//        dialog.setContentView(rootView);
//        FrameLayout bottomSheet = (FrameLayout) dialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
//        bottomSheet.setBackgroundResource(R.drawable.dialog_rounded_bg);
//    }


    //    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
//    }

    public static BottomAnswerDialogFragment newInstance(Question test, List<Answer> answerList)
    {

        Bundle args = new Bundle();
        args.putSerializable(Constant.QUESTION_FOR_CHECK, test);
        args.putSerializable(Constant.ANSWER_CHECK_LIST, (Serializable) answerList);
        BottomAnswerDialogFragment fragment = new BottomAnswerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

//    public BottomAnswerDialogFragment()
//    {
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }



//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final Dialog dialog =  super.onCreateDialog(savedInstanceState);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////        int width = getResources().getDimensionPixelSize(R.dimen.test);
////        int height = getResources().getDimensionPixelSize(R.dimen.test);
////        dialog.getWindow().setLayout(width, height);
//        return dialog;
//
//    }

    @Override
    public void onStart() {
        super.onStart();
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        int width = size.x;
//        int height = size.y;
//        if (this != null) {
//            getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_rounded_bg));
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            //        int height = getResources().getDimensionPixelSize(R.dimen.test);
//            getWindow().setLayout(width, height);
//            getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_rounded_bg));
//        }

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        this.test = (Question) getArguments().getSerializable(Constant.QUESTION_FOR_CHECK);
        this.answerList = (List<Answer>) getArguments().getSerializable(Constant.ANSWER_CHECK_LIST);

        fragmentBottomAnswerDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.fragment_bottom_answer_dialog,
                null, false);
        bottomAnswerDialogViewModel = new BottomAnswerDialogViewModel(getContext(), this, test);


        fragmentBottomAnswerDialogBinding.setAnswer(bottomAnswerDialogViewModel);

//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bottom_answer_dialog,null,false);
//        getDialog().setContentView(rootView);
//        FrameLayout bottomSheet = (FrameLayout) getDialog().getWindow().findViewById(R.layout.fragment_bottom_answer_dialog);
//        bottomSheet.setBackgroundResource(R.drawable.dialog_rounded_bg);

//        bottomSheetFragment.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        bottomSheetFragment

//        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((fragmentBottomAnswerDialogBinding.getRoot()));
//        mBehavior.setPeekHeight(1500);

//        fragmentBottomAnswerDialogBinding.getRoot()..getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setR(fragmentBottomAnswerDialogBinding.listAnswersCheck);

        return fragmentBottomAnswerDialogBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void  setR(RecyclerView listAnswers)
    {
//        List<Answer> tempAnswerList = new ArrayList<>();
//        for (Answer a : answerList)
//        {
//            if (a.getCorrect() == 1)
//            {
//                tempAnswerList.add(a);
//            }
//        }

        answerCheckAdapter = new AnswerCheckAdapter(answerList);
        listAnswers.setAdapter(answerCheckAdapter);
        listAnswers.setLayoutManager(new LinearLayoutManager(getContext()));
    }

//    private void setUpListOfAnswersView(RecyclerView listAnswers)
//    {
//
//
//        answerAdapter = new AnswerAdapter(answers, resultTag);
////            answers = test.getAnswers();
////            answerAdapter.setAnswerList(answers);
//
//        answerAdapter.setHasStableIds(true);
//
//        listAnswers.setAdapter(answerAdapter);
////            listAnswers.
////        int index = mList.getFirstVisiblePosition();
//
//        listAnswers.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        if (resultTag == null) {
//
//            answerAdapter.setOnItemListener(new AnswerAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(Answer item, int position) throws CloneNotSupportedException {
//                    changePricesInTheList(position);
//                }
//            });
//        }
//    }

}
