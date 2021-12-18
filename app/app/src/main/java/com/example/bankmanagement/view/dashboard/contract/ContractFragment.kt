package com.example.bankmanagement.view.dashboard.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentContractBinding
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.view.dashboard.profile.ProfileAdapter
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.dashboard.contract.ContractViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContractFragment : BaseFragment<FragmentContractBinding, ContractViewModel>() {

    override fun layoutRes(): Int=R.layout.fragment_contract

    override val viewModel: ContractViewModel by viewModels()

    private val contractAdapter = ContractAdapter(
        itemClickListener = object : BaseItemClickListener<LoanContract> {
            override fun onItemClick(adapterPosition: Int, item: LoanContract) {
//                viewModel.reviewLoanProfileArgs.value = item;
                findNavController().navigate(R.id.action_dashboardFragment_to_reviewProfileFragment)
            }
        }
    );

    override fun viewModelClass(): Class<ContractViewModel>
            =ContractViewModel::class.java


    override fun initViewModel(viewModel: ContractViewModel) {
    }

    override fun initView() {
    }

    override fun initData() {
        binding.loanContractRV.adapter = contractAdapter;
        viewModel.getContract()
    }

    override fun initAction() {
        viewModel.loanContracts.observe(this, {
            contractAdapter.submitList(it)
        });
    }


}