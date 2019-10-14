package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.icu.util.ULocale;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import itest.kz.R;
import itest.kz.model.Node;
import itest.kz.model.NodeChildren;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.MaterialsActivity;
import itest.kz.view.activity.NodeByNodeActivity;

import static android.content.Context.MODE_PRIVATE;

public class ItemNodeViewModel extends BaseObservable
{
    private Context context;
    private NodeChildren node;
    private Subject subject;
    private String language;

    public ItemNodeViewModel(Context context, NodeChildren node, Subject subject)
    {
        this.context = context;
        this.node = node;
        this.subject = subject;
        SharedPreferences settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
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

    public int getBackgroundColor()
    {
        return Color.parseColor(subject.getColorBg());
    }

    public void onClick(View view)
    {


//        Fragment fragment = NodeByNodeActivity.newInstance(node);
//        System.out.println("CreateView");
        clickCategory();
//        ((MainHomeActivity)(Activity)context).loadFragment(fragment);
//        Intent intent = new Intent(view.getContext(), NodeByNodeActivity.class);
//        intent.putExtra(Constant.SELECTED_NODE, node);

//
//        context.startActivity(intent);

    }

    public void clickCategory () {
        FragmentManager manager = ((MainHomeActivity) getContext()).getSupportFragmentManager();
        manager.executePendingTransactions();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment = NodeByNodeActivity.newInstance(node);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.frame2, fragment, NodeByNodeActivity.class.getSimpleName())
                .addToBackStack(MaterialsActivity.class.getSimpleName())
                .commit();
    }

    public String getLectureCount()
    {
        if (language.equals(Constant.RU))
        {
            if (node.getLectureCount() == 1)
                return node.getLectureCount() + " конспект";
            else if (
                    node.getLectureCount() == 2
                    || node.getLectureCount() == 3
                    || node.getLectureCount() == 4
//                    lastDigit(node.getLectureCount()
//                    )
                )
                return node.getLectureCount() + " конспекта";
            else
                return node.getLectureCount() + " конспектов";

        }
        return node.getLectureCount() + " конспект";


    }
    public boolean lastDigit(int number) {
        return (number % 10 == 2)
                || (number % 10 == 3
                || (number % 10 == 4));
    }

}
