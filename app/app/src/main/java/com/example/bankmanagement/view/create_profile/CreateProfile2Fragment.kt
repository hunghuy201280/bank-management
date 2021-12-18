package com.example.bankmanagement.view.create_profile

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
class CreateProfile2Fragment(private val TAG: String = "CreateProfile2Fragment") :
    BaseFragment<FragmentCreateProfile2Binding, CreateProfileViewModel>(), CreateProfile2UICallback {
    override fun layoutRes(): Int = R.layout.fragment_create_profile2

    override val viewModel: CreateProfileViewModel by activityViewModels()

    val mainVM: MainViewModel by activityViewModels()
    private val proofOfIncomeAdapter =
        ProofOfIncomeImageAdapter(object : BaseItemClickListener<Uri> {
            override fun onItemClick(adapterPosition: Int, item: Uri) {
                viewModel.proofOfIncomeDeleted(adapterPosition);
            }
        });

    override fun viewModelClass(): Class<CreateProfileViewModel> =
        CreateProfileViewModel::class.java;

    override fun initViewModel(viewModel: CreateProfileViewModel) {
        binding.viewModel = viewModel;
        binding.mainVM = mainVM;
        viewModel.init(this);
        viewModel.uiCallBack2=this
        viewModel.proofOfIncomes.observe(this, {
            proofOfIncomeAdapter.submitList(it[viewModel.currentIncomeType.value])
            proofOfIncomeAdapter.notifyDataSetChanged()
        });
        viewModel.currentIncomeType.observe(this, {
            proofOfIncomeAdapter.submitList(viewModel.proofOfIncomes.value?.get(it))
            proofOfIncomeAdapter.notifyDataSetChanged()
        })


    }

    override fun initView() {
        binding.proofOfIncomeRV.adapter = proofOfIncomeAdapter;


        //region loantype adapter
        val loanAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item, LoanType.getValues());
        binding.loanTypeDropDown.adapter = loanAdapter
        //endregion

        //region proof of income type adapter
        val proofOfIncomeTypeAdapter =
            ArrayAdapter(requireContext(), R.layout.list_item, IncomeType.getValues());
        Log.d(TAG, "create adapter 2 ${proofOfIncomeTypeAdapter.count}");

        binding.proofOfIncomeTypeDropDown.adapter = proofOfIncomeTypeAdapter
        // binding.proofOfIncomeTypeDropDown.setText(LoanType.values().first().name,false);

        //endregion
    }


    override fun initData() {
        viewModel.branchInfo = mainVM.currentBranch.value!!;
    }

    override fun initAction() {

        binding.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false);
        }
        binding.previousButton.setOnClickListener {
            findNavController().popBackStack();
        }

        binding.addProofIncome.setOnClickListener {
            pickProofOfIncomeImages.launch("image/*")
        }
        binding.addSignature.setOnClickListener {
            pickSignatureImage.launch("image/*")
        }


        binding.loanTypeDropDown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedLoanType.value = LoanType.values()[position];
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding.proofOfIncomeTypeDropDown.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.currentIncomeType.value = IncomeType.values()[position];

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }



        viewModel.selectedLoanType.observe(this, {
            println("$TAG : $it")
        })


    }

    private val pickProofOfIncomeImages =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri: List<Uri>? ->
            uri?.let { viewModel.proofOfIncomesAdded(it); }
        }

    private val pickSignatureImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { viewModel.signatureImageSelected(it); }
        }

    override fun onNextClick() {
        findNavController().navigate(R.id.action_createProfile2Fragment_to_createProfile3Fragment);
    }


}