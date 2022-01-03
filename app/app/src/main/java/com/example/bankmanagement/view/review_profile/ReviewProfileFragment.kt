package com.example.bankmanagement.view.review_profile

import android.net.Uri
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentReviewProfileBinding
import com.example.bankmanagement.models.IncomeType
import com.example.bankmanagement.utils.helper.LoanProfilePDFGenerator
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.create_profile.ProofOfIncomeImageAdapter
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.review_profile.ReviewProfileViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewProfileFragment : BaseFragment<FragmentReviewProfileBinding, ReviewProfileViewModel>(), ReviewProfileUICallback{


    private val TAG = "ReviewProfileFragment";
    override fun layoutRes(): Int = R.layout.fragment_review_profile

    override val viewModel: ReviewProfileViewModel by viewModels()
    val mainVM: MainViewModel by activityViewModels()
    private val proofOfIncomeAdapter =
        ProofOfIncomeImageAdapter(
            object : BaseItemClickListener<Uri> {
                override fun onItemClick(adapterPosition: Int, item: Uri) {
                }
            }, isRemoveAble = false);

    override fun viewModelClass(): Class<ReviewProfileViewModel> = ReviewProfileViewModel::class.java


    override fun initViewModel(viewModel: ReviewProfileViewModel) {
        binding.viewModel=viewModel
        binding.mainVM=mainVM
        viewModel.init(this)

        viewModel.currentIncomeType.observe(this, {
            proofOfIncomeAdapter.submitList(viewModel.proofOfIncomes.value?.get(it))
            proofOfIncomeAdapter.notifyDataSetChanged()
        })

    }

    override fun initView() {
        binding.proofOfIncomeRV.adapter = proofOfIncomeAdapter;
        val proofOfIncomeTypeAdapter =
            CustomSpinnerAdapter(requireContext(), R.layout.list_item, IncomeType.values().map { it.getName() })
        binding.proofOfIncomeTypeDropDown.adapter=proofOfIncomeTypeAdapter

    }

    override fun initData() {



    }

    override fun initAction() {

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
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


        //test pdf
        binding.titleTextView.setOnClickListener{
            LoanProfilePDFGenerator().generatePDF(viewModel.loanProfile.value!!)
        }
    }

    override fun onBack() {
        findNavController().popBackStack()
    }

    override fun showCreateContractDialogFragment() {
        CreateContractFragment().show(childFragmentManager,TAG)
    }


}