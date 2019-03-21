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
    public List<Test> testList = new ArrayList<>();
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
//        testList = new ArrayList<>();
//        this.subject = subject;
//        fetchTestList();

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
//        id=58756, question='<p><meta charset="utf-8" /></p>
//                <p dir="ltr">Вычислите: cos <span class="math-tex">\(cos\)</span>&nbsp;78<span class="math-tex">\(^{\circ}\)</span>&nbsp;cos&nbsp;<span class="math-tex">\(cos\)</span> 18<span class="math-tex">\(^{\circ}\)</span>&nbsp;+ sin&nbsp;<span class="math-tex">\(sin\)</span> 78<span class="math-tex">\(^{\circ}\)</span> sin&nbsp;<span class="math-tex">\(sin\)</span>&nbsp;18<span class="math-tex">\(^{\circ}\)</span>.</p>', description='<p>cos&nbsp;<span class="math-tex">\(cos\)</span> 78<span class="math-tex">\(^{\circ}\)</span> cos&nbsp;<span class="math-tex">\(cos\)</span> 18<span class="math-tex">\(^{\circ}\)</span>&nbsp;+ sin&nbsp;<span class="math-tex">\(sin\)</span> 78<span class="math-tex">\(^{\circ}\)</span> sin&nbsp;<span class="math-tex">\(sin\)</span> 18<span class="math-tex">\(^{\circ}\)</span> = cos&nbsp;<span class="math-tex">\(cos\)</span> (78<span class="math-tex">\(^{\circ}\)</span>-18<span class="math-tex">\(^{\circ}\)</span>)= cos&nbsp;<span class="math-tex">\(cos\)</span> 60<span class="math-tex">\(^{\circ}\)</span> = <span class="math-tex">\(\frac12\)</span> = 0,5.</p>
//    <p><meta charset="utf-8" /></p>', nodeId=249, subjectId=1, langId=1, examSubjectId=0, difficultyLevel=1, checked=0, answerType=8, answers=[Answer{id=309695, questionId=58756, answer='<p><span class="math-tex">\(-\frac12\)</span></p>', correct=0, letter=null}, Answer{id=309696, questionId=58756, answer='<p><span class="math-tex">\(\frac12\)</span></p>', correct=1, letter=null}, Answer{id=309697, questionId=58756, answer='<p>1</p>', correct=0, letter=null}, Answer{id=309698, questionId=58756, answer='<p>0</p>', correct=0, letter=null}, Answer{id=309699, questionId=58756, answer='<p><span class="math-tex">\(\sqrt3\)</span></p>', correct=0, letter=null}, Answer{id=309700, questionId=58756, answer='<p>0,5</p>', correct=1, letter=null}, Answer{id=309701, questionId=58756, answer='<p><span class="math-tex">\(-\sqrt3\)</span></p>', correct=0, letter=null}, Answer{id=309702, questionId=58756, answer='<p>&ndash; 0,5</p>', correct=0, letter=null}]}

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

