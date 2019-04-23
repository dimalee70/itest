package itest.kz.viewmodel.actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONObject;

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
import itest.kz.view.activity.FullTestActivity;
import itest.kz.view.activity.HomeActivity;
import itest.kz.view.activity.MainHomeActivity;
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
//    ObservableBoolean mIsLoading;

    public LoginAction(Context context,
                       ObservableField<String> login,
                       ObservableField<String> password)
    {
        this.context = context;
        this.login = login;
        this.password = password;
//        this.mIsLoading = mIsLoading;
        settings = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = settings.getString(Constant.LANG, "kz");
    }
//    AppController appController = new AppController();
//    CompositeDisposable compositeDisposable = new CompositeDisposable();
//    //        AppController appController = AppController.create(context);
//    UserService userService = appController.getUserService();
//
//    Disposable disposable = userService.register(login.get(), password.get(),
//            confirmPassword.get())
//            .subscribeOn(appController.subscribeScheduler())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Consumer<RegisterResponse>() {
//                @Override
//                public void accept(RegisterResponse registerResponse) throws Exception
//                {
//                    Toast toast;
//                    toast = Toast.makeText(context.getApplicationContext(),
//                            registerResponse.getMessage(),
//                            Toast.LENGTH_LONG);
//                    toast.show();
//                }
//            });
//
//        compositeDisposable.add(disposable);
//    public void setIsLoading(boolean isLoading) {
//        mIsLoading.set(isLoading);
//    }

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
                    Constant.ACCEPT,
                    login.get(),
                    password.get())
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LoginResponse>() {
                                   @Override
                                   public void accept(LoginResponse loginResponse) throws Exception {
//                        SharedPreferences sharedPref = (Activity)context.getPreferences()
////                                context..getPreferences(Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
//                        editor.commit();

                                       if (loginResponse.getAccessToken() == null
                                               || loginResponse.getAccessToken() == "") {
//                                           loginLayout.showCustomLoading(false);
                                           Toast toast = Toast.makeText(context,
                                                   loginResponse.getError(), Toast.LENGTH_SHORT);
                                           toast.show();
                                       } else {
//                            Intent intent = new Intent(context, HomeActivity.class);
//                            intent.putExtra(Constant.ACCESS_TOKEN, loginResponse.getAccessToken());
//                            context.startActivity(intent);
                                           accessToken = loginResponse.getAccessToken();
//                                           System.out.println(accessToken);
                                           checkActiveTest();


//                                           setIsLoading(false);
                                       }


                                   }

                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {

                                }
                            });
            compositeDisposable.add(disposable);
        }

        else
        {
//            loginLayout.showCustomLoading(false);
            Toast toast = Toast.makeText(context,
                    "Нету Интернета", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void checkActiveTest()
    {
        if (CheckUtility.isNetworkConnected(context))
        {
//            setIsLoading(true);
//            System.out.println("language");

//            System.out.println(language);
//            System.out.println(accessToken);
            AppController appController = new AppController();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            SubjectService subjectService = appController.getSubjectService();
            Disposable disposable = subjectService.getActiveTest(
                    Constant.ACCEPT,
                    language,
                    "Bearer " + accessToken)
                    .subscribeOn(appController.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Consumer<JsonObject>() {
                                @Override
                                public void accept(JsonObject jsonObject) throws Exception
                                {

//                                    System.out.println("JsonReader");
//                                    System.out.println(jsonReader.toString());
//                                    System.out.println(jsonReader.peek());
//                                    System.out.println("JSon");
//                                    System.out.println(jsonObject.toString());
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
                                            loginLayout.showCustomLoading(false);
                                        }


                                    }
                                    else
                                    {
                                        Intent intent = new Intent(context, MainHomeActivity.class);
                                        intent.putExtra(Constant.ACCESS_TOKEN, accessToken);
                                        context.startActivity(intent);
                                        loginLayout.showCustomLoading(false);
                                    }
                                    }
                                }
//                            ,
//                            new Consumer<Throwable>() {
//                                        @Override
//                                        public void accept(Throwable throwable) throws Exception
//                                        {
//                                            loginLayout.showCustomLoading(false);
//                                            System.out.println("error");
//                                        }
//                                    }
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



    public void fetchFullTestQuestionsGenerate(Long id)
    {

        AppController appController = new AppController();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        AppController appController = AppController.create(context);
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getQuestions(Constant.ACCEPT,
                language,
                "Bearer " + accessToken, id)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                           {
                               @Override
                               public void accept(JsonObject jsonObject) throws Exception
                               {

//                                   System.out.println(jsonObject.toString());
                                   List<Subject> subjectList = new ArrayList<>();
                                   JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                   ArrayList<Tests> questions =
                                           TestsUtils.deserializeFromJson(jsonObject);
                                   for (Tests t : questions)
                                   {
                                       subjectList.add(t.getSubject());
                                   }

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
//                                   loginLayout.showCustomLoading(false);
//
                               }
                           }
                );

        compositeDisposable.add(disposable);


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
            editor.putString(Constant.ACCESS_TOKEN, accessToken);
            editor.apply();
            editor.commit();

//            System.out.println(sharedPreferences.getString(Constant.ACCESS_TOKEN, null));

        }
}