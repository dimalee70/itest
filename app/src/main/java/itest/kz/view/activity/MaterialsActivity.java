package itest.kz.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.ActivityMaterialsBinding;
import itest.kz.model.Node;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.adapters.MaterialsAdapter;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.viewmodel.MaterialsViewModel;

public class MaterialsActivity extends AppCompatActivity implements Observer
{

    private ActivityMaterialsBinding activityMaterialsBinding;
    private Subject subject;
    private MaterialsViewModel materialsViewModel;
    private MaterialsAdapter materialsAdapter;
    private List<Node> nodeList;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.activityMaterialsBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_materials);
        getExtrasFromIntent();

//        System.out.println(subject);

        materialsViewModel = new MaterialsViewModel(this, subject);
        activityMaterialsBinding.setMaterials(materialsViewModel);

        setUpListOfNodesView(activityMaterialsBinding.materialList);
        setUpObserver(materialsViewModel);




    }
    private void setUpListOfUsersView(RecyclerView materialList)
    {
        if (nodeList != null) {
            MaterialsAdapter materialsAdapter = new MaterialsAdapter();

            materialList.setLayoutManager(new GridLayoutManager(this, 2));
            materialList.setAdapter(materialsAdapter);
        }
    }

    public static Intent fillSelectedSubject(Context context, Subject subject)
    {
        Intent intent = new Intent(context, MaterialsActivity.class);
        intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) subject);

    return intent;
    }

    private void getExtrasFromIntent()
    {
        this.subject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
//        MaterialsViewModel materialsViewModel = new MaterialsViewModel(getApplication(), subject);
//        activityMaterialsBinding.setMaterials(materialsViewModel);
    //        activityTestBinding.setTest(testViewModel);

    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof MaterialsViewModel)
        {
            MaterialsAdapter materialsAdapter = (MaterialsAdapter) activityMaterialsBinding
                    .materialList.getAdapter();
            MaterialsViewModel materialsViewModel =
                    (MaterialsViewModel) o;
            materialsAdapter.setListNode(materialsViewModel.getNodeList());

        }
    }

    public void  setUpListOfNodesView(RecyclerView listNodes)
    {
        MaterialsAdapter materialsAdapter = new MaterialsAdapter();
        listNodes.setAdapter(materialsAdapter);
        listNodes.setLayoutManager(new GridLayoutManager(this, 2));

    }

    public void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        materialsViewModel.reset();
    }

//    public void setR(RecyclerView recyclerView)
//    {
//        materialsViewModel = new MaterialsViewModel(this,subject);
//        activityMaterialsBinding.setMaterials(materialsViewModel);
//        MaterialsAdapter materialsAdapter = new MaterialsAdapter(materialsViewModel.getTests(), this);
//        recyclerView.setAdapter(resultAdapter);
//
//
//    }
}
