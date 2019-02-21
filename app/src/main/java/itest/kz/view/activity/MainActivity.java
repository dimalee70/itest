package itest.kz.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import itest.kz.R;
import itest.kz.databinding.ActivityMainBinding;
import itest.kz.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity
{
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        activityMainBinding.setMainViewModel(mainViewModel);

    }

}
