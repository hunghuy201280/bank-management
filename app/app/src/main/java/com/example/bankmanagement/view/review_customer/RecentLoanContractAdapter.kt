package com.example.bankmanagement.view.review_customer

import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemReviewCustomerRecentContractsItemBinding
import com.example.bankmanagement.models.LoanContract

class RecentLoanContractAdapter(itemClickListener: BaseItemClickListener<LoanContract>) :
    BaseBindingListAdapter<LoanContract>(
        DiffCallback(),
        itemClickListener = itemClickListener
    ) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_review_customer_recent_contracts_item
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<LoanContract>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding = (holder.binding as ItemReviewCustomerRecentContractsItemBinding);


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