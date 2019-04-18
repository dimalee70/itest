package itest.kz.viewmodel;

import android.content.Context;

import java.util.List;
import java.util.Observable;

import itest.kz.model.Question;
import itest.kz.model.Tests;

public class OneFromFullResultViewModel extends Observable
{
    private Context context;
    private Tests tests;

    public OneFromFullResultViewModel(Context context, Tests tests)
    {
        this.context = context;
        this.tests = tests;
    }


    public String getTitle()
    {
        return tests.getSubject().getTitle();
    }



}
