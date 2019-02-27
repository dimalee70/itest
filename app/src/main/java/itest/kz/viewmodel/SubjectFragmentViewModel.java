package itest.kz.viewmodel;

import android.content.Context;
import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;

public class SubjectFragmentViewModel
{
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;

    public SubjectFragmentViewModel(Context context)
    {
        this.context = context;
    }
}
