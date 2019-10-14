package itest.kz.util;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Cancellable;
import io.reactivex.plugins.RxJavaPlugins;

public class RxUtils
{
    /**
     * Converts an ObservableField to an Observable. Note that setting null value inside
     * ObservableField (except for initial value) throws a NullPointerException.
     * @return Observable that contains the latest value in the ObservableField
     */
    @NonNull
    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> field) {

        return Observable.create(new ObservableOnSubscribe<T>() {

//            public void subscribe(ObservableEmitter oe) throws Exception {
//                try{
//                } catch {
//                    if (!oe.isDisposed()) {
//                        if (ex instanceof IOException) {
//                            oe.onError(new NetException(...)); //pass error to emitter
//                        }
//                    } else{
//            ...
//                    }
//                }
//                oe.onComplete();
//            }
            @Override
            public void subscribe(final ObservableEmitter<T> emitter) throws Exception {
                try {
                    T initialValue = field.get();
                    if (initialValue != null) {
                        //Emit initial value
                        emitter.onNext(initialValue);

                    }
//                    emitter.onError(error);
                    final OnPropertyChangedCallback callback = new OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(android.databinding.Observable observable, int i) {
                            //Emit value whenever there is change in observableField
                            emitter.onNext(field.get());
                        }
                    };
                    field.addOnPropertyChangedCallback(callback);
                    emitter.setCancellable(new Cancellable() {
                        @Override
                        public void cancel() throws Exception {
                            //Remove property change listener when observable is no longer required
                            field.removeOnPropertyChangedCallback(callback);
                        }
                    });
                    RxJavaPlugins.setErrorHandler(e -> {
                        if (e instanceof UndeliverableException) {
                            e = e.getCause();
                        }
                        if ((e instanceof IOException)) {
                            // fine, irrelevant network problem or API that throws on cancellation
                            return;
                        }
                        if (e instanceof InterruptedException) {
                            // fine, some blocking code was interrupted by a dispose call
                            return;
                        }
                        if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                            // that's likely a bug in the application
                            Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                            return;
                        }
                        if (e instanceof IllegalStateException) {
                            // that's a bug in RxJava or in a custom operator
                            Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                            return;
                        }
                        Log.w("Undeliverable exception", e);
                    });
                }
                catch(Exception ex)
                {
                    if (!emitter.isDisposed())
                    {
                        System.out.println("error");
                    }
                }
//                emitter.onComplete();
            }
        });
    }

    /**
     * A convenient wrapper for {@code ReadOnlyField#create(Observable)}
     * @return DataBinding field created from the specified Observable
     */
    @NonNull
    public static <T> ReadOnlyField<T> toField(@NonNull final Observable<T> observable) {
        return ReadOnlyField.create(observable);
    }
}
