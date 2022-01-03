package com.example.bankmanagement.view.dashboard.profile

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentProfileBinding
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.view.dashboard.profile.adapter.ProfileAdapter
import com.example.bankmanagement.view.dashboard.profile.adapter.ProfileItemClickListener
import com.example.bankmanagement.view_models.dashboard.profile.ProfileViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(),
    ProfileUICallback {


    private val TAG = "ProfileFragment";
    override fun layoutRes(): Int = R.layout.fragment_profile
    private val profileAdapter = ProfileAdapter(
        itemClickListener = object : BaseItemClickListener<LoanProfile> {
            override fun onItemClick(adapterPosition: Int, item: LoanProfile) {}
        },
        profileItemClickListener = object : ProfileItemClickListener {
            override fun onLoanNumberClick(item: LoanProfile) {
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
        viewModel.loanProfiles.observe(this, {
            profileAdapter.submitList(it)
        })
    }

    override fun initView() {

        //region Loan type dropdown
        val loanTypes = LoanType.getFilterValues()
        val loanTypesAdapter = CustomSpinnerAdapter(requireContext(), R.layout.list_item, loanTypes)
        loanTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.loanTypeDropDown.adapter = loanTypesAdapter

        //endregion

        //region Loan status dropdown
        val loanStatuses = LoanStatus.getValues();
        val loanStatusesAdapter =
            CustomSpinnerAdapter(requireContext(), R.layout.list_item, loanStatuses)
        binding.loanStatusDropdown.adapter = loanStatusesAdapter

        //endregion
    }

    override fun initData() {
        binding.loanProfileRV.adapter = profileAdapter
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

        viewModel.selectedLoanType.observe(this, {
            binding.loanTypeDropDown.setSelection(LoanType.values().indexOf(it), true)
        });
        viewModel.loanStatus.observe(this, {
            binding.loanStatusDropdown.setSelection(LoanStatus.values().indexOf(it), true)
        })

    }

    override fun onCreateClicked() {
        findNavController().navigate(R.id.action_dashboardFragment_to_createProfile1Fragment)
    }


}