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
        setTextSpan(binding, item)
    }

    private fun initListener(binding: ItemLoanContractTableItemBinding, item: LoanContract) {
        binding.contractNumberTv.setOnClickListener {
            contractClickListener?.onContractNumberClick(item)
        }

        binding.loanNumberTv.setOnClickListener {
            contractClickListener?.onLoanNumberClick(item.loanProfile)
        }
    }

    private fun setTextSpan(binding: ItemLoanContractTableItemBinding, item: LoanContract) {
        //region ContractNumberTv
        val contractNumberSpan = SpannableString(item.contractNumber)
        contractNumberSpan.setSpan(UnderlineSpan(), 0, item.contractNumber.length, 0)
        binding.contractNumberTv.text = contractNumberSpan
        //endregion

        //region LoanNumberTv
        val loanNumberSpan = SpannableString(item.loanProfile.loanApplicationNumber)
        loanNumberSpan.setSpan(UnderlineSpan(), 0, item.loanProfile.loanApplicationNumber.length, 0)
        binding.loanNumberTv.text = loanNumberSpan
        //endregion
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