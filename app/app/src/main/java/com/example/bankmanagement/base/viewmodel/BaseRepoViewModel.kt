package com.example.bankmanagement.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import com.example.bankmanagement.base.BaseUiCallback
import com.example.bankmanagement.base.listener.ConnectionFailUiCallBack
import com.example.bankmanagement.repo.BaseRepo

abstract class BaseRepoViewModel<T : BaseRepo?, V : BaseUiCallback?> : ViewModel(), LifecycleOwner,
    ConnectionFailUiCallBack {

    var uiCallback: V? = null

    fun init(uiCallback: V) {
        this.uiCallback = uiCallback
    }

    private val lifecycleRegistry: LifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    init {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    protected abstract fun createRepo(lifecycleOwner: LifecycleOwner): T

    protected var repo: T? = null
        get() {
            if (field == null) {
                field = createRepo(this)
            }
            return field
        }

    override fun onConnectionFail() {
        if (uiCallback is ConnectionFailUiCallBack)
            (uiCallback as ConnectionFailUiCallBack).onConnectionFail()
    }

    override fun onCleared() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onCleared()
    }
}