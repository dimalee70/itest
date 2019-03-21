package itest.kz.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import itest.kz.R;
import itest.kz.databinding.ActivitySubjectBinding;
import itest.kz.util.FragmentHelper;
import itest.kz.view.adapters.ViewPagerAdapter;
import itest.kz.view.fragments.CertificationFragment;
import itest.kz.view.fragments.SubjectFragment;
import itest.kz.viewmodel.SubjectViewModel;

public class SubjectActivity extends AppCompatActivity
{
    private SubjectViewModel subjectViewModel;
    List<Fragment> mFragments;
    TabLayout mTabLayout;
    ViewPager mViewPager;

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
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivitySubjectBinding activitySubjectBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_subject);

        subjectViewModel = new SubjectViewModel(this);
        activitySubjectBinding.setSubject(subjectViewModel);

        mFragments = new ArrayList<>();
//        SubjectFragment s = new SubjectFragment();
//        s.
        mFragments.add(new SubjectFragment());
        mFragments.add(new CertificationFragment());

        // do we need to implement databinding on each layout? I don't think so. but feel free to bind the layout if you want to.
        mViewPager = (ViewPager) findViewById(R.id.vp_fragments_container);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));

        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("ENT");
        mTabLayout.getTabAt(1).setText("Subjects");


//        FragmentHelper.addFragment(this, R.id.vp_fragments_container, new SubjectFragment());
//        FragmentHelper.addFragment(this, R.id.vp_fragments_container, new CertificationFragment());



    }
}
