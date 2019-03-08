package itest.kz.viewmodel;

import android.content.Context;

import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import itest.kz.model.Answer;
import itest.kz.model.Lecture;
import itest.kz.model.LectureResponse;
import itest.kz.model.Node;
import itest.kz.model.Subject;
import itest.kz.model.Test;

public class LectureViewModel extends Observable
{
    private LectureResponse lectureResponse;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;

    public LectureViewModel(Context context, LectureResponse lectureResponse)
    {
        this.context = context;
        this.lectureResponse = lectureResponse;
    }

    public LectureResponse getLectureResponse()
    {
        return lectureResponse;
    }

    public void setLectureResponse(LectureResponse lectureResponse)
    {
        this.lectureResponse = lectureResponse;
    }

    public CompositeDisposable getCompositeDisposable()
    {
        return compositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable)
    {
        this.compositeDisposable = compositeDisposable;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public String getData()
    {
        return lectureResponse.getDescription();
    }

    public String getTitle()
    {
        return lectureResponse.getLecture().getTitle();
    }

    private void unSubscribeFromObservable()
    {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset()
    {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }

}
