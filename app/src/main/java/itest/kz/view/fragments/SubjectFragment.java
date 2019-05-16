package itest.kz.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentEntBinding;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.util.EqualSpacingItemDecoration;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.adapters.EntMainAdapter;
import itest.kz.viewmodel.SubjectFragmentViewModel;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class SubjectFragment extends Fragment implements Observer
{
    public SubjectFragmentViewModel subjectFragmentViewModel;
    public FragmentEntBinding fragmentEntBinding;
    private List<Subject> selectedSubects = new ArrayList<>();
    public List<Subject> subjects = new ArrayList<>();


    final android.arch.lifecycle.Observer<List<Subject>> listObserver = new android.arch.lifecycle.Observer<List<Subject>>() {
        @Override
        public void onChanged(@Nullable List<Subject> subjectList)
        {
//            subjectList = selectedSubects;
            if (subjectList != null && subjectList.size() > 0)
            {
                subjects = subjectList;
//                System.out.println("CouNt");
//                System.out.println(subjectList.toString());
            }
        }
    };

    public void setFragments()
    {

    }

    public void initContent()
    {
//        fragmentEntBinding.loader.showCustomLoading(true,  R.drawable.ic_itest_logo_larger);
        setUpListOfSbjectsMainView(fragmentEntBinding.
                listSubjectMain);
        setUpListOfSbjectsMainView(fragmentEntBinding.listSubject);

//        fragmentEntBinding.loader.showCustomLoading(false);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        selectedSubects = new ArrayList<>();

        fragmentEntBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ent, container, false);
        subjectFragmentViewModel = new SubjectFragmentViewModel(getContext());
        subjectFragmentViewModel.getTests().observe(this, listObserver);
        fragmentEntBinding.setEnt(subjectFragmentViewModel);

//        fragmentEntBinding
//                .buttonReflesh.setOnClickListener
//                (new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        System.out.println("hello");
//                    }
//                });
        initContent();

        setUpObserver(subjectFragmentViewModel);


//        fragmentEntBinding.loader.showCustomLoading(true, );


        fragmentEntBinding.entStartCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                if (selectedSubects.size() == 5)
//                {
//                    System.out.println("listOf2");
//                    System.out.println(selectedSubects.toString());
                if (selectedSubects.size() == Constant.FULL_TEST_SUBJECT_COUNT)
                {
                    Intent intent = new Intent(getContext(), FullTestActivity.class);

                    intent.putExtra(Constant.SUBJECT_LIST, (ArrayList<Subject>) selectedSubects);
//                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(getContext(),
                            "Предметов должно быть 5",
                            Toast.LENGTH_SHORT).show();
                }


//                    selectedSubects.addAll(subjectFragmentViewModel.getSubjectListMain());
//                    System.out.println(selectedSubects.toString());
//                }
            }
        });




//        }






        return fragmentEntBinding.getRoot();
    }

//    public void getListMainSubject()
//    {
//        ArrayList<Subject> subjectArrayListMain = new ArrayList<>();
//        EntMainAdapter e = (EntMainAdapter) fragmentEntBinding.listSubjectMain.getAdapter();
////        RecyclerView.Adapter e = fragmentEntBinding.listSubjectMain.getAdapter();
//        System.out.println(e.getItemCount());
//        for (int i = 0; i < e.getItemCount(); i++)
//        {
//            subjectArrayListMain.add(e.getItem(i));
//        }
//        System.out.println("ListMain");
//        System.out.println(subjectArrayListMain.toString());
//
//    }

    public List<Subject> getSelectedSubects()
    {
        return selectedSubects;
    }

    public void setSelectedSubects(List<Subject> selectedSubects)
    {
        this.selectedSubects = selectedSubects;
    }

    public void addToSelectedList(Subject subject)
    {
        fragmentEntBinding
                .entCancelCardview
                .setVisibility(View.VISIBLE);
        selectedSubects.add(subject);
        if (selectedSubects.size() == Constant.FULL_TEST_SUBJECT_COUNT)
        {
//            System.out.println("5");
            fragmentEntBinding
                    .entStartCardview
                    .setVisibility(View.VISIBLE);
        }
    }

    private void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

