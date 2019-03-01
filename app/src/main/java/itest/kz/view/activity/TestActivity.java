package itest.kz.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ActivityTestBinding;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.viewmodel.TestViewModel;

public class TestActivity extends FragmentActivity
{
    public ActivityTestBinding activityTestBinding;
    private TestViewModel testViewModel;
    private ArrayList<Test> tests;



    MyAdapter mAdapter;
    ViewPager mPager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);

        final Observer<List<Test>> listObserver = new Observer<List<Test>>() {
            @Override
            public void onChanged(@Nullable List<Test> tests)
            {
                if (tests != null && tests.size() > 0)
                {
                    setContentView(R.layout.activity_test);
                    mPager = (ViewPager) findViewById(R.id.pager);
                    mPager.setAdapter(new MyAdapter(getSupportFragmentManager(), tests));
                }
            }

        };

        testViewModel.getTests().observe(this, listObserver);

        activityTestBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_test);

//        getExtrasFromIntent();




    }

//    private void getExtrasFromIntent()
//    {
//        Subject subject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
//        TestViewModel testViewModel = new TestViewModel(getApplication(), subject);
//        activityTestBinding.setTest(testViewModel);
//
//
//
//
//
//    }
//
//    public static Intent fillSelectedSubject(Context context, Subject subject) {
//        Intent intent = new Intent(context, TestActivity.class);
//        intent.putExtra(Constant.SELECTED_SUBJECT, subject);
//
//        return intent;
//    }

}

