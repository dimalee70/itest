package itest.kz.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
import itest.kz.util.Constant;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.ResultsActivity;
import itest.kz.view.activity.TestActivity;

import static android.content.Context.MODE_PRIVATE;

public class ResultsFragmentViewModel extends Observable
{
    private Context context;
    private TestFinishResponse testFinishResponse;
    public Action clickAgainTest;
    private Subject selectedSubject;
    private List<Subject> subjectList;
    private String typeTest;
    private String language;
    private SharedPreferences settings;
    public ObservableInt progress = new ObservableInt(View.VISIBLE);
    public ObservableInt nestedVisible = new ObservableInt(View.GONE);


    public ObservableInt getProgress()
    {
        return progress;
    }

    public void setProgress(boolean isProgress)
    {
        if (isProgress)
            progress.set(View.VISIBLE);
        else
            {
                progress.set(View.GONE);
                nestedVisible.set(View.VISIBLE);
            }

        notifyObservers();
    }

    //    private String text = "This is <font color='red'>red</font>. This is <font color='blue'>blue</font>.";

    public ResultsFragmentViewModel(Context context, TestFinishResponse testFinishResponse,
                                    List<Subject> subjectList, Subject selectedSubject, String typeTest)
    {
        this.context = context;
        this.testFinishResponse = testFinishResponse;
        this.subjectList = subjectList;
        this.selectedSubject = selectedSubject;
        this.typeTest = typeTest;
        settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        this.clickAgainTest = ()->
        {
            if (typeTest.equals(Constant.TYPEFULLTEST))
            {
                Intent intent = new Intent(context, FullTestActivity.class);
                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);
                intent.putExtra(Constant.TYPE, typeTest);
                context.startActivity(intent);
            }
            else if (typeTest.equals(Constant.TYPESUBJECTTEST))
            {
                Intent intent = new Intent((Activity) context, TestActivity.class);
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                intent.putExtra(Constant.TYPE, typeTest);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }

            else if (typeTest.equals(Constant.TYPELECTURETEST))
            {
                Intent intent = new Intent(context, TestActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                intent.putExtra(Constant.TYPE, typeTest);
                context.startActivity(intent);
            }
        };
    }

    public int getShowResultText()
    {
        if (language.equals(Constant.KZ))
            return R.string.resultShowKz;
        return R.string.resultShowRu;
    }
    public Spanned getHtmlText()
    {
        if (testFinishResponse != null)
        {
            int all = 0;
            int points = 0;
            if(testFinishResponse.getResult()!= null)
            {
                all = testFinishResponse.getResult().getAll();
                points = testFinishResponse.getResult().getPoints();//style="color:#000000"
            }

            String text = "<font color=#68DA78>" + points + "</font>"
                    + "/<font color='black'>" + all + "</font>";
            return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml("", HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    public  int getPercent()
    {
        if (testFinishResponse.getResult() != null)
        {
            return  (int )testFinishResponse.getResult().getPercent();
        }
        return  0;
    }

    public int getAgainTestText()
    {
        if (language.equals(Constant.KZ))
            return R.string.againTestKz;
        return R.string.againTestRu;
    }
}
