package itest.kz.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.ActivityResultBinding;
import itest.kz.model.Test;
import itest.kz.view.adapters.ResultAdapter;
import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.viewmodel.ResultViewModel;

public class ResultActivity extends AppCompatActivity implements Observer
{

    public ResultViewModel resultViewModel;
    public ActivityResultBinding activityResultBinding;
    private List<Test> tests;
    private Test test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        test = (Test)getIntent().getSerializableExtra("test");
        tests = getIntent().getParcelableArrayListExtra("tests");

        activityResultBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_result);
        setR(activityResultBinding.listAnswers);


//
    }

    public void setR(RecyclerView recyclerView)
    {
        resultViewModel = new ResultViewModel(this,tests);
        activityResultBinding.setResult(resultViewModel);
        ResultAdapter resultAdapter = new ResultAdapter(tests, this);
        recyclerView.setAdapter(resultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void update(Observable o, Object arg)
    {

    }

    @Override
    public void onBackPressed()
    {
//        Intent parentIntent = NavUtils.getParentActivityIntent(this);
////        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(parentIntent);
        finish();
//        super.onBackPressed();
    }
}
