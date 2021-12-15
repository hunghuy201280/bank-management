package com.example.bankmanagement.view.dashboard.contract

import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.databinding.LayoutLoanContractListTableItemBinding
import com.example.bankmanagement.models.LoanContract

class ContractAdapter : BaseBindingListAdapter<LoanContract>(
    DiffCallback(),
){

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_loan_contract_list_table_item;
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<LoanContract>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding  = (holder.binding as LayoutLoanContractListTableItemBinding);


    }
    class DiffCallback : DiffUtil.ItemCallback<LoanContract>() {
        override fun areItemsTheSame(
            oldItem: LoanContract,
            newItem: LoanContract
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: LoanContract,
            newItem: LoanContract
        ): Boolean {
            return false
        }

    }
}