//    public void setClickItem()
//    {
//        fragmentEntBinding.
//    }

    private void setUpListOfSbjectsMainView(RecyclerView listSubjectMain)
    {
        EntMainAdapter entMainAdapter = new EntMainAdapter();
        listSubjectMain.setAdapter(entMainAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        listSubjectMain.addItemDecoration(new EqualSpacingItemDecoration(20));

        listSubjectMain.setLayoutManager(gridLayoutManager);

//        listNodes.setLayoutManager(new GridLayoutManager(this, 2));


//        System.out.println("ent adapter");
//        System.out.println(entMainAdapter.getSubjectListMain());

        fragmentEntBinding
                .entCancelCardview
                .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


//                    for (Subject s : selectedSubects
//                    ) {
//
//                        s.setIsSelected(0);
//                    }
//
//                    fragmentEntBinding.entStartCardview.setVisibility(View.GONE);
//
//                    subjectFragmentViewModel.setSubjectList(new ArrayList<Subject>());
//                    subjectFragmentViewModel.setSubjectListMain(new ArrayList<Subject>());
//                    selectedSubects = new ArrayList<>();
//                    setUpListOfSbjectsMainView(fragmentEntBinding.listSubject);
//                    setUpListOfSbjectsMainView(fragmentEntBinding.listSubjectMain);

//                    subjectFragmentViewModel.fetchSubjectList();
//                    System.out.println("Uodate list");
//                    System.out.println(subjectFragmentViewModel.getSubjectList());


//                    subjectFragmentViewModel.setCancelCardView(false);
                if (selectedSubects.size() > 3)
                {
                    selectedSubects.get
                            (selectedSubects.size() - 1).setIsSelected(0);
                    selectedSubects
                            .remove
                                    (selectedSubects.size() - 1);

                    if (selectedSubects.size() == Constant.FULL_TEST_SUBJECT_COUNT_ONE_CHOISE)
                    {
                        Subject item = selectedSubects
                                .get(selectedSubects.size() - 1);
//                        item.setIsSelected(0);
                        ArrayList<Subject> sublings = new ArrayList<>();
                        sublings.add(selectedSubects.get(selectedSubects.size() - 1));

                        for (Subject s : subjectFragmentViewModel
                        .getSubjectList())
                        {
                            if (item.getSublings().toString().contains(s.getAlias()) && s != item
                                    && s.getIsSelected() != 1
                            ) {
                                sublings.add(s);
                            }
                        }
                        entMainAdapter.setSubjectList(sublings);
                    }
                    else
                    {
                        ArrayList<Subject> sublings = new ArrayList<>();
//                        sublings.add(selectedSubects.get(selectedSubects.size() - 1));

                        for (Subject s : subjectFragmentViewModel
                                .getSubjectList())
                        {
                            if (!s.isMain())
                            {
                                s.setIsSelected(0);
                                sublings.add(s);
                            }

                        }
                        entMainAdapter.setSubjectList(sublings);
                        fragmentEntBinding
                                .entCancelCardview
                                .setVisibility(View.GONE);


                    }

                    System.out.println(selectedSubects);

                    fragmentEntBinding
                            .entStartCardview
                            .setVisibility(View.GONE);

                }



            }
        });

//        entMainAdapter
//                .setOnItemListener(new EntMainAdapter.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(Subject item, List<Subject> subjects, int i) {
//
//                List<Subject> sublings = new ArrayList<>();
//
//                for (Subject s : subjects) {
//                    if (s.getIsSelected() == 1) {
//                        sublings.add(s);
//                    }
//                }
//
//                if (!item.isMain &&
//                        sublings.size() < Constant.CHOISE_SUBJECT_COUNT
//                )
//                {
//                    subjectFragmentViewModel.setCancelCardView(true);
//
//                    if (getSelectedSubects().size() == Constant.FULL_TEST_SUBJECT_COUNT_NO_CHOISE)
//                    {
//                        if (item.getIsSelected() != 1)
//                        {
//
//                            sublings.add(item);
//                            item.setIsSelected(1);
//                            addToSelectedList(item);
//
//                        }
//
//
//
//
//                        for (Subject s : subjects) {
//                            if (item.getSublings().toString().contains(s.getAlias()) && s != item
//                                    && s.getIsSelected() != 1
//                            ) {
//                                sublings.add(s);
//                            }
//                        }
//
//                        entMainAdapter.setSubjectList(sublings);
//
//                    }
//                    else if (getSelectedSubects().size() == Constant.FULL_TEST_SUBJECT_COUNT_ONE_CHOISE)
//                    {
//                        sublings.clear();
////
//                        if (item.getIsSelected() != 1) {
////
//                            sublings.add(getSelectedSubects().get(getSelectedSubects().size() - 1));
//                            item.setIsSelected(1);
//                            addToSelectedList(item);
//                            sublings.add(item);
//
//                            entMainAdapter.setSubjectList(sublings);
//                        }
//                    }
//                }
//
//
//            }
//
//        });
    }



    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof SubjectFragmentViewModel) {
            EntMainAdapter entMainAdapter = (EntMainAdapter) fragmentEntBinding
                    .listSubjectMain
                    .getAdapter();
            SubjectFragmentViewModel subjectFragmentViewModel
                    = (SubjectFragmentViewModel) o;

            entMainAdapter.setSubjectListMain(subjectFragmentViewModel
                    .getSubjectListMain());
//            selectedSubects = subjectFragmentViewModel.getSubjectListMain();
//            System.out.println("selected");
//            System.out.println(entMainAdapter.getSubjectListMain().get(0).title);
            selectedSubects.addAll(entMainAdapter.getSubjectListMain());
//
            EntMainAdapter entMainAdapter2 = (EntMainAdapter) fragmentEntBinding
                    .listSubject
                    .getAdapter();
            entMainAdapter2.setSubjectListMain(subjectFragmentViewModel
            .getSubjectList());

        }
    }

    //    private
}
