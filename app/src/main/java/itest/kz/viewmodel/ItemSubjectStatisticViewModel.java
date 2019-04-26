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
//                Intent intent = new Intent(context, TestActivity.class);
//                //////        intent.putExtra()
//                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0
//                        );
//                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
//                intent.putExtra(Constant.IS_STARTED_FIRST, false);
//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//                intent.putExtra(Constant.TYPE, typeTest);
//                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);



//            Intent intent = new Intent(context, TestActivity.class);
//            intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0);
//            intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
//            intent.putExtra(Constant.IS_STARTED_FIRST, false);
//            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//            intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            intent.putExtra(Constant.TEST_MAIN_ID, test.getId());


                TestFinishResponse testFinishResponse = new TestFinishResponse(statisticSubject.getResult());
                Intent intent = new Intent(context, ResultsActivity.class);
                intent.putExtra(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
                intent.putExtra(Constant.TEST_MAIN_ID, selectedSubject.getId());
                intent.putExtra(Constant.TYPE, Constant.TYPESUBJECTTEST);
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) null);

                intent.putExtra(Constant.STATISTIC_TAG, Constant.STATISTIC_TAG);



//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);



//                intent.putExtra("list", testList);
//                    System.out.println("testList");
//                    System.out.println(testList);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                        Intent.FLAG_ACTIVITY_NEW_TASK);







//                Intent intent = new Intent(context, TestActivity.class);
//                Subject subject = new Subject(statisticSubject.getId(), statisticSubject.getTitle());

//                System.out.println(subject);

                //////        intent.putExtra()
//                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0);
//                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) subject);
//                intent.putExtra(Constant.IS_STARTED_FIRST, false);
//                intent.putExtra("list", testList);
////                    System.out.println("testList");
////                    System.out.println(testList);
//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//                intent.putExtra(Constant.TYPE, typeTest);
//                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());

//            System.out.println(statisticSubject.getChildren());

//                Intent intent = new Intent(context, ResultActivity.class);
//                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
                context.startActivity(intent);
            }
            else if(typeTest.equals(Constant.TYPEFULLTEST))
            {


//                System.out.println("Statistic ");
//                System.out.println(statisticSubject.getChildren());
//                Intent intent = new Intent(context, FullTestActivity.class);
//                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) statisticSubject.getChildren());
//                intent.putExtra(Constant.IS_STARTED_FIRST, false);
////                intent.putExtra("list", testList);
////                    System.out.println("testList");
////                    System.out.println(testList);
//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//                intent.putExtra(Constant.TYPE, typeTest);
//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);



//                Subject selectedSubject = new Subject(test.getId(), test.getTitle());
//            Intent intent = new Intent(context, TestActivity.class);
//            intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0);
//            intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
//            intent.putExtra(Constant.IS_STARTED_FIRST, false);
//            intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//            intent.putExtra(Constant.TYPE, Constant.TYPELECTURETEST);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            intent.putExtra(Constant.TEST_MAIN_ID, test.getId());


                TestFinishResponse testFinishResponse = new TestFinishResponse(statisticSubject.getResult());
                Intent intent = new Intent(context, ResultsActivity.class);
                intent.putExtra(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
                intent.putExtra(Constant.TYPE, Constant.TYPEFULLTEST);
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) statisticSubject.getChildren().get(0));
                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) statisticSubject.getChildren());
                intent.putExtra(Constant.STATISTIC_TAG, Constant.STATISTIC_TAG);
                context.startActivity(intent);


//                Intent intent = new Intent(context, FulltestResultActivity.class);
//                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());
//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) statisticSubject.getChildren());
//                context.startActivity(intent);
            }
            else if (typeTest.equals(Constant.TYPELECTURETEST))
            {
//                Subject selectedSubject = new Subject(statisticSubject.getId(), statisticSubject.getTitle());
//                Intent intent = new Intent(context, TestActivity.class);
//                //////        intent.putExtra()
//                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, 0
//                );
//                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
//                intent.putExtra(Constant.IS_STARTED_FIRST, false);
////                intent.putExtra("list", testList);
////                    System.out.println("testList");
////                    System.out.println(testList);
//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
//                intent.putExtra(Constant.TYPE, typeTest);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                intent.putExtra(Constant.TEST_MAIN_ID, statisticSubject.getId());


                Subject selectedSubject = new Subject(statisticSubject.getId(), statisticSubject.getTitle());
                TestFinishResponse testFinishResponse = new TestFinishResponse(statisticSubject.getResult());
                Intent intent = new Intent(context, ResultsActivity.class);
                intent.putExtra(Constant.TEST_FINISH_RESPONSE, testFinishResponse);
                intent.putExtra(Constant.TEST_MAIN_ID, selectedSubject.getId());
                intent.putExtra(Constant.TYPE, Constant.TYPESUBJECTTEST);
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) null);

                intent.putExtra(Constant.STATISTIC_TAG, Constant.STATISTIC_TAG);


//                intent.putExtra(Constant.RESULT_TAG, Constant.RESULT_TAG);
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
                + "<font color=#AAAAAA>" + "/" + all + "</font>";
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
