package itest.kz.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.view.View;



import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import itest.kz.app.AppController;
import itest.kz.model.NodeChildren;
import itest.kz.model.NodeResponse;
import itest.kz.model.Subject;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;

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
                           }
                );

        compositeDisposable.add(disposable);
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

}
