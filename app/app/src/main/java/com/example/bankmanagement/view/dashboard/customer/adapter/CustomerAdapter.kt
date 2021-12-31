package com.example.bankmanagement.view.dashboard.customer.adapter

import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemCustomerTableItemBinding
import com.example.bankmanagement.models.Customer

class CustomerAdapter(
    itemClickListener: BaseItemClickListener<Customer>,
) : BaseBindingListAdapter<Customer>(
    DiffCallback(),
    itemClickListener = itemClickListener
) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_customer_table_item;
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<Customer>, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
        val binding = (holder.binding as ItemCustomerTableItemBinding)

     
    }

    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(
            oldItem: Customer,
            newItem: Customer
        ): Boolean {
            return oldItem == newItem;
        }

        override fun areContentsTheSame(
            oldItem: Customer,
            newItem: Customer
        ): Boolean {
            return false
        }

    }
}