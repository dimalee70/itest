package itest.kz.view.fragments;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentEntBinding;
import itest.kz.model.Subject;
import itest.kz.view.adapters.EntMainAdapter;
import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.viewmodel.CertificationFragmentViewModel;
import itest.kz.viewmodel.SubjectFragmentViewModel;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

public class SubjectFragment extends Fragment implements Observer
{
    public SubjectFragmentViewModel subjectFragmentViewModel;
    public FragmentEntBinding fragmentEntBinding;
    private List<Subject> selectedSubects = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        selectedSubects = new ArrayList<>();
        fragmentEntBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ent, container, false);
        subjectFragmentViewModel = new SubjectFragmentViewModel(getContext());
        fragmentEntBinding.setEnt(subjectFragmentViewModel);

        setUpListOfSbjectsMainView(fragmentEntBinding.listSubjectMain);
        setUpListOfSbjectsMainView(fragmentEntBinding.listSubject);

        setUpObserver(subjectFragmentViewModel);

        return fragmentEntBinding.getRoot();
    }

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
//        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), HORIZONTAL);
//        itemDecor
//        listSubjectMain.addItemDecoration(itemDecor);
        listSubjectMain.setAdapter(entMainAdapter);
        listSubjectMain.setLayoutManager(new LinearLayoutManager(getContext()));

        entMainAdapter.setOnItemListener(new EntMainAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(Subject item, List<Subject> subjects)
            {
//                System.out.println(item.getSublings().toString());

//                System.out.println(item.getSublings().toString());
                List<Subject> sublings = new ArrayList<>();
                sublings.add(item);

                if (getSelectedSubects().size() < 2) {
                    item.setIsSelected(1);
                    addToSelectedList(item);
//                else
//                {
//                    System.out.println("selected list");
//                    System.out.println(getSelectedSubects().toString());
//                }


                    for (Subject s : subjects) {
                        if (item.getSublings().toString().contains(s.getAlias()) && s != item) {
                            sublings.add(s);
                        }
                    }

//                System.out.println("List");
//                System.out.println(sublings.toString());

                    setUpListOfSbjectsMainView(fragmentEntBinding.listSubject);

                    EntMainAdapter entMainAdapter2 = (EntMainAdapter) fragmentEntBinding.listSubject
                            .getAdapter();
                    entMainAdapter2.setSubjectListMain(sublings);
                }



//                updateDataList(sublings);
//
//                System.out.println("List");
//                System.out.println(subjects.toString());
            }
//            @Override
//            public void onItemClick(Subject item)
//            {
//                if (!(item.isMain))
//                {
//                    System.out.println("Click on ent subject Item");
//                }
//            }
        });
    }

//    private void updateDataList(List<Subject> sublings)
//    {
//
//    }


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
//
            EntMainAdapter entMainAdapter2 = (EntMainAdapter) fragmentEntBinding.listSubject
                    .getAdapter();
            entMainAdapter2.setSubjectListMain(subjectFragmentViewModel
            .getSubjectList());


//            EntMainAdapter entMainAdapter = (EntMainAdapter) fragmentEntBinding.listSubject
//                    .getAdapter();
//            SubjectFragmentViewModel subjectFragmentViewModel
//                    = (SubjectFragmentViewModel) o;
//            entMainAdapter.setSubjectList(subjectFragmentViewModel
//                    .getSubjectList());
        }
    }

    //    private
}
