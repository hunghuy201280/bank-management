package com.example.bankmanagement.view.dashboard.contract

import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemLoanContractTableItemBinding
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.view.dashboard.contract.adapter.ContractItemClickListener

class ContractAdapter(
    rootClickListener: BaseItemClickListener<LoanContract>,
    val contractClickListener: ContractItemClickListener? = null
) : BaseBindingListAdapter<LoanContract>(
    DiffCallback(),
    itemClickListener = rootClickListener

) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_loan_contract_table_item;
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<LoanContract>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding = (holder.binding as ItemLoanContractTableItemBinding);

        initListener(binding, item)
    }

    private fun initListener(binding: ItemLoanContractTableItemBinding, item: LoanContract) {
        binding.contractNumberTv.setOnClickListener {
            contractClickListener?.onContractNumberClick(item)
        }

        binding.loanNumberTv.setOnClickListener {
            contractClickListener?.onLoanNumberClick(item.loanProfile)
        }
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