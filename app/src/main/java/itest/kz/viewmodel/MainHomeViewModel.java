package itest.kz.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.util.TestsUtils;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.MainHomeActivity;

import static android.content.Context.MODE_PRIVATE;

public class MainHomeViewModel extends BaseObservable
{
    private Context context;
    private String accessToken;
    String language;
    private Long testIdMain;
    private SharedPreferences sharedPreferences;
    private TextView dialogText;
    private Button buttonYes;
    private Button buttonNo;

    public MainHomeViewModel(Context context, String accessToken)
    {
        this.context = context;
        this.accessToken = accessToken;
        SharedPreferences lang = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = lang.getString(Constant.LANG, "kz");
        checkActiveTest();
    }

    public void checkActiveTest() {
        if (CheckUtility.isNetworkConnected(context)) {

            AppController appController = new AppController();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            SubjectService subjectService = appController.getSubjectService(accessToken);
            Disposable disposable = subjectService.getActiveTest(
                    language)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Consumer<JsonObject>() {
                                @Override
                                public void accept(JsonObject jsonObject) throws Exception {
                                    JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                    if (jsonObject1.has("data")) {
                                        JSONObject data = jsonObject1.getJSONObject("data");
                                        if (data.has("id")) {
                                            testIdMain = data.getLong("id");
                                            fetchFullTestQuestionsGenerate(testIdMain);
                                        }
//                                        } else {
//                                            Intent intent = new Intent(context, MainHomeActivity.class);
//                                            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                            context.startActivity(intent);

//                                        }


                                    }
//                                    else {
//                                        Intent intent = new Intent(context, MainHomeActivity.class);
//                                        intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                        context.startActivity(intent);
//                                    }
                                }
                            }

                            ,
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
//                                    Intent intent = new Intent(context, MainHomeActivity.class);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                }
                            }
                    );
            compositeDisposable.add(disposable);
        } else {
            Toast toast = Toast.makeText(context,
                    R.string.server_error_alert, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

        public void fetchFullTestQuestionsGenerate(Long id)
        {

            AppController appController = new AppController();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
            SubjectService subjectService = appController.getSubjectService(accessToken);

            Disposable disposable = subjectService.getQuestions(
                    language,
                    id)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<JsonObject>() {
                                   @Override
                                   public void accept(JsonObject jsonObject) throws Exception {

//                                   System.out.println(jsonObject.toString());
                                       List<Subject> subjectList = new ArrayList<>();
                                       JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                       JSONObject data = jsonObject1.getJSONObject("data");
                                       if (data.has("tests"))
                                       {
                                           ArrayList<Tests> questions =
                                                   TestsUtils.deserializeFromJson(jsonObject);
                                           for (Tests t : questions) {
                                               subjectList.add(t.getSubject());
                                           }
                                           showToastHaveActiveTest(subjectList);
                                       }
//                                       else
//                                       {
//                                           Intent intent = new Intent(context, MainHomeActivity.class);
//                                           intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                           context.startActivity(intent);
//
////
//                                       }
//
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception
                                {
//                                Tests questions =
//                                        TestsUtils.deserializeFromJsonToTests
//                                                ()
//                                    Intent intent = new Intent(context, MainHomeActivity.class);
//                                    intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
//                                    context.startActivity(intent);
                                }
                            }
                    );

            compositeDisposable.add(disposable);

    }

    public void showToastHaveActiveTest(List<Subject> subjectList)
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(context);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                openTestActivity(subjectList);
//                    finishTest(testIdMain);
                //System.out.println(testIdMain);//103080954
//                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogText = dialog.findViewById(R.id.dialog_text);
        buttonYes = dialog.findViewById(R.id.buttonOk);
        buttonNo = dialog.findViewById(R.id.buttonCancel);
        buttonNo.setVisibility(View.GONE);
        buttonYes.setText(R.string.ok);

        dialogText.setText(R.string.hasActiveTestDialog);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYes.setEnabled(false);
                dialog.dismiss();
                openTestActivity(subjectList);
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

    private void openTestActivity(List<Subject> subjectList)
    {
        Intent intent = new Intent(context, FullTestActivity.class);
        sharedPreferences = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        setAccessToken();
        intent.putExtra(Constant.IS_STARTED_FIRST, false);
        intent.putExtra(Constant.SUBJECT_LIST, (ArrayList<Subject>) subjectList);
        intent.putExtra(Constant.TEST_MAIN_ID, testIdMain);
        intent.putExtra(Constant.hasActiveTest, true);
//                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    public void setAccessToken()
    {
//        this.accessToken = getIntent().getStringExtra(Constant.ACCESS_TOKEN);
//        if (accessToken == null || accessToken.equals(""))
//        {
//            finish();
//
//        }
//        else
//        {
//            System.out.println("Access ");
//            System.out.println(accessToken);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.putString(Constant.ACCESS_TOKEN, accessToken);
        editor.apply();
        editor.commit();

//            System.out.println(sharedPreferences.getString(Constant.ACCESS_TOKEN, null));

    }

}
