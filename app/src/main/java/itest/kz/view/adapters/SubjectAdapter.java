package itest.kz.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemSubjectBinding;
import itest.kz.model.Subject;
import itest.kz.viewmodel.ItemSubjectFragmentViewModel;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectAdapterViewHolder>
{

    private List<Subject> subjectList;
    @NonNull
    @Override
    public SubjectAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemSubjectBinding itemSubjectBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_subject, viewGroup, false);


        return new SubjectAdapterViewHolder(itemSubjectBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapterViewHolder subjectAdapterViewHolder, int i)
    {
        subjectAdapterViewHolder.bindSubject(subjectList.get(i));
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

    public static class SubjectAdapterViewHolder extends RecyclerView.ViewHolder
    {
//        ItemSubjectBinding itemSubjectBinding;

        ItemSubjectBinding itemSubjectBinding;

        public SubjectAdapterViewHolder(ItemSubjectBinding itemSubjectBinding)
        {
            super(itemSubjectBinding.getRoot());
            this.itemSubjectBinding = itemSubjectBinding;
        }

        void bindSubject(Subject subject)
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
        }


    }
}