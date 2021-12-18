package com.example.bankmanagement.utils

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.prefs.Preferences
import com.example.bankmanagement.prefs.PreferencesKey

object UserHelper {
    var branchCode: String? = null
    get() {
        if (field == null) {
            field = Preferences.getInstance()
                .getString(PreferencesKey.ALREADY_LOG_IN_DEVICE)
        }
        return field
    }
    set(value) {
        field = value
        Preferences.getInstance().storeValue(PreferencesKey.ALREADY_LOG_IN_DEVICE, value)
    }



}