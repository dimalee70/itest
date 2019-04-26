package itest.kz.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.R;
import itest.kz.app.AppController;
import itest.kz.model.NodeChildren;
import itest.kz.model.NodeResponse;
import itest.kz.model.Subject;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;
import itest.kz.view.activity.AuthActivity;

import static android.content.Context.MODE_PRIVATE;

public class MaterialsViewModel extends Observable
{
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    private MutableLiveData<NodesBySubject> listMutableLiveData;
    private Subject subject;
//    private String accessToken;
    private NodeResponse nodeResponse;
    private String language;
    private List<NodeChildren> nodeList = new ArrayList<>();
    private ObservableInt material_list;
    private TextView dialogTextAuth;
    private Button buttonYesAuth;
    private Button buttonNoAuth;



    public MaterialsViewModel(Context context, Subject subject)
    {
        this.context = context;
        this.subject = subject;
        this.material_list = new ObservableInt(View.GONE);
//        SharedPreferences settings = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
////        settings.edit().clear().commit();
//        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);

        SharedPreferences lang = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");

        fetchNodeList();
    }

    private void fetchNodeList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();

//        System.out.println("lang");
//        System.out.println(language);
//        if (subject != null) {
        Disposable disposable = subjectService.getNodeBySubject(Constant.ENT,
                subject.getId(), Constant.ACCEPT, language
//                , accessToken
        )
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NodeResponse>()
                           {
                               @Override
                               public void accept(NodeResponse nodeResponse) throws Exception {
                                   updateNodeBySubjectData(nodeResponse);
//
                                   material_list.set(View.VISIBLE);
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

                );

        compositeDisposable.add(disposable);
//        }
    }

//    private TextView dialogTextAuth;
//    private Button buttonYesAuth;
//    private Button buttonNoAuth;

    public void showToastUnauthorized()
    {

//        public void showFinishTimeDialog()
//        {
        Dialog dialog = new Dialog(context);
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
        if(language.equals(Constant.KZ))
        {
//            buttonNo.setText(R.string.noKz);

            dialogTextAuth.setText(R.string.sessionErrorKz);

        }
        else
        {
//            buttonNo.setText(R.string.noRu);
//            buttonYes.setText(R.string.yesRu);
            dialogTextAuth.setText(R.string.sessionErrorRu);
        }
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
        Intent intent = new Intent(context, AuthActivity.class);
        ((Activity)context).startActivity(intent);
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

    private void updateNodeBySubjectData(NodeResponse nodeResponse)
    {
        this.nodeList.addAll(nodeResponse.getData().getChildren());
//        this.subject = nodeResponse.getData()..getSubject();
        setChanged();
        notifyObservers();
    }

    public List<NodeChildren> getNodeList()
    {
        return nodeList;
    }




    private void unSubscribeFromObservable()
    {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset()
    {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public String getTitleText()
    {
        return subject.getTitle();
    }
}
