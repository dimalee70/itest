package itest.kz.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.Lecture;
import itest.kz.model.LectureResponse;
import itest.kz.model.Node;
import itest.kz.model.NodesBySubject;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.activity.LectureActivity;

public class ItemNodeByNodeViewModel extends BaseObservable
{
    private Context context;
    private Lecture lecture;
    private LectureResponse lectureResponse = null;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ItemNodeByNodeViewModel(Context contex, Lecture lecture)
    {
        this.context = contex;
        this.lecture = lecture;
    }

    public String getTitle()
    {
        return lecture.getTitle();
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public Lecture getLecture()
    {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
        notifyChange();
    }

    public void getLectureResponse()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();

//        if (subject != null) {
        Disposable disposable = subjectService.getLecture(Constant.ATTESTATION,
                lecture.getId(), Constant.ACCEPT, "ru")
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LectureResponse>()
                           {
                               @Override
                               public void accept(LectureResponse lectureResponse) throws Exception
                               {

                                   updateLectureResponseData(lectureResponse);
                                   Intent intent = new Intent(getContext(), LectureActivity.class);
                                   intent.putExtra(Constant.SELECTED_LECTURE_RESPONSE, lectureResponse);
                                   context.startActivity(intent);
                               }
                           }
                );

        compositeDisposable.add(disposable);
    }

    public void onClick(View view)
    {
        getLectureResponse();

    }

    private void updateLectureResponseData(LectureResponse lectureResponse)
    {
        this.lectureResponse = lectureResponse;
//        System.out.println("desc");
//        System.out.println(lectureResponse.getDescription());

    }
}
