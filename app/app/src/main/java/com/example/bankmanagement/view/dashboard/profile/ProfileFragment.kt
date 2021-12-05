package com.example.bankmanagement.view.dashboard.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.ViewDataBinding
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentProfileBinding
import com.example.bankmanagement.view_models.MainViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {



    override fun layoutRes(): Int=R.layout.fragment_profile

    override val viewModel: BaseViewModel = MainViewModel()


    override fun viewModelClass(): Class<BaseViewModel>
            = BaseViewModel::class.java


    override fun initViewModel(viewModel: BaseViewModel) {
    }

    override fun initView() {
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.loanTypeDropDown.setAdapter(adapter)
    }

    override fun initData() {
    }

    override fun initAction() {
    }


}