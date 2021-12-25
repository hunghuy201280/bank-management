package com.example.bankmanagement.view.dashboard.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentApplicationBinding
import com.example.bankmanagement.databinding.FragmentContractBinding
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.view.dashboard.contract.ContractAdapter
import com.example.bankmanagement.view.dashboard.contract.adapter.ContractItemClickListener
import com.example.bankmanagement.view_models.dashboard.application.ApplicationViewModel
import com.example.bankmanagement.view_models.dashboard.contract.ContractViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApplicationFragment: BaseFragment<FragmentApplicationBinding, ApplicationViewModel>(),
    BaseUserView {

    override fun layoutRes(): Int = R.layout.fragment_application

    override val viewModel: ApplicationViewModel by viewModels()

    private val contractAdapter = ContractAdapter(
        rootClickListener = object : BaseItemClickListener<LoanContract> {
            override fun onItemClick(adapterPosition: Int, item: LoanContract) {}
        },
        contractClickListener = object : ContractItemClickListener {
            override fun onContractNumberClick(item: LoanContract) {

            }

            override fun onLoanNumberClick(item: LoanProfile) {
            }
        }
    );

    override fun viewModelClass(): Class<ApplicationViewModel> = ApplicationViewModel::class.java


    override fun initViewModel(viewModel: ApplicationViewModel) {
        viewModel.init(this)
        binding.viewModel = viewModel
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initAction() {

    }



}