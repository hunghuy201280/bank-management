package com.example.bankmanagement.view.review_customer.customer_info

import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.databinding.FragmentCustomerInfoBinding
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.utils.helper.BarcodeHelper
import com.example.bankmanagement.view.review_profile.ReviewCustomerUICallback
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_customer.CustomerInfoViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CustomerInfoFragment(
    private val parent: ReviewCustomerUICallback,
    private val customer: Customer,
) :
    BaseFragment<FragmentCustomerInfoBinding, CustomerInfoViewModel>(),
    BaseUserView {


    private val TAG = "CustomerInfoFragment"
    override fun layoutRes(): Int = R.layout.fragment_customer_info

    override val viewModel: CustomerInfoViewModel by viewModels()
    val mainVM: MainViewModel by activityViewModels()


    override fun viewModelClass(): Class<CustomerInfoViewModel> =
        CustomerInfoViewModel::class.java


    override fun initViewModel(viewModel: CustomerInfoViewModel) {
        binding.viewModel = viewModel
        binding.mainVM = mainVM
        viewModel.init(this)

        viewModel.customer.value = customer

        binding.editButton.setOnClickListener {
            parent.onEditCustomerInfo()
        }

    }

    override fun initView() {
        binding.barcodeImage.let {
            it.setImageBitmap(
                BarcodeHelper.createBarcodeBitmap(
                    barcodeValue = customer.getCustomerCode(),
                    barcodeColor = ContextCompat.getColor(it.context, R.color.primaryText),
                    backgroundColor = ContextCompat.getColor(it.context, android.R.color.transparent),
                    widthPixels = resources.getDimensionPixelSize(R.dimen._100sdp),
                    heightPixels = resources.getDimensionPixelSize(R.dimen._22sdp)
                )
            )
        }
    }


    override fun initData() {

    }

    override fun initAction() {


    }


}