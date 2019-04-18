package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import java.text.SimpleDateFormat;

import io.reactivex.functions.Action;
import itest.kz.R;
import itest.kz.model.LectureStatisticResponse;
import itest.kz.model.Test;

public class ItemLectureStatisticViewModel extends BaseObservable
{
    public ObservableInt subLecturetRecycler;
    private Context context;
    private LectureStatisticResponse
            lectureStatisticResponse;
    public Action onClickSubject;


    public ItemLectureStatisticViewModel(Context context,
                                       LectureStatisticResponse lectureStatisticResponse)
    {
        this.context = context;
        this.lectureStatisticResponse =
                lectureStatisticResponse;
        this.subLecturetRecycler = new ObservableInt(View.GONE);
//        onClickSubject = () ->
//        {
//            if (subLecturetRecycler.get() == View.VISIBLE)
//            {
//                subLecturetRecycler.set(View.GONE);
//                notifyChange();
////                getIconStrelka();
//            }
//
//            else
//            {
//                subLecturetRecycler.set(View.VISIBLE);
//                notifyChange();
////                getIconStrelka();
//            }
//
//        };
    }

    public boolean checkSubLectureRecylle()
    {
        return lectureStatisticResponse.isExpand();

    }

    public int getIconStrelka()
    {
        if (lectureStatisticResponse.isExpand())
            return R.drawable.ic_icon_strelka_top;
        return R.drawable.ic_icon_strelka_bottom;
    }
    public LectureStatisticResponse getLectureStatisticResponse()
    {
        return lectureStatisticResponse;
    }

    public void setLectureStatisticResponse(LectureStatisticResponse lectureStatisticResponse)
    {
        this.lectureStatisticResponse = lectureStatisticResponse;
        notifyChange();
    }

    public String getTitle()
    {
        return lectureStatisticResponse
                .getSubject().getTitle();
    }

    public String getFinishDate()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        return format.format(lectureStatisticResponse.getSubject().getDate());
    }

    public Spanned getTestResultsLecture()
    {
        int all = 0;
        int points = 0;
        for (Test t : lectureStatisticResponse.getTestList()
             ) {
            all += t.getResult().getAll();
            points += t.getResult().getPoints();
        }
        String text = "<font color=#68DA78>" + points + "</font>"
                + "<font color=#FFAAAAAA>" + "/" + all + "</font>";
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

}
