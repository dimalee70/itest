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
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
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
import itest.kz.util.LETTERS;
import itest.kz.viewmodel.ItemQuestionsViewModel;
import itest.kz.viewmodel.ItemResultViewModel;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionAdapterViewHolder>
//        RecyclerView.Adapter<ResultAdapter.ResultAdapterViewHolder>
{

    private LETTERS[] letters = LETTERS.values();
    private List<Answer> answers;
    private Context context;
    private ItemQuestionsBinding itemQuestionsBinding;
    private String resultTag;

    public QuestionsAdapter(List<Answer> answers, Context context)
    {
        this.answers = answers;
        this.context = context;
        this.resultTag = null;
    }

    public QuestionsAdapter(List<Answer> answers, Context context, String resultTag)
    {
        this.answers = answers;
        this.context = context;
        this.resultTag = resultTag;
    }


    @NonNull
    @Override
    public QuestionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        itemQuestionsBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_questions,viewGroup,false);


//        String text = answers.get(i).getLetter();
//        itemQuestionsBinding.btn.setText(letters[i].toString());
//        System.out.println(i);


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
//        notifyItemChanged(i);
        questionAdapterViewHolder.bindAnswer(answers.get(i));
        itemQuestionsBinding.btn.setText(letters[i].toString());
//        System.out.println("changed");
//        answers.get(i).getUserAnswer() != null

        StateListDrawable gradientDrawable = (StateListDrawable) itemQuestionsBinding.btn.getBackground();
        DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
        Drawable[] children = drawableContainerState.getChildren();
        GradientDrawable selectedItem = (GradientDrawable) children[0];
        if (resultTag != null)
        {

            if ( answers.get(i).getUserAnswer() == 1)
            {
//            if (answers.get(i).getAnswerResponce() == answers.get(i).getId()
//            && answers.get(i).getCorrect() == 1)
//            {

//                StateListDrawable gradientDrawable = (StateListDrawable) itemQuestionsBinding.btn.getBackground();
//                DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
//                Drawable[] children = drawableContainerState.getChildren();
//                GradientDrawable selectedItem = (GradientDrawable) children[0];
//            LayerDrawable unselectedItem = (LayerDrawable) children[1];
//            GradientDrawable selectedDrawable = (GradientDrawable) selectedItem.getDrawable(0);
//            GradientDrawable unselectedDrawable = (GradientDrawable) unselectedItem.getDrawable(0);
//            selectedItem.setStroke(1, Color.RED);

                selectedItem.setColor(Color.parseColor("#ffff6969"));



            }
            if (answers.get(i).getCorrect() == 1 && isAnswered())
            {
//                StateListDrawable gradientDrawable = (StateListDrawable) itemQuestionsBinding.btn.getBackground();
//                DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
//                Drawable[] children = drawableContainerState.getChildren();
//                GradientDrawable selectedItem = (GradientDrawable) children[0];
//            LayerDrawable unselectedItem = (LayerDrawable) children[1];
//            GradientDrawable selectedDrawable = (GradientDrawable) selectedItem.getDrawable(0);
//            GradientDrawable unselectedDrawable = (GradientDrawable) unselectedItem.getDrawable(0);
//            selectedItem.setStroke(1, Color.RED);

                selectedItem.setColor(Color.parseColor("#ff68da78"));
            }
            else if (!isAnswered() && answers.get(i).getCorrect() == 1)
            {

//            LayerDrawable unselectedItem = (LayerDrawable) children[1];
//            GradientDrawable selectedDrawable = (GradientDrawable) selectedItem.getDrawable(0);
//            GradientDrawable unselectedDrawable = (GradientDrawable) unselectedItem.getDrawable(0);
//            selectedItem.setStroke(1, Color.RED);

                selectedItem.setColor(Color.parseColor("#a0b5c2"));
            }
        }
        else
            {
            if (answers.get(i).getUserAnswer() == 1) {
//            if (answers.get(i).getAnswerResponce() == answers.get(i).getId()
//            && answers.get(i).getCorrect() == 1)
//            {

//                StateListDrawable gradientDrawable = (StateListDrawable) itemQuestionsBinding.btn.getBackground();
//                DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
//                Drawable[] children = drawableContainerState.getChildren();
//                GradientDrawable selectedItem = (GradientDrawable) children[0];
//            LayerDrawable unselectedItem = (LayerDrawable) children[1];
//            GradientDrawable selectedDrawable = (GradientDrawable) selectedItem.getDrawable(0);
//            GradientDrawable unselectedDrawable = (GradientDrawable) unselectedItem.getDrawable(0);
//            selectedItem.setStroke(1, Color.RED);

                selectedItem.setColor(Color.parseColor("#D2D0D3"));


            }

        }
//        else
//            {
//            ColorFilter filter = new LightingColorFilter(Color.GREEN, Color.BLACK);
//            Drawable txt = itemQuestionsBinding.btn.getBackground();
//            txt.setColorFilter(filter);
//        }


    }

    public boolean isAnswered()
    {
        for (Answer a : answers)
        {
            if (a.getUserAnswer() == 1)
                return true;
        }
        return false;
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

    public void setAnswerList(List<Answer> answerList)
    {
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
