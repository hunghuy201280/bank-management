package com.example.bankmanagement.view.device_code

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentDeviceCodeBinding
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.device_code.DeviceCodeViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeviceCodeFragment : BaseFragment<FragmentDeviceCodeBinding, DeviceCodeViewModel>() {
    override fun layoutRes(): Int = R.layout.fragment_device_code;

    override val viewModel: DeviceCodeViewModel by viewModels()

    val mainVM:MainViewModel by activityViewModels();

    override fun viewModelClass(): Class<DeviceCodeViewModel> =DeviceCodeViewModel::class.java;

    override fun initViewModel(viewModel: DeviceCodeViewModel) {
        binding.viewModel = viewModel

        viewModel.branch.observe(this,{
            it?.let {
                mainVM.currentBranch.value=it;
                findNavController().navigate(R.id.action_deviceCodeFragment_to_signInFragment)
            }
        })
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initAction() {

    }



}


