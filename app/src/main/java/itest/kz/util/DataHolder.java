package itest.kz.util;

import java.util.ArrayList;
import java.util.List;

import itest.kz.model.Tests;

public class DataHolder
{
    public final List<Tests> arrayListArrayListQuestions = new ArrayList<>();

    public DataHolder() {}

    public static DataHolder getInstance() {
        if( instance == null ) {
            instance = new DataHolder();
        }
        return instance;
    }

    public static DataHolder instance;
}
