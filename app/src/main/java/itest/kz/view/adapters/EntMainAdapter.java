package itest.kz.view.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ahmadrosid.svgloader.SvgLoader;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemEntBinding;
import itest.kz.databinding.ItemSubjectBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.viewmodel.ItemEntViewModel;
import itest.kz.viewmodel.ItemSubjectFragmentViewModel;

public class EntMainAdapter extends RecyclerView.Adapter<EntMainAdapter.EntMainAdapterViewHolder>
{
    public interface OnItemClickListener
    {
        void onItemClick(Subject item, List<Subject> subjects, int i);
    }

    private ItemEntViewModel itemEntViewModel;
    private OnItemClickListener listener;

    private List<Subject> subjectList;

//    public void setSubjects(Subject subject)
//    {
//        itemEntViewModel.se
//    }

    public Subject getItem(int position)
    {
        return subjectList.get(position);
    }
    @NonNull
    @Override
    public EntMainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemEntBinding itemEntBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_ent, viewGroup, false);
        return new EntMainAdapterViewHolder(itemEntBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EntMainAdapterViewHolder entMainAdapterViewHolder, int i)
    {
        entMainAdapterViewHolder.bindSubject(subjectList.get(i), subjectList, i, listener);
    }

    @Override
    public int getItemCount()
    {
        if (subjectList != null)
            return subjectList.size();
        return 0;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public List<Subject> getSubjectListMain()
    {
        return subjectList;
    }

    public void setSubjectListMain(List<Subject> subjectList)
    {
        this.subjectList = subjectList;
        notifyDataSetChanged();
    }

    public void setSubjectList(List<Subject> subjectList)
    {
        this.subjectList = subjectList;
//        itemEntViewModel.setSubject();
        notifyDataSetChanged();
    }



    public void setOnItemListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }



    public static class EntMainAdapterViewHolder extends RecyclerView.ViewHolder
    {
//        ItemSubjectBinding itemSubjectBinding;

        ItemEntViewModel itemEntViewModel;
        ItemEntBinding itemEntBinding;

        public EntMainAdapterViewHolder(ItemEntBinding itemEntBinding)
        {
            super(itemEntBinding.getRoot());
            this.itemEntBinding = itemEntBinding;
        }


        void bindSubject(Subject subject, List<Subject> subjectList, int i, final OnItemClickListener listener)
        {
            if(itemEntBinding.getEnt() == null)
            {
                itemEntViewModel = new ItemEntViewModel(itemView.getContext(), subject);

                itemEntBinding.setEnt(
                        itemEntViewModel);
            }
            else
            {
                itemEntBinding.getEnt().setSubject(subject);
            }

//            if (subject.getIsSelected() == 1)
//            {
//                itemEntViewModel
//                        .setSubject(subject);
//            }

            itemEntBinding.getRoot().findViewById(R.id.cardview_subject).setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {

                            listener.onItemClick(subject, subjectList, i);

                        }
                    });
//            ImageView imageView = itemEntBinding.imageView1;
//            SvgLoader.pluck()
//                    .with((Activity) itemView.getContext())
//                    .load(subject.getIcon(), imageView);
        }
    }
}
