package com.example.bankmanagement.view.review_contract

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentReviewContractBinding
import com.example.bankmanagement.models.DisburseCertificate
import com.example.bankmanagement.models.LiquidationDecision
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.review_profile.ReviewContractUICallback
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_contract.ReviewContractViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewContractFragment :
    BaseFragment<FragmentReviewContractBinding, ReviewContractViewModel>(),
    ReviewContractUICallback {


    private val TAG = "ReviewProfileFragment";
    override fun layoutRes(): Int = R.layout.fragment_review_contract

    override val viewModel: ReviewContractViewModel by viewModels()
    val mainVM: MainViewModel by activityViewModels()
    private val disburseAdapter =
        DisburseAdapter(
            object : BaseItemClickListener<DisburseCertificate> {
                override fun onItemClick(adapterPosition: Int, item: DisburseCertificate) {
                }
            })
    private val receiptAdapter =
        PaymentReceiptAdapter(
            object : BaseItemClickListener<LiquidationDecision> {
                override fun onItemClick(adapterPosition: Int, item: LiquidationDecision) {
                }
            })

    override fun viewModelClass(): Class<ReviewContractViewModel> =
        ReviewContractViewModel::class.java


    override fun initViewModel(viewModel: ReviewContractViewModel) {
        binding.viewModel = viewModel
        binding.mainVM = mainVM
        viewModel.init(this)
    }

    override fun initView() {
//        binding.proofOfIncomeRV.adapter = proofOfIncomeAdapter;
//        val proofOfIncomeTypeAdapter =
//            ArrayAdapter(requireContext(), R.layout.list_item, IncomeType.values().map { it.name });
//        binding.proofOfIncomeTypeDropDown.adapter=proofOfIncomeTypeAdapter

        binding.disburseCertRV.adapter = disburseAdapter
        binding.paymentRV.adapter = receiptAdapter

        initPieChart()
    }

    private fun initPieChart() {
        viewModel.loanContract.observe(this,{
            val totalPaid = viewModel.totalPayment.value?.toFloat() ?: 0f
            val totalUnpaid = viewModel.unPaid.value?.toFloat()?:0f;
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
        });



        binding.pieChart.legend.isEnabled = false
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setDrawEntryLabels(false)
    }

    override fun initData() {

    }

    override fun initAction() {

//        binding.proofOfIncomeTypeDropDown.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    viewModel.currentIncomeType.value = IncomeType.values()[position];
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                }
//            }
        viewModel.disburseCertificateList.observe(this) {
            disburseAdapter.submitList(it)
        }

        viewModel.liquidationApplicationList.observe(this) {
            receiptAdapter.submitList(it)
        }
    }

    override fun onBack() {
        findNavController().popBackStack()
    }

    override fun showCreateContractDialogFragment() {
        CreateContractFragment().show(childFragmentManager, TAG)
    }


}