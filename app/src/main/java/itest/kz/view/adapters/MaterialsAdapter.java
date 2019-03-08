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
import itest.kz.viewmodel.ItemNodeViewModel;
import itest.kz.viewmodel.ItemQuestionsViewModel;
import itest.kz.viewmodel.MaterialsViewModel;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MaterialsAdpterViewHolder>
{

    private List<Node> listNode;
    private Context context;
    private ItemNodeBinding itemNodeBinding;

//    public MaterialsAdapter(List<Node> listNode, Context context)
//    {
//        this.listNode = listNode;
//        this.context = context;
//    }

    @NonNull
    @Override
    public MaterialsAdpterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemNodeBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_node, viewGroup, false);

        return new MaterialsAdpterViewHolder(itemNodeBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull MaterialsAdpterViewHolder materialsAdpterViewHolder, int i)
    {
        materialsAdpterViewHolder.bindNode(listNode.get(i));
//        materialsAdpterViewHolder.bindNode(node);
    }

    @Override
    public int getItemCount()
    {
        if (listNode != null)
            return listNode.size();
        return 0;
    }

    public List<Node> getListNode() {
        return listNode;
    }

    public void setListNode(List<Node> listNode) {
        this.listNode = listNode;
        notifyDataSetChanged();
    }

    public static class MaterialsAdpterViewHolder extends RecyclerView.ViewHolder
    {

        ItemNodeViewModel itemNodeViewModel;
        ItemNodeBinding itemNodeBinding;

        public MaterialsAdpterViewHolder(ItemNodeBinding itemNodeBinding) {
            super(itemNodeBinding.getRoot());
            this.itemNodeBinding = itemNodeBinding;
        }


        void bindNode(Node node) {
            if (itemNodeBinding.getNode() == null) {
                itemNodeBinding.setNode
                        (new ItemNodeViewModel(itemView.getContext(), node));
            } else {
                itemNodeBinding.getNode().setNode(node);
            }
        }
    }
}
