package com.example.bankmanagement.view.review_contract

import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemLoanContractDecisionItemBinding
import com.example.bankmanagement.models.application.BaseDecision

class DecisionAdapter(itemClickListener: BaseItemClickListener<BaseDecision>) :
    BaseBindingListAdapter<BaseDecision>(
        DiffCallback(),
        itemClickListener = itemClickListener
    ) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_loan_contract_decision_item
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<BaseDecision>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding = (holder.binding as ItemLoanContractDecisionItemBinding);


    }

    class DiffCallback : DiffUtil.ItemCallback<BaseDecision>() {
        override fun areItemsTheSame(
            oldItem: BaseDecision,
            newItem: BaseDecision
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: BaseDecision,
            newItem: BaseDecision
        ): Boolean {
            return false
        }

    }
}