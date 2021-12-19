package com.example.bankmanagement.view.review_contract

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentReviewContractBinding
import com.example.bankmanagement.databinding.FragmentReviewProfileBinding
import com.example.bankmanagement.models.DisburseCertificate
import com.example.bankmanagement.models.IncomeType
import com.example.bankmanagement.models.LiquidationDecision
import com.example.bankmanagement.models.PaymentReceipt
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.create_profile.ProofOfIncomeImageAdapter
import com.example.bankmanagement.view.review_profile.ReviewContractUICallback
import com.example.bankmanagement.view.review_profile.ReviewProfileUICallback
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_contract.ReviewContractViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewContractFragment  : BaseFragment<FragmentReviewContractBinding, ReviewContractViewModel>(),
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

    override fun viewModelClass(): Class<ReviewContractViewModel> = ReviewContractViewModel::class.java


    override fun initViewModel(viewModel: ReviewContractViewModel) {
        binding.viewModel=viewModel
        binding.mainVM=mainVM
        viewModel.init(this)



    }

    override fun initView() {
//        binding.proofOfIncomeRV.adapter = proofOfIncomeAdapter;
//        val proofOfIncomeTypeAdapter =
//            ArrayAdapter(requireContext(), R.layout.list_item, IncomeType.values().map { it.name });
//        binding.proofOfIncomeTypeDropDown.adapter=proofOfIncomeTypeAdapter

        binding.disburseCertRV.adapter=disburseAdapter
        disburseAdapter.submitList(viewModel.loanContract.disburseCertificates)
        binding.paymentRV.adapter=receiptAdapter
        receiptAdapter.submitList(viewModel.loanContract.getLiquidationDecisions())
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
    }

    override fun onBack() {
        findNavController().popBackStack()
    }

    override fun showCreateContractDialogFragment() {
        CreateContractFragment().show(childFragmentManager,TAG)
    }


}