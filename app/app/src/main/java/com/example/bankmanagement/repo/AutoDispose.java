package com.example.bankmanagement.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AutoDispose implements LifecycleObserver {

    private CompositeDisposable compositeDisposable;

    AutoDispose() {
        compositeDisposable = new CompositeDisposable();
    }

    void bindTo(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public boolean isDestroy() {
        return compositeDisposable.isDisposed();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
