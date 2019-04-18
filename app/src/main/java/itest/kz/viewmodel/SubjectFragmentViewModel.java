package itest.kz.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.SubjectResponce;
import itest.kz.model.Test;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class SubjectFragmentViewModel extends Observable
{
    private ObservableInt subjectRecycler;
    private List<Subject> subjectListMain;
    private List<Subject> subjectList;
    private boolean isStartedFirst = false;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private ObservableInt entVisibleBtn;
    private String language;
    private String accessToken;

    public void onClickCancel()
    {
        subjectListMain = new ArrayList<>();
        subjectList = new ArrayList<>();
        fetchSubjectList();
    }

    private MutableLiveData<List<Subject>> listMutableLiveData;

    public LiveData<List<Subject>> getTests() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<List<Subject>>();
            fetchSubjectList();
        }
        return listMutableLiveData;
    }

    public void setSubjectListMain(List<Subject> subjectListMain)
    {
        this.subjectListMain = subjectListMain;
    }

    public void setSubjectList(List<Subject> subjectList)
    {
        this.subjectList = subjectList;
        notifyObservers();
//        notifyChange();
    }

    public SubjectFragmentViewModel(Context context)
    {
        this.context = context;
        this.subjectListMain = new ArrayList<>();
        this.subjectList = new ArrayList<>();
        this.subjectRecycler = new ObservableInt(View.GONE);
        this.entVisibleBtn = new ObservableInt(View.VISIBLE);
//        fetchSubjectList();
        isStartedFirst = true;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
        SharedPreferences accessTok = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "kz");
    }

    public ObservableInt getEntVisibleBtn()
    {
        return entVisibleBtn;
    }

    public void setVisibilityGone()
    {
        entVisibleBtn.set(View.GONE);
    }

    public void setVisibilityVisible()
    {
        entVisibleBtn.set(View.VISIBLE);
    }


    public void fetchSubjectList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


        Disposable disposable = subjectService.getSubjects(Constant.ENT,
                "Bearer " + accessToken, Constant.ACCEPT, language)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SubjectResponce>() {
                    @Override
                    public void accept(SubjectResponce subjectResponce) throws Exception
                    {
                        updateSubjectDataList(subjectResponce.getList());
                        subjectRecycler.set(View.VISIBLE);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public ObservableInt getSubjectRecycler()
    {
        return subjectRecycler;
    }

    public void updateSubjectDataList(List<Subject> list)
    {

        for (Subject s : list)
        {
            if (s.isMain)
                subjectListMain.add(s);
            else
                subjectList.add(s);
        }
//        System.out.println(subjectList.toString());
//        subjectList.addAll(list);
        setChanged();
        notifyObservers();

    }

    public List<Subject> getSubjectListMain()
    {
        return subjectListMain;
    }

    public List<Subject> getSubjectList()
    {
        return subjectList;
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

    public int getRequiredSubjects()
    {
        if (language.equals(Constant.KZ))
            return R.string.requiredSubjectsKz;
        return R.string.requiredSubjectsRu;
    }

    public int getChosedSubjects()
    {
        if (language.equals(Constant.KZ))
            return R.string.chosedSubjectsKz;
        return R.string.chosedSubjectsRu;
    }

    public int getStartFullTest()
    {
        if (language.equals(Constant.KZ))
            return R.string.fullTestStartKz;
        return R.string.fullTestStartRu;

    }

    public int getCancel()
    {
        if (language.equals(Constant.KZ))
            return R.string.cancelKz;
        return R.string.cancelRu;
    }
}
