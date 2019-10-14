package itest.kz.viewmodel.actions;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import id.co.flipbox.sosoito.LoadingLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.LoginResponse;
import itest.kz.model.RegisterResponse;
import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.network.SubjectService;
import itest.kz.network.UserService;
import itest.kz.util.CheckUtility;
import itest.kz.util.Constant;
import itest.kz.util.TestsUtils;
import itest.kz.view.activity.AuthActivity;
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainHomeActivity;
import itest.kz.view.activity.TestActivity;
import jp.wasabeef.picasso.transformations.internal.Utils;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;

public class LoginAction implements Action
{
    private Context context;
    private ObservableField<String> login;
    private  ObservableField<String> password;
    private String language;
    private SharedPreferences settings;
    private LoadingLayout loginLayout;
    public String accessToken;
    private Long testIdMain;
    private SharedPreferences sharedPreferences;
    private TextView dialogText;
    private Button buttonYes;
    private Button buttonNo;

    public LoginAction(Context context,
                       ObservableField<String> login,
                       ObservableField<String> password)
    {
        this.context = context;
        this.login = login;
        this.password = password;
        sharedPreferences = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
        settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
        language = settings.getString(Constant.LANG, "kz");
    }

    @Override
    public void run() throws Exception
    {
        loginLayout = ((Activity) context).findViewById(R.id.loginLoading);
        loginLayout.showCustomLoading(true, R.drawable.ic_itest_logo_larger);

        if (CheckUtility.isNetworkConnected(context))
        {
//            setIsLoading(true);
            AppController appController = new AppController();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            UserService userService = appController.getUserService();
            Disposable disposable = userService.login(language,
                    login.get(),
                    password.get())
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LoginResponse>() {
                                   @Override
                                   public void accept(LoginResponse loginResponse) throws Exception {

                                       if (loginResponse.getAccessToken() == null
                                               || loginResponse.getAccessToken() == "") {
                                           loginLayout.showCustomLoading(false);
                                           Toast toast = Toast.makeText(context,
                                                   loginResponse.getError(), Toast.LENGTH_SHORT);
                                           toast.show();
                                       } else {
//                            Intent intent = new Intent(context, HomeActivity.class);
//                            intent.putExtra(Constant.ACCESS_TOKEN, loginResponse.getAccessToken());
//                            context.startActivity(intent);
                                           accessToken = loginResponse.getAccessToken();
//                                           setAccessToken();
//                                           System.out.println(accessToken);
                                           checkActiveTest();


//                                           setIsLoading(false);
                                       }


                                   }

                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (throwable.getMessage().contains("401")) {
                                        showToastUnauthorized();
                                    }
                                    else
                                    {
                                        loginLayout.showCustomLoading(false);
                                        Intent intent = new Intent(context, MainHomeActivity.class);
                                        intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                    }
//                                    System.out.println("error");

//                                System.out.println(throwable.getLocalizedMessage());
//                                System.out.println(throwable.getMessage());

                                }
                            }
                            );
            compositeDisposable.add(disposable);
        }

        else
        {
            loginLayout.showCustomLoading(false);
            Toast toast = Toast.makeText(context,
                    "Нету Интернета", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void showToastUnauthorized()
    {

        Dialog dialog = new Dialog(context);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                openAuthActivity();

            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogText = dialog.findViewById(R.id.dialog_text);
        buttonYes = dialog.findViewById(R.id.buttonOk);
        buttonNo = dialog.findViewById(R.id.buttonCancel);
        buttonNo.setVisibility(View.GONE);
        buttonYes.setText(R.string.ok);


        dialogText.setText(R.string.sessionError);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                buttonYes.setEnabled(false);
                dialog.dismiss();
                openAuthActivity();

            }
        });

        dialog.show();
    }

    public  void openAuthActivity()
    {
        Intent intent = new Intent(context, AuthActivity.class);
        ((Activity)context).startActivity(intent);
    }

    public void checkActiveTest()
    {
        if (CheckUtility.isNetworkConnected(context))
        {

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
                                public void accept(JsonObject jsonObject) throws Exception
                                {
                                    JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                    if (jsonObject1.has("data"))
                                    {
                                        JSONObject data = jsonObject1.getJSONObject("data");
                                        if (data.has("id"))
                                        {
                                            testIdMain = data.getLong("id");
                                            fetchFullTestQuestionsGenerate(testIdMain);
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(context, MainHomeActivity.class);
                                            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                            context.startActivity(intent);

                                        }


                                    }
                                    else
                                    {
                                        Intent intent = new Intent(context, MainHomeActivity.class);
                                        intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                        context.startActivity(intent);
                                        loginLayout.showCustomLoading(false);
                                    }
                                    loginLayout.showCustomLoading(false);
                                    }
                                }

                            ,
                            new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception
                                        {
                                            loginLayout.showCustomLoading(false);
                                            Intent intent = new Intent(context, MainHomeActivity.class);
                                            intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                        }
                                    }
                    );
            loginLayout.showCustomLoading(false);
            compositeDisposable.add(disposable);
        }

        else
        {
            loginLayout.showCustomLoading(false);
            Toast toast = Toast.makeText(context,
                    "Нету Интернета", Toast.LENGTH_SHORT);
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
                                   else
                                   {
                                       Intent intent = new Intent(context, MainHomeActivity.class);
                                       intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                       context.startActivity(intent);
                                       loginLayout.showCustomLoading(false);

//
                                   }
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
                                Intent intent = new Intent(context, MainHomeActivity.class);
                                intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                context.startActivity(intent);
                                loginLayout.showCustomLoading(false);
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
        loginLayout.showCustomLoading(false);
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
