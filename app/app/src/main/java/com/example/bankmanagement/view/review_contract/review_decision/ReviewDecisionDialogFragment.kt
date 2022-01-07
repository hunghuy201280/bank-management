package com.example.bankmanagement.view.review_contract.review_decision

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentReviewApplicationDialogBinding
import com.example.bankmanagement.databinding.FragmentReviewDecisionDialogBinding
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.models.application.BaseDecision
import com.example.bankmanagement.models.application.liquidation.LiquidationDecision
import com.example.bankmanagement.view.dashboard.application.review_application.ReviewApplicationUICallback
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_application.ReviewApplicationViewModel
import com.example.bankmanagement.view_models.review_contract.ReviewDecisionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDecisionDialogFragment
constructor(
    private val decision: BaseDecision,
    private val refreshData:()->Unit
) : DialogFragment(), ReviewDecisionUICallback {
    companion object {
        const val TAG = "ReviewDecisionDialogFragment"
    }

    private var _binding: FragmentReviewDecisionDialogBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ReviewDecisionViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentReviewDecisionDialogBinding.inflate(LayoutInflater.from(context))
        initViewModel()
        initData()
        initAction()
        initView()
        return MaterialDialog(requireContext()).apply {
            setContentView(binding.root)
        }
    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window ?: return
        val params = window.attributes
        params.width = resources.getDimension(R.dimen._170sdp).toInt()
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT
        window.attributes = params
    }

    private fun initView() {

    }

    private fun initData() {
        binding.item = decision
    }

    private fun initAction() {

    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        viewModel.init(this)
        binding.viewModel = viewModel
        viewModel.decision.value = decision
    }


    override fun onBack(refresh: Boolean) {
        if(refresh)
        {
            refreshData()
        }
        dismiss()
    }

    override fun showLoading(show: Boolean) {
    }
}