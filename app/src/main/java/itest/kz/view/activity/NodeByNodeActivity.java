package itest.kz.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.ActivityNodeByNodeBinding;
import itest.kz.model.Lecture;
import itest.kz.model.Node;
import itest.kz.model.NodeChildren;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.view.adapters.MaterialsAdapter;
import itest.kz.view.adapters.NodeByNodeAdapter;
import itest.kz.viewmodel.MaterialsViewModel;
import itest.kz.viewmodel.NodeByNodeViewModel;

import static android.content.Context.MODE_PRIVATE;

public class NodeByNodeActivity extends Fragment implements Observer
{
    private ActivityNodeByNodeBinding activityNodeByNodeBinding;
    private NodeByNodeViewModel nodeByNodeViewModel;
    private NodeChildren node;
    private Toolbar toolbarNode;
    private TextView mainToolbarText;
    private String accessToken;
    private BottomNavigationViewEx bottomNavigationView;
    private boolean isStartedFirst = true;
    private String previousActivity;
    private NodeByNodeAdapter nodeByNodeAdapter;

    public static NodeByNodeActivity newInstance(NodeChildren node) {

        Bundle args = new Bundle();
        args.putSerializable(Constant.SELECTED_NODE, node);
        NodeByNodeActivity fragment = new NodeByNodeActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        this.node = (NodeChildren) getArguments().getSerializable(Constant.SELECTED_NODE);;
        activityNodeByNodeBinding = DataBindingUtil.inflate(inflater, R.layout.activity_node_by_node, container, false);
        nodeByNodeViewModel = new NodeByNodeViewModel(getContext(), node);
        activityNodeByNodeBinding.setNode(nodeByNodeViewModel);
        activityNodeByNodeBinding.viewDisableLayout123.setVisibility(View.GONE);
        setMyToolbar();
        setUpListOfNodesView(activityNodeByNodeBinding.lecturesList);
        setUpObserver(nodeByNodeViewModel);
        SharedPreferences accessTok = getActivity().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
        return activityNodeByNodeBinding.getRoot();
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        this.activityNodeByNodeBinding =
//                DataBindingUtil.setContentView(this, R.layout.activity_node_by_node);
//        getExtrasFromIntent();
//        nodeByNodeViewModel = new NodeByNodeViewModel(this, node);
//        activityNodeByNodeBinding.setNode(nodeByNodeViewModel);
//
//        setMyToolbar();
//        setUpListOfNodesView(activityNodeByNodeBinding.lecturesList);
//        setUpObserver(nodeByNodeViewModel);
//        SharedPreferences accessTok = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
//        bottomNavigationView = activityNodeByNodeBinding
//                .bottomNavigationView;
//        bottomNavigationView.enableAnimation(false);
//        bottomNavigationView.setLabelVisibilityMode(1);
//        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
//
//        final View iconView = menuView.getChildAt(0).findViewById(android.support.design.R.id.icon);
//        final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
//        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics);
//        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
//        iconView.setLayoutParams(layoutParams);
//
//        final View iconView1 = menuView.getChildAt(2).findViewById(android.support.design.R.id.icon);
//        final ViewGroup.LayoutParams layoutParams1 = iconView.getLayoutParams();
//        final DisplayMetrics displayMetrics1 = getResources().getDisplayMetrics();
//        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics1);
//        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics1);
//        iconView1.setLayoutParams(layoutParams1);
//
//        bottomNavigationView.setSelectedItemId(R.id.item_test);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
//                    {
//                        Intent intent = null;
//                        switch (menuItem.getItemId())
//                        {
//                            case R.id.item_test:
////                                if (!isStartedFirst)
////                                {
////                                if (previousActivity != null && previousActivity.equals(Constant.SUBJECT_ACTIVITY))
////                                {
////                                    NodeByNodeActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(NodeByNodeActivity.this, SubjectActivity.class);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                }
////                                }
////                                isStartedFirst = false;
//
//                                break;
//                            case R.id.item_statistic:
////                                finish();
////                                if (previousActivity != null && previousActivity.equals(Constant.STATISTIC_ACTIVITY))
////                                {
////                                    NodeByNodeActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(NodeByNodeActivity.this, StatisticActivity.class);
//                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.SUBJECT_ACTIVITY);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                }
//                                break;
//                            case R.id.item_user:
////                                fetchProfileInfo();
////                                finish();
////                                if (previousActivity != null && previousActivity.equals(Constant.PROFILE_ACTIVITY))
////                                {
////                                    NodeByNodeActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(NodeByNodeActivity.this, HomeActivity.class);
//                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.SUBJECT_ACTIVITY);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                fragmentManager.beginTransaction().replace(R.id.viewpager, fragment).commit();
////                                }
//                                break;
//
//                        }
//
//
//                        return true;
//                    }
//                }
//        );
////        bottomNavigationView.setSelectedItemId(R.id.item_test);
//
//    }

    private void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    private void setUpListOfNodesView(RecyclerView lecturesList)
    {
        if (lecturesList != null) {
            nodeByNodeAdapter = new NodeByNodeAdapter();
            lecturesList.setLayoutManager(new LinearLayoutManager(getContext()));
            lecturesList.setAdapter(nodeByNodeAdapter);
        }
    }

//    private void getExtrasFromIntent()
//    {
//        this.node = (NodeChildren) getIntent().getSerializableExtra(Constant.SELECTED_NODE);
//    }

//    public static Intent fillSelectedSubject(Context context, Node node)
//    {
//        Intent intent = new Intent(context, MaterialsActivity.class);
//        intent.putExtra(Constant.SELECTED_NODE, node);
//
//        return intent;
//    }
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

    public void setMyToolbar()
    {
        toolbarNode = (Toolbar) activityNodeByNodeBinding
                .toolbarNodeByNode;
        mainToolbarText = (TextView) activityNodeByNodeBinding
                .toolbarTitle;
//        mainToolbarText.setTextColor(Color.WHITE);
        toolbarNode.setTitle("");
        toolbarNode.setNavigationIcon(R.drawable.ic_navigation_strelka);
        toolbarNode.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().popBackStack(MaterialsActivity.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onResume()
    {
        activityNodeByNodeBinding.viewDisableLayout123.setVisibility(View.GONE);
        super.onResume();



    }
}
