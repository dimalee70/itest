package itest.kz.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemSubjectStatisticBinding;
import itest.kz.model.StatisticSubject;
import itest.kz.util.Constant;
import itest.kz.viewmodel.ItemSubjectStatisticViewModel;

public class SubjectStatisticAdapter extends RecyclerView.Adapter<SubjectStatisticAdapter.SubjectStatisticAdapterViewHolder>
{
    private List<StatisticSubject> statisticSubjectList;
    public String typeTest;

    public SubjectStatisticAdapter(String typeTest)
    {
        this.typeTest = typeTest;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public SubjectStatisticAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemSubjectStatisticBinding itemSubjectStatisticBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_subject_statistic, viewGroup, false);
        return new SubjectStatisticAdapterViewHolder(itemSubjectStatisticBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectStatisticAdapterViewHolder subjectStatisticAdapterViewHolder, int i)
    {
        subjectStatisticAdapterViewHolder.bindSubjectStatistic
                (statisticSubjectList.get(i), typeTest);
    }

    @Override
    public int getItemCount()
    {
        if (statisticSubjectList != null)
            return statisticSubjectList.size();
        return 0;
    }

    public void setStatisticSubjectList(List<StatisticSubject> statisticSubjectList)
    {
        this.statisticSubjectList = statisticSubjectList;
//        Collections.sort(this.statisticSubjectList, Collections.reverseOrder());
        notifyDataSetChanged();
    }

    public static class  SubjectStatisticAdapterViewHolder extends RecyclerView.ViewHolder
    {

        ItemSubjectStatisticBinding itemSubjectStatisticBinding;

        public SubjectStatisticAdapterViewHolder(ItemSubjectStatisticBinding itemSubjectStatisticBinding)
        {
            super(itemSubjectStatisticBinding.getRoot());
            this.itemSubjectStatisticBinding = itemSubjectStatisticBinding;
        }

        void  bindSubjectStatistic(StatisticSubject statisticSubject, String typeTest)
        {
            if (itemSubjectStatisticBinding.getItem() == null)
            {
                itemSubjectStatisticBinding.setItem
                        (new ItemSubjectStatisticViewModel(statisticSubject,
                                itemView.getContext(), typeTest));
            }
            else
            {
                itemSubjectStatisticBinding.getItem().setStatisticSubject(statisticSubject);
            }
        }
    }
}


