package com.example.bankmanagement.view.dashboard.admin.add_balance

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
import androidx.fragment.app.activityViewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentCreateDepositBinding
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.dashboard.admin.CreateDepositViewModel
import kotlin.math.max


@AndroidEntryPoint
class CreateDepositDialogFragment(
): DialogFragment(), CreateDepositUICallback {

    companion object {
        val TAG = "CreateDepositDialogFragment"
    }

    private var _binding: FragmentCreateDepositBinding? = null


    private val binding get() = _binding!!
    val viewModel: CreateDepositViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCreateDepositBinding.inflate(LayoutInflater.from(context))
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
        viewModel.branchCode = mainViewModel.currentBranch.value?.branchCode
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    fun initAction() {

    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window ?: return
        val params = window.attributes
        params.width = resources.getDimension(R.dimen._150sdp).toInt()
        params.height = resources.getDimension(R.dimen._85sdp).toInt()
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