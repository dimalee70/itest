package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import java.util.Observable;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.model.Question;
import itest.kz.util.Constant;
import itest.kz.view.fragments.BottomAnswerDialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class BottomAnswerDialogViewModel extends Observable
{
    private Context context;
    private BottomAnswerDialogFragment bottomAnswerDialogFragment;
    public Action onClickClose;
    private Question test;
    private String language;
    public ObservableField<String> questionText = new ObservableField<>();
    public ObservableField<String> descriptionText = new ObservableField<>();
    public ObservableInt whyTextVisibility;


//    private Context context;
//
//    List<Fragment> mFragments;
//    TabLayout mTabLayout;
//    ViewPager mViewPager;
//
//    public SubjectViewModel(Context context)
//    {
//        this.context = context;
//        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
////        settings.edit().clear().commit();
//        language = settings.getString(Constant.LANG, "kz");
    public BottomAnswerDialogViewModel(Context context, BottomAnswerDialogFragment bottomAnswerDialogFragment, Question test)
    {
        this.context = context;
        this.bottomAnswerDialogFragment = bottomAnswerDialogFragment;
        this.test = test;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
////        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        questionText.set(Constant.MATHJAX + test.getQuestion());
        descriptionText.set(Constant.MATHJAX + test.getDescription());
        if (descriptionText.get().contains("null"))
        {
            whyTextVisibility = new ObservableInt(View.GONE);
        }
        else
        {
            whyTextVisibility = new ObservableInt(View.VISIBLE);
        }
        this.onClickClose = () ->
        {
            bottomAnswerDialogFragment.dismiss();
        };
    }

//    int all = testFinishResponse.getResult().getAll();
//    int points = testFinishResponse.getResult().getPoints();//style="color:#000000"
//    String text = "<font color=#68DA78>" + points + "</font>"
//            + "/<font color='black'>" + all + "</font>";
//        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);

    public int getQuestiondAndAnswer()
    {
        if (language.equals(Constant.KZ))
            return R.string.questionAnswerKz;
        return R.string.questionAnswerRu;
    }

//    public void setWhyTextVisibility(boolean isWhyTextVisibility)
//    {
//        if (descriptionText.get() == null || descriptionText.get().contains("null"))
////        if (isWhyTextVisibility)
//            whyTextVisibility.set(View.GONE);
//        else
//        {
//            whyTextVisibility.set(View.VISIBLE);
//        }
//        notifyObservers();
//    }

    public Spanned getDescription()
    {
//        if (test.getDescription() == null || test.getDescription().contains("null"))
//        {
//
//           setWhyTextVisibility(false);
//        }
//        else
//        {
//            setWhyTextVisibility(true);
//        }
//        notifyObservers();

        return Html.fromHtml("<font color='white'>" + test.getDescription() + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    public int getWhyText()
    {
        if (language.equals(Constant.KZ))
            return R.string.whyKz;
        return R.string.whyRu;
    }


//    public String getQuestionText()
//    {
//
//    }

//    public void onClickClose()
//    {
//        bottomAnswerDialogFragment.dismiss();
//    }


}
