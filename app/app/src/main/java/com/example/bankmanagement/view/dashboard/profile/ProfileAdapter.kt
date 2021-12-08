package com.example.bankmanagement.view.dashboard.profile

import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.models.LoanProfile
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.databinding.ItemRowLoanProfileBinding

class ProfileAdapter : BaseBindingListAdapter<LoanProfile>(
DiffCallback(),
){

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_row_loan_profile;
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<LoanProfile>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding  = (holder.binding as ItemRowLoanProfileBinding);


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