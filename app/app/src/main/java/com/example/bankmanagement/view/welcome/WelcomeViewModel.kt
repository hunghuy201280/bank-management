package com.example.bankmanagement.view.welcome

import com.example.bankmanagement.base.viewmodel.BaseUiViewModel

class WelcomeViewModel: BaseUiViewModel<WelcomeUiCallback>() {
    fun openDeviceCode() {
        uiCallback.openDeviceCode()
    }
}