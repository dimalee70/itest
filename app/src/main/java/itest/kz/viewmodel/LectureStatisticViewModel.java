package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.view.View;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.network.SubjectService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.model.LectureStatisticResponse;
import itest.kz.util.TestsUtils;

import static android.content.Context.MODE_PRIVATE;

public class LectureStatisticViewModel  extends Observable
{
    private Context context;
    private String accessToken;
    private String language;
    public ObservableInt subjectRecycler;
    public ObservableInt imageButtonVisibility;
    private List<LectureStatisticResponse> lectureStatisticResponseList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LectureStatisticViewModel(Context context)
    {
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");
        this.context = context;
        this.subjectRecycler = new ObservableInt(View.GONE);
        this.imageButtonVisibility = new ObservableInt(View.GONE);
        this.lectureStatisticResponseList = new ArrayList<>();
        fetchLectureStatisticList();
    }

    private void fetchLectureStatisticList()
    {

        if (CheckUtility.isNetworkConnected(context)) {
//
            AppController appController = new AppController();
            SubjectService subjectService = appController.getSubjectService();


            Disposable disposable = subjectService.getStatisticLecture(Constant.ACCEPT,
                    language,
                    "Bearer " + accessToken)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
//                    .timeout(500, TimeUnit.MILLISECONDS)
                    .subscribe(
                            new Consumer<JsonObject>() {
                                @Override
                                public void accept(JsonObject jsonObject) throws Exception
                                {
//                                    System.out.println("Result");
//                                    System.out.println(jsonObject.toString());
                                    updateLectureDataList(TestsUtils
                                            .deserializeFromJsonToLectureStatistic
                                                    (jsonObject));
                                    subjectRecycler.set(View.VISIBLE);
                                    imageButtonVisibility.set(View.GONE);
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
//                                    System.out.println("error");
                                    throwable.getMessage();
                                    imageButtonVisibility.set(View.GONE);
                                }
                            }
                    );

            compositeDisposable.add(disposable);
        }
        else
        {
            subjectRecycler.set(View.GONE);
            imageButtonVisibility.set(View.VISIBLE);

        }
    }

    private void updateLectureDataList(List<LectureStatisticResponse> data)
    {
        lectureStatisticResponseList.addAll(data);
        setChanged();
        notifyObservers();
    }


    public List<LectureStatisticResponse> getLectureStatisticResponseList()
    {
        return lectureStatisticResponseList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}
