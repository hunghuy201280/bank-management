package com.example.bankmanagement.view.dashboard.profile

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentProfileBinding
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.view_models.dashboard.profile.ProfileViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(),ProfileUICallback {


    private val TAG = "ProfileFragment";
    override fun layoutRes(): Int = R.layout.fragment_profile
    private val profileAdapter=ProfileAdapter();

    override val viewModel: ProfileViewModel by viewModels()


    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java


    override fun initViewModel(viewModel: ProfileViewModel) {
        binding.viewModel=viewModel;
        viewModel.init(this)
    }

    override fun initView() {

        //region Loan type dropdown
        val loanTypes = LoanType.values().map { it.getName() };
        val loanTypesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, loanTypes)
        binding.loanTypeDropDown.setAdapter(loanTypesAdapter)

        //endregion

        //region Loan status dropdown
        val loanStatuses = LoanStatus.values().map { it.getName() };
        val loanStatusesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, loanStatuses)
        binding.loanStatusDropdown.setAdapter(loanStatusesAdapter)

        //endregion
    }

    override fun initData() {
        binding.loanProfileRV.adapter=profileAdapter;
    }

    override fun initAction() {
        binding.loanTypeDropDown.setOnItemClickListener { _, _, position, _ ->
            viewModel.selectedLoanType.value= LoanType.values()[position];
         }
        binding.loanStatusDropdown.setOnItemClickListener { _, _, position, _ ->
            viewModel.loanStatus.value= LoanStatus.values()[position];
         }

        viewModel.loanProfiles.observe(this,{
           profileAdapter.submitList(it)
        });
        viewModel.selectedLoanType.observe(this,{
            binding.loanTypeDropDown.setText(it.getName(),false)
        });
        viewModel.loanStatus.observe(this,{
            binding.loanStatusDropdown.setText(it.getName(),false)
        });

    }

    override fun onCreateClicked() {
        findNavController().navigate(R.id.action_dashboardFragment_to_createProfile1Fragment)
    }


}