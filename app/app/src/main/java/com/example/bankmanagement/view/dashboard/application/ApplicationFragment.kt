package com.example.bankmanagement.view.dashboard.application

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentApplicationBinding
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.view.dashboard.application.adapter.ApplicationAdapter
import com.example.bankmanagement.view.dashboard.application.adapter.ApplicationItemClickListener
import com.example.bankmanagement.view_models.dashboard.application.ApplicationViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApplicationFragment: BaseFragment<FragmentApplicationBinding, ApplicationViewModel>(),
    ApplicationUICallback {

    override fun layoutRes(): Int = R.layout.fragment_application

    override val viewModel: ApplicationViewModel by viewModels()

    private val applicationAdapter = ApplicationAdapter(
        applicationItemClickListener = object : ApplicationItemClickListener {

            override fun onContractClicked(item: String) {
                viewModel.onContractNumberClicked(contractNumber = item)
            }
        },

    );

    override fun viewModelClass(): Class<ApplicationViewModel> = ApplicationViewModel::class.java


    override fun initViewModel(viewModel: ApplicationViewModel) {
        viewModel.init(this)
        binding.viewModel = viewModel

        viewModel.exemptions.observe(this,{
            applicationAdapter.submitList(it.toList())
        })
    }

    override fun initView() {
        binding.applicationRV.adapter=applicationAdapter
    }

    override fun initData() {

    }

    override fun initAction() {

    }

    override fun onContractNumberClicked() {
        findNavController().navigate(R.id.action_dashboardFragment_to_reviewContractFragment)
    }


}