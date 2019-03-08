package itest.kz.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ItemQuestionsBinding;
import itest.kz.databinding.ItemResultBinding;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.viewmodel.ItemQuestionsViewModel;
import itest.kz.viewmodel.ItemResultViewModel;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionAdapterViewHolder>
//        RecyclerView.Adapter<ResultAdapter.ResultAdapterViewHolder>
{

    private List<Answer> answers;
    private Context context;
    private ItemQuestionsBinding itemQuestionsBinding;

    public QuestionsAdapter(List<Answer> answers, Context context)
    {
        this.answers = answers;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemQuestionsBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_questions,viewGroup,false);





//        setTextViewDrawableColor(txt, Color.BLACK);
        return new QuestionAdapterViewHolder(itemQuestionsBinding);

    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapterViewHolder questionAdapterViewHolder, int i)
    {
//
        questionAdapterViewHolder.bindAnswer(answers.get(i));
        if (answers.get(i).getAnswerResponce() != null) {
            if (answers.get(i).getAnswerResponce() == answers.get(i).getId()
            && answers.get(i).getCorrect() == 1)
            {
                ColorFilter filter = new LightingColorFilter(Color.YELLOW, Color.GREEN);
//        myIcon.setColorFilter(filter);

                Drawable txt = itemQuestionsBinding.btn.getBackground();
                txt.setColorFilter(filter);

            } else {
                ColorFilter filter = new LightingColorFilter(Color.GREEN, Color.BLACK);
//        myIcon.setColorFilter(filter);

                Drawable txt = itemQuestionsBinding.btn.getBackground();
                txt.setColorFilter(filter);
            }
        }


    }

    @Override
    public int getItemCount() {
        if (answers != null)
            return answers.size();
        return 0;
    }
    public List<Answer> getAnswerList() {
        return answers;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answers = answerList;
        notifyDataSetChanged();
    }

    public static class QuestionAdapterViewHolder extends RecyclerView.ViewHolder
    {

        ItemQuestionsBinding itemQuestionsBinding;

        public QuestionAdapterViewHolder(ItemQuestionsBinding itemQuestionsBinding) {
            super(itemQuestionsBinding.getRoot());
            this.itemQuestionsBinding = itemQuestionsBinding;
        }


        void bindAnswer(Answer answer) {
            if (itemQuestionsBinding.getQuestions() == null) {
                itemQuestionsBinding.setQuestions
                        (new ItemQuestionsViewModel(itemView.getContext(), answer));
            } else {
                itemQuestionsBinding.getQuestions().setAnswer(answer);
            }
        }
    }
}
