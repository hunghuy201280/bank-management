package com.example.bankmanagement.view.review_customer

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentReviewContractBinding
import com.example.bankmanagement.databinding.FragmentReviewCustomerBinding
import com.example.bankmanagement.models.DisburseCertificate
import com.example.bankmanagement.models.application.BaseDecision
import com.example.bankmanagement.models.application.extension.ExtensionDecision
import com.example.bankmanagement.models.application.liquidation.LiquidationDecision
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.review_profile.ReviewContractUICallback
import com.example.bankmanagement.view.review_profile.ReviewCustomerUICallback
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_contract.ReviewContractViewModel
import com.example.bankmanagement.view_models.review_customer.ReviewCustomerViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewCustomerFragment :
    BaseFragment<FragmentReviewCustomerBinding, ReviewCustomerViewModel>(),
    ReviewCustomerUICallback {


    private val TAG = "ReviewCustomerFragment";
    override fun layoutRes(): Int = R.layout.fragment_review_customer

    override val viewModel: ReviewCustomerViewModel by viewModels()
    val mainVM: MainViewModel by activityViewModels()


    override fun viewModelClass(): Class<ReviewCustomerViewModel> =
        ReviewCustomerViewModel::class.java


    override fun initViewModel(viewModel: ReviewCustomerViewModel) {
        binding.viewModel = viewModel
        binding.mainVM = mainVM
        viewModel.init(this)
    }

    override fun initView() {


        initPieChart()
    }

    private fun initPieChart() {
        viewModel.customerDetail.observe(this, {
            val totalPaid = it.statistics.paid.toFloat()
            val totalUnpaid = it.statistics.unPaid.toFloat();
            val valueList = mutableListOf(
                PieEntry(totalPaid, getString(R.string.paid)),
                PieEntry(totalUnpaid, getString(R.string.unpaid))
            )

            //Fill the chart
            val pieDataSet = PieDataSet(valueList, "")   //Chart style
            pieDataSet.sliceSpace = 0.1f
            pieDataSet.colors = mutableListOf(
                ContextCompat.getColor(requireContext(), R.color.paid),
                ContextCompat.getColor(requireContext(), R.color.unpaid)
            )
            pieDataSet.valueTextColor = Color.TRANSPARENT
            val pieData = PieData(pieDataSet)
            binding.pieChart.data = pieData
        })



        binding.pieChart.legend.isEnabled = false
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setDrawEntryLabels(false)
    }

    override fun initData() {

    }

    override fun initAction() {


    }

    override fun onBack() {
        findNavController().popBackStack()
    }

    override fun showCreateContractDialogFragment() {
        CreateContractFragment().show(childFragmentManager, TAG)
    }


}