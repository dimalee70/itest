package itest.kz.viewmodel;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Answer;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.adapters.TestAdapter;
import itest.kz.view.fragments.BottomAnswerDialogFragment;
import itest.kz.view.fragments.DialogFragments;

import static android.content.Context.MODE_PRIVATE;

public class TestFragmentViewModel extends Observable
{
//    public ObservableField<String> url = new ObservableField<>();
    private Subject subject;
    private List<Question> testList;
//    private TestAdapter testAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private Question test;
    private  List<Answer> answers;
    public Action onClickShowAnswer;
    private FragmentActivity fragmentActivity;
    private String language;
    public ObservableInt subjectRecycler;
    public ObservableInt solutionTextVisible;
    private  boolean isExpandText = false;
    public ObservableInt progress = new ObservableInt(View.VISIBLE);

    public List<Answer> getAnswers() {
        return answers;
    }

    public ObservableInt getProgress()
    {
        return progress;
    }

    public void setProgress(boolean isProgress)
    {
        if (isProgress)
        {
            progress.set(View.VISIBLE);
        }
        else
        {
            progress.set(View.GONE);
        }
        notifyObservers();
    }

    public boolean checkExpandable()
    {
        return isExpandText;
    }

    public boolean isExpandText()
    {
        return isExpandText;
    }

    public void setExpandText(boolean expandText)
    {
        isExpandText = expandText;
        notifyObservers();
    }

    public TestFragmentViewModel(Context context, Question test, FragmentActivity fragmentActivity)
    {

        this.context = context;
        this.test = test;
        this.answers = test.getAnswers();
        this.subjectRecycler = new ObservableInt(View.VISIBLE);
        this.fragmentActivity = fragmentActivity;
        solutionTextVisible = new ObservableInt(View.VISIBLE);

        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = settings.getString(Constant.LANG, "kz");

        onClickShowAnswer = () ->
        {
//            FragmentManager fm = fragmentActivity.getFragmentManager();
//            DialogFragments dialogFragment = new DialogFragments(fragmentActivity);
//            dialogFragment.show(fm, "Bott");
            List<Answer> tempanswersList = new ArrayList<>();

            for (Answer a : answers)
            {
                if (a.getCorrect() == 1)
                {
                    tempanswersList.add(a);
                }
            }

            BottomAnswerDialogFragment bottomSheetFragment = BottomAnswerDialogFragment.newInstance(test, tempanswersList);
            bottomSheetFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
            bottomSheetFragment.show(fragmentActivity.getSupportFragmentManager(), "test");
        };
    }

    public TestFragmentViewModel(Context context, Question test)
    {
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = settings.getString(Constant.LANG, "kz");

        this.context = context;

        this.test = test;

        this.answers = test.getAnswers();
        this.subjectRecycler = new ObservableInt(View.VISIBLE);
        solutionTextVisible = new ObservableInt(View.GONE);

    }

    public String getData()
    {
        return  Constant.MATHJAX + test.getQuestion();
    }
    public void setTestList(List<Question> testList)
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

    public List<Question> getTestList()
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

    public int getAnswerDialogText()
    {
        if (language.equals(Constant.KZ))
            return R.string.solutionTextKz;
        return R.string.solutionTextRu;
    }

    public int getQuestionText()
    {
        if (language.equals(Constant.KZ))
            return R.string.questionTextKz;
        return R.string.questionTextRu;
    }

    public int getTextTest()
    {
        if (language.equals(Constant.KZ))
            return R.string.textTestKz;
        return R.string.textTestRu;
    }

    public int getAnswersText()
    {
        if (language.equals(Constant.KZ))
            return R.string.answersTextKz;
        return R.string.answersTextRu;
    }

}

