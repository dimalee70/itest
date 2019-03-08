package itest.kz.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
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
import itest.kz.view.adapters.TestAdapter;

public class TestFragmentViewModel extends Observable
{
//    public ObservableField<String> url = new ObservableField<>();
    private Subject subject;
    private List<Test> testList;
//    private TestAdapter testAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private Test test;
    private  List<Answer> answers;


    public ObservableInt subjectRecycler;

    public List<Answer> getAnswers() {
        return answers;
    }

    public TestFragmentViewModel(Context context, Test test)
    {

        this.context = context;

        this.test = test;

        this.answers = test.getAnswers();
        this.subjectRecycler = new ObservableInt(View.VISIBLE);
    }


//    public TestAdapter getTestAdapter() {
//        return testAdapter;
//    }
//
//    public void setTestAdapter(TestAdapter testAdapter) {
//        this.testAdapter = testAdapter;
//    }

    public String getData()
    {
        return  test.getQuestion();
    }
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

//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        // Get the selected item text from ListView
//        Answer selectedItem = (Answer) parent.getItemAtPosition(position);
//        System.out.println(selectedItem.toString());
//
//        // Display the selected item text on TextView
//    }

//    public void onItemClick(View view)
//    {
////        String selectedItem = (String) view.getItemAtPosition(position);
//        Log.d("dcsd","On Item Click ");
//    }
}

