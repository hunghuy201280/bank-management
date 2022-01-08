package com.example.bankmanagement.view.review_customer.customer_info

import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.databinding.ItemRecentServiceBinding
import com.example.bankmanagement.databinding.ItemRevenueByTypeTableItemBinding
import com.example.bankmanagement.models.LoanType

class RecentServiceAdapter : BaseBindingListAdapter<LoanType>(
    DiffCallback(),
) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_recent_service;
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<LoanType>,
        position: Int
    ) {
        val item = getItem(position)
        val binding = (holder.binding as ItemRecentServiceBinding);
        binding.loanType = item
    }

    class DiffCallback : DiffUtil.ItemCallback<LoanType>() {
        override fun areItemsTheSame(
            oldItem: LoanType,
            newItem: LoanType
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: LoanType,
            newItem: LoanType
        ): Boolean {
            return false
        }

    }
}