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
import com.example.bankmanagement.models.application.BaseDecision
import com.example.bankmanagement.models.application.extension.ExtensionDecision
import com.example.bankmanagement.models.application.liquidation.LiquidationDecision
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.review_contract.disburse.CreateDisburseFragment
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

    private val extensionAdapter =
        DecisionAdapter(
            itemClickListener = object : BaseItemClickListener<BaseDecision> {
                override fun onItemClick(adapterPosition: Int, item: BaseDecision) {
                }

            }
        )
    private val exemptionAdapter =
        DecisionAdapter(
            itemClickListener = object : BaseItemClickListener<BaseDecision> {
                override fun onItemClick(adapterPosition: Int, item: BaseDecision) {
                }

            }
        )
    private val liquidationAdapter =
        DecisionAdapter(
            itemClickListener = object : BaseItemClickListener<BaseDecision> {
                override fun onItemClick(adapterPosition: Int, item: BaseDecision) {
                }

            }
        )

    override fun viewModelClass(): Class<ReviewContractViewModel> =
        ReviewContractViewModel::class.java


    override fun initViewModel(viewModel: ReviewContractViewModel) {
        binding.viewModel = viewModel
        binding.mainVM = mainVM
        viewModel.init(this)
    }

    override fun initView() {

        binding.disburseCertRV.adapter = disburseAdapter
        binding.paymentRV.adapter = receiptAdapter
        binding.extensionRV.adapter=extensionAdapter
        binding.exemptionRV.adapter=exemptionAdapter
        binding.liquidationRV.adapter=liquidationAdapter

        initPieChart()
    }

    private fun initPieChart() {
        viewModel.loanContract.observe(this, {
            val totalPaid = viewModel.totalPayment.value?.toFloat() ?: 0f
            val totalUnpaid = viewModel.unPaid.value?.toFloat() ?: 0f;
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

        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }
r

        viewModel.disburseCertificateList.observe(this) {
            disburseAdapter.submitList(it)
        }

        viewModel.liquidationApplicationList.observe(this) {
            receiptAdapter.submitList(it)
        }

        viewModel.extensionDecisions.observe(this,{
            extensionAdapter.submitList(it)
        })
        viewModel.exemptionDecisions.observe(this,{
            exemptionAdapter.submitList(it)
        })
        viewModel.liquidationDecisions.observe(this,{
            liquidationAdapter.submitList(it)
        })
    }

    override fun onBack() {
        findNavController().popBackStack()
    }

    override fun showCreateContractDialogFragment() {
        CreateContractFragment().show(childFragmentManager, TAG)
    }

    override fun showCreateDisburseDialogFragment(contractId: String, maxAmount: Double) {
        CreateDisburseFragment(contractId, maxAmount).show(childFragmentManager, CreateDisburseFragment.TAG)
    }
}