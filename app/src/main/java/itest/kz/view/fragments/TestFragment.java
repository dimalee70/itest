package itest.kz.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.FragmentTestBinding;
import itest.kz.model.Answer;
import itest.kz.model.Test;
import itest.kz.view.activity.ResultActivity;
import itest.kz.view.activity.TestActivity;
import itest.kz.view.adapters.AnswerAdapter;
import itest.kz.view.adapters.TestAdapter;
import itest.kz.viewmodel.TestFragmentViewModel;

public class TestFragment extends Fragment implements Observer
{
    private boolean isStarted = false;
    int mLastSelectedIndex = -1;
    private FragmentTestBinding fragmentTestBinding;
    private TestFragmentViewModel testFragmentViewModel;
    private List<Test> testList;
    private Test test;

    public boolean isStartedRecycle = false;


    public Test getTest()
    {
        return test;
    }

    public void setTest(Test test)
    {
        this.test = test;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.main, menu);
//    }

    //    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        if (!isStarted)
//        {
//            super.onCreate(savedInstanceState);
//            this.test = (Test) getArguments().getSerializable("test");
////        tests = savedInstanceState.getParcelableArrayList("tests");
//
//            this.testList = getArguments().getParcelableArrayList("tests");
//            isStarted = true;
//        }

//        System.out.println(testList);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_github:

                Intent intent = new Intent(getContext(), ResultActivity.class);
//            intent.putExtra("test",(Serializable) test);
                intent.putExtra("tests", (ArrayList<Test>) testList);
//                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//            container.onInterceptTouchEvent(MotionEvent.ACTION_DOWN);

//        Toolbar myToolbar = (Toolbar) this.findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        if (!isStarted) {
            this.test = (Test) getArguments().getSerializable("test");
//        tests = savedInstanceState.getParcelableArrayList("tests");

            this.testList = getArguments().getParcelableArrayList("tests");
            fragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test,
                    container, false);
            testFragmentViewModel = new TestFragmentViewModel(getContext(), test);
            fragmentTestBinding.setTest(testFragmentViewModel);




//            ViewConfiguration vc = ViewConfiguration.get(getContext());

//          Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//          Toolbar t = inflater.inflate(R.menu.main, );
//            setSupportActionBar(myToolbar);

            setHasOptionsMenu(true);

            setUpListOfAnswersView(fragmentTestBinding.answerList);
            setUpObserver(testFragmentViewModel);
//            notify();
//            isStarted = true;
//        }
        TestActivity testActivity = ((TestActivity)getActivity());
//        System.out.println("Position");
//        System.out.println(testActivity.getPosition());
//        if (testActivity.getPosition() == 4)
//        {
//            Intent intent = new Intent(getContext(), ResultActivity.class);
////            intent.putExtra("test",(Serializable) test);
//            intent.putExtra("tests", (ArrayList<Test>) testList);
//            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(intent);
//        }
        return fragmentTestBinding.getRoot();

    }


