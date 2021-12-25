package com.example.bankmanagement

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.bankmanagement.prefs.Preferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BankApplication : Application(){

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        Preferences.getInstance().init(applicationContext)
    }
}