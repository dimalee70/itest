package itest.kz.viewmodel;

import android.content.Context;
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
import itest.kz.model.Node;
import itest.kz.model.NodesBySubject;
import itest.kz.model.Subject;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;

public class MaterialsViewModel extends Observable
{
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    private MutableLiveData<NodesBySubject> listMutableLiveData;
    private Subject subject;

    private NodesBySubject nodesBySubject;
    private List<Node> nodeList = new ArrayList<>();
    private ObservableInt material_list;



    public MaterialsViewModel(Context context, Subject subject)
    {
        this.context = context;
        this.subject = subject;
        this.material_list = new ObservableInt(View.GONE);
        fetchNodeList();
    }

    private void fetchNodeList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();

//        if (subject != null) {
        Disposable disposable = subjectService.getNodeBySubject(Constant.ATTESTATION,
                subject.getId(), Constant.ACCEPT, "ru")
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NodesBySubject>() {
                               @Override
                               public void accept(NodesBySubject nodesBySubject) throws Exception {

//                                   System.out.println("Nodes");
//                                   System.out.println(subject);

////                                   nodeList = nodesBySubject.getNodes();
//                                   System.out.println(nodesBySubject.getNodes().toString());
                                   updateNodeBySubjectData(nodesBySubject);
                                   material_list.set(View.VISIBLE);
                               }
                           }
                );

        compositeDisposable.add(disposable);
//        }
    }

    private void updateNodeBySubjectData(NodesBySubject nodesBySubject)
    {
        this.nodeList.addAll(nodesBySubject.getNodes());
        this.subject = nodesBySubject.getSubject();
        setChanged();
        notifyObservers();
    }

    public List<Node> getNodeList()
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
