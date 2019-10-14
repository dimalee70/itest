package itest.kz.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.databinding.ActivityResultBinding;
import itest.kz.model.Question;
import itest.kz.model.Subject;
import itest.kz.model.Test;
import itest.kz.model.TestGenerateCredentials;
import itest.kz.model.TestGenerateResponse;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.util.DividerItemDecoration;
import itest.kz.util.TestsUtils;
import itest.kz.util.VerticalSpaceItemDecoration;
import itest.kz.view.adapters.ResultAdapter;
import itest.kz.view.adapters.SubjectAdapter;
import itest.kz.viewmodel.ResultViewModel;

public class ResultActivity extends AppCompatActivity implements Observer
{

    public ResultViewModel resultViewModel;
    public ActivityResultBinding activityResultBinding;
    private Tests tests;
    private Test test;
    private String resultTag;
    private Subject selectedSubject;
    private Long testIdMain;
    private String language;
    private String accessToken;
    private Toolbar toolbar;
    private ImageButton buttonCloseResult;
    private String typeTest;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");

        SharedPreferences acessTok = getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = acessTok.getString(Constant.ACCESS_TOKEN, "");
//        test = (Test)getIntent().getSerializableExtra("test");
        typeTest = getIntent().getExtras().getString(Constant.TYPE);
        tests = (Tests)getIntent().getSerializableExtra("tests");
        testIdMain = getIntent().getExtras().getLong(Constant.TEST_MAIN_ID);
        selectedSubject =(Subject) getIntent().getSerializableExtra(Constant.SELECTED_SUBJECT);

//        System.out.println(selectedSubject);
        resultTag = getIntent().getExtras().getString(Constant.RESULT_TAG, null);



        activityResultBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_result);
        if (tests != null)
        {
            setR(activityResultBinding.listAnswers);
        }
        else
        {
            fetchFullTestQuestionsGenerate(testIdMain);
        }


//
    }

    private void fetchFullTestQuestionsGenerate(Long testId)
    {
        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService(accessToken);

        Disposable disposable = subjectService.getQuestions(
                language, testId)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

//                                   System.out.println(jsonObject.toString());
                                   Tests questions =
                                           TestsUtils.deserializeFromJsonToTests(jsonObject);
                                   updateTestDataList(questions);
//
//                                   setArraListArrayListQuestions(questions);
////
//                                   Tests arrayList = questions.get(0);
//
//
//////
//                                   setFragment(arrayList);

                               }
                           },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (throwable.getMessage().contains("401"))
                                {
                                    showToastUnauthorized();
                                }
                            }
                        }

//                        new Consumer<JSONObject>() {
//                    @Override
//                    public void accept(JSONObject jsonObject) throws Exception
//                    {
//                        System.out.println("json");
//                        System.out.println(jsonObject.toString());
//                    }
//                }
                );

        compositeDisposable.add(disposable);
    }

//    private TextView dialogTextAuth;
//    private Button buttonYesAuth;
//    private Button buttonNoAuth;

    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTextAuth = dialog.findViewById(R.id.dialog_text);
        buttonYesAuth = dialog.findViewById(R.id.buttonOk);
        buttonNoAuth = dialog.findViewById(R.id.buttonCancel);
        buttonNoAuth.setVisibility(View.GONE);
        buttonYesAuth.setText(R.string.ok);
        dialogTextAuth.setText(R.string.sessionError);
        buttonYesAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYesAuth.setEnabled(false);
                dialog.dismiss();
                openAuthActivity();
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954

            }
        });

//        buttonNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(this, AuthActivity.class);
        ((Activity)this).startActivity(intent);
//        if (language.equals(Constant.KZ))
//
//            Toast.makeText(this,
//                    R.string.sessionErrorKz,
//                    Toast.LENGTH_SHORT).show();
//        else
//        {
//            Toast.makeText(this,
//                    R.string.sessionErrorRu,
//                    Toast.LENGTH_SHORT).show();
//        }
    }


    private void updateTestDataList(Tests questions)
    {
        this.tests = questions;
        setR(activityResultBinding.listAnswers);
    }

    public void setR(RecyclerView recyclerView)
    {
        resultViewModel = new ResultViewModel(this,tests);
        activityResultBinding.setResult(resultViewModel);

        toolbar = activityResultBinding.myToolbar;
//                findViewById(R.id.my_toolbar);
        buttonCloseResult = activityResultBinding.buttonCloseResult;
//                findViewById(R.id.buttonCloseResult);
        buttonCloseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                System.out.println("Close");
//                finish();
                ResultActivity.super.onBackPressed();
            }
        });

        if (selectedSubject == null)
        {
            selectedSubject = tests.getSubject();
        }
        ResultAdapter resultAdapter = new ResultAdapter(tests, this,
                selectedSubject, testIdMain,null,
                null,null,0, resultTag, typeTest);

//        recyclerView.setOnClickListener();
//
//        resultAdapter.setOnItemListener(new ResultAdapter.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(Test test,r )
//            {
//
//            }
//        });
//        resultAdapter.setOnItemListener(new ResultAdapter.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(Test test, List<Test> testList)
//            {
//                tests = testList;
//                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
//////        intent.putExtra()
//                intent.putExtra(Constant.SELECTED_TEST_POSITION_ID, num );
//////        context.startActivity(TestActivity.fillSelectedSubject(view.etContext(), subject));
//////                StartActivityForResult(intent, 0);
////                context.startActivity(intent);
//            }
//        });
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
//        layoutManager.setFlexWrap(FlexWrap.WRAP);


//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
//                R.dimen.dimen_offset);
//        recyclerView.addItemDecoration(dividerItemDecoration);

//        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration(50);
        recyclerView.setAdapter(resultAdapter);
//        recyclerView.addItemDecoration(verticalSpaceItemDecoration);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void update(Observable o, Object arg)
    {

    }

    @Override
    public void onBackPressed()
    {
//        Intent parentIntent = NavUtils.getParentActivityIntent(this);
////        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(parentIntent);
//        finish();
//        super.onBackPressed();
    }
}
