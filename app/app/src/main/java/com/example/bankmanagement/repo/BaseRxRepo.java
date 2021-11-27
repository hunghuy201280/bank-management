package com.example.bankmanagement.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

public abstract class BaseRxRepo extends BaseRepo {

    protected AutoDispose autoDispose;

    public BaseRxRepo(@NonNull Lifecycle lifecycle) {
        super();
        autoDispose = new AutoDispose();
        autoDispose.bindTo(lifecycle);
    }
}
