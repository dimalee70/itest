package itest.kz.util;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import itest.kz.model.Subject;
import itest.kz.model.Tests;
import itest.kz.viewmodel.TestViewModel;

public class TestViewModelFactory implements ViewModelProvider.Factory
{
    private Application application;
    private Subject subject;
    private String typeTest;
    private boolean isStartedFirst;
    private Tests  tests;

    public TestViewModelFactory(Application application, Subject subject)
    {
        this.application = application;
        this.subject = subject;
    }


    public TestViewModelFactory(Application application, Subject subject, String typeTest, boolean isStartedFirst, Tests tests)
    {
        this.application = application;
        this.subject = subject;
        this.typeTest = typeTest;
        this.isStartedFirst = isStartedFirst;
        this.tests = tests;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        return (T) new TestViewModel(application, subject, typeTest, isStartedFirst, tests);
    }
}
