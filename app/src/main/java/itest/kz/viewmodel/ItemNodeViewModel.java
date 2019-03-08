package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import itest.kz.model.Node;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.view.activity.MaterialsActivity;
import itest.kz.view.activity.NodeByNodeActivity;

public class ItemNodeViewModel extends BaseObservable
{
    private Context context;
    private Node node;
    private Subject subject;

    public ItemNodeViewModel(Context context, Node node)
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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
        notifyChange();
    }

    public void onClick(View view)
    {
//
//        System.out.println("Hello");
//        System.out.println(node);
        Intent intent = new Intent(view.getContext(), NodeByNodeActivity.class);
//////        System.out.println("Subject");
//////        System.out.println(node.toString());
        intent.putExtra(Constant.SELECTED_NODE, node);

        context.startActivity(intent);
//        context.
//        view.getContext().s
    }


}
