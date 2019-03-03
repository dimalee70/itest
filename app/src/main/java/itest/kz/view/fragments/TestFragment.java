package itest.kz.view.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import android.arch.lifecycle.Observer;

import io.github.kexanie.library.MathView;
import itest.kz.R;
import itest.kz.databinding.FragmentTestBinding;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.adapters.AnswerAdapter;
import itest.kz.viewmodel.TestFragmentViewModel;

public class TestFragment extends Fragment
{

//    public  int


    private FragmentTestBinding fragmentTestBinding;
    private TestFragmentViewModel testFragmentViewModel;


    int fragVal;
    Test test;
    MathView formula_two;
    String tex = "This come from string. You can insert inline formula:" +
            " \\(ax^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$";

    public TestFragment()
    {

    }

    public static TestFragment newInstance(int val, List<Test> tests)
    {

        Bundle args = new Bundle();
        args.putInt("val", val);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        args.putSerializable("test", tests.get(val));
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        fragVal = getArguments().get("val") != null ? getArguments().getInt("val") : 1;
        test = (Test) getArguments().getSerializable("test");



//        final
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        fragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test,container, false);
        testFragmentViewModel = new TestFragmentViewModel(getContext(), test);
        fragmentTestBinding.setTest(testFragmentViewModel);

        // Construct the data source
//        List<Answer> arrayOfAnswers = new test.getAnswers();
// Create the adapter to convert the array to views







        AnswerAdapter adapter = new AnswerAdapter(this.getContext(), test.getAnswers());
// Attach the adapter to a ListView







//        formula_two = (MathView) fragmentTestBinding.getRoot().findViewById(R.id.formula_two);
//        formula_two.setText(test.getQuestion());

        WebView browser = (WebView) fragmentTestBinding.getRoot().findViewById(R.id.webview);
////        web.getSettings().setDomStorageEnabled(true);
//        browser.getSettings().setDomStorageEnabled(true);
//
////        browser.loadUrl("http://www.tutorialspoint.com");
//        browser.getSettings().setJavaScriptEnabled(true);
//
//        browser.getSettings().setAppCacheEnabled(true);
//        browser.getSettings().setLoadsImagesAutomatically(true);
//        browser.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        browser.loadData(Constant.MATHJAX + test.getQuestion(), "text/html; charset=utf-8", "UTF-8");








        ListView listView = (ListView) fragmentTestBinding.getRoot().findViewById(R.id.answer_list);
        listView.setAdapter(adapter);





//        System.out.println(test.getQuestion());

//        <p>Укажите пары противоположных сторон четырёхугольника.</p>
//    <p><img alt="" src="/upload/images/1425288728.6612.jpeg.png" style="height:69px; width:120px" /></p>
//          <p>Стороны прямоугольника равны 15 и 20 см. На сколько процентов увеличится его площадь, если каждую сторону прямоугольника увеличить на 20%?</p>

        return fragmentTestBinding.getRoot();






//        return super.onCreateView(inflater, container, savedInstanceState);
//        View layoutView = inflater.inflate(R.layout.fragment_test, container,
//                false);
//        View tv = layoutView.findViewById(R.id.text);
//        ((TextView) tv).setText("Truiton Fragment # " + fragVal);
//        return layoutView;
    }

    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState)
//    {
//        super.onActivityCreated(savedInstanceState);
//        setListAdapter(new );
//    }
}
