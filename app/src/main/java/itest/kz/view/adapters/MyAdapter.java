package itest.kz.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.Tests;
import itest.kz.util.Constant;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.fragments.TestFragment;
import itest.kz.viewmodel.TestViewModel;

public class MyAdapter extends FragmentStatePagerAdapter
{
    public String question;
    public Tests test;
//    public List<Test> test2;
    public static int NUM;
    private Subject selectedSubject;
    private String resultTag;
    private String typeTest;
    private String statisticTag;
//    public MyAdapter(FragmentManager fm, List<Test> test)
//    {
//        super(fm);
//        this.test2 = test;
//        this.NUM = test.size();
////            this.question = question;
//    }

    public MyAdapter(FragmentManager fm,
                     Tests test,
                     Subject selectedSubject,
                     String resultTAg, String typeTest,
                     String statisticTag)
    {
        super(fm);
        this.test = test;
        this.NUM = test.getQuestions().size();
        this.selectedSubject = selectedSubject;
        this.resultTag = resultTAg;
        this.typeTest = typeTest;
        this.statisticTag = statisticTag;
//            this.question = question;
    }


    @Override
    public Fragment getItem(int i)
    {

//            return ArrayListFragment.init(i);


        if (i >= 0 && i < NUM)
        {
//            TestFragment testFragment =
//                    TestFragment.newInstance(i, test);
//            if (i == test.size() - 1)
//                testFragment.setTa
//            Fragment fragment = TestFragment.newInstance(i, test);
            return TestFragment.newInstance(i, test,
                    selectedSubject, typeTest ,
                    test.getTestId(),resultTag,
                    statisticTag);

        }
        return null;
//            switch (i)
//            {
//                case 0:
//                    return  TestFragment.init(i);
////                    break;
//                case 1:
//                    return  TestFragment.init(i);
////                    break;
//            }
//            return null;
    }

    @Override
    public int getCount() {
        return test.getQuestions().size();
    }
}