package itest.kz.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import itest.kz.R;
import itest.kz.databinding.ActivityTestBinding;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.viewmodel.TestViewModel;

public class TestActivity extends FragmentActivity
{
    public ActivityTestBinding activityTestBinding;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityTestBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_test);
        getExtrasFromIntent();

//        activityTestBinding.getTest().getTestList()
    }

    private void getExtrasFromIntent()
    {
        Subject subject = (Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);
        TestViewModel testViewModel = new TestViewModel(this, subject);
        activityTestBinding.setTest(testViewModel);
    }

    public static Intent fillSelectedSubject(Context context, Subject subject)
    {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra(Constant.SELECTED_SUBJECT, subject);

        return intent;
    }
}
