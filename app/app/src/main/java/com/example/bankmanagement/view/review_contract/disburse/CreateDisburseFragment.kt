package com.example.bankmanagement.view.review_contract.disburse

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.example.bankmanagement.databinding.FragmentCreateDisburseBinding
import com.example.bankmanagement.view_models.review_contract.CreateDisburseViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.view.WindowManager
import com.example.bankmanagement.R
import kotlin.math.max


@AndroidEntryPoint
class CreateDisburseFragment(private val contractId: String, private val maxAmount: Double): DialogFragment(), CreateDisburseUICallback {

    companion object {
        val TAG = "CreateDisburseFragment"
    }

    private var _binding: FragmentCreateDisburseBinding? = null

    private val binding get() = _binding!!
    val viewModel: CreateDisburseViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCreateDisburseBinding.inflate(LayoutInflater.from(context))
        initViewModel()
        initView()
        initAction()
        return MaterialDialog(requireContext()).apply {
            setContentView(binding.root)
        }
    }


    private fun initView() {

    }

    fun initViewModel() {
        viewModel.init(this)
        viewModel.remainingDisburseAmount = maxAmount
        viewModel.contractId = contractId

        binding.viewModel = viewModel
        binding.maxAmount = maxAmount
        binding.lifecycleOwner = this
    }

    fun initAction() {

    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window ?: return
        val params = window.attributes
        params.width = resources.getDimension(R.dimen._170sdp).toInt()
        params.height = resources.getDimension(R.dimen._96sdp).toInt()
        window.attributes = params
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun dismissDialog() {
        dismiss()
    }


    override fun showLoading(show: Boolean) {
    }
}