package itest.kz.view.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemQuestionsBinding;
import itest.kz.databinding.ItemResultBinding;
import itest.kz.databinding.ItemSubjectBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.viewmodel.ItemQuestionsViewModel;
import itest.kz.viewmodel.ItemResultViewModel;
import itest.kz.viewmodel.ItemSubjectFragmentViewModel;
import itest.kz.viewmodel.ResultViewModel;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultAdapterViewHolder>
{

    List<Answer> answerList;
    List<Test> testList;
    private Context context;
    private ItemQuestionsViewModel itemQuestionsViewModel;
//    private ItemQuestionsBinding itemQuestionsBinding;


//    public ResultAdapter (List<Answer> answers)
//    {
//        this.answerList = answers;
////        this.answersAdapterListener = answersAdapterListener;
//    }
    public ResultAdapter(List<Test> testList, Context context)
    {
        this.context = context;
        this.testList = testList;

    }
    @NonNull
    @Override
    public ResultAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemResultBinding itemResultBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext())
                        , R.layout.item_result, viewGroup, false );



//        Drawable[] drawable = txt.getCompoundDrawables();
//        for (int j=0; j < drawable.length; j++)
//        {
//            if (drawable[j] != null)
//            {
//                drawable[j].setColorFilter(filter);
//            }
//        }

//        setTextViewDrawableColor(txt, R.color.my_color);

//        itemQuestionsViewModel = new ItemQuestionsViewModel(viewGroup.getContext(),testList.get(i).getAnswers());
//        activityResultBinding.setResult(resultViewModel);
//        ResultAdapter resultAdapter = new ResultAdapter(tests);
//        recyclerView.setAdapter(resultAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        resultViewModel = new ResultViewModel(this,test.getAnswers());
//        activityResultBinding.setResult(resultViewModel);
//        ResultAdapter resultAdapter = new ResultAdapter(test.getAnswers());
//        recyclerView.setAdapter(resultAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        return new ResultAdapterViewHolder(itemResultBinding);
    }

//    private void setTextViewDrawableColor(TextView textView, int color) {
//        for (Drawable drawable : textView.getCompoundDrawables()) {
//            if (drawable != null) {
//                drawable.setColorFilter(new PorterDuffColorFilter(getColor(color), PorterDuff.Mode.SRC_IN));
//            }
//        }
//    }
    @Override
    public void onBindViewHolder(@NonNull ResultAdapterViewHolder resultAdapterViewHolder, int i)
    {
//        resultAdapterViewHolder.bindAnswer(answerList.get(i));
        resultAdapterViewHolder.bindTest(testList.get(i));
        this.answerList = testList.get(i).getAnswers();
        RecyclerView recyclerView = resultAdapterViewHolder.recyclerView;
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(answerList, context);
        recyclerView.setAdapter(questionsAdapter);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));





//        this.answerList = testList.get(i).getAnswers();

    }
    //    public void setR(RecyclerView recyclerView)
//    {
////        itemQuestionsViewModel = new ItemQuestionsViewModel(this,tests);
////        activityResultBinding.setResult(resultViewModel);
//        QuestionsAdapter questionsAdapter = new QuestionsAdapter(answerList);
//        recyclerView.setAdapter(questionsAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(, LinearLayoutManager.HORIZONTAL
//                , false));
//        resultViewModel = new ResultViewModel(this,test.getAnswers());
////        activityResultBinding.setResult(resultViewModel);
////        ResultAdapter resultAdapter = new ResultAdapter(test.getAnswers());
////        recyclerView.setAdapter(resultAdapter);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//    }


    @Override
    public int getItemCount()
    {
        if (testList != null)
            return testList.size();
        return 0;
//        if (answerList != null)
//            return answerList.size();
//        return 0;
    }
    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
        notifyDataSetChanged();
    }




    public static class ResultAdapterViewHolder extends RecyclerView.ViewHolder {
//        ItemSubjectBinding itemSubjectBinding;

        ItemResultBinding itemResultBinding;
        ItemQuestionsBinding itemQuestionsBinding;
        RecyclerView recyclerView;

        public ResultAdapterViewHolder(ItemResultBinding itemResultBinding) {
            super(itemResultBinding.getRoot());
            this.itemResultBinding = itemResultBinding;
            recyclerView = (RecyclerView) itemResultBinding.listAnswers2;


        }

//        void bindAnswer(Answer answer) {
//            if (itemResultBinding.getResult() == null) {
//                itemResultBinding.setResult(
//                        new ItemResultViewModel(answer, itemView.getContext()));
//            } else {
//                itemResultBinding.getResult().setTest(answer);
//            }
//        }

        void bindTest(Test test)
        {
            if (itemResultBinding.getResult() == null)
            {
                itemResultBinding.setResult(
                        new ItemResultViewModel(test, itemView.getContext()));
            } else {
                itemResultBinding.getResult().setTest(test);
            }
        }

    }

}

