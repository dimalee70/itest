package itest.kz.viewmodel;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.functions.Action;
import itest.kz.model.StatisticSubject;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.activity.FulltestResultActivity;
import itest.kz.view.activity.ResultActivity;

public class ItemSubjectStatisticViewModel extends BaseObservable
{
    private Context context;
    private StatisticSubject statisticSubject;
    public Action onClickSubject;
    private String typeTest;

    public ItemSubjectStatisticViewModel(StatisticSubject statisticSubject, Context context, String typeTest)
    {
        this.context = context;
        this.statisticSubject = statisticSubject;
        this.typeTest = typeTest;
        onClickSubject = () ->
        {

            if (typeTest.equals(Constant.TYPESUBJECTTEST)) {

//            System.out.println(statisticSubject.getChildren());
                Intent intent = new Intent(context, ResultActivity.class);
                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                context.startActivity(intent);
            }
            else if(typeTest.equals(Constant.TYPEFULLTEST))
            {
                Intent intent = new Intent(context, FulltestResultActivity.class);
                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) statisticSubject.getChildren());
                context.startActivity(intent);
            }
        };
    }

    public String getTitle()
    {
        return statisticSubject.getTitle();
    }

    public String getFinishDate()
    {
//        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
//        System.out.println(statisticSubject);
//        return format.format(statisticSubject.getCreatedAt());
        return statisticSubject.getCreatedAt();
    }

    public Spanned getTestResultsSubject()
    {
        int all = statisticSubject.getResult().getAll();
        int points = statisticSubject.getResult().getPoints();
        String text = "<font color=#68DA78>" + points + "</font>"
                + "<font color=#FFAAAAAA>" + "/" + all + "</font>";
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    public StatisticSubject getStatisticSubject()
    {
        return statisticSubject;
    }

    public void setStatisticSubject(StatisticSubject statisticSubject)
    {
        this.statisticSubject = statisticSubject;
    }
}
