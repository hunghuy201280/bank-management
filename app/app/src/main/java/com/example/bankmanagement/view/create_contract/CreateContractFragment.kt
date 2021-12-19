package com.example.bankmanagement.view.create_contract

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentCreateContractBinding
import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.create_contract.CreateContractViewModel
import dagger.hilt.android.AndroidEntryPoint

data class CreateContractFragmentArgs(
    val loanProfile:LoanProfile,
)
@AndroidEntryPoint
class CreateContractFragment : DialogFragment() {
    private var _binding: FragmentCreateContractBinding? = null

    private val binding get() = _binding!!
    val viewModel:CreateContractViewModel by viewModels()
    val mainVM:MainViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCreateContractBinding.inflate(LayoutInflater.from(context))
        binding.viewModel=viewModel
        binding.mainVM=mainVM
        binding.lifecycleOwner=this
        initAction()
        return MaterialDialog(requireContext()).apply {
            setContentView(binding.root)
        }
    }




    fun initAction(){
        binding.uploadSignature.setOnClickListener {
            pickSignatureImage.launch("image/*")
        }
        binding.cancelButton.setOnClickListener{
            dismiss()
        }

    }
    private val pickSignatureImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.signatureImageSelected(it); }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}