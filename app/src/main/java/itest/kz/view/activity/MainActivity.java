package itest.kz.view.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import itest.kz.R;
import itest.kz.databinding.ActivityMainBinding;
import itest.kz.util.Constant;
import itest.kz.util.FragmentHelper;
import itest.kz.view.fragments.LoginFragment;
import itest.kz.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity
{
    private MainViewModel mainViewModel;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    @Override public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_github) {
////            startActivityActionView();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

//        Toast toast = Toast.makeText(getApplicationContext(),
//                "This is " + value,
//                Toast.LENGTH_SHORT);
//
//        toast.show();

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        activityMainBinding.setMainViewModel(mainViewModel);


//        System.out.println("Access_token");
//        System.out.println(value);


    }

}
