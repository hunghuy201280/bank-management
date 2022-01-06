package com.example.bankmanagement.view.dashboard.admin

import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.databinding.ItemRevenueByTypeTableItemBinding
import com.example.bankmanagement.models.LoanType

class RevenueByTypeAdapter : BaseBindingListAdapter<Pair<LoanType, Double>>(
    DiffCallback(),
) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_revenue_by_type_table_item;
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<Pair<LoanType, Double>>,
        position: Int
    ) {
        val item = getItem(position)
        val binding = (holder.binding as ItemRevenueByTypeTableItemBinding);
        binding.loanType = item.first
        binding.amount = item.second
    }

    class DiffCallback : DiffUtil.ItemCallback<Pair<LoanType, Double>>() {
        override fun areItemsTheSame(
            oldItem: Pair<LoanType, Double>,
            newItem: Pair<LoanType, Double>
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: Pair<LoanType, Double>,
            newItem: Pair<LoanType, Double>
        ): Boolean {
            return false
        }

    }
}