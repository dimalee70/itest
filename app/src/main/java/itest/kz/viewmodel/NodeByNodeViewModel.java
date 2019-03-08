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
import itest.kz.model.Lecture;
import itest.kz.model.Node;
import itest.kz.model.NodesByNode;
import itest.kz.model.NodesBySubject;
import itest.kz.model.Subject;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;

public class NodeByNodeViewModel extends Observable
{
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Node node;
    private NodesByNode nodesBySubject;
    private List<Lecture> lectureList = new ArrayList<>();
    private ObservableInt lectures_list;

    public NodeByNodeViewModel(Context context, Node node)
    {
        this.context = context;
        this.node = node;
        this.lectures_list = new ObservableInt(View.GONE);
        fetchNodeByNodeList();
    }

    private void fetchNodeByNodeList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getNodeByNode(Constant.ATTESTATION,
                node.getId(), Constant.ACCEPT, "ru")
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NodesByNode>()
                {
                    @Override
                    public void accept(NodesByNode nodesByNode) throws Exception
                    {
                        updateNodeBySubjectData(nodesByNode.getLectures());
                        lectures_list.set(View.VISIBLE);
                    }
                });
    }

    private void updateNodeBySubjectData(List<Lecture> lectureList)
    {
        this.lectureList.addAll(lectureList);
        setChanged();
        notifyObservers();
    }

    public List<Lecture> getLectureList()
    {
        return lectureList;
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
