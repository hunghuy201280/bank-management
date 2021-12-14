package com.example.bankmanagement.view.create_profile

import android.net.Uri
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentCreateProfile2Binding
import com.example.bankmanagement.models.IncomeType
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.models.ProofOfIncome
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.create_profile.CreateProfileViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import com.example.bankmanagement.view_models.create_profile.CreateProfile2ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProfile2Fragment(private val TAG: String ="CreateProfile2Fragment") :
    BaseFragment<FragmentCreateProfile2Binding, CreateProfile2ViewModel>(), BaseUserView {
    override fun layoutRes(): Int = R.layout.fragment_create_profile2

    override val viewModel: CreateProfile2ViewModel by viewModels()

    val createProfileVM: CreateProfileViewModel by activityViewModels()
    val mainVM: MainViewModel by activityViewModels()
    private val proofOfIncomeAdapter=ProofOfIncomeImageAdapter(object :BaseItemClickListener<Uri>{
        override fun onItemClick(adapterPosition: Int, item: Uri) {
           viewModel.proofOfIncomeDeleted(adapterPosition);
        }
    });

    override fun viewModelClass(): Class<CreateProfile2ViewModel> =
        CreateProfile2ViewModel::class.java;

    override fun initViewModel(viewModel: CreateProfile2ViewModel) {
        binding.viewModel = viewModel;
        binding.mainVM=mainVM;
        binding.createProfileVM=createProfileVM;
        viewModel.init(this);

        viewModel.proofOfIncomes.observe(this,{
            proofOfIncomeAdapter.submitList(it[viewModel.currentIncomeType.value])
            proofOfIncomeAdapter.notifyDataSetChanged()
        });
        viewModel.currentIncomeType.observe(this,{
            proofOfIncomeAdapter.submitList(viewModel.proofOfIncomes.value?.get(it))
            proofOfIncomeAdapter.notifyDataSetChanged()
        })


    }

    override fun initView() {
        binding.proofOfIncomeRV.adapter=proofOfIncomeAdapter;


        //region loantype adapter
        val loanAdapter = ArrayAdapter(requireContext(), R.layout.list_item, LoanType.values().map { it.name });
        binding.loanTypeDropDown.setAdapter(loanAdapter)
        //endregion

        //region proof of income type adapter
        val proofOfIncomeTypeAdapter = ArrayAdapter(requireContext(), R.layout.list_item, IncomeType.values().map { it.name });
        binding.proofOfIncomeTypeDropDown.setAdapter(proofOfIncomeTypeAdapter)
        //endregion
    }


    override fun initData() {
        viewModel.customer= createProfileVM.selectedCustomer.value!!;
        viewModel.branchInfo= mainVM.currentBranch.value!!;



    }

    override fun initAction() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack();
        }
        binding.addProofIncome.setOnClickListener{
            pickProofOfIncomeImages.launch("image/*")
        }
        binding.addSignature.setOnClickListener{
            pickSignatureImage.launch("image/*")
        }
        binding.loanTypeDropDown.setOnItemClickListener { _, _, position, _ ->
            viewModel.selectedLoanType.value= LoanType.values()[position];
        }

        binding.proofOfIncomeTypeDropDown.setOnItemClickListener { _, _, position, _ ->
            viewModel.currentIncomeType.value= IncomeType.values()[position];
        }

        viewModel.selectedLoanType.observe(this, {
            println("$TAG : $it")
        })



    }

    private val pickProofOfIncomeImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){ uri: List<Uri>? ->
        uri?.let { it ->
            viewModel.proofOfIncomesAdded(it);
        }
    }

    private val pickSignatureImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
        uri?.let { it ->

            viewModel.signatureImageSelected(it);

        }
    }


}