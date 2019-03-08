package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.util.List;

import itest.kz.R;
import itest.kz.model.Answer;

public class ItemQuestionsViewModel extends BaseObservable
{
    private Context context;
    private Answer answer;

    public ItemQuestionsViewModel(Context context, Answer answers)
    {
        this.context = context;
        this.answer = answers;

//        Drawable myIcon = ContextCompat.getDrawable(context, R.drawable.white_circle);
//        ColorFilter filter = new LightingColorFilter( Color.BLACK, Color.BLACK);
//        myIcon.setColorFilter(filter);
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public Answer getAnswer()
    {
        return answer;
    }

    public void setAnswer(Answer answer)
    {
        this.answer = answer;

//        Drawable myIcon = context.getResources().getDrawable( R.drawable.white_circle );

        notifyChange();
    }
}
