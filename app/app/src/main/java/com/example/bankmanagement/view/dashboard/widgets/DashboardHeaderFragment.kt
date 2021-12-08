package com.example.bankmanagement.view.dashboard.widgets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentDashboardHeaderBinding
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.device_code.DeviceCodeViewModel
import com.example.bankmanagement.view_models.welcome.WelcomeViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardHeaderFragment : BaseFragment<FragmentDashboardHeaderBinding, BaseViewModel>() {



    override fun layoutRes(): Int=R.layout.fragment_dashboard_header

    override val viewModel: BaseViewModel by viewModels();



    override fun viewModelClass(): Class<BaseViewModel>
            = BaseViewModel::class.java


    override fun initViewModel(viewModel: BaseViewModel) {

    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initAction() {
    }


}