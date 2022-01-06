package com.example.bankmanagement.view.dashboard.admin

import androidx.fragment.app.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.databinding.FragmentAdminBinding
import com.example.bankmanagement.view_models.dashboard.admin.AdminViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFragment : BaseFragment<FragmentAdminBinding, AdminViewModel>(), BaseUserView {


    private val TAG = "AdminFragment";
    override fun layoutRes(): Int = R.layout.fragment_admin


    override val viewModel: AdminViewModel by viewModels()


    override fun viewModelClass(): Class<AdminViewModel> = AdminViewModel::class.java

    val revenueByTypeAdapter = RevenueByTypeAdapter()

    override fun initViewModel(viewModel: AdminViewModel) {
        binding.viewModel = viewModel;
        viewModel.init(this)

        viewModel.revenueStatistic.observe(this) { it ->
            revenueByTypeAdapter.submitList(it.revenueByType.map { mapIt ->
                Pair(
                    mapIt.key,
                    mapIt.value
                )
            })
        }
    }

    override fun initView() {
//
//        //region Loan type dropdown
//        val loanTypes = LoanType.getFilterValues()
//        val loanTypesAdapter = CustomSpinnerAdapter(requireContext(), R.layout.list_item, loanTypes)
//        loanTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.loanTypeDropDown.adapter = loanTypesAdapter
//
//        //endregion
//
//        //region Loan status dropdown
//        val loanStatuses = LoanStatus.getValues();
//        val loanStatusesAdapter =
//            CustomSpinnerAdapter(requireContext(), R.layout.list_item, loanStatuses)
//        binding.loanStatusDropdown.adapter = loanStatusesAdapter
//
//        //endregion
    }

    override fun initData() {
        binding.revenueByTypeRV.adapter=revenueByTypeAdapter
    }

    override fun initAction() {
//        binding.loanTypeDropDown.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    viewModel.selectedLoanType.value = LoanType.values()[position];
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {}
//            }

//        binding.loanStatusDropdown.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    viewModel.loanStatus.value = LoanStatus.values()[position];
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                }
//            }
//
//        viewModel.selectedLoanType.observe(this, {
//            binding.loanTypeDropDown.setSelection(LoanType.values().indexOf(it), true)
//        });
//        viewModel.loanStatus.observe(this, {
//            binding.loanStatusDropdown.setSelection(LoanStatus.values().indexOf(it), true)
//        })

    }


}