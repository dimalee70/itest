package itest.kz.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.fragments.TestFragment;

public class FullTestAdapter extends FragmentStatePagerAdapter
{

    public String question;
    public Tests test;
    private ArrayList<Tests> arrayListArrayListQuestions;
    public int NUM;
    private Long testIdMain;
    private List<Subject> subjectList;
    private Integer currentPosition;
    private  Integer selectedTestPosition;
    private String resultTag;

    public FullTestAdapter(FragmentManager fm, Tests test, Long testIdMain, List<Subject> subjectList,
                           Integer currentPosition)
    {
        super(fm);
        this.test = test;
//        this.selectedTestPosition = selectedTestPosition;
//        this.selectedSubjectPosition = selectedSubjectPosition;
        this.NUM = test.getQuestions().size();
        this.currentPosition = currentPosition;
        this.testIdMain = testIdMain;
        this.subjectList = subjectList;
//        this.selectedSubjectPosition = selectedSubjectPosition;
//        this.arrayListArrayListQuestions = arrayListArrayListQuestions;

    }

    public FullTestAdapter(FragmentManager fm, Tests test, Long testIdMain, List<Subject> subjectList,
                           Integer currentPosition, String resultTag)
    {
        super(fm);
        this.test = test;
//        this.selectedTestPosition = selectedTestPosition;
//        this.selectedSubjectPosition = selectedSubjectPosition;
        this.NUM = test.getQuestions().size();
        this.currentPosition = currentPosition;
        this.testIdMain = testIdMain;
        this.subjectList = subjectList;
        this.resultTag = resultTag;
//        this.selectedSubjectPosition = selectedSubjectPosition;
//        this.arrayListArrayListQuestions = arrayListArrayListQuestions;

    }

    @Override
    public Fragment getItem(int i)
    {
        if (i >= 0 && i < NUM)
        {

            return TestFragment.newInstance(i, test, null, Constant.TYPEFULLTEST,
                    testIdMain, subjectList, currentPosition, resultTag);

        }
        return null;
    }

    @Override
    public int getCount()
    {
        if (test != null)
            return test.getQuestions().size();
        return 0;
    }



}
