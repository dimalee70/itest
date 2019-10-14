package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.SubitemLectureStatisticBinding;
import itest.kz.model.Test;
import itest.kz.viewmodel.SubItemLectureStatisticViewModel;

public class SubLectureAdapter extends RecyclerView.Adapter<SubLectureAdapter.SubLectureAdapterViewHolder>
{
    private List<Test> testList;
    private SubitemLectureStatisticBinding subitemLectureStatisticBinding;

    public SubLectureAdapter(List<Test> testList)
    {
        this.testList = testList;
    }

    @NonNull
    @Override
    public SubLectureAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        subitemLectureStatisticBinding =
                DataBindingUtil.inflate
                        (LayoutInflater.from(viewGroup.getContext()),
                                R.layout.subitem_lecture_statistic,
                                viewGroup,false
                                );

        return new SubLectureAdapterViewHolder(subitemLectureStatisticBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubLectureAdapterViewHolder subLectureAdapterViewHolder, int i)
    {
        subLectureAdapterViewHolder
                .bindSubLecture(testList.get(i));
    }

    public void setTestList(List<Test> testList)
    {
        this.testList = testList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        if (testList != null)
            return testList.size();
        return 0;
    }

    public static class SubLectureAdapterViewHolder extends RecyclerView.ViewHolder
    {
        SubitemLectureStatisticBinding subitemLectureStatisticBinding;

        public SubLectureAdapterViewHolder
                (SubitemLectureStatisticBinding subitemLectureStatisticBinding) {
            super(subitemLectureStatisticBinding.getRoot());
            this.subitemLectureStatisticBinding =
                    subitemLectureStatisticBinding;
        }

        void bindSubLecture(Test test)
        {
            if (subitemLectureStatisticBinding.getSubitem() == null)
            {
                subitemLectureStatisticBinding
                        .setSubitem
                                (new SubItemLectureStatisticViewModel
                                        (itemView.getContext(), test));
            }
            else
            {
                subitemLectureStatisticBinding
                        .getSubitem().setTest(test);
            }
        }
    }

}
//    private void setTextViewDrawableColor(TextView textView, int color) {
//        for (Drawable drawable : textView.getCompoundDrawables()) {
//            if (drawable != null) {
//                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
//            }
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull QuestionAdapterViewHolder questionAdapterViewHolder, int i)
//    {
//
//        questionAdapterViewHolder.bindAnswer(answers.get(i));
//        itemQuestionsBinding.btn.setText(letters[i].toString());
//
//        StateListDrawable gradientDrawable = (StateListDrawable) itemQuestionsBinding.btn.getBackground();
//        DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
//        Drawable[] children = drawableContainerState.getChildren();
//        GradientDrawable selectedItem = (GradientDrawable) children[0];
//        if (resultTag != null)
//        {
//
//            if ( answers.get(i).getUserAnswer() == 1)
//            {
//                selectedItem.setColor(Color.parseColor("#ffff6969"));
//            }
//
//            if (answers.get(i).getCorrect() == 1 && isAnswered())
//            {
//                selectedItem.setColor(Color.parseColor("#ff68da78"));
//            }
//
//            else if (!isAnswered() && answers.get(i).getCorrect() == 1)
//            {
//                selectedItem.setColor(Color.parseColor("#a0b5c2"));
//            }
//        }
//        else
//        {
//            if (answers.get(i).getUserAnswer() == 1) {
//                selectedItem.setColor(Color.parseColor("#D2D0D3"));
//            }
//
//        }
//
//
//    }
//
//    public boolean isAnswered()
//    {
//        for (Answer a : answers)
//        {
//            if (a.getUserAnswer() == 1)
//                return true;
//        }
//        return false;
//    }
//    @Override
//    public int getItemCount() {
//        if (answers != null)
//            return answers.size();
//        return 0;
//    }
//    public List<Answer> getAnswerList() {
//        return answers;
//    }
//
//    public void setAnswerList(List<Answer> answerList)
//    {
//        this.answers = answerList;
//        notifyDataSetChanged();
//    }
//

//}