package com.example.bankmanagement.view.dashboard.profile

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentProfileBinding
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.view_models.dashboard.profile.ProfileViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(),
    ProfileUICallback {


    private val TAG = "ProfileFragment";
    override fun layoutRes(): Int = R.layout.fragment_profile
    private val profileAdapter = ProfileAdapter(
        itemClickListener = object : BaseItemClickListener<LoanProfile> {
            override fun onItemClick(adapterPosition: Int, item: LoanProfile) {
                viewModel.reviewLoanProfileArgs.value = item;
                findNavController().navigate(R.id.action_dashboardFragment_to_reviewProfileFragment)
            }
        }
    );

    override val viewModel: ProfileViewModel by viewModels()


    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java


    override fun initViewModel(viewModel: ProfileViewModel) {
        binding.viewModel = viewModel;
        viewModel.init(this)
    }

    override fun initView() {

        //region Loan type dropdown
        val loanTypes = LoanType.getFilterValues()
        val loanTypesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, loanTypes)
        binding.loanTypeDropDown.adapter = loanTypesAdapter

        //endregion

        //region Loan status dropdown
        val loanStatuses = LoanStatus.getValues();
        val loanStatusesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, loanStatuses)
        binding.loanStatusDropdown.adapter = loanStatusesAdapter

        //endregion
    }

    override fun initData() {
        binding.loanProfileRV.adapter = profileAdapter;
        viewModel.getProfiles()
    }

    override fun initAction() {
        binding.loanTypeDropDown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedLoanType.value = LoanType.values()[position];
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.loanStatusDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.loanStatus.value = LoanStatus.values()[position];
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        viewModel.loanProfiles.observe(this, {
            profileAdapter.submitList(it)
        });
        viewModel.selectedLoanType.observe(this, {
            binding.loanTypeDropDown.setSelection(LoanType.values().indexOf(it), true)
        });
        viewModel.loanStatus.observe(this, {
            binding.loanStatusDropdown.setSelection(LoanStatus.values().indexOf(it), true)
        });

    }

    override fun onCreateClicked() {
        findNavController().navigate(R.id.action_dashboardFragment_to_createProfile1Fragment)
    }


}