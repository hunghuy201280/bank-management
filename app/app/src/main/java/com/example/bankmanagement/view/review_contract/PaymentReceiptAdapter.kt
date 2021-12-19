package com.example.bankmanagement.view.review_contract

import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemLoanContractDisburseItemBinding
import com.example.bankmanagement.databinding.ItemLoanContractPaymentReceiptItemBinding
import com.example.bankmanagement.models.LiquidationDecision

class PaymentReceiptAdapter(itemClickListener: BaseItemClickListener<LiquidationDecision>) : BaseBindingListAdapter<LiquidationDecision>(
DiffCallback(),
    itemClickListener = itemClickListener
){

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_loan_contract_payment_receipt_item
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<LiquidationDecision>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding  = (holder.binding as ItemLoanContractPaymentReceiptItemBinding);


    }
    class DiffCallback : DiffUtil.ItemCallback<LiquidationDecision>() {
        override fun areItemsTheSame(
            oldItem: LiquidationDecision,
            newItem: LiquidationDecision
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: LiquidationDecision,
            newItem: LiquidationDecision
        ): Boolean {
            return false
        }

    }
}