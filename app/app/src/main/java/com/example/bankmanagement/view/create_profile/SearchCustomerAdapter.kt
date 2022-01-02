package com.example.bankmanagement.view.create_profile

import com.example.bankmanagement.base.adapter.BaseBindingListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.bankmanagement.R
import com.example.bankmanagement.base.adapter.BaseBindingViewHolder
import com.example.bankmanagement.base.adapter.BaseItemClickListener
import com.example.bankmanagement.databinding.ItemSearchCustomerResultBinding
import com.example.bankmanagement.models.customer.Customer


class SearchCustomerAdapter(private val itemClickListener: BaseItemClickListener<Customer>) : BaseBindingListAdapter<Customer>(
DiffCallback(),
    itemClickListener = itemClickListener
){

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_search_customer_result;
    }
    fun setSelectedCustomer(position: Int){
        val oldPosition=selectedCustomerPosition;
        selectedCustomerPosition=position;
        notifyItemChanged(position);
        oldPosition?.let { notifyItemChanged(it) };
    }
    private  var selectedCustomerPosition:Int?=null;
    override fun onBindViewHolder(holder: BaseBindingViewHolder<Customer>, position: Int) {
        val item = getItem(position);
        holder.bindItem(item);
        val binding  = (holder.binding as ItemSearchCustomerResultBinding);
        binding.selectedCustomer= selectedCustomerPosition?.let {
            if(it<0)
                return
            getItem(it)
        }

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