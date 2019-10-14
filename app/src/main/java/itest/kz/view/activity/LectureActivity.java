package itest.kz.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.ActivityLectureBinding;
import itest.kz.model.Lecture;
import itest.kz.model.LectureResponse;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.viewmodel.LectureViewModel;

import static android.content.Context.MODE_PRIVATE;
import static itest.kz.util.AppUtils.setCheckable;

public class LectureActivity extends Fragment implements Observer
{
    private LectureResponse lectureResponse;
    private ActivityLectureBinding activityLectureBinding;
    private LectureViewModel lectureViewModel;
    private Toolbar toolbarLecture;
    private TextView mainToolbarText;
    private String accessToken;
    private BottomNavigationViewEx bottomNavigationView;
    private boolean isStartedFirst = true;
    private String previousActivity;

    public static LectureActivity newInstance(LectureResponse lectureResponse)
    {

        Bundle args = new Bundle();
        args.putSerializable(Constant.SELECTED_LECTURE_RESPONSE, lectureResponse);
        LectureActivity fragment = new LectureActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        this.lectureResponse = (LectureResponse) getArguments().getSerializable(Constant.SELECTED_LECTURE_RESPONSE);
        activityLectureBinding = DataBindingUtil.inflate(inflater,  R.layout.activity_lecture, container,false);
        lectureViewModel = new LectureViewModel(getContext(), lectureResponse);
        activityLectureBinding.setLecture(lectureViewModel);

        setMyToolbar();

        SharedPreferences accessTok = getActivity().getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");

        return activityLectureBinding.getRoot();
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        this.activityLectureBinding =
//                DataBindingUtil.setContentView(this, R.layout.activity_lecture);
//
//        getExtrasFromIntent();
//
//        lectureViewModel = new LectureViewModel(this, lectureResponse);
//        activityLectureBinding.setLecture(lectureViewModel);
//        setMyToolbar();
//
//        SharedPreferences accessTok = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        accessToken = accessTok.getString(Constant.ACCESS_TOKEN, "");
//        bottomNavigationView =  activityLectureBinding
//                .bottomNavigationViewLecture;
//        bottomNavigationView.enableAnimation(false);
//        bottomNavigationView.setLabelVisibilityMode(1);
//        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
////        bottomNavigationView.getMenu().getItem(0).setChecked(true);
////        bottomNavigationView.getMenu().getItem(1).setChecked(false);
////        bottomNavigationView.getMenu().getItem(2).setChecked(false);
////        setTitle(R.string.mainNavigation);
////        bottomNavigationView.getMenu().getItem(1).setTitle(R.string.statisticNavigation);
////        bottomNavigationView.getMenu().getItem(2).setTitle(R.string.profileNavigation);
//
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
//
//        final View iconView1 = menuView.getChildAt(2).findViewById(android.support.design.R.id.icon);
//        final ViewGroup.LayoutParams layoutParams1 = iconView1.getLayoutParams();
//        final DisplayMetrics displayMetrics1 = getResources().getDisplayMetrics();
//        layoutParams1.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics1);
//        layoutParams1.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics1);
//        iconView1.setLayoutParams(layoutParams1);
//
//
//        final View iconView = menuView.getChildAt(0).findViewById(android.support.design.R.id.icon);
//        final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
//        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics);
//        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
//        iconView.setLayoutParams(layoutParams);
//
//
//
//
////        bottomNavigationView.getMenu().findItem(R.id.item_test).setChecked(false);
////
////        bottomNavigationView.getMenu().findItem(R.id.item_statistic).setChecked(true);
////        bottomNavigationView.getMenu().findItem(R.id.item_user).setChecked(false);
//
//
////        bottomNavigationView.getMenu().getItem(0).setChecked(false);
////        bottomNavigationView.getMenu().getItem(1).setChecked(true);
////        bottomNavigationView.getMenu().getItem(2).setChecked(false);
////
////        bottomNavigationView.setSelectedItemId(R.id.item_test);
//
//        setCheckable(bottomNavigationView, false,0);
////        bottomNavigationView.getMenu().getItem(0).setChecked(true);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
//                    {
//                        Intent intent = null;
//                        switch (menuItem.getItemId())
//                        {
//                            case R.id.item_test:
////                                if (previousActivity != null && previousActivity.equals(Constant.SUBJECT_ACTIVITY))
////                                {
////                                    LectureActivity.super.onBackPressed();
////                                }
////                                else {
////                                if (!isStartedFirst) {
//                                    intent = new Intent(LectureActivity.this, SubjectActivity.class);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                }
////                                isStartedFirst = false;
////                                }
//                                break;
//                            case R.id.item_statistic:
////                                finish();
////                                if (previousActivity != null && previousActivity.equals(Constant.STATISTIC_ACTIVITY))
////                                {
////                                    LectureActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(LectureActivity.this, StatisticActivity.class);
//                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.SUBJECT_ACTIVITY);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                }
//                                break;
//                            case R.id.item_user:
////                                fetchProfileInfo();
////                                finish();
////                                if (previousActivity != null && previousActivity.equals(Constant.PROFILE_ACTIVITY))
////                                {
////                                    LectureActivity.super.onBackPressed();
////                                }
////                                else {
//                                    intent = new Intent(LectureActivity.this, HomeActivity.class);
//                                    intent.putExtra(Constant.FROM_ACTIVITY, Constant.SUBJECT_ACTIVITY);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    startActivity(intent);
////                                fragmentManager.beginTransaction().replace(R.id.viewpager, fragment).commit();
////                                }
//                                break;
//
//                        }
//
//
//                        return true;
//                    }
//                }
//        );
//
//
//
//
//    }
//
//    private void getExtrasFromIntent()
//    {
//        this.lectureResponse = (LectureResponse) getIntent().getSerializableExtra(Constant.SELECTED_LECTURE_RESPONSE);
////        this.previousActivity = getIntent().getStringExtra(Constant.FROM_ACTIVITY);
////        System.out.println(lectureResponse);
//
//    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof LectureViewModel)
        {
            LectureViewModel lectureViewModel =
                    (LectureViewModel) o;
        }
    }

    public void setMyToolbar()
    {
        toolbarLecture = activityLectureBinding
                .toolbarLecture;
        mainToolbarText = activityLectureBinding
                .toolbarTitle;
//        mainToolbarText.setTextColor(Color.WHITE);
        toolbarLecture.setTitle("");
        toolbarLecture.setNavigationIcon(R.drawable.ic_navigation_strelka);
        toolbarLecture.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).findViewById(R.id.viewDisableLayout123).setVisibility(View.GONE);
                getFragmentManager().popBackStack(NodeByNodeActivity.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                getFragmentManager().popBackStack();
            }
        });
    }

//    @Override
//    protected void onResume()
//    {
//        setCheckable(bottomNavigationView, false, 0);
//        super.onResume();
//    }
}
