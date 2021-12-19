package com.example.bankmanagement.view.review_contract

import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemLoanContractDisburseItemBinding
import com.example.bankmanagement.models.DisburseCertificate

class DisburseAdapter(itemClickListener: BaseItemClickListener<DisburseCertificate>) : BaseBindingListAdapter<DisburseCertificate>(
DiffCallback(),
    itemClickListener = itemClickListener
){

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_loan_contract_disburse_item
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<DisburseCertificate>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding  = (holder.binding as ItemLoanContractDisburseItemBinding);


    }
    class DiffCallback : DiffUtil.ItemCallback<DisburseCertificate>() {
        override fun areItemsTheSame(
            oldItem: DisburseCertificate,
            newItem: DisburseCertificate
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: DisburseCertificate,
            newItem: DisburseCertificate
        ): Boolean {
            return false
        }

    }
}