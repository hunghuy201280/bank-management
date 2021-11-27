package com.example.bankmanagement.base.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.example.bankmanagement.repo.BaseRxRepo;


public abstract class BaseDataViewModel<T extends BaseRxRepo> extends ViewModel {

    protected T repo;

    public void init(LifecycleOwner lifecycleOwner) {
        repo = createRepo(lifecycleOwner);
    }

    protected abstract T createRepo(LifecycleOwner lifecycleOwner);
}
