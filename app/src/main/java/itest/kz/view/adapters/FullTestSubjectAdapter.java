package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemEntBinding;
import itest.kz.databinding.ItemFullSubjectBinding;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.util.FullTestSubjectDiffUtilCallback;
import itest.kz.viewmodel.ItemEntViewModel;
import itest.kz.viewmodel.ItemFullEntViewModel;

public class FullTestSubjectAdapter extends RecyclerView.Adapter<FullTestSubjectAdapter.FullTestSubjectAdapterViewHolder>
{

    public interface OnItemClickListener
    {
        void onItemClick(Subject item, int position) throws CloneNotSupportedException;
    }
    private OnItemClickListener listener;
    private List<Subject> subjectList;
    private ItemFullSubjectBinding itemFullSubjectBinding;
    private Context context;

    public FullTestSubjectAdapter(Context context, List<Subject> subjectList)
    {
        this.context = context;
        this.subjectList = subjectList;
    }

    public void setOnItemListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }


    @NonNull
    @Override
    public FullTestSubjectAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemFullSubjectBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_full_subject, viewGroup, false);

        return new FullTestSubjectAdapterViewHolder(itemFullSubjectBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FullTestSubjectAdapterViewHolder fullTestSubjectAdapterViewHolder, int i)
    {
        fullTestSubjectAdapterViewHolder.bindSubject(subjectList.get(i), i, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FullTestSubjectAdapterViewHolder fullTestSubjectAdapterViewHolder,
                                 int position,
                                 @NonNull List<Object> payloads)
    {
        if (payloads.isEmpty())
            super.onBindViewHolder(fullTestSubjectAdapterViewHolder, position, payloads);
        else
        {

            Bundle o = (Bundle) payloads.get(0);
            for (String key : o.keySet()) {
                if (key.equals("price"))
                {
                    itemFullSubjectBinding.entStartCardview
                            .setCardBackgroundColor(
                                    Color.parseColor(Constant.colorSelectedSubjectOnEnt));
                    itemFullSubjectBinding.titleSubjectText.setTextColor(Color.WHITE);
                }
                else
                {
                    itemFullSubjectBinding.entStartCardview
                            .setCardBackgroundColor(
                                    Color.parseColor("#F1F2F6FF"));
                    itemFullSubjectBinding.titleSubjectText.setTextColor(Color.parseColor("#ff2daafc"));
                }
            }

        }
    }


    @Override
    public int getItemCount()
    {
        if (subjectList != null)
            return subjectList.size();
        return 0;
    }

    public void setData(List<Subject> newData)
    {

//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FullTestSubjectDiffUtilCallback(newData, subjectList));
//        diffResult.dispatchUpdatesTo(this);
        subjectList.clear();
        subjectList.addAll(newData);
        notifyDataSetChanged();
    }

    public List<Subject> getData() {
        return subjectList;
    }


    public List<Subject> getSubjectList()
    {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList)
    {
        this.subjectList = subjectList;
        notifyDataSetChanged();
    }

    public static class FullTestSubjectAdapterViewHolder extends RecyclerView.ViewHolder
    {

//        ItemEntBinding itemEntBinding;
        ItemFullSubjectBinding itemFullSubjectBinding;

        public FullTestSubjectAdapterViewHolder(ItemFullSubjectBinding itemFullSubjectBinding)
        {
            super(itemFullSubjectBinding.getRoot());
            this.itemFullSubjectBinding = itemFullSubjectBinding;
        }



        void bindSubject(Subject subject, int position, final OnItemClickListener listener)
        {
            if(itemFullSubjectBinding.getFullSubject() == null)
            {
                itemFullSubjectBinding.setFullSubject(
                        new ItemFullEntViewModel(itemView.getContext(),subject));
            }
            else
            {
                itemFullSubjectBinding.getFullSubject().setSubject(subject);

            }
            if (subject.getOnClickedRecycle() == 1)
            {
                itemFullSubjectBinding.entStartCardview
                        .setCardBackgroundColor(
                                Color.parseColor(Constant.colorSelectedSubjectOnEnt));
                itemFullSubjectBinding.subjectListRelative
                        .setBackgroundColor(Color.parseColor(Constant.colorSelectedSubjectOnEnt));
                itemFullSubjectBinding.titleSubjectText.setTextColor(Color.WHITE);

            }
            else
            {
                itemFullSubjectBinding.entStartCardview
                        .setCardBackgroundColor(
                                Color.parseColor("#F1F2F6FF"));
                itemFullSubjectBinding.subjectListRelative
                        .setBackgroundColor(
                                Color.parseColor("#F1F2F6FF"));
                itemFullSubjectBinding.titleSubjectText.setTextColor(Color.parseColor("#ff2daafc"));
            }
            itemFullSubjectBinding.entStartCardview.setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            try {
                                listener.onItemClick(subject, position);
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
//                            subject.setOnClickedRecycle(1);
//                            itemFullSubjectBinding.entStartCardview
//                                    .setCardBackgroundColor(Color.parseColor(Constant.colorSelectedSubjectOnEnt));
//                            itemFullSubjectBinding.titleSubjectText.setTextColor(Color.WHITE);
                        }
                    });
//            itemEntBinding.getRoot().findViewById(R.id.cardview_subject).setOnClickListener
//                    (new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v)
//                        {
//                            listener.onItemClick(subject, subjectList);
//                        }
//                    });

        }
    }
}
