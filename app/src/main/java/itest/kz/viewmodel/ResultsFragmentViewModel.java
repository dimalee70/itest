package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

import io.reactivex.functions.Action;
import itest.kz.model.Subject;
import itest.kz.model.TestFinishResponse;
import itest.kz.util.Constant;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.TestActivity;

public class ResultsFragmentViewModel extends Observable
{
    private Context context;
    private TestFinishResponse testFinishResponse;
    public Action clickAgainTest;
    private Subject selectedSubject;
    private List<Subject> subjectList;
    private String typeTest;
//    private String text = "This is <font color='red'>red</font>. This is <font color='blue'>blue</font>.";

    public ResultsFragmentViewModel(Context context, TestFinishResponse testFinishResponse,
                                    List<Subject> subjectList, Subject selectedSubject, String typeTest)
    {
        this.context = context;
        this.testFinishResponse = testFinishResponse;
        this.subjectList = subjectList;
        this.selectedSubject = selectedSubject;
        this.typeTest = typeTest;

        this.clickAgainTest = ()->
        {
            if (typeTest.equals(Constant.TYPEFULLTEST))
            {
                Intent intent = new Intent(context, FullTestActivity.class);
                intent.putExtra(Constant.SUBJECT_LIST, (Serializable) subjectList);
                context.startActivity(intent);
            }
            else if (typeTest.equals(Constant.TYPESUBJECTTEST))
            {
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra(Constant.SELECTED_SUBJECT, (Serializable) selectedSubject);
                context.startActivity(intent);
            }

        };
    }

    public Spanned getHtmlText()
    {
        int all = testFinishResponse.getResult().getAll();
        int points = testFinishResponse.getResult().getPoints();//style="color:#000000"
        String text = "<font color=#68DA78>" + points + "</font>"
                + "/<font color='black'>" + all + "</font>";
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    public  int getPercent()
    {
        return  (int )testFinishResponse.getResult().getPercent();
    }
}
