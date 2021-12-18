package com.example.bankmanagement.view.dashboard.profile

import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.models.LoanProfile
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemLoanProfileTableItemBinding

class ProfileAdapter(itemClickListener: BaseItemClickListener<LoanProfile>) : BaseBindingListAdapter<LoanProfile>(
DiffCallback(),
    itemClickListener = itemClickListener
){

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_loan_profile_table_item;
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<LoanProfile>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding  = (holder.binding as ItemLoanProfileTableItemBinding);


    }
    class DiffCallback : DiffUtil.ItemCallback<LoanProfile>() {
        override fun areItemsTheSame(
            oldItem: LoanProfile,
            newItem: LoanProfile
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: LoanProfile,
            newItem: LoanProfile
        ): Boolean {
            return false
        }

    }
}