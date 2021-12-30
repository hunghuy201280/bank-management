package com.example.bankmanagement.view_models.create_application

import android.content.Context
import com.example.bankmanagement.base.adapter.BaseSpinnerAdapter
import com.example.bankmanagement.databinding.ItemContractSuggestionBinding
import com.example.bankmanagement.models.LoanContract

class ContractSuggestionAdapter(context: Context, resId: Int) :
    BaseSpinnerAdapter<LoanContract, ItemContractSuggestionBinding>(context, resId) {
    override fun binding(binding: ItemContractSuggestionBinding?, item: LoanContract?) {
        binding?.item = item
    }


}