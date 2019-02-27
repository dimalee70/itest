package itest.kz.app;

import android.app.Application;
import android.content.Context;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import itest.kz.network.ApiFactory;
import itest.kz.network.SubjectService;
import itest.kz.network.UserService;

public class AppController extends Application
{
    private UserService userService;
    private SubjectService subjectService;
    private Scheduler scheduler;

    public AppController(){}

    public static AppController get
            (Context context) {
        return AppController.get(context);
    }

    public static AppController create(Context context)
    {
        return AppController.get(context);
    }

    public UserService getUserService()
    {
        if (userService == null)
            userService = ApiFactory.create();

        return userService;
    }

    public SubjectService getSubjectService()
    {
        if (subjectService == null)
            subjectService = ApiFactory.createSubjectService();

        return subjectService;
    }

    public Scheduler subscribeScheduler()
    {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    public void setScheduler(Scheduler scheduler)
    {
        this.scheduler = scheduler;
    }

    public void setSubjectService(SubjectService subjectService)
    {
        this.subjectService = subjectService;
    }

}