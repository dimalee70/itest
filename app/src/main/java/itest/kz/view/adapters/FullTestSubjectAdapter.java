package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
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
import itest.kz.viewmodel.ItemEntViewModel;
import itest.kz.viewmodel.ItemFullEntViewModel;

public class FullTestSubjectAdapter extends RecyclerView.Adapter<FullTestSubjectAdapter.FullTestSubjectAdapterViewHolder>
{

    public interface OnItemClickListener
    {
        void onItemClick(Subject item, int position);
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
    public int getItemCount()
    {
        if (subjectList != null)
            return subjectList.size();
        return 0;
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
            itemFullSubjectBinding.entStartCardview.setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            listener.onItemClick(subject, position);
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
