package com.example.bankmanagement.base.activity

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<T : ViewDataBinding> : BaseAppCompatActivity() {

    protected lateinit var binding: T

    override fun initLayout() {
        super.initLayout()
        binding = DataBindingUtil.setContentView<ViewDataBinding>(this, setLayout()) as T
        binding.lifecycleOwner = this
    }

    protected val activity: Activity
        get() = this
}