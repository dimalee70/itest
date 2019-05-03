package itest.kz.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.adapters.EntMainAdapter;
import itest.kz.viewmodel.SubjectFragmentViewModel;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class SubjectFragment extends Fragment implements Observer
{
    public SubjectFragmentViewModel subjectFragmentViewModel;
    public FragmentEntBinding fragmentEntBinding;
    private List<Subject> selectedSubects = new ArrayList<>();


    final android.arch.lifecycle.Observer<List<Subject>> listObserver = new android.arch.lifecycle.Observer<List<Subject>>() {
        @Override
        public void onChanged(@Nullable List<Subject> subjectList)
        {
//            subjectList = selectedSubects;
            if (subjectList != null && subjectList.size() > 0)
            {
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
        setUpListOfSbjectsMainView(fragmentEntBinding.listSubjectMain);
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
                if (selectedSubects.size() == 5)
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
        selectedSubects.add(subject);
        if (selectedSubects.size() == 5)
        {
//            System.out.println("5");
            fragmentEntBinding.entStartCardview.setVisibility(View.VISIBLE);
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
        listSubjectMain.setLayoutManager(new LinearLayoutManager(getContext()));


//        System.out.println("ent adapter");
//        System.out.println(entMainAdapter.getSubjectListMain());

        fragmentEntBinding.entCancelCardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                for (Subject s : selectedSubects
                     ) {

                    s.setIsSelected(0);
                }

                fragmentEntBinding.entStartCardview.setVisibility(View.GONE);

                subjectFragmentViewModel.setSubjectList(new ArrayList<Subject>());
                subjectFragmentViewModel.setSubjectListMain(new ArrayList<Subject>());
                selectedSubects = new ArrayList<>();
                setUpListOfSbjectsMainView(fragmentEntBinding.listSubject);
                setUpListOfSbjectsMainView(fragmentEntBinding.listSubjectMain);
                subjectFragmentViewModel.fetchSubjectList();
                subjectFragmentViewModel.setCancelCardView(false);


            }
        });

        entMainAdapter.setOnItemListener(new EntMainAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(Subject item, List<Subject> subjects) {
//                System.out.println(item.getSublings().toString());

//                System.out.println(item.getSublings().toString());
                if (!item.isMain) {
                    List<Subject> sublings = new ArrayList<>();
                    sublings.add(item);

                    subjectFragmentViewModel.setCancelCardView(true);

                    if (getSelectedSubects().size() < 5)
                    {

                        if (item.getIsSelected() != 1)
                        {
                            item.setIsSelected(1);
                            addToSelectedList(item);
                        }



                        for (Subject s : subjects) {
                            if (item.getSublings().toString().contains(s.getAlias()) && s != item) {
                                sublings.add(s);
                            }
                        }


                        setUpListOfSbjectsMainView(fragmentEntBinding.listSubject);

                        EntMainAdapter entMainAdapter2 = (EntMainAdapter) fragmentEntBinding.listSubject
                                .getAdapter();
                        entMainAdapter2.setSubjectListMain(sublings);
                    }


                }
            }

        });
    }



    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof SubjectFragmentViewModel) {
            EntMainAdapter entMainAdapter = (EntMainAdapter) fragmentEntBinding.listSubjectMain
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
            EntMainAdapter entMainAdapter2 = (EntMainAdapter) fragmentEntBinding.listSubject
                    .getAdapter();
            entMainAdapter2.setSubjectListMain(subjectFragmentViewModel
            .getSubjectList());

        }
    }

    //    private
}
