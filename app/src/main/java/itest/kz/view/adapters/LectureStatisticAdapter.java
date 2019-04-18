package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemLectureStatisticBinding;
import itest.kz.model.LectureStatisticResponse;
import itest.kz.model.Subject;
import itest.kz.viewmodel.ItemLectureStatisticViewModel;

public class LectureStatisticAdapter extends RecyclerView.Adapter<LectureStatisticAdapter
        .LectureStatisticAdapterViewHolder>
{
    public interface OnItemClickListener
    {
        void onItemClick(LectureStatisticResponse lectureStatisticResponses, int i) throws CloneNotSupportedException;
    }
    private List<LectureStatisticResponse> lectureStatisticResponses;
    private Context context;
    public OnItemClickListener listener;

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
    public LectureStatisticAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemLectureStatisticBinding itemLectureStatisticBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_lecture_statistic, viewGroup, false);
        context = viewGroup.getContext();
        return new LectureStatisticAdapterViewHolder(itemLectureStatisticBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureStatisticAdapterViewHolder lectureStatisticAdapterViewHolder, int i)
    {
        lectureStatisticAdapterViewHolder.bindLectureStatistic
        (lectureStatisticResponses.get(i), listener);
        RecyclerView recyclerView =
                lectureStatisticAdapterViewHolder.recyclerView;
        SubLectureAdapter subLectureAdapter =
                new SubLectureAdapter(lectureStatisticResponses.get(i).getTestList());
        recyclerView.setAdapter(subLectureAdapter);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public int getItemCount()
    {
        if (lectureStatisticResponses != null)
            return lectureStatisticResponses
                    .size();
        return 0;
    }

    public void setOnItemListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public List<LectureStatisticResponse> getLectureStatisticResponses()
    {
        return lectureStatisticResponses;
    }

    public void setLectureStatisticResponses(List<LectureStatisticResponse> lectureStatisticResponseList)
    {
        this.lectureStatisticResponses = lectureStatisticResponseList;
        notifyDataSetChanged();
    }

    public static  class  LectureStatisticAdapterViewHolder extends RecyclerView.ViewHolder
    {
        ItemLectureStatisticBinding itemLectureStatisticBinding;
        RecyclerView recyclerView;

        public LectureStatisticAdapterViewHolder(
                ItemLectureStatisticBinding itemLectureStatisticBinding)
        {
            super(itemLectureStatisticBinding.getRoot());
            this.itemLectureStatisticBinding =
                    itemLectureStatisticBinding;
            recyclerView = (RecyclerView) itemLectureStatisticBinding.listLectureStatistic;
        }

        void bindLectureStatistic(LectureStatisticResponse
                                  lectureStatisticResponse, OnItemClickListener listener)
        {
            if (itemLectureStatisticBinding.getItem() == null)
            {
                itemLectureStatisticBinding.setItem
                        (new ItemLectureStatisticViewModel
                                (itemView.getContext(),
                                        lectureStatisticResponse));
            }
            else
            {
                itemLectureStatisticBinding.getItem().setLectureStatisticResponse
                        (lectureStatisticResponse);
            }

            if (lectureStatisticResponse.isExpand())
                itemLectureStatisticBinding
                        .listLectureStatistic
                        .setVisibility(View.VISIBLE);
            else
            {
                itemLectureStatisticBinding
                        .listLectureStatistic
                        .setVisibility(View.GONE);
            }

//                if (subject.isExpand())
//                {
//                    itemSubjectBinding.expandCardview.setVisibility(View.VISIBLE);
//                }
//                else
//                    itemSubjectBinding.expandCardview.setVisibility(View.GONE);

            itemLectureStatisticBinding.testResultSubjectCatdview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    try {
                        listener.onItemClick(lectureStatisticResponse, getAdapterPosition());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}




