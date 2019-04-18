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
import itest.kz.model.Lecture;
import itest.kz.model.Node;
import itest.kz.model.NodeChildren;
import itest.kz.model.NodeResponse;
import itest.kz.model.NodesByNode;
import itest.kz.network.SubjectService;
import itest.kz.util.Constant;

import static android.content.Context.MODE_PRIVATE;

public class NodeByNodeViewModel extends Observable
{
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NodeChildren node;
    private List<Lecture> lectureList = new ArrayList<>();
    private ObservableInt lectures_list;
    private String accessToken;
    private String language;

    public NodeByNodeViewModel(Context context, NodeChildren node)
    {
        this.context = context;
        this.node = node;
        this.lectures_list = new ObservableInt(View.GONE);
        SharedPreferences settings = context.getSharedPreferences(Constant.MY_PREF, MODE_PRIVATE);
//        settings.edit().clear().commit();
        accessToken = settings.getString(Constant.ACCESS_TOKEN, null);

        SharedPreferences lang = context.getSharedPreferences(Constant.MY_LANG, MODE_PRIVATE);
//        settings.edit().clear().commit();
        language = lang.getString(Constant.LANG, "kz");
        fetchNodeByNodeList();
    }

    private void fetchNodeByNodeList()
    {
        AppController appController = new AppController();
        SubjectService subjectService = appController.getSubjectService();

        Disposable disposable = subjectService.getNodeBySubject(Constant.ENT,
                node.getId(), Constant.ACCEPT, language)
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NodeResponse>() {
                               @Override
                               public void accept(NodeResponse nodeResponse) throws Exception
                               {
                                   updateNodeBySubjectData(nodeResponse.getData().getLectures());
                                   lectures_list.set(View.VISIBLE);
                               }
                           }
//                        new Consumer<NodesByNode>()
//                {
//                    @Override
//                    public void accept(NodesByNode nodesByNode) throws Exception
//                    {
//                        updateNodeBySubjectData(nodesByNode.getLectures());
//                        lectures_list.set(View.VISIBLE);
//                    }
//                }
                );
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
