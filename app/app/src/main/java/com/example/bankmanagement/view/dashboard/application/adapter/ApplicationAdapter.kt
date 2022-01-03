package com.example.bankmanagement.view.dashboard.application.adapter
import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.databinding.ItemApplicationTableItemBinding
import com.example.bankmanagement.models.application.BaseApplication

class ApplicationAdapter(
    val applicationItemClickListener: ApplicationItemClickListener,
) : BaseBindingListAdapter<BaseApplication>(
    DiffCallback(),
) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_application_table_item;
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<BaseApplication>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding = (holder.binding as ItemApplicationTableItemBinding);
        binding.contractNumberTv.setOnClickListener {
            applicationItemClickListener.onContractClicked(item.loanContract)
        }
        binding.root.setOnClickListener{
            applicationItemClickListener.onApplicationClicked(item)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BaseApplication>() {
        override fun areItemsTheSame(
            oldItem: BaseApplication,
            newItem: BaseApplication
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: BaseApplication,
            newItem: BaseApplication
        ): Boolean {
            return false
        }

    }
}