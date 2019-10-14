package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemNodeBinding;
import itest.kz.databinding.ItemNodeByNodeBinding;
import itest.kz.model.Lecture;
import itest.kz.model.Node;
import itest.kz.viewmodel.ItemNodeByNodeViewModel;
import itest.kz.viewmodel.ItemNodeViewModel;

public class NodeByNodeAdapter extends RecyclerView.Adapter<NodeByNodeAdapter.NodeByNodeAdapterViewHolder>
{

    private List<Lecture> lectureList;
    private Context context;
    private ItemNodeByNodeBinding itemNodeByNodeBinding;

    @NonNull
    @Override
    public NodeByNodeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemNodeByNodeBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_node_by_node, viewGroup, false);

        return new NodeByNodeAdapterViewHolder(itemNodeByNodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeByNodeAdapterViewHolder nodeByNodeAdapterViewHolder, int i)
    {
        nodeByNodeAdapterViewHolder.bindLecture(lectureList.get(i));
    }

    @Override
    public int getItemCount()
    {
        if (lectureList != null)
            return lectureList.size();
        return 0;
    }

    public List<Lecture> getLectureList()
    {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList)
    {
        this.lectureList = lectureList;
        notifyDataSetChanged();
    }

    public static class NodeByNodeAdapterViewHolder extends RecyclerView.ViewHolder
    {
        ItemNodeByNodeViewModel itemNodeByNodeViewModel;
        ItemNodeByNodeBinding itemNodeByNodeBinding;

        public NodeByNodeAdapterViewHolder(ItemNodeByNodeBinding itemNodeByNodeBinding) {
            super(itemNodeByNodeBinding.getRoot());
            this.itemNodeByNodeBinding = itemNodeByNodeBinding;
        }


        void bindLecture(Lecture lecture) {
            if (itemNodeByNodeBinding.getLecture() == null) {
                itemNodeByNodeBinding.setLecture
                        (new ItemNodeByNodeViewModel(itemView.getContext(), lecture));
            } else {
                itemNodeByNodeBinding.getLecture().setLecture(lecture);
            }
        }


    }
}



