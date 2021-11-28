package com.example.bankmanagement.view_models.welcome


import com.example.bankmanagement.view.welcome.WelcomeUiCallback
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel
@Inject
constructor(): BaseUiViewModel<WelcomeUiCallback>() {
    fun openDeviceCode() {
        uiCallback?.openDeviceCode()
    }
}