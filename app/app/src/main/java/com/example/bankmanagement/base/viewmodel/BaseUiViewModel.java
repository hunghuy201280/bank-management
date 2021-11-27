package com.example.bankmanagement.base.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.bankmanagement.base.BaseUiCallback;
import com.example.bankmanagement.base.listener.ConnectionFailUiCallBack;

public abstract class BaseUiViewModel<V extends BaseUiCallback> extends ViewModel {

    protected V uiCallback;

    public void init(@NonNull V uiCallback) {
        this.uiCallback = uiCallback;
    }

    public void onConnectionFail() {
        if (uiCallback instanceof ConnectionFailUiCallBack)
            ((ConnectionFailUiCallBack) uiCallback).onConnectionFail();
    }
}
