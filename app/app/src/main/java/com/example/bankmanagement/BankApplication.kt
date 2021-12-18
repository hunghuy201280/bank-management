package com.example.bankmanagement

import android.app.Application
import android.content.Context
import com.example.bankmanagement.prefs.Preferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BankApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Preferences.getInstance().init(applicationContext)
    }
}