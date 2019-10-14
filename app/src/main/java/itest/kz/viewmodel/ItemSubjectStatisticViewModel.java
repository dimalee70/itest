package itest.kz.viewmodel;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Build;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.functions.Action;
import itest.kz.model.StatisticSubject;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.FulltestResultActivity;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.ResultsActivity;
import itest.kz.view.activity.TestActivity;

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

            if (typeTest.equals(Constant.TYPESUBJECTTEST))
            {




                Subject selectedSubject = new Subject(statisticSubject.getId(), statisticSubject.getTitle());
                ArrayList<Subject> subjects = new ArrayList<>();
                subjects.add(selectedSubject);

                Intent intent = new Intent(context, TestActivity.class);
                //////        intent.putExtra()
                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0
                        );
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                intent.putExtra(Constant.IS_STARTED_FIRST, false);
                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                intent.putExtra(Constant.TYPE, typeTest);
                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra(Constant.STATISTIC_TAG, Constant.STATISTIC_TAG);


                context.startActivity(intent);
            }
            else if(typeTest.equals(Constant.TYPEFULLTEST))
            {

                Intent intent = new Intent(context, FullTestActivity.class);
                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) statisticSubject.getChildren());
                intent.putExtra(Constant.IS_STARTED_FIRST, false);

                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                intent.putExtra(Constant.TYPE, typeTest);
                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra(Constant.STATISTIC_TAG, Constant.STATISTIC_TAG);

                context.startActivity(intent);
            }
            else if (typeTest.equals(Constant.TYPELECTURETEST))
            {
                Subject selectedSubject = new Subject(statisticSubject.getId(), statisticSubject.getTitle());
                Intent intent = new Intent(context, TestActivity.class);
                //////        intent.putExtra()
                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0
                );
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                intent.putExtra(Constant.IS_STARTED_FIRST, false);
//                intent.putExtra("list", testList);
//                    System.out.println("testList");
//                    System.out.println(testList);
                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                intent.putExtra(Constant.TYPE, typeTest);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
                intent.putExtra(Constant.STATISTIC_TAG, Constant.STATISTIC_TAG);


            }
        };
    }

    public String getTitle()
    {
        return statisticSubject.getTitle();
    }

    public String getFinishDate()
    {

        return statisticSubject.getCreatedAt();
    }

    public Spanned getTestResultsSubject()
    {
        int all = statisticSubject.getResult().getAll();
        int points = statisticSubject.getResult().getPoints();
        String text = "<font color=#68DA78>" + points + "</font>"
                + "<font color=#AAAAAA>" + "/" + all + "</font>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml(text);
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
