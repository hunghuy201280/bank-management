package com.example.bankmanagement.view.welcome

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.ActivityWelcomeBinding
import com.example.bankmanagement.utils.helper.SystemHelper
import com.example.bankmanagement.utils.view.openActivity
import com.example.bankmanagement.view_models.welcome.WelcomeViewModel
import com.example.bankmanagement.base.activity.BaseActivity
import com.example.bankmanagement.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.bankmanagement.view.MainActivity as MainActivity1
@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding, WelcomeViewModel>(), WelcomeUiCallback {




    override val viewModel: WelcomeViewModel by viewModels()


    override fun openDeviceCode() {
        openActivity<MainActivity1>()
    }

    override fun layoutRes(): Int= R.layout.activity_welcome

    override fun initView() {
        //force light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SystemHelper.hideNavBar(this)
        viewModel.init(this)

        binding.viewModel = viewModel
    }

    override fun initData() {
    }

    override fun initAction() {
    }



    override fun initViewModel(viewModel: WelcomeViewModel) {
    }

    override fun viewModelClass(): Class<WelcomeViewModel> =WelcomeViewModel::class.java
}