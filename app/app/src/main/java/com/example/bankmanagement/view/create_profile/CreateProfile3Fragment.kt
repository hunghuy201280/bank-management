package com.example.bankmanagement.view.create_profile

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.FragmentCreateProfile2Binding
import com.example.bankmanagement.databinding.FragmentCreateProfile3Binding
import com.example.bankmanagement.models.IncomeType
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.create_profile.CreateProfileViewModel
import com.example.bankmanagement.widgets.adapter.CustomSpinnerAdapter
import com.hanheldpos.ui.base.fragment.BaseFragment


class CreateProfile3Fragment (private val TAG: String = "CreateProfile3Fragment") :
    BaseFragment<FragmentCreateProfile3Binding, CreateProfileViewModel>(), CreateProfile3UICallback {
    override fun layoutRes(): Int = R.layout.fragment_create_profile3

    override val viewModel: CreateProfileViewModel by activityViewModels()

    val mainVM: MainViewModel by activityViewModels()
    private val proofOfIncomeAdapter =
        ProofOfIncomeImageAdapter(
            object : BaseItemClickListener<Uri> {
            override fun onItemClick(adapterPosition: Int, item: Uri) {}
        }, isRemoveAble = false);

    override fun viewModelClass(): Class<CreateProfileViewModel> =
        CreateProfileViewModel::class.java;

    override fun initViewModel(viewModel: CreateProfileViewModel) {
        binding.viewModel = viewModel;
        binding.mainVM = mainVM;
        viewModel.uiCallBack3=this

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

        //region proof of income type adapter
        val proofOfIncomeTypeAdapter =
            CustomSpinnerAdapter(requireContext(), R.layout.list_item, IncomeType.values().map { it.name });
        binding.proofOfIncomeTypeDropDown.adapter=proofOfIncomeTypeAdapter
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
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_createProfile2Fragment_to_createProfile3Fragment);
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





    }


    override fun onProfileCreated() {
        binding.backButton.performClick();
    }


}