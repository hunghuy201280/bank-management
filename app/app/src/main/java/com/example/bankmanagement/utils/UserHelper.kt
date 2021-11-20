package com.example.bankmanagement.utils

import com.example.bankmanagement.prefs.Const
import com.example.bankmanagement.prefs.Preferences

object UserHelper {
    var isAlreadyInstalled: Boolean? = null
    get() {
        if (field == null) {
            field = Preferences.getInstance()
                .getBoolean(Const.ALREADY_INSTALLED)
        }
        return field
    }
    set(value) {
        field = value
        Preferences.getInstance().storeValue(Const.ALREADY_INSTALLED, value)
    }
}