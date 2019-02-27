package itest.kz.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import itest.kz.R;
import itest.kz.model.Test;

public class TestFragment extends ListFragment
{
//    private ArrayList<Test> tests;
    private int nums;

    private static String[] cheeses = {"Cheddar", "Jack", "Gamonedo", "Lancashire",
            "Limburger", "Pepperjack", "Skyr", "Feta", "Asiago"};

    public static TestFragment newInstance(int i)
    {
//        this.tests = tests;
        TestFragment testFragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt("num", i);
        testFragment.setArguments(args);
        return testFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        nums = getArguments() != null? getArguments().getInt("num"): 1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_test_list, container, false);
        View tv = view.findViewById(R.id.text);
        ((TextView)tv).setText("sdcdsc " + nums);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, cheeses));
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState)
//    {
//        super.onActivityCreated(savedInstanceState);
//        setListAdapter(new );
//    }
}
