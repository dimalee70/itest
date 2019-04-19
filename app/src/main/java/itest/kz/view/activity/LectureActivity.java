package itest.kz.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import itest.kz.R;
import itest.kz.databinding.ActivityLectureBinding;
import itest.kz.model.Lecture;
import itest.kz.model.LectureResponse;
import itest.kz.model.Subject;
import itest.kz.util.Constant;
import itest.kz.viewmodel.LectureViewModel;

public class LectureActivity extends AppCompatActivity implements Observer
{
    private LectureResponse lectureResponse;
    private ActivityLectureBinding activityLectureBinding;
    private LectureViewModel lectureViewModel;
    private Toolbar toolbarLecture;
    private TextView mainToolbarText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.activityLectureBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_lecture);

        getExtrasFromIntent();

        lectureViewModel = new LectureViewModel(this, lectureResponse);
        activityLectureBinding.setLecture(lectureViewModel);
        setMyToolbar();

    }

    private void getExtrasFromIntent()
    {
        this.lectureResponse = (LectureResponse) getIntent().getSerializableExtra(Constant.SELECTED_LECTURE_RESPONSE);
        System.out.println(lectureResponse);

    }

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
        toolbarLecture = (Toolbar) activityLectureBinding
                .toolbarLecture;
        mainToolbarText = (TextView) activityLectureBinding
                .toolbarTitle;
//        mainToolbarText.setTextColor(Color.WHITE);
        toolbarLecture.setTitle("");
        toolbarLecture.setNavigationIcon(R.drawable.ic_navigation_strelka);
        toolbarLecture.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
