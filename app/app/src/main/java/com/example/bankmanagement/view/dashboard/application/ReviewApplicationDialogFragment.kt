package com.example.bankmanagement.view.dashboard.application

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentCreateContractBinding
import com.example.bankmanagement.databinding.FragmentReviewApplicationDialogBinding
import com.example.bankmanagement.models.application.BaseApplication


class ReviewApplicationDialogFragment
    constructor(
        private val application:BaseApplication
    )
    : DialogFragment() {
    companion object {
        const val TAG = "ReviewApplicationDialogFragment"
    }

    private var _binding: FragmentReviewApplicationDialogBinding? = null

    private val binding get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentReviewApplicationDialogBinding.inflate(LayoutInflater.from(context))
        initViewModel()
        initData()
        initAction()
        return MaterialDialog(requireContext()).apply {
            setContentView(binding.root)
        }
    }

    private fun initData() {
        binding.item=application
    }

    private fun initAction() {

    }

    private fun initViewModel() {
    }
}