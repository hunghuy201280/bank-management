package com.example.bankmanagement.view.dashboard.customer

import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentCustomerBinding
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.models.customer.CustomerType
import com.example.bankmanagement.view.create_customer.CreateCustomerFragment
import com.example.bankmanagement.view.dashboard.customer.adapter.CustomerAdapter
import com.example.bankmanagement.view_models.dashboard.customer.CustomerViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CustomerFragment : BaseFragment<FragmentCustomerBinding, CustomerViewModel>(),
    CustomerUICallback {


    private val TAG = "CustomerFragment";
    override fun layoutRes(): Int = R.layout.fragment_customer
    private val customerAdapter = CustomerAdapter(
        itemClickListener = object : BaseItemClickListener<Customer> {
            override fun onItemClick(adapterPosition: Int, item: Customer) {
//                viewModel.reviewLoanProfileArgs.value = item;
//                findNavController().navigate(R.id.action_dashboardFragment_to_reviewProfileFragment)
            }
        },

    );

    override val viewModel: CustomerViewModel by viewModels()


    override fun viewModelClass(): Class<CustomerViewModel> = CustomerViewModel::class.java


    override fun initViewModel(viewModel: CustomerViewModel) {
        binding.viewModel = viewModel;
        viewModel.init(this)
        viewModel.customers.observe(this,{
            customerAdapter.submitList(it)
        })
    }

    override fun initView() {

        //region customer type dropdown
        val customerTypeAdapter = CustomSpinnerAdapter(
            requireContext(),
            R.layout.list_item,
            CustomerType.getFilterValues()
        )
        customerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.customerTypeDropdown.adapter = customerTypeAdapter

        //endregion



    }

    override fun initData() {
        binding.customerRV.adapter = customerAdapter;
    }

    override fun initAction() {
        binding.customerTypeDropdown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.customerType.value = CustomerType.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        viewModel.customerType.observe(this, {
            binding.customerTypeDropdown.setSelection(CustomerType.values().indexOf(it), true)
        })

        binding.createButton.setOnClickListener {
            val popup = PopupMenu(context, binding.createButton, Gravity.END)
            popup.menuInflater
                .inflate(R.menu.customer_create_popup, popup.menu)

            popup.setOnMenuItemClickListener {
                val type = when (it.itemId) {
                    R.id.business_customer -> CustomerType.Business
                    R.id.resident_customer -> CustomerType.Resident
                    else -> throw Exception("Unknow menu item $it")
                }
                viewModel.onCreateClicked(type)
                true
            }

            popup.show() //showing pop

        }
    }


    override fun onCreateClicked(type: CustomerType) {
        CreateCustomerFragment(type, refreshData = viewModel::onFindClicked).show(childFragmentManager,
            CreateCustomerFragment.TAG)

    }


}