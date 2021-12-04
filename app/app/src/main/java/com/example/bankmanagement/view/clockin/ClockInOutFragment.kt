package com.example.bankmanagement.view.clockin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.databinding.FragmentClockInOutBinding
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.clockin.ClockInOutViewModel
import com.example.bankmanagement.view_models.sign_in.SignInViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClockInOutFragment(
) :BaseFragment<FragmentClockInOutBinding,ClockInOutViewModel>(),ClockInOutUICallback {
    override fun layoutRes(): Int=R.layout.fragment_clock_in_out

    override val viewModel: ClockInOutViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()



    override fun viewModelClass(): Class<ClockInOutViewModel> =ClockInOutViewModel::class.java;

    override fun initViewModel(viewModel: ClockInOutViewModel) {
        binding.viewModel=viewModel;
        binding.mainVM=mainViewModel;
        viewModel.init(this);
    }

    override fun initView() {
        initClockInAsText();
    }

    private fun initClockInAsText(){
        if(!viewModel.isClockedIn.value!!){
            binding.clockInAsTV.text="You will clock in as ${mainViewModel.currentStaff.value?.role?.name} Staff";
        }
        else
        {
            binding.clockInAsTV.text="Clocked in as ${mainViewModel.currentStaff.value?.role?.name} Staff";

        }
    }

    override fun initData() {
        
    }

    override fun initAction() {
        
    }

    override fun onClockedIn() {
    }

    override fun onClockedOut() {
    }

}