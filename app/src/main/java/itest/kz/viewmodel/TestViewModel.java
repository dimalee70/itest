package itest.kz.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.fragments.TestFragment;

public class TestViewModel extends AndroidViewModel
{
    static final int ITEMS = 25;

    private MutableLiveData<List<Test>> listMutableLiveData;

    private Subject subject;
    public List<Test> testList ;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;


    public LiveData<List<Test>> getTests() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<List<Test>>();
            fetchTestList();
        }
        return listMutableLiveData;
    }

    public TestViewModel(Application application)
    {
        super(application);
        testList = new ArrayList<>();
//        this.subject = subject;
        fetchTestList();

    }
//
//    public TestViewModel(Context context, Subject subject)
//    {
//        this.subject = subject;
//        this.context = context;
//        this.testList = new ArrayList<>();
////        testList.add(new Test(1,"2121","323232",111,12,1,2,2, 1, 1, Arrays.asList(new Answer[]{new Answer(1, 1, "2", 1, 1),})));
//        fetchTestList();
//    }

    public void setTestList(List<Test> testList)
    {
        this.testList = testList;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public String getSubjectId()
    {
        return String.valueOf(subject.getId());
    }

    public void fetchTestList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();


        Disposable disposable = subjectService.getTests(Constant.ACCEPT)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Test>>() {
                               @Override
                               public void accept(List<Test> tests) throws Exception {
//                                   System.out.println("Test list");
//                                   System.out.println(tests);
                                   updateTestDataList(tests);
                               }
                           }
                );

        compositeDisposable.add(disposable);
    }

    private void updateTestDataList(List<Test> tests)
    {
        testList.clear();
        testList.addAll(tests);
        listMutableLiveData.setValue(testList);
//        setTestList(testList);

//        System.out.println("hello world");
//        setChanged();
//        notifyObservers();
    }

    public List<Test> getTestList()
    {
        return testList;
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

