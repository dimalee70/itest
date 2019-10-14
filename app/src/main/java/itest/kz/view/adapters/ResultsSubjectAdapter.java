package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemAnswerBinding;
import itest.kz.databinding.ItemResultSubjectBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.util.LETTERS;
import itest.kz.viewmodel.ItemAnswerViewModel;
import itest.kz.viewmodel.ItemResultSubject;
import itest.kz.viewmodel.ItemResultViewModel;

public class ResultsSubjectAdapter extends RecyclerView.Adapter<ResultsSubjectAdapter.ResultsSubjectAdapterViewHolder>
{
//    private List<Answer> answerList;
    private List<Tests> testsList;

//    private Context context;

    private ItemResultSubjectBinding itemResultSubjectBinding;
    private ItemResultSubject itemResultSubject;

//    private LETTERS[] letters;
//    private ViewGroup viewGroup;
//    private int position;

    public interface OnItemClickListener
    {
        void onItemClick(Tests item, int position) throws CloneNotSupportedException;
    }

    public ResultsSubjectAdapter(List<Tests> testsList)
    {
        this.testsList = testsList;
    }

    private OnItemClickListener listener;

    @NonNull
    @Override
    public ResultsSubjectAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemResultSubjectBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_result_subject, viewGroup, false);

        return new ResultsSubjectAdapterViewHolder(itemResultSubjectBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsSubjectAdapterViewHolder resultsSubjectAdapterViewHolder, int i)
    {
        resultsSubjectAdapterViewHolder.bindResultSubject(testsList.get(i), i, listener);
    }


    @Override
    public int getItemCount()
    {
        if (testsList != null)
            return testsList.size();
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setOnItemListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }


    public List<Tests> getTestsList()
    {
        return testsList;
    }

    public void setTestsList(List<Tests> testsList)
    {
        this.testsList = testsList;
        notifyDataSetChanged();

    }

    public static class ResultsSubjectAdapterViewHolder extends RecyclerView.ViewHolder
    {
        ItemResultSubjectBinding itemResultSubjectBinding;
        ItemResultSubject itemResultSubject;

        public ResultsSubjectAdapterViewHolder(ItemResultSubjectBinding itemResultSubjectBinding) {
            super(itemResultSubjectBinding.getRoot());
            this.itemResultSubjectBinding = itemResultSubjectBinding;
        }


        void bindResultSubject(Tests tests, int position, final OnItemClickListener listener)
        {
            if(itemResultSubjectBinding.getItem() == null)
            {
                itemResultSubjectBinding.setItem(
                        new ItemResultSubject(itemView.getContext(), tests));
            }
            else
            {
                itemResultSubjectBinding.getItem().setTests(tests);

            }


//        itemResultSubjectBinding.testResultSubjectCatdview.setOnClickListener
//                (new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        try {
//                            listener.onItemClick(tests, getAdapterPosition());
//                        } catch (CloneNotSupportedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
        }

    }
}
