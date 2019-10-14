package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.Observable;

import itest.kz.R;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class SubjectFragmentActivityViewModel extends Observable
{
    private String language;
    private Context context;

    List<Fragment> mFragments;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    public SubjectFragmentActivityViewModel(Context context)
    {
        this.context = context;
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

    }

    public int getToolbarTitle()
    {
        return R.string.subjects;
    }

    public String getLanguage()
    {
        return language;
    }
}
