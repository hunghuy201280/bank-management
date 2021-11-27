package com.example.bankmanagement.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import com.example.bankmanagement.repo.BaseRxRepo

/**
 * This ViewModel can self aware life cycle
 */
abstract class BaseAwareViewModel<T : BaseRxRepo?> : ViewModel(), LifecycleOwner {

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

    override fun onCleared() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onCleared()
    }
}