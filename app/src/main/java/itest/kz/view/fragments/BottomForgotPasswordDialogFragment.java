package itest.kz.view.fragments;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.FragmentBottomResetPasswordDialogBinding;
import itest.kz.model.Answer;
import itest.kz.model.Question;
import itest.kz.util.Constant;
import itest.kz.viewmodel.BottomAnswerDialogViewModel;
import itest.kz.viewmodel.BottomForgotPasswordDialogViewModel;

public class BottomForgotPasswordDialogFragment extends BottomSheetDialogFragment
{
    private FragmentBottomResetPasswordDialogBinding
                    fragmentBottomResetPasswordDialogBinding;
    private BottomForgotPasswordDialogViewModel
                    bottomForgotPasswordDialogViewModel;
    public BottomForgotPasswordDialogFragment(){}

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.
//                YELLOW
                parseColor("#ff37acf9")
        ));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        fragmentBottomResetPasswordDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.fragment_bottom_reset_password_dialog,
                null, false);
        bottomForgotPasswordDialogViewModel = new BottomForgotPasswordDialogViewModel(getContext());


        fragmentBottomResetPasswordDialogBinding.setForgot(bottomForgotPasswordDialogViewModel);

        fragmentBottomResetPasswordDialogBinding.backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

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
//        setR(fragmentBottomAnswerDialogBinding.listAnswersCheck);

        return fragmentBottomResetPasswordDialogBinding.getRoot();
    }

}
