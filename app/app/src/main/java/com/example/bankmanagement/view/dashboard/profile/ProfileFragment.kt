package com.example.bankmanagement.view.dashboard.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentProfileBinding
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.view_models.MainViewModel
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
        val items = viewModel.loanTypes

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.loanTypeDropDown.setAdapter(adapter)
    }

    override fun initData() {
        binding.loanProfileRV.adapter=profileAdapter;
    }

    override fun initAction() {
        binding.loanTypeDropDown.setOnItemClickListener { _, _, position, _ ->
            viewModel.selectedTypePosition.value=position
         }
        viewModel.selectedTypePosition.observe(this, {
            println("$TAG : ${viewModel.loanTypes[it ?: 0]}")
        })
        viewModel.loanProfiles.observe(this,{
           profileAdapter.submitList(it)
        });

    }

    override fun onCreateClicked() {
        findNavController().navigate(R.id.action_dashboardFragment_to_createProfile1Fragment)
    }


}