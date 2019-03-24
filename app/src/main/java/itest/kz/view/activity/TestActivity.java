package itest.kz.view.activity;

import android.app.ActionBar;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ActivityTestBinding;
import itest.kz.model.Answer;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.util.CustomViewPager;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.viewmodel.TestViewModel;

public class TestActivity extends AppCompatActivity
{
    public ActivityTestBinding activityTestBinding;
    private TestViewModel testViewModel;
    public ArrayList<Test> tests;
    public static ArrayList<Answer> newAnswers = new ArrayList<>();
    private Toolbar myToolbar;
    private Integer SELECTED_TEST_POSITION_ID = 0;
    private boolean isStartedFirst= true;


    MyAdapter mAdapter;
    public CustomViewPager mPager;

    public ViewPager getmPager()
    {
        return mPager;
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater =
//                getMenuInflater();
////                        .inflate(R.menu.main, menu);
//        menuInflater.inflate(R.menu.main, menu);
////        menuInflater.
//        return true;
//    }
//    @Override public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_github) {
////            startActivityActionView();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public List<Test> getTests()
    {
        return tests;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            this.SELECTED_TEST_POSITION_ID = extras.getInt(Constant.SELECTED_TEST_POSITION_ID);
            this.isStartedFirst = extras.getBoolean(Constant.IS_STARTED_FIRST);
            this.tests = extras.getParcelableArrayList("list");
        }


//        setContentView();

//        getSupportActionBar().setIcon(R.drawable.ic_launcher_background);
//        myToolbar.setNavigationIcon(R.drawable.ic_answers);
//        getSupportActionBar().setLogo(R.drawable.ic_answers);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        myToolbar.inflateMenu(R.menu.main);

//        getSupportActionBar().setIcon(R.drawable.ic_answers);
//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_answers);
//        myToolbar.setNavigationIcon(drawable);
//        getSupportActionBar().setNavigationMode();



        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);

//
//        if (SELECTED_TEST_POSITION_ID == null || !(isStartedFirst))
//        {
//
//            testViewModel.fetchTestList();
////            isFirstStarted = true;
//        }






        final Observer<List<Test>> listObserver = new Observer<List<Test>>() {

            @Override
            public void onChanged(@Nullable List<Test> tests)
            {

//                System.out.println(tests);
//                tests =
                if (!isStartedFirst)
                {
                    tests = getTests();
                }
                if (tests != null && tests.size() > 0)
                {
//                    setContentView(R.layout.activity_test);
//                    mPager = (CustomViewPager) findViewById(R.id.pager);
//                    mPager.setOffscreenPageLimit(2);
////                    if (isStartedFirst)
////                    {
//
//                    mPager.setAdapter( new MyAdapter(getSupportFragmentManager(), tests));
////                    }
//
//                    myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//                    myToolbar.setTitle("");
//                    setSupportActionBar(myToolbar);

                }
                if (!isStartedFirst)
                {
                    mPager.setCurrentItem(SELECTED_TEST_POSITION_ID);
                }

            }


        };




//        if(isStartedFirst)
//        {
        testViewModel.getTests().observe(this, listObserver);
//        }


        activityTestBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_test);

        activityTestBinding.setTest(testViewModel);



//        getE




    }

    public int getPosition()
    {
        return mPager.getCurrentItem();
    }


    public void setPosition (int pos)
    {
        mPager.setCurrentItem(pos);
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


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}

