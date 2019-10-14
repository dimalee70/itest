package itest.kz.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentCertificationBinding;


import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.util.EqualSpacingItemDecoration;
import itest.kz.view.activity.LectureActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.MaterialsActivity;
import itest.kz.view.activity.NodeByNodeActivity;
import itest.kz.view.adapters.EntMainAdapter;
import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.viewmodel.CertificationFragmentViewModel;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class CertificationFragment extends Fragment implements Observer
{
    public CertificationFragmentViewModel certificationFragmentViewModel;
    public FragmentCertificationBinding fragmentCertificationBinding;
    private Toolbar myToolbar;
    private ImageButton titleButton;
    private ImageButton titleButtonClose;
    private ImageButton buttonYes;
    private ImageButton buttonNo;
    private TextView dialogText;
    private String language;
    private SharedPreferences settings;
    private SubjectAdapter subjectAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fragmentCertificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_certification, container, false);
        certificationFragmentViewModel = new CertificationFragmentViewModel(getContext());
        fragmentCertificationBinding.setCertification(certificationFragmentViewModel);
        settings = getContext().getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = settings.getString(Constant.LANG, "kz");
        setUpListOfSbjectsView(fragmentCertificationBinding.listSubject);
        setUpObserver(certificationFragmentViewModel);

        return fragmentCertificationBinding.getRoot();
    }


    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof  CertificationFragmentViewModel) {
            subjectAdapter = (SubjectAdapter) fragmentCertificationBinding.listSubject
                    .getAdapter();
            CertificationFragmentViewModel certificationFragmentViewModel
                    = (CertificationFragmentViewModel) o;
            subjectAdapter.setSubjectList(certificationFragmentViewModel
                    .getSubjectList());
        }
    }



    // set up the list of user with recycler view
    private void setUpListOfSbjectsView(RecyclerView listSubject) {
        SubjectAdapter subjectAdapter = new SubjectAdapter();
        listSubject.addItemDecoration(new EqualSpacingItemDecoration(10));

//        listSubject.setLayoutManager(gridLayoutManager);



        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
//        layoutManager.setAlignItems(AlignItems.CENTER);
        listSubject.setLayoutManager(layoutManager);
        subjectAdapter.setOnItemListener(new SubjectAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(Subject item) throws CloneNotSupportedException
            {

//                ((Activity)getContext()).findViewById(R.id.activity_main_relative)
//                .setVisibility(View.INVISIBLE);
//                MaterialsActivity test = (MaterialsActivity) ((MainHomeActivity) getContext())
//                        .getSupportFragmentManager().findFragmentByTag(Constant.MATERIALS_ACTIVITY);
//
//                if (test != null)
//                {
//                }
//                else
//                {
                    FragmentManager manager = ((MainHomeActivity) getContext()).getSupportFragmentManager();
                    manager.executePendingTransactions();
                    FragmentTransaction transaction = manager.beginTransaction();

                    Fragment fragment = MaterialsActivity.newInstance(item);

                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .replace(R.id.frame, fragment, MaterialsActivity.class.getSimpleName())
                            .addToBackStack(CertificationFragment.class.getSimpleName())
                            .commit();
//                }
//                FragmentManager manager = ((MainHomeActivity) getContext()).getSupportFragmentManager();
//                manager.executePendingTransactions();
//                FragmentTransaction transaction = manager.beginTransaction();
//                Fragment fragment = MaterialsActivity.newInstance(item);
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .replace(R.id.frame, fragment)
//                        .addToBackStack(null)
//                        .commit();



//                ((MainHomeActivity)getActivity()).loadFragment(fragment);
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.viewpager, fragment)
//                        .commit();
            }
        });
        listSubject.setAdapter(subjectAdapter);

    }


//    public void clickCategory (String name) {
//        FragmentManager manager = ((MainHomeActivity) getContext()).getSupportFragmentManager();
//        manager.executePendingTransactions();
//        FragmentTransaction transaction = manager.beginTransaction();
//
//        Fragment fragment = NodeByNodeActivity.newInstance(node);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .replace(R.id.frame, fragment)
//                .addToBackStack(null)
//                .commit();
//    }

    private void changePricesInTheList(Subject item) throws CloneNotSupportedException {

        List<Subject> subjectList = subjectAdapter.getSubjectList();
        ArrayList<Subject> models = new ArrayList<>();
//        int previousPosition = 0;
//        int nextPosition;

//        subjectAdapter.setSubjectList(certificationFragmentViewModel
//                .getSubjectList());

        Integer position = null;
        for (Subject model : subjectList) {
            models.add(model.clone());
        }

        for (int i = 0; i < models.size(); i++)
        {
            if (models.get(i).isExpand() && models.get(i).getId() != item.getId())
            {
                models.get(i).setExpand(false);
            }
            if (models.get(i).getId() == item.getId())
                position = i;
        }
        if (position != null)
        {
            if (!models.get(position).isExpand())
                models.get(position).setExpand(true);
            else
                models.get(position).setExpand(false);
        }


//        System.out.println(models);



        subjectAdapter.setSubjectList(models);
//        setData(models);
    }

    public void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        certificationFragmentViewModel.reset();
    }
}
