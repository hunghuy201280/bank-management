package com.example.bankmanagement.base.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.example.bankmanagement.base.BaseActions
import com.example.bankmanagement.utils.helper.SystemHelper

abstract class BaseAppCompatActivity : AppCompatActivity(), BaseActions, LifecycleObserver {

    private var autoHideKeyboard = true

    override fun onCreate(savedInstanceState: Bundle?) {
        preOnCreate();
        super.onCreate(savedInstanceState)

        initLayout()
        initSettings()
        initView()
        initValues()
        initActions()
        fetchData()
    }

    protected open fun initSettings() {}
    protected open fun preOnCreate() {}

    protected open fun initView() {}

    protected open fun fetchData() {}

    protected open fun initLayout() {
        setContentView(setLayout())
        lifecycle.addObserver(this)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val dispatchTouchEvent = super.dispatchTouchEvent(event)
        if (autoHideKeyboard && event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus

            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)

                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    // Hide keyboard
                    v.clearFocus()
                    val inputMethodManager = applicationContext
                        .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0)

                    // Hide Navigation bar
                    SystemHelper.hideNavBar(this)
                }
            }
        }
        return dispatchTouchEvent
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            // Hide Navigation bar
            SystemHelper.hideNavBar(this)
        }
    }
}