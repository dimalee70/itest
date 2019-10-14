package itest.kz.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import itest.kz.databinding.FragmentLectureStatisticBinding;
import itest.kz.model.LectureStatisticResponse;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.view.adapters.LectureStatisticAdapter;
import itest.kz.viewmodel.LectureStatisticViewModel;

public class LectureStatisticFragment extends Fragment implements Observer
{
    private FragmentLectureStatisticBinding fragmentLectureStatisticBinding;
    private LectureStatisticViewModel lectureStatisticViewModel;
    private LectureStatisticAdapter lectureStatisticAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        fragmentLectureStatisticBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_lecture_statistic,
                        container, false);
        lectureStatisticViewModel =
                new LectureStatisticViewModel(getContext());
        fragmentLectureStatisticBinding
                .setLecture(lectureStatisticViewModel);
        setUpListOfSbjectsView(fragmentLectureStatisticBinding.listLectureStatistic);
        setUpObserver(lectureStatisticViewModel);
        return fragmentLectureStatisticBinding.getRoot();
    }

    private void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    private void setUpListOfSbjectsView(RecyclerView listLectureStatistic)
    {
        lectureStatisticAdapter =
                new LectureStatisticAdapter();
        listLectureStatistic.setAdapter(lectureStatisticAdapter);
        listLectureStatistic.setLayoutManager(new LinearLayoutManager(getContext()));
        lectureStatisticAdapter.setOnItemListener(
                new LectureStatisticAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(LectureStatisticResponse lectureStatisticResponses, int i) throws CloneNotSupportedException
                    {
                        if (!lectureStatisticResponses.isExpand())
                        {
                            lectureStatisticResponses.setExpand(true);
                        }
                        else
                            lectureStatisticResponses.setExpand(false);
                        lectureStatisticAdapter.notifyItemChanged(i);
                    }
                }
//                new LectureStatisticAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(LectureStatisticResponse lectureStatisticResponses) throws CloneNotSupportedException
//            {
////                changePricesInTheList(lectureStatisticResponses);
//                if (!lectureStatisticResponses.isExpand())
//                {
//                    lectureStatisticResponses.setExpand(true);
//                }
//            }
//        }
        );
    }

    private void changePricesInTheList(LectureStatisticResponse lectureStatisticResponses) throws CloneNotSupportedException {
//        List<LectureStatisticResponse> lectureStatisticResponseList =
//                lectureStatisticAdapter.getLectureStatisticResponses();
//        ArrayList<LectureStatisticResponse> models = new ArrayList<>();
//        int previousPosition = 0;
//        int nextPosition;

//        subjectAdapter.setSubjectList(certificationFragmentViewModel
//                .getSubjectList());

//        Integer position = null;
//        for (LectureStatisticResponse model : lectureStatisticResponseList) {
//            models.add(model.clone());
//        }
//
//        for (int i = 0; i < models.size(); i++)
//        {
//            if (models.get(i).isExpand() && models.get(i).getSubject().getId() !=
//                    lectureStatisticResponses.getSubject().getId())
//            {
//                models.get(i).setExpand(false);
//            }
//            if (models.get(i).getSubject().getId() == lectureStatisticResponses.getSubject().getId())
//                position = i;
//        }
//        if (position != null)
//        {
//            if (!models.get(position).isExpand())
//                models.get(position).setExpand(true);
//            else
//                models.get(position).setExpand(false);
//        }


//        System.out.println(models);



//        lectureStatisticAdapter.setLectureStatisticResponses(models);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof LectureStatisticViewModel)
        {
            LectureStatisticAdapter lectureStatisticAdapter =
                    (LectureStatisticAdapter)
                    fragmentLectureStatisticBinding.listLectureStatistic
                    .getAdapter();
            LectureStatisticViewModel lectureStatisticViewModel =
                    (LectureStatisticViewModel) o;
            lectureStatisticAdapter.setLectureStatisticResponses
                    (lectureStatisticViewModel.getLectureStatisticResponseList());
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        lectureStatisticViewModel.reset();
    }
}
