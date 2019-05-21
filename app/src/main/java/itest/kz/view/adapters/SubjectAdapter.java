package itest.kz.view.adapters;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemSubjectBinding;
import itest.kz.model.Subject;
import itest.kz.viewmodel.ItemSubjectFragmentViewModel;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectAdapterViewHolder>
{

    public interface OnItemClickListener
    {
        void onItemClick(Subject item, int i) throws CloneNotSupportedException;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public OnItemClickListener listener;
    private SubjectAdapterViewHolder subjectAdapterViewHolder;

    public void setOnItemListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    private List<Subject> subjectList;
    @NonNull
    @Override
    public SubjectAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemSubjectBinding itemSubjectBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_subject, viewGroup, false);

        subjectAdapterViewHolder = new SubjectAdapterViewHolder(itemSubjectBinding);
        return subjectAdapterViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapterViewHolder subjectAdapterViewHolder, int i)
    {
        subjectAdapterViewHolder.bindSubject(subjectList.get(i),listener);
    }

    @Override
    public int getItemCount() {
        if (subjectList != null)
            return subjectList.size();
        return 0;
    }

    public void setSubjectList(List<Subject> subjectList)
    {
        this.subjectList = subjectList;
        notifyDataSetChanged();
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public SubjectAdapterViewHolder getSubjectAdapterViewHolder()
    {
        return subjectAdapterViewHolder;
    }

    public static class SubjectAdapterViewHolder extends RecyclerView.ViewHolder
    {
//        ItemSubjectBinding itemSubjectBinding;

        ItemSubjectBinding itemSubjectBinding;

        public SubjectAdapterViewHolder(ItemSubjectBinding itemSubjectBinding)
        {
            super(itemSubjectBinding.getRoot());
            this.itemSubjectBinding = itemSubjectBinding;
        }

        void bindSubject(Subject subject,final OnItemClickListener listener)
        {
            if(itemSubjectBinding.getSubjectFragmentViewModel() == null)
            {
                itemSubjectBinding.setSubjectFragmentViewModel(
                        new ItemSubjectFragmentViewModel(subject, itemView.getContext()));
            }
            else
            {
                itemSubjectBinding.getSubjectFragmentViewModel().setSubject(subject);
            }

//            if (subject.isExpand())
//            {
//                itemSubjectBinding.expandCardview.setVisibility(View.VISIBLE);
////                itemSubjectBinding
////                        .titleSubject
////                        .setTextColor(Color.parseColor("#ff2daafc"));
//            }
//            else
//            {
//                itemSubjectBinding.expandCardview.setVisibility(View.GONE);
////                itemSubjectBinding
////                        .titleSubject
////                        .setTextColor(Color.BLACK);
//            }

            itemSubjectBinding.subjectLinear.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    try {
                        listener.onItemClick(subject, getAdapterPosition());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
}