package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import itest.kz.model.Node;
import itest.kz.model.NodeChildren;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.view.activity.MaterialsActivity;
import itest.kz.view.activity.NodeByNodeActivity;

public class ItemNodeViewModel extends BaseObservable
{
    private Context context;
    private NodeChildren node;
    private Subject subject;

    public ItemNodeViewModel(Context context, NodeChildren node)
    {
        this.context = context;
        this.node = node;
    }

    public String getTitle()
    {
        return node.getTitle();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public NodeChildren getNode() {
        return node;
    }

    public void setNode(NodeChildren node) {
        this.node = node;
        notifyChange();
    }

    public void onClick(View view)
    {


        Intent intent = new Intent(view.getContext(), NodeByNodeActivity.class);
        intent.putExtra(Constant.SELECTED_NODE, node);


        context.startActivity(intent);

    }


}
