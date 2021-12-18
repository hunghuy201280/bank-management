package com.example.bankmanagement.utils

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.prefs.Preferences
import com.example.bankmanagement.prefs.PreferencesKey

object UserHelper {
    var alreadyLogInDevice: Boolean? = null
    get() {
        if (field == null) {
            field = Preferences.getInstance()
                .getBoolean(PreferencesKey.ALREADY_LOG_IN_DEVICE)
        }
        return field
    }
    set(value) {
        field = value
        Preferences.getInstance().storeValue(PreferencesKey.ALREADY_LOG_IN_DEVICE, value)
    }
}