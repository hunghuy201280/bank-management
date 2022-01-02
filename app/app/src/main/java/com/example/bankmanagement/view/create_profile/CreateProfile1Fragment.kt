package com.example.bankmanagement.view.create_profile

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentCreateProfile1Binding
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.view_models.create_profile.CreateProfile1ViewModel
import com.example.bankmanagement.view_models.create_profile.CreateProfileViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import com.example.bankmanagement.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.widget.addTextChangedListener

@AndroidEntryPoint
class CreateProfile1Fragment :
    BaseFragment<FragmentCreateProfile1Binding, CreateProfile1ViewModel>(), BaseUserView {
    override fun layoutRes(): Int = R.layout.fragment_create_profile1

    override val viewModel: CreateProfile1ViewModel by viewModels()
    private lateinit var searchCustomerAdapter: SearchCustomerAdapter;

    val createProfileVM: CreateProfileViewModel by activityViewModels()

    override fun viewModelClass(): Class<CreateProfile1ViewModel> =
        CreateProfile1ViewModel::class.java;

    override fun initViewModel(viewModel: CreateProfile1ViewModel) {
        binding.viewModel = viewModel;
        viewModel.init(this);

        viewModel.customers.observe(this, {
            searchCustomerAdapter.submitList(it)
        });
    }

    override fun initView() {
    }


    override fun initData() {
        searchCustomerAdapter =
            SearchCustomerAdapter(itemClickListener = object : BaseItemClickListener<Customer> {
                override fun onItemClick(adapterPosition: Int, item: Customer) {
                    createProfileVM.selectedCustomer.value = item;
                    searchCustomerAdapter.setSelectedCustomer(adapterPosition);
                }
            })
        binding.customerRV.adapter = searchCustomerAdapter;

    }

    override fun initAction() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack();
        }
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_createProfile1Fragment_to_createProfile2Fragment);
        }

        val onSearchQueryChanged: (String) -> Unit = Utils.debounce(
            300L,
            viewLifecycleOwner.lifecycleScope,
            viewModel::onSearch
        )

        binding.searchTextField.addTextChangedListener {
            onSearchQueryChanged(it.toString().trim())
        }
    }


}