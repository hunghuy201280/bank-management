package com.example.bankmanagement.base.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.example.bankmanagement.base.BaseUiCallback;
import com.example.bankmanagement.base.listener.ConnectionFailUiCallBack;
import com.example.bankmanagement.repo.BaseRxRepo;

public abstract class BaseRxViewModel<T extends BaseRxRepo, V extends BaseUiCallback> extends ViewModel {

    protected V uiCallback;
    protected T repo;

    public void init(@NonNull V uiCallback, LifecycleOwner lifecycleOwner) {
        this.uiCallback = uiCallback;
        repo = createRepo(lifecycleOwner);
    }

    protected abstract T createRepo(LifecycleOwner lifecycleOwner);

    public void onConnectionFail() {
        if (uiCallback instanceof ConnectionFailUiCallBack)
            ((ConnectionFailUiCallBack) uiCallback).onConnectionFail();
    }
}
