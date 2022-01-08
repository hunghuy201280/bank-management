package com.example.bankmanagement.view.create_application

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentCreateApplicationBinding
import com.example.bankmanagement.models.application.ApplicationType
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.view_models.create_application.ContractSuggestionAdapter
import com.example.bankmanagement.view_models.create_application.CreateApplicationViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class CreateApplicationFragment(private val type: ApplicationType,private val refreshData:()->Unit) : DialogFragment(),
    CreateApplicationUICallback {

    companion object {
        val TAG = "CreateApplicationFragment"
    }

    private var _binding: FragmentCreateApplicationBinding? = null

    private val binding get() = _binding!!
    val viewModel: CreateApplicationViewModel by viewModels()
    lateinit var contractSuggestionAdapter : ContractSuggestionAdapter
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCreateApplicationBinding.inflate(LayoutInflater.from(context))
        initViewModel()
        initView()
        initAction()
        return MaterialDialog(requireContext()).apply {
            setContentView(binding.root)

        }
    }


    private fun initView() {

        binding.contractNumberAutoComplete. onItemClickListener=AdapterView.OnItemClickListener { parent, view, position, id ->
            viewModel.loanContract.value=contractSuggestionAdapter.getItem(position)
        }

        val onSearchQueryChanged: (String) -> Unit = Utils.debounce(
            400L,
            this.lifecycleScope,
            viewModel::onSearch
        )

        binding.contractNumberAutoComplete.addTextChangedListener {
            onSearchQueryChanged(it.toString().trim())

        }
    }

    fun initViewModel() {
        viewModel.init(this)

        viewModel.applicationType=type
        binding.type=type
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        viewModel.contractSuggestions.observe(this, {
            for(item in it){
                println("item :${item.contractNumber}")
            }
             contractSuggestionAdapter = ContractSuggestionAdapter(
                requireContext(), R.layout.item_contract_suggestion,
            )
            contractSuggestionAdapter.submitList(it)
            binding.contractNumberAutoComplete.setAdapter(contractSuggestionAdapter)
            binding.contractNumberAutoComplete.invalidate()
            binding.contractNumberAutoComplete.showDropDown()
        })
    }


    fun initAction() {
        binding.uploadSignature.setOnClickListener {
            pickSignatureImage.launch("image/*")
        }
        binding.cancelButton.setOnClickListener {
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


    override fun dismissDialog() {

        dismiss()
    }

    override fun refreshData() {
        this.refreshData()

    }

    override fun showLoading(show: Boolean) {
    }


}