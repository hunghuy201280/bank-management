package com.example.bankmanagement.base.viewmodel

import com.example.bankmanagement.base.BaseUserView

abstract class BaseUiViewModel<V : BaseUserView?> : BaseViewModel() {

    protected var uiCallback: V? = null

    fun init(uiCallback: V) {
        this.uiCallback = uiCallback
    }
}