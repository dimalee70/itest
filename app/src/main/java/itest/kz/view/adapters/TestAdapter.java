package itest.kz.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemAnswerBinding;
import itest.kz.databinding.ItemSubjectBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.viewmodel.ItemAnswerViewModel;
import itest.kz.viewmodel.ItemSubjectFragmentViewModel;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestAdapterViewHolder>
{
    private List<Answer> answerList;
    @NonNull
    @Override
    public TestAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemAnswerBinding itemAnswerBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_answer, viewGroup, false);
        return new TestAdapter.TestAdapterViewHolder(itemAnswerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapterViewHolder testAdapterViewHolder, int i)
    {
        testAdapterViewHolder.bindAnswer(answerList.get(i));
    }

    @Override
    public int getItemCount()
    {
        if (answerList != null)
            return answerList.size();
        return 0;
    }

    public void setAnswerList(List<Answer> answerList)
    {
        this.answerList = answerList;
        notifyDataSetChanged();
    }

    //    private List<Subject> subjectList;
//    @NonNull
//    @Override
//    public SubjectAdapter.SubjectAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
//    {
//        ItemSubjectBinding itemSubjectBinding = DataBindingUtil
//                .inflate(LayoutInflater.from(viewGroup.getContext()),
//                        R.layout.item_subject, viewGroup, false);
//
//
//        return new SubjectAdapter.SubjectAdapterViewHolder(itemSubjectBinding);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SubjectAdapter.SubjectAdapterViewHolder subjectAdapterViewHolder, int i)
//    {
//        subjectAdapterViewHolder.bindSubject(subjectList.get(i));
//    }
//
//    @Override
//    public int getItemCount() {
//        if (subjectList != null)
//            return subjectList.size();
//        return 0;
//    }
//
//    public void setSubjectList(List<Subject> subjectList)
//    {
//        this.subjectList = subjectList;
//        notifyDataSetChanged();
//    }
    public static class TestAdapterViewHolder extends RecyclerView.ViewHolder
    {
        ItemAnswerBinding itemAnswerBinding;

        public TestAdapterViewHolder(@NonNull ItemAnswerBinding itemAnswerBinding)
        {
            super(itemAnswerBinding.getRoot());
            this.itemAnswerBinding = itemAnswerBinding;
        }

        void bindAnswer(Answer answer)
        {
            if (itemAnswerBinding.getAnswer() == null)
                itemAnswerBinding.setAnswer
                        (new ItemAnswerViewModel(itemView.getContext(), answer));
            else
                itemAnswerBinding.getAnswer().setAnswer(answer);
        }
//        ItemSubjectBinding itemSubjectBinding;

//        ItemSubjectBinding itemSubjectBinding;
//
//        public SubjectAdapterViewHolder(ItemSubjectBinding itemSubjectBinding)
//        {
//            super(itemSubjectBinding.getRoot());
//            this.itemSubjectBinding = itemSubjectBinding;
//        }
//
//        void bindSubject(Subject subject)
//        {
//            if(itemSubjectBinding.getSubjectFragmentViewModel() == null)
//            {
//                itemSubjectBinding.setSubjectFragmentViewModel(
//                        new ItemSubjectFragmentViewModel(subject, itemView.getContext()));
//            }
//            else
//            {
//                itemSubjectBinding.getSubjectFragmentViewModel().setSubject(subject);
//            }
//        }


    }
}
