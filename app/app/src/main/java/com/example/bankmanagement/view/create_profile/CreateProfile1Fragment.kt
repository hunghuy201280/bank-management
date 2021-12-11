package com.example.bankmanagement.view.create_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentCreateProfile1Binding
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.clockin.ClockInOutViewModel
import com.example.bankmanagement.view_models.create_profile.CreateProfileViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment

class CreateProfile1Fragment: BaseFragment<FragmentCreateProfile1Binding, CreateProfileViewModel>(),BaseUserView {
    override fun layoutRes(): Int=R.layout.fragment_create_profile1

    override val viewModel: CreateProfileViewModel by activityViewModels()



    override fun viewModelClass(): Class<CreateProfileViewModel> = CreateProfileViewModel::class.java;

    override fun initViewModel(viewModel: CreateProfileViewModel) {
        binding.viewModel=viewModel;
        viewModel.init(this);
    }

    override fun initView() {
    }



    override fun initData() {

    }

    override fun initAction() {
        binding.backButton.setOnClickListener{
            findNavController().popBackStack();
        }
    }


}