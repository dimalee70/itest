package itest.kz.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.ActivityNodeByNodeBinding;
import itest.kz.model.Lecture;
import itest.kz.model.Node;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.view.adapters.MaterialsAdapter;
import itest.kz.view.adapters.NodeByNodeAdapter;
import itest.kz.viewmodel.MaterialsViewModel;
import itest.kz.viewmodel.NodeByNodeViewModel;

public class NodeByNodeActivity extends AppCompatActivity implements Observer
{
    private ActivityNodeByNodeBinding activityNodeByNodeBinding;
    private NodeByNodeViewModel nodeByNodeViewModel;
    private Node node;
    private List<Lecture> lectures;
    private NodeByNodeAdapter nodeByNodeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.activityNodeByNodeBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_node_by_node);
        getExtrasFromIntent();
        nodeByNodeViewModel = new NodeByNodeViewModel(this, node);
        activityNodeByNodeBinding.setNode(nodeByNodeViewModel);

        setUpListOfNodesView(activityNodeByNodeBinding.lecturesList);
        setUpObserver(nodeByNodeViewModel);

    }

    private void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    private void setUpListOfNodesView(RecyclerView lecturesList)
    {
        if (lecturesList != null) {
            NodeByNodeAdapter nodeByNodeAdapter = new NodeByNodeAdapter();
            lecturesList.setLayoutManager(new LinearLayoutManager(this));
            lecturesList.setAdapter(nodeByNodeAdapter);
        }
    }

    private void getExtrasFromIntent()
    {
        this.node = (Node) getIntent().getSerializableExtra(Constant.SELECTED_NODE);
    }

    public static Intent fillSelectedSubject(Context context, Node node)
    {
        Intent intent = new Intent(context, MaterialsActivity.class);
        intent.putExtra(Constant.SELECTED_NODE, node);

        return intent;
    }
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof NodeByNodeViewModel)
        {
            NodeByNodeAdapter nodeByNodeAdapter = (NodeByNodeAdapter) activityNodeByNodeBinding
                    .lecturesList.getAdapter();
            NodeByNodeViewModel nodeByNodeViewModel =
                    (NodeByNodeViewModel) o;
            nodeByNodeAdapter.setLectureList(nodeByNodeViewModel.getLectureList());

        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        nodeByNodeViewModel.reset();
    }


}
