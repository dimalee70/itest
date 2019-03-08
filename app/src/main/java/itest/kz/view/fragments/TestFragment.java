package itest.kz.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.github.kexanie.library.MathView;
import itest.kz.R;
import itest.kz.databinding.FragmentTestBinding;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.util.Constant;
import itest.kz.view.activity.MainActivity;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.adapters.AnswerAdapter;
import itest.kz.view.adapters.MyAdapter;
import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.view.adapters.TestAdapter;
import itest.kz.viewmodel.CertificationFragmentViewModel;
import itest.kz.viewmodel.TestFragmentViewModel;

public class TestFragment extends Fragment implements Observer
{

//    public  int


    private FragmentTestBinding fragmentTestBinding;
    private TestFragmentViewModel testFragmentViewModel;
    private List<Test> tests;
    private ViewPager viewPager;
//    private TestAdapter testAdapter;

    public int fragVal;
    Test test;
    MathView formula_two;
    String tex = "This come from string. You can insert inline formula:" +
            " \\(ax^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$";

    public TestFragment()
    {

    }
//    public TestFragment(ViewPager viewPager)
//    {
//        this.viewPager = viewPager;
//    }


    public static TestFragment newInstance(int val, List<Test> tests)
    {

        Bundle args = new Bundle();
        args.putInt("val", val);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        args.putSerializable("test", tests.get(val));
        args.putParcelableArrayList("tests", (ArrayList<Test>) tests);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        fragVal = getArguments().get("val") != null ? getArguments().getInt("val") : 1;
        test = (Test) getArguments().getSerializable("test");
//        tests = savedInstanceState.getParcelableArrayList("tests");

        tests = getArguments().getParcelableArrayList("tests");
//        books = savedInstanceState.getParcelableArrayList(“books”);



//        final
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        fragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test,container, false);
        testFragmentViewModel = new TestFragmentViewModel(getContext(), test);
        fragmentTestBinding.setTest(testFragmentViewModel);

//        setUpListOfAnswersView(fragmentTestBinding.answerList);
        setUpObserver(testFragmentViewModel);



        // Construct the data source
//        List<Answer> arrayOfAnswers = new test.getAnswers();
// Create the adapter to convert the array to views







        AnswerAdapter adapter = new AnswerAdapter(this.getContext(), test.getAnswers());
// Attach the adapter to a ListView








        WebView browser = (WebView) fragmentTestBinding.getRoot().findViewById(R.id.webview);

        browser.loadData(Constant.MATHJAX + test.getQuestion(), "text/html; charset=utf-8", "UTF-8");







//
        ListView listView = (ListView) fragmentTestBinding.getRoot().findViewById(R.id.answer_list);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TestActivity testActivity = ((TestActivity)getActivity());
                Answer a = (Answer) parent.getItemAtPosition(position);

                if (testActivity.getPosition() == 4)
                {
//                    TestActivity.newAnswers.add(a);
                    a.setAnswerResponce( a.getId());

//                    Intent parentIntent = NavUtils.getParentActivityIntent(getActivity());
//                    parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//
//                    startActivity(parentIntent);
//                    finish();

                    Intent intent = new Intent(getContext(), ResultActivity.class);
                    intent.putExtra("test",(Serializable) test);
                    intent.putExtra("tests", (ArrayList<Test>) tests);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                    getActivity().finish();

//
                }


                else
                {
//                    TestActivity.newAnswers.add(a);
                    a.setAnswerResponce(a.getId());
                    System.out.println(a.getAnswerResponce());
//                    Intent intent = new Intent(getContext(), ResultActivity.class);
//                    intent.putExtra("test",(Serializable) test);
//                    intent.putExtra("tests", (ArrayList<Test>) tests);
//                    startActivity(intent);




                    System.out.println("click!");
                }
                System.out.println("Test Click");
                System.out.println(testActivity.getPosition());
//                System.out.println(a.toString());

//                TestActivity.newAnswers.add(a);

//
//                TestActivity testActivity = ((TestActivity)getActivity());
//                testActivity.setPosition
//                        ((testActivity.getPosition() < 0)? 0 : testActivity.getPosition()+1);
//
//                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                view.getContext().get
//                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                parent.ge
//                testFragmentViewModel
            }

        });
        listView.setAdapter(adapter);







//        System.out.println(test.getQuestion());

//        <p>Укажите пары противоположных сторон четырёхугольника.</p>
//    <p><img alt="" src="/upload/images/1425288728.6612.jpeg.png" style="height:69px; width:120px" /></p>
//          <p>Стороны прямоугольника равны 15 и 20 см. На сколько процентов увеличится его площадь, если каждую сторону прямоугольника увеличить на 20%?</p>

        return fragmentTestBinding.getRoot();





    }



    private void setUpListOfAnswersView(RecyclerView listAnswers) {
        TestAdapter testAdapter = new TestAdapter();
        listAnswers.setAdapter(testAdapter);
        listAnswers.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof TestFragmentViewModel)
        {
            TestAdapter testAdapter = (TestAdapter) fragmentTestBinding
                    .answerList.getAdapter();
            TestFragmentViewModel testFragmentViewModel
                    = (TestFragmentViewModel) o;
            testAdapter.setAnswerList(testFragmentViewModel.getAnswers());
        }

    }
}
