package com.example.bankmanagement.view.dashboard.contract

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentContractBinding
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.view_models.dashboard.contract.ContractViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContractFragment : BaseFragment<FragmentContractBinding, ContractViewModel>(),BaseUserView {

    override fun layoutRes(): Int = R.layout.fragment_contract

    override val viewModel: ContractViewModel by viewModels()

    private val contractAdapter = ContractAdapter(
        itemClickListener = object : BaseItemClickListener<LoanContract> {
            override fun onItemClick(adapterPosition: Int, item: LoanContract) {
                viewModel.reviewLoanContractArgs.value = item
                findNavController().navigate(R.id.action_dashboardFragment_to_reviewContractFragment)
            }
        }
    );

    override fun viewModelClass(): Class<ContractViewModel> = ContractViewModel::class.java


    override fun initViewModel(viewModel: ContractViewModel) {
        viewModel.init(this)
        binding.viewModel=viewModel
    }

    override fun initView() {
        //region Loan type dropdown
        val loanTypes = LoanType.getFilterValues()
        val loanTypesAdapter = CustomSpinnerAdapter(requireContext(), R.layout.list_item, loanTypes)
        binding.loanTypeDropDown.adapter = loanTypesAdapter
        //endregion
    }

    override fun initData() {
        binding.loanContractRV.adapter = contractAdapter;
        viewModel.getContract()
    }

    override fun initAction() {
        viewModel.loanContracts.observe(this, {
            contractAdapter.submitList(it)
        })

        //region loanTypeDropDown
        binding.loanTypeDropDown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.loanType.value = LoanType.values()[position];
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        viewModel.loanType.observe(this, {
            binding.loanTypeDropDown.setSelection(LoanType.values().indexOf(it), true)
        });
        //endregion
    }


}