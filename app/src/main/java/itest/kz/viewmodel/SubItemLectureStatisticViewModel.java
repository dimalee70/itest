package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

import io.reactivex.functions.Action;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.TestActivity;

public class SubItemLectureStatisticViewModel extends BaseObservable
{
    private Context context;
    private Test test;
    public Action onClickSubItem;

    public SubItemLectureStatisticViewModel(Context context, Test test)
    {
        this.context = context;
        this.test = test;
        onClickSubItem = () ->
        {
            Subject selectedSubject = new Subject(test.getId(), test.getTitle());
            Intent intent = new Intent(context, TestActivity.class);
            intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0);
            intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
            intent.putExtra(Constant.IS_STARTED_FIRST, false);
            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
            intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra(Constant.TEST_MAIN_ID, test.getId());

            context.startActivity(intent);
        };
    }

    public Test getTest()
    {
        return test;
    }

    public void setTest(Test test)
    {
        this.test = test;
        notifyChange();
    }

    public String getTitle()
    {
        return test.getTitle();
    }

    public Spanned getTestResultsSubLecture()
    {
        int all = test.getResult().getAll();
        int points = test.getResult().getPoints();
        String text = "<font color=#68DA78>" + points + "</font>"
                + "<font color=#FFAAAAAA>" + "/" + all + "</font>";
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);

    }

//    public Spanned getTestResultsLecture()
//    {
//        int all = 0;
//        int points = 0;
//        for (Test t : lectureStatisticResponse.getTestList()
//        ) {
//            all += t.getResult().getAll();
//            points += t.getResult().getPoints();
//        }
//        String text = "<font color=#68DA78>" + points + "</font>"
//                + "<font color=#FFAAAAAA>" + "/" + all + "</font>";
//        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
//    }
}
