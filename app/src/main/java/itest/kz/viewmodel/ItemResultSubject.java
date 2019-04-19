package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;

import itest.kz.model.Answer;
import itest.kz.model.Tests;

public class ItemResultSubject extends BaseObservable
{
    private Context context;
    private Tests tests;

    public ItemResultSubject(Context context, Tests tests)
    {
        this.context = context;
        this.tests = tests;
    }

    public String getTitle()
    {
        return tests.getSubject().getTitle();
    }

    public Spanned getTestResultsSubject()
    {
        int all = tests.getTest().getResult().getAll();//style="color:#000000"
        int points = tests.getTest().getResult().getPoints();
        String text = "<font color=#68DA78>" + points + "</font>"
                + "<font color='black'>/" + all + "</font>";
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    public void setTests(Tests tests)
    {
        this.tests = tests;
        notifyChange();
    }

    public Tests getTests()
    {
        return tests;
    }
}
