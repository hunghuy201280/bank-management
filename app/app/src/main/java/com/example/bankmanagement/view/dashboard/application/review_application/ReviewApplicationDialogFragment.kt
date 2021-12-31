package com.example.bankmanagement.view.dashboard.application.review_application

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.example.bankmanagement.databinding.FragmentReviewApplicationDialogBinding
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.StaffRole
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_application.ReviewApplicationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewApplicationDialogFragment
constructor(
    private val application: BaseApplication,
    private val refreshData:()->Unit
) : DialogFragment(), ReviewApplicationUICallback {
    companion object {
        const val TAG = "ReviewApplicationDialogFragment"
    }

    private var _binding: FragmentReviewApplicationDialogBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ReviewApplicationViewModel by viewModels()
    private val mainVM: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentReviewApplicationDialogBinding.inflate(LayoutInflater.from(context))
        initViewModel()
        initData()
        initAction()
        initView()
        return MaterialDialog(requireContext()).apply {
            setContentView(binding.root)
        }
    }

    private fun initView() {
//        val canApprove= mainVM.currentStaff.value?.getRoleName()==StaffRole.Director.name && viewModel.application.value?.status==LoanStatus.Pending
//        binding.BODButtons.visibility= if(canApprove) View.VISIBLE else View.GONE
    }

    private fun initData() {
        binding.item = application
    }

    private fun initAction() {

    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        viewModel.init(this)
        binding.viewModel = viewModel
        binding.mainVM = mainVM
        viewModel.application.value = application
        viewModel.temp.value = "test"
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