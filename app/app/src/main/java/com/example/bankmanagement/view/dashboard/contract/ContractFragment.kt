package com.example.bankmanagement.view.dashboard.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.view_models.MainViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContractFragment : BaseFragment<ViewDataBinding, BaseViewModel>() {

    override fun layoutRes(): Int=R.layout.fragment_contract

    override val viewModel: BaseViewModel= MainViewModel()


    override fun viewModelClass(): Class<BaseViewModel>
            =BaseViewModel::class.java


    override fun initViewModel(viewModel: BaseViewModel) {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initAction() {
    }


}