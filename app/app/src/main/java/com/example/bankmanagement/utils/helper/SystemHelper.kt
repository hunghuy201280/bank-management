package com.example.bankmanagement.utils.helper

import android.app.Activity
import android.view.View
import android.view.WindowManager

object SystemHelper {

    /**
     * Hide navigation bar
     */
    fun hideNavBar(activity: Activity?) {
        activity?.let {
            it.window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    fun hideStatusBar(activity: Activity?) {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

}