//    @Override public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_github) {
//            startActivityActionView();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void setUpListOfAnswersView(RecyclerView listAnswers)
    {
//        Answer testAdapter = new TestAdapter();

//        if (!isStartedRecycle)
//        {

            AnswerAdapter answerAdapter = new AnswerAdapter();
            List<Answer> answers = test.getAnswers();
            answerAdapter.setAnswerList(answers);

//        dialeecyclerView.post(new Runnable()
//        {
//            @Override
//            public void run() {
//                myadapter.notifyDataSetChanged();
//            }
//        });
//            listAnswers.

//            answerAdapter.


//            answerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
//            {
//                @Override
//                public void onChanged()
//                {
//                    super.onChanged();
//                }
//            });

//            answerAdapter.setTouchListener(new AnswerAdapter.OnItemTouchListener() {
//                @Override
//                public void OnItemTouch(Answer item, List<Answer> answerList)
//                {
//                                        System.out.println("Touch");
////                    if (isStartedRecycle) {
//                        for (Answer a : answerList) {
//                            a.setAnswerResponce(null);
//                            item.setAnswerResponce(item.getId());
//                        }
//
//                        setUpListOfAnswersView(fragmentTestBinding.answerList);
////                    }
//                    item.setAnswerResponce(item.getId());
//                    isStartedRecycle = true;
//                }
//            });
            answerAdapter.setOnItemListener(new AnswerAdapter. OnItemClickListener()
            {

                @Override
                public void onItemClick(Answer item, List<Answer> answerList) {

//                    isStartedRecycle &&
                    if (isStartedRecycle && item.getAnswerResponce() == null)
                    {
                        for (Answer a : answerList) {
                            a.setAnswerResponce(null);

                        }
                        item.setAnswerResponce(item.getId());

                        setUpListOfAnswersView(fragmentTestBinding.answerList);
                    }
                    else if (item.getAnswerResponce() != null)
                    {
                        item.setAnswerResponce(null);
                        setUpListOfAnswersView(fragmentTestBinding.answerList);
                    }
                    else
                    {
                        for (Answer a : answerList) {
                            a.setAnswerResponce(null);

                        }
                        item.setAnswerResponce(item.getId());
                        setUpListOfAnswersView(fragmentTestBinding.answerList);
                        isStartedRecycle = true;
                    }
                }
            });


//            answerAdapter.notifyDataSetChanged();
//            listAnswers.post(new Runnable()
//            {
//                @Override
//                public void run() {
//                    answerAdapter.notifyDataSetChanged();
//                }
//            });
            listAnswers.setAdapter(answerAdapter);

//            answerAdapter.notifyDataSetChanged();
//        listAnswers.setAdapter(testAdapter);
            listAnswers.setLayoutManager(new LinearLayoutManager(getContext()));

//        }
//

//            listAnswers.onInterceptTouchEvent();


//            isStarted = true;
//            listAnswers.setOnClickListener(new AdapterView.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//                    System.out.println("View Click");
//                    TestActivity testActivity = ((TestActivity)getActivity());
//                    if (testActivity.getPosition() == 4)
//                    {
//                        Intent intent = new Intent(getContext(), ResultActivity.class);
//                        intent.putExtra("test",(Serializable) test);
//                        intent.putExtra("tests", (ArrayList<Test>) testList);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivity(intent);
//                    }
//                }
//            });

            //        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                TestActivity testActivity = ((TestActivity)getActivity());
//                Answer a = (Answer) parent.getItemAtPosition(position);
//
//                if (testActivity.getPosition() == 4)
//                {
////                    TestActivity.newAnswers.add(a);
//                    a.setAnswerResponce( a.getId());
//
////                    Intent parentIntent = NavUtils.getParentActivityIntent(getActivity());
////                    parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////
////                    startActivity(parentIntent);
////                    finish();
//
//                    Intent intent = new Intent(getContext(), ResultActivity.class);
//                    intent.putExtra("test",(Serializable) test);
//                    intent.putExtra("tests", (ArrayList<Test>) tests);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
////                    getActivity().finish();
//
////
//                }
//
//
//                else
//                {
////                    TestActivity.newAnswers.add(a);
//                    a.setAnswerResponce(a.getId());
//                    System.out.println(a.getAnswerResponce());
////                    Intent intent = new Intent(getContext(), ResultActivity.class);
////                    intent.putExtra("test",(Serializable) test);
////                    intent.putExtra("tests", (ArrayList<Test>) tests);
////                    startActivity(intent);
//
//
//
//
////                    System.out.println("click!");
//                }

//        }



    }

    public void setUpObserver(Observable observable)
    {
        observable.addObserver(this);
    }

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
    public void update(Observable o, Object arg)
    {
        if (o instanceof TestFragmentViewModel)
        {
            TestAdapter testAdapter = (TestAdapter)
                fragmentTestBinding.answerList.getAdapter();

            TestFragmentViewModel testFragmentViewModel =
                    (TestFragmentViewModel) o;
            testAdapter.setAnswerList(testFragmentViewModel.getAnswers());

        }
    }

//    public  int

























