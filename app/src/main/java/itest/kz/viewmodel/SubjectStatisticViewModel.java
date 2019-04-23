package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.StatisticSubject;
import itest.kz.model.StatisticSubjectResponce;
import itest.kz.network.SubjectService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class SubjectStatisticViewModel extends Observable
{
    private Context context;
    private String accessToken;
    private String language;
    public ObservableInt subjectRecycler;
    public ObservableInt imageButtonVisibility;
    private List<StatisticSubject> statisticSubjectList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SubjectStatisticViewModel(Context context)
    {
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");
//        System.out.println("Access token");
//        System.out.println(accessToken);
        this.context = context;
        this.subjectRecycler = new ObservableInt(View.GONE);
        this.imageButtonVisibility = new ObservableInt(View.GONE);
        this.statisticSubjectList = new ArrayList<>();
        fetchSubjectStatisticList();
    }

    private void fetchSubjectStatisticList()
    {

        if (CheckUtility.isNetworkConnected(context)) {
            AppController appController = new AppController();
            SubjectService subjectService = appController.getSubjectService();


            Disposable disposable = subjectService.getStatisticSubject(Constant.ACCEPT,
                    language,
                    "Bearer " + accessToken)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<StatisticSubjectResponce>() {
                                   @Override
                                   public void accept(StatisticSubjectResponce statisticSubjectResponce) throws Exception {
//                                   System.out.println(statisticSubjectResponce);
                                       updateSubjectDataList(statisticSubjectResponce.getData());
                                       subjectRecycler.set(View.VISIBLE);
                                   }
                               }
//                        new Consumer<SubjectResponce>() {
//                    @Override
//                    public void accept(SubjectResponce subjectResponce) throws Exception
//                    {
//                        updateSubjectDataList(subjectResponce.getList());
//                        subjectRecycler.set(View.VISIBLE);
//                    }
//                }
                    );

            compositeDisposable.add(disposable);
        }
        else
        {
            imageButtonVisibility.set(View.VISIBLE);
        }
    }

    private void updateSubjectDataList(List<StatisticSubject> data)
    {
        statisticSubjectList.clear();
        statisticSubjectList.addAll(data);
        setChanged();
        notifyObservers();
    }

    public List<StatisticSubject> getStatisticSubjectList()
    {
        return statisticSubjectList;
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
