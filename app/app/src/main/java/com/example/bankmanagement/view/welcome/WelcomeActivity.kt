package com.example.bankmanagement.view.welcome

import androidx.activity.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.activity.BaseBindingActivity
import com.example.bankmanagement.databinding.ActivityWelcomeBinding
import com.example.bankmanagement.utils.helper.SystemHelper
import com.example.bankmanagement.utils.view.openActivity
import com.example.bankmanagement.view.device_code.DeviceCodeActivity

class WelcomeActivity: BaseBindingActivity<ActivityWelcomeBinding>(), WelcomeUiCallback {
    override fun setLayout() = R.layout.activity_welcome

    private val viewModel by viewModels<WelcomeViewModel>()

    override fun initValues() {
        SystemHelper.hideNavBar(this)
        viewModel.init(this)

        binding.viewModel = viewModel
    }

    override fun initActions() {

    }

    override fun openDeviceCode() {
        openActivity<DeviceCodeActivity>()
    }
}