//    private FragmentTestBinding fragmentTestBinding;
//    private TestFragmentViewModel testFragmentViewModel;
//    private List<Test> tests;
//    private ViewPager viewPager;
////    private TestAdapter testAdapter;
//
//    public int fragVal;
//    Test test;
//    MathView formula_two;
//    String tex = "This come from string. You can insert inline formula:" +
//            " \\(ax^2 + bx + c = 0\\) " +
//            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$";
//
//    public TestFragment()
//    {
//
//    }
////    public TestFragment(ViewPager viewPager)
////    {
////        this.viewPager = viewPager;
////    }
//
//
//    public static TestFragment newInstance(int val, List<Test> tests)
//    {
//
//        Bundle args = new Bundle();
//        args.putInt("val", val);
//        TestFragment fragment = new TestFragment();
//        fragment.setArguments(args);
//        args.putSerializable("test", tests.get(val));
//        args.putParcelableArrayList("tests", (ArrayList<Test>) tests);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        fragVal = getArguments().get("val") != null ? getArguments().getInt("val") : 1;
//        test = (Test) getArguments().getSerializable("test");
////        tests = savedInstanceState.getParcelableArrayList("tests");
//
//        tests = getArguments().getParcelableArrayList("tests");
////        books = savedInstanceState.getParcelableArrayList(“books”);
//
//
//
////        final
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//
//
//        fragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test,container, false);
//        testFragmentViewModel = new TestFragmentViewModel(getContext(), test);
//        fragmentTestBinding.setTest(testFragmentViewModel);
//
////        setUpListOfAnswersView(fragmentTestBinding.answerList);
//        setUpObserver(testFragmentViewModel);
//
//
//
//        // Construct the data source
////        List<Answer> arrayOfAnswers = new test.getAnswers();
//// Create the adapter to convert the array to views
//
//
//
//
//
//
//
//        AnswerAdapter adapter = new AnswerAdapter(this.getContext(), test.getAnswers());
//// Attach the adapter to a ListView








//        WebView browser = (WebView) fragmentTestBinding.getRoot().findViewById(R.id.webview);
//
//        browser.loadData(Constant.MATHJAX + test.getQuestion(), "text/html; charset=utf-8", "UTF-8");







//
//        ListView listView = (ListView) fragmentTestBinding.getRoot().findViewById(R.id.answer_list);
//        listView.setClickable(true);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                TestActivity testActivity = ((TestActivity)getActivity());
//                Answer a = (Answer) parent.getItemAtPosition(position);
//
//                if (testActivity.getPosition() == 4)
//                {
////                    TestActivity.newAnswers.add(a);
//                    a.setAnswerResponce( a.getId());
//
////                    Intent parentIntent = NavUtils.getParentActivityIntent(getActivity());
////                    parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////
////                    startActivity(parentIntent);
////                    finish();
//
//                    Intent intent = new Intent(getContext(), ResultActivity.class);
//                    intent.putExtra("test",(Serializable) test);
//                    intent.putExtra("tests", (ArrayList<Test>) tests);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
////                    getActivity().finish();
//
////
//                }
//
//
//                else
//                {
////                    TestActivity.newAnswers.add(a);
//                    a.setAnswerResponce(a.getId());
//                    System.out.println(a.getAnswerResponce());
////                    Intent intent = new Intent(getContext(), ResultActivity.class);
////                    intent.putExtra("test",(Serializable) test);
////                    intent.putExtra("tests", (ArrayList<Test>) tests);
////                    startActivity(intent);
//
//
//
//
////                    System.out.println("click!");
//                }


















//                System.out.println("Test Click");
//                System.out.println(testActivity.getPosition());
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
//            }

//        });
//        listView.setAdapter(adapter);







//        System.out.println(test.getQuestion());

//        <p>Укажите пары противоположных сторон четырёхугольника.</p>
//    <p><img alt="" src="/upload/images/1425288728.6612.jpeg.png" style="height:69px; width:120px" /></p>
//          <p>Стороны прямоугольника равны 15 и 20 см. На сколько процентов увеличится его площадь, если каждую сторону прямоугольника увеличить на 20%?</p>

//        return fragmentTestBinding.getRoot();





//    }



//    private void setUpListOfAnswersView(RecyclerView listAnswers) {
//        TestAdapter testAdapter = new TestAdapter();
//        listAnswers.setAdapter(testAdapter);
//        listAnswers.setLayoutManager(new LinearLayoutManager(getContext()));
//    }
//
//    public void setUpObserver(Observable observable)
//    {
//        observable.addObserver(this);
//    }
//
//    @Override
//    public void update(Observable o, Object arg)
//    {
//        if (o instanceof TestFragmentViewModel)
//        {
//            TestAdapter testAdapter = (TestAdapter) fragmentTestBinding
//                    .answerList.getAdapter();
//            TestFragmentViewModel testFragmentViewModel
//                    = (TestFragmentViewModel) o;
//            testAdapter.setAnswerList(testFragmentViewModel.getAnswers());
//        }
//
//    }
}
