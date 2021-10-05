package com.example.bankmanagement.base.activity

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

abstract class BasePermissionActivity<T : ViewDataBinding> : BaseBindingActivity<T>() {

    protected abstract fun onAllPermissionsGranted()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun requestPermissions() {
    }
}