package com.example.bankmanagement.view.dashboard.widgets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentDashboardHeaderBinding
import com.example.bankmanagement.view_models.MainViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardHeaderFragment : BaseFragment<FragmentDashboardHeaderBinding, BaseViewModel>() {



    override fun layoutRes(): Int=R.layout.fragment_dashboard_header

    override val viewModel: BaseViewModel = MainViewModel()

    private val mainViewModel: MainViewModel by activityViewModels()


    override fun viewModelClass(): Class<BaseViewModel>
            = BaseViewModel::class.java


    override fun initViewModel(viewModel: BaseViewModel) {
        binding.mainVM=mainViewModel;

    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initAction() {
    }


}