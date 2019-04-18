package itest.kz.view.adapters;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.TestGenerate;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.fragments.OneFromFullResultFragment;

import static android.content.Context.MODE_PRIVATE;

public class FullTestResultAdapter extends FragmentStatePagerAdapter
{
    private List<Tests> tests;
    private Long testIdMain;
    private String accessToken;
    private  int NUM;
    private ArrayList<Tests> arrayListArrayListQuestions;
    private ArrayList<Subject> subjectList;
    private Integer currentPosition;
    private String resultTag;
    private String typeTest;
    private Subject selectedSubject;

    public  FullTestResultAdapter(FragmentManager fm,
                                  ArrayList<Tests> arrayListArrayListQuestions, Long testIdMain,
                                  ArrayList<Subject> subjectList,
                                  Integer currentPosition, String resultTag, String typeTest,
                                  Subject selectedSubject
                                  )
    {
        super(fm);
        this.currentPosition = currentPosition;
        this.testIdMain = testIdMain;
        this.accessToken = accessToken;
        this.subjectList = subjectList;
        this.arrayListArrayListQuestions = arrayListArrayListQuestions;
        this.NUM = arrayListArrayListQuestions.size();
        this.resultTag = resultTag;
        this.typeTest = typeTest;
        this.selectedSubject = selectedSubject;
    }

    public Integer getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    @Override
    public Fragment getItem(int i)
    {
        if (i >= 0 && i < NUM )
        {
            return OneFromFullResultFragment.newInstance(arrayListArrayListQuestions.get(i), testIdMain,i,
                    subjectList, resultTag, typeTest, selectedSubject);
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return arrayListArrayListQuestions.size();
    }
    @Override
    public Parcelable saveState() {
        Bundle bundle = (Bundle) super.saveState();
        bundle.putParcelableArray("states", null); // Never maintain any states from the base class, just null it out
        return bundle;
    }

}
