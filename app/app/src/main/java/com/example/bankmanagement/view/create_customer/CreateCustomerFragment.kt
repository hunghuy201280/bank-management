package com.example.bankmanagement.view.create_customer

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.example.bankmanagement.databinding.FragmentCreateCustomerBinding
import com.example.bankmanagement.models.customer.CustomerType
import com.example.bankmanagement.view_models.create_application.ContractSuggestionAdapter
import com.example.bankmanagement.view_models.create_customer.CreateCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class CreateCustomerFragment(private val type: CustomerType, private val refreshData:()->Unit) : DialogFragment(),
    CreateCustomerUICallback {

    companion object {
        val TAG = "CreateCustomerFragment"
    }

    private var _binding: FragmentCreateCustomerBinding? = null

    private val binding get() = _binding!!
    val viewModel: CreateCustomerViewModel by viewModels()
    lateinit var contractSuggestionAdapter : ContractSuggestionAdapter
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCreateCustomerBinding.inflate(LayoutInflater.from(context))
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

        viewModel.type.value=type
        binding.viewModel = viewModel
        binding.lifecycleOwner = this




    }


    fun initAction() {
        binding.pickBusinessCert.setOnClickListener {
            pickSignatureImage.launch("image/*")
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }

    }

    private val pickSignatureImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.onBusinessCertChanged(it) }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun dismissDialog(refresh: Boolean) {
        if(refresh){
            this.refreshData()
        }
        dismiss()
    }

    override fun showLoading(show: Boolean) {
    }


}