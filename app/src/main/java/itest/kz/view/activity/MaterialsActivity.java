package itest.kz.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityMaterialsBinding;
import itest.kz.model.Node;
import itest.kz.model.ProfileInfo;
import itest.kz.model.ProfileResponse;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.network.UserService;
import itest.kz.util.Constant;
import itest.kz.util.EqualSpacingItemDecoration;
import itest.kz.view.adapters.MaterialsAdapter;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.view.fragments.CertificationFragment;
import itest.kz.viewmodel.MaterialsViewModel;

import static android.content.Context.MODE_PRIVATE;
import static itest.kz.util.AppUtils.setCheckable;

public class MaterialsActivity extends Fragment implements Observer
{

    private ActivityMaterialsBinding activityMaterialsBinding;
    private Subject subject;
    private MaterialsViewModel materialsViewModel;
    private MaterialsAdapter materialsAdapter;
    private List<Node> nodeList;
    private Toolbar toolbarMaterials;
    private TextView mainToolbarText;
    private BottomNavigationViewEx bottomNavigationView;
    private String accessToken;
    private boolean isstartedFirst = true;
    private String prefiousActivity;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public static MaterialsActivity newInstance(Subject subject) {

        Bundle args = new Bundle();
        args.putSerializable(Constant.SELECTED_SUBJECT, subject);
        MaterialsActivity fragment = new MaterialsActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        getExtrasFromIntent();
        this.subject = (Subject) getArguments().getSerializable(Constant.SELECTED_SUBJECT);

        activityMaterialsBinding = DataBindingUtil.inflate(inflater, R.layout.activity_materials,
                container, false);
        materialsViewModel = new MaterialsViewModel(getContext(), subject);
        activityMaterialsBinding.setMaterials(materialsViewModel);
        setMyToolbar();
        setUpListOfNodesView(activityMaterialsBinding.materialList);
        setUpObserver(materialsViewModel);
        SharedPreferences accessTok = getActivity().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");

        return activityMaterialsBinding.getRoot();
    }


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        getExtrasFromIntent();
//        this.activityMaterialsBinding =
//                DataBindingUtil.setContentView(this, R.layout.activity_materials);
//
//
////        System.out.println(subject);
//
//        materialsViewModel = new MaterialsViewModel(this, subject);
//        activityMaterialsBinding.setMaterials(materialsViewModel);
//
//        setMyToolbar();
//        setUpListOfNodesView(activityMaterialsBinding.materialList);
//        setUpObserver(materialsViewModel);
//        SharedPreferences accessTok = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
////        settings.edit().clear().commit();
//        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
//        bottomNavigationView = activityMaterialsBinding.bottomNavigationView;
//        bottomNavigationView.enableAnimation(false);
//        bottomNavigationView.setLabelVisibilityMode(1);
//        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
//
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
//        setCheckable(bottomNavigationView, false,0);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
//                    {
//                        Intent intent = null;
//                        switch (menuItem.getItemId())
//                        {
//                            case R.id.item_test:
////                                if(!isstartedFirst) {
////                                if (prefiousActivity != null && prefiousActivity.equals(Constant.SUBJECT_ACTIVITY))
////                                {
////                                    MaterialsActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(MaterialsActivity.this, SubjectActivity.class);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                }
////                                }
////                                isstartedFirst = false;
//                                break;
//                            case R.id.item_statistic:
//                                finish();
////                                if (prefiousActivity != null && prefiousActivity.equals(Constant.STATISTIC_ACTIVITY))
////                                {
////                                    MaterialsActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(MaterialsActivity.this, StatisticActivity.class);
//                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.SUBJECT_ACTIVITY);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                }
//                                break;
//                            case R.id.item_user:
////                                fetchProfileInfo();
//                                finish();
////                                if (prefiousActivity != null && prefiousActivity.equals(Constant.PROFILE_ACTIVITY))
////                                {
////                                    MaterialsActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(MaterialsActivity.this, HomeActivity.class);
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
//
//
//
//
//    }
//    private void setUpListOfUsersView(RecyclerView materialList)
//    {
//        if (nodeList != null) {
//            MaterialsAdapter materialsAdapter = new MaterialsAdapter(materialsViewModel.getSubject());
//
//            materialList.setLayoutManager(new GridLayoutManager(this, 2));
//            materialList.setAdapter(materialsAdapter);
//        }
//    }

    public static Intent fillSelectedSubject(Context context, Subject subject)
    {
        Intent intent = new Intent(context, MaterialsActivity.class);
        intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) subject);
//        System.out.println(subject);

    return intent;
    }
//
//    private void getExtrasFromIntent()
//    {
//
////        System.out.println(subject);
////        MaterialsViewModel materialsViewModel = new MaterialsViewModel(getApplication(), subject);
////        activityMaterialsBinding.setMaterials(materialsViewModel);
//    //        activityTestBinding.setTest(testViewModel);
//
//    }

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
        MaterialsAdapter materialsAdapter = new MaterialsAdapter(subject);
        listNodes.setAdapter(materialsAdapter);
        listNodes.addItemDecoration(new EqualSpacingItemDecoration(14));

        listNodes.setLayoutManager(new GridLayoutManager(getContext(), 2));
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

    public void setMyToolbar()
    {
        toolbarMaterials = activityMaterialsBinding
                .toolbarMaterials;
        mainToolbarText = activityMaterialsBinding
                .toolbarTitle;
//        mainToolbarText.setTextColor(Color.WHITE);
        toolbarMaterials.setTitle("");
        toolbarMaterials.setNavigationIcon(R.drawable.ic_navigation_strelka);
        toolbarMaterials.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack(CertificationFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }


}
