package itest.kz.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemQuestionsBinding;
import itest.kz.databinding.ItemResultBinding;
import itest.kz.databinding.ItemSubjectBinding;
import itest.kz.model.Answer;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.FulltestResultActivity;
import itest.kz.view.activity.TestActivity;
import itest.kz.viewmodel.ItemQuestionsViewModel;
import itest.kz.viewmodel.ItemResultViewModel;
import itest.kz.viewmodel.ItemSubjectFragmentViewModel;
import itest.kz.viewmodel.ResultViewModel;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultAdapterViewHolder>
{
//

    public interface OnItemClickListener
    {
        void onItemClick(Question test, List<Question> testList);
    }
//    public interface OnItemClickListener
//    {
//        void onItemClick(Test test, int i);
//    }

    private OnItemClickListener listener;
    List<Answer> answerList;
    boolean isStart = false;
    Tests testList;
    private Context context;
    private Question test;
    private Subject selectedSubject;
    private ItemQuestionsViewModel itemQuestionsViewModel;
    private Integer currentPosition;
    private Long testIdMain;
    private ArrayList<Subject> subjectList;
    private Integer selectedSubjectPosition;
    private int i = 0;
    private String resultTag;
    private String typeTest;

    public Integer getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition)
    {
        this.currentPosition = currentPosition;
        notifyDataSetChanged();
    }

    public ResultAdapter(Tests testList, Context context,
                         Subject selectedSubject, Long testIdMain,
                         @Nullable Integer currentPosition,
                         ArrayList<Subject> subjectList,
                         Integer selectedSubjectPosition,
                         int i, String resultTag, String typeTest

    )
    {

        this.context = context;
        this.testList = testList;
        this.selectedSubjectPosition = selectedSubjectPosition;
        this.subjectList = subjectList;
        this.selectedSubject = selectedSubject;
        this.currentPosition = currentPosition;
        this.testIdMain = testIdMain;
        this.i = i;
        this.resultTag = resultTag;
        this.typeTest = typeTest;

    }

    public void setOnItemListener(ResultAdapter.OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @NonNull
    @Override
    public ResultAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemResultBinding itemResultBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext())
                        , R.layout.item_result, viewGroup, false );

        return new ResultAdapterViewHolder(itemResultBinding);
    }

    public int getI()
    {
        return i;
    }

    public void setI(int i)
    {
        this.i = i;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapterViewHolder resultAdapterViewHolder, int i)
    {
//        resultAdapterViewHolder.bindAnswer(answerList.get(i));
        this.test = testList.getQuestions().get(i);
        resultAdapterViewHolder.bindTest(test, i);
        this.answerList = testList.getQuestions().get(i).getAnswers();

        RecyclerView recyclerView = resultAdapterViewHolder.recyclerView;

        QuestionsAdapter questionsAdapter = new QuestionsAdapter(answerList, context, resultTag);
        recyclerView.setAdapter(questionsAdapter);



        resultAdapterViewHolder.itemResultBinding.getRoot().findViewById(R.id.cardview_questions)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = null;

                if (currentPosition != null && typeTest.equals(Constant.TYPEFULLTEST))
                {
                    intent = new Intent(context, FullTestActivity.class);
                    intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, resultAdapterViewHolder
                            .getAdapterPosition());
                    intent.putExtra(Constant.SUBJECT_LIST, subjectList);
                    intent.putExtra(Constant.CURRENT_POSITION_SUBJECT, currentPosition);
                    intent.putExtra(Constant.IS_STARTED_FIRST, false);
                    intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
                    intent.putExtra(Constant.RESULT_TAG, resultTag);
                    intent.putExtra(Constant.TYPE, typeTest);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    intent.putExtra("list", testList);
                }
                else
                {
                    intent = new Intent(context, TestActivity.class);
                    //////        intent.putExtra()
                    intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, resultAdapterViewHolder
                            .getAdapterPosition());
                    intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                    intent.putExtra(Constant.IS_STARTED_FIRST, false);
                    intent.putExtra("list", testList);
                    intent.putExtra(Constant.RESULT_TAG, resultTag);
                    intent.putExtra(Constant.TYPE, typeTest);
                    intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }

                context.startActivity(intent);
            }
        });


        resultAdapterViewHolder.itemResultBinding.cardviewQuestions
                .setCardBackgroundColor((i % 2 == 1)? Color.WHITE : Color.parseColor("#f8f8f8"));
        resultAdapterViewHolder.recyclerView.setBackgroundColor((i % 2 == 1)? Color.WHITE :  Color.parseColor("#f8f8f8"));

//        int orientation = StaggeredGridLayoutManager.HORIZONTAL;
//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, orientation);
//        recyclerView.setLayoutManager
//                (gridLayoutManager);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount()
    {
        if (testList != null)
            return testList.getQuestions().size();
        return 0;
    }
    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
        notifyDataSetChanged();
    }




    public static class ResultAdapterViewHolder extends RecyclerView.ViewHolder
    {

        static boolean isStarted = false;
        ItemResultBinding itemResultBinding;
        ItemQuestionsBinding itemQuestionsBinding;
        RecyclerView recyclerView;

        public ResultAdapterViewHolder(ItemResultBinding itemResultBinding) {
            super(itemResultBinding.getRoot());
            this.itemResultBinding = itemResultBinding;
            recyclerView = (RecyclerView) itemResultBinding.listAnswers2;
        }


        void bindTest(Question test, int i)
        {

            if (itemResultBinding.getResult() == null)
            {
                itemResultBinding.setResult(
                        new ItemResultViewModel(test, itemView.getContext(),i));
            } else {
                itemResultBinding.getResult().setTest(test);
            }


        }

    }

}

