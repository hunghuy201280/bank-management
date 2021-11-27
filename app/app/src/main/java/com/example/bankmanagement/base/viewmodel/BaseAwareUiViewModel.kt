package com.example.bankmanagement.base.viewmodel

import com.example.bankmanagement.base.BaseUiCallback
import com.example.bankmanagement.base.listener.ConnectionFailUiCallBack
import com.example.bankmanagement.repo.BaseRxRepo

abstract class BaseAwareUiViewModel<T : BaseRxRepo?, V : BaseUiCallback?> : BaseAwareViewModel<T>(),
    ConnectionFailUiCallBack {

    var uiCallback: V? = null

    fun init(uiCallback: V) {
        this.uiCallback = uiCallback
    }

    override fun onConnectionFail() {
        if (uiCallback is ConnectionFailUiCallBack)
            (uiCallback as ConnectionFailUiCallBack).onConnectionFail()
    }
}