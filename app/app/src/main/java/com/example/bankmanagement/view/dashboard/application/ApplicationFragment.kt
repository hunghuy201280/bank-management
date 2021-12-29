package com.example.bankmanagement.view.dashboard.application

import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.databinding.FragmentApplicationBinding
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.view.dashboard.application.adapter.ApplicationAdapter
import com.example.bankmanagement.view.dashboard.application.adapter.ApplicationItemClickListener
import com.example.bankmanagement.view_models.dashboard.application.ApplicationViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast

import com.example.bankmanagement.view.MainActivity

import android.view.MenuItem
import android.widget.PopupMenu
import com.example.bankmanagement.R
import com.example.bankmanagement.models.application.ApplicationType
import com.example.bankmanagement.models.application.BaseApplication


@AndroidEntryPoint
class ApplicationFragment : BaseFragment<FragmentApplicationBinding, ApplicationViewModel>(),
    ApplicationUICallback {

    override fun layoutRes(): Int = R.layout.fragment_application

    override val viewModel: ApplicationViewModel by viewModels()

    private val applicationAdapter = ApplicationAdapter(
        applicationItemClickListener = object : ApplicationItemClickListener {

            override fun onContractClicked(item: String) {
                viewModel.onContractNumberClicked(contractNumber = item)
            }

            override fun onApplicationCliced(item: BaseApplication) {
                val reviewFrags = ReviewApplicationDialogFragment(item)
                reviewFrags.show(childFragmentManager,ReviewApplicationDialogFragment.TAG)
            }
        },

        );

    override fun viewModelClass(): Class<ApplicationViewModel> = ApplicationViewModel::class.java


    override fun initViewModel(viewModel: ApplicationViewModel) {
        viewModel.init(this)
        binding.viewModel = viewModel

        viewModel.applications.observe(this, {
            applicationAdapter.submitList(it.toList())
        })

        viewModel.status.observe(this, {
            binding.applicationStatusDropdown.setSelection(LoanStatus.values().indexOf(it), true)
        });
    }

    override fun initView() {
        binding.applicationRV.adapter = applicationAdapter

        val loanStatusesAdapter = CustomSpinnerAdapter(
            requireContext(),
            com.example.bankmanagement.R.layout.list_item,
            LoanStatus.getValues()
        )
        binding.applicationStatusDropdown.adapter = loanStatusesAdapter

    }

    override fun initData() {

    }

    override fun initAction() {

        binding.applicationStatusDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.status.value = LoanStatus.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        binding.createButton.setOnClickListener {
            val popup = PopupMenu(context, binding.createButton, Gravity.END)
            popup.menuInflater
                .inflate(R.menu.application_popup, popup.menu)

            popup.setOnMenuItemClickListener {
                val type = when (it.itemId) {
                    R.id.liquidation_application -> ApplicationType.Liquidation
                    R.id.exemption_application -> ApplicationType.Exemption
                    R.id.extension_application -> ApplicationType.Extension
                    else -> throw Exception("Unknow menu item $it")
                }
                viewModel.onCreateClicked(type)
                true
            }

            popup.show() //showing pop

        }
    }

    override fun onContractNumberClicked() {
        findNavController().navigate(R.id.action_dashboardFragment_to_reviewContractFragment)
    }


}