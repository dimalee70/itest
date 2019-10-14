package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ActivityMaterialsBinding;
import itest.kz.databinding.ItemNodeBinding;
import itest.kz.databinding.ItemQuestionsBinding;
import itest.kz.model.Answer;
import itest.kz.model.Node;
import itest.kz.model.NodeChildren;
import itest.kz.model.Subject;
import itest.kz.viewmodel.ItemNodeViewModel;
import itest.kz.viewmodel.ItemQuestionsViewModel;
import itest.kz.viewmodel.MaterialsViewModel;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MaterialsAdpterViewHolder>
{

    private List<NodeChildren> listNode;
    private Context context;
    private ItemNodeBinding itemNodeBinding;
    public Subject subject;

    public MaterialsAdapter(Subject subject)
    {
        this.subject = subject;
    }

    @NonNull
    @Override
    public MaterialsAdpterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemNodeBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_node, viewGroup, false);

        return new MaterialsAdpterViewHolder(itemNodeBinding, subject);

    }

    @Override
    public void onBindViewHolder(@NonNull MaterialsAdpterViewHolder materialsAdpterViewHolder, int i)
    {
        materialsAdpterViewHolder.bindNode(listNode.get(i));
    }

    @Override
    public int getItemCount()
    {
        if (listNode != null)
            return listNode.size();
        return 0;
    }

    public List<NodeChildren> getListNode() {
        return listNode;
    }

    public void setListNode(List<NodeChildren> listNode) {
        this.listNode = listNode;
//        this.subject = subject;
        notifyDataSetChanged();
    }

    public static class MaterialsAdpterViewHolder extends RecyclerView.ViewHolder
    {

        ItemNodeViewModel itemNodeViewModel;
        ItemNodeBinding itemNodeBinding;
        private Subject subject;

        public MaterialsAdpterViewHolder(ItemNodeBinding itemNodeBinding, Subject subject)
        {
            super(itemNodeBinding.getRoot());
            this.itemNodeBinding = itemNodeBinding;
            this.subject = subject;
        }


        void bindNode(NodeChildren node) {
            if (itemNodeBinding.getNode() == null) {
                itemNodeBinding.setNode
                        (new ItemNodeViewModel(itemView.getContext(), node, subject));
            } else {
                itemNodeBinding.getNode().setNode(node);
            }
        }
    }
}
