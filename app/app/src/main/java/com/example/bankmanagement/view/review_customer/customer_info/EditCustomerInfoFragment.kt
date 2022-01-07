package com.example.bankmanagement.view.review_customer.customer_info

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.databinding.FragmentEditCustomerInfoBinding
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.view.review_profile.ReviewCustomerUICallback
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_customer.EditCustomerInfoViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditCustomerInfoFragment(
    private val parent:ReviewCustomerUICallback,
    private val customer: Customer,
    ) :
    BaseFragment<FragmentEditCustomerInfoBinding, EditCustomerInfoViewModel>(),
    BaseUserView {


    private val TAG = "CustomerInfoFragment"
    override fun layoutRes(): Int = R.layout.fragment_edit_customer_info

    override val viewModel: EditCustomerInfoViewModel by viewModels()
    val mainVM: MainViewModel by activityViewModels()


    override fun viewModelClass(): Class<EditCustomerInfoViewModel> =
        EditCustomerInfoViewModel::class.java


    override fun initViewModel(viewModel: EditCustomerInfoViewModel) {
        binding.viewModel = viewModel
        binding.mainVM = mainVM
        viewModel.init(parent)
        viewModel.setInitialData(customer)
        binding.cancelButton.setOnClickListener{
            parent.onViewCustomerInfo()
        }
    }

    override fun initView() {
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }


    override fun initData() {

    }

    override fun initAction() {


    }


}