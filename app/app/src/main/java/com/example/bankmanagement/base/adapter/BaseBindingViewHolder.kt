package com.example.bankmanagement.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView


class BaseBindingViewHolder<T>(
    val binding: ViewDataBinding,
    private val itemClickListener: BaseItemClickListener<T>? = null
) : RecyclerView.ViewHolder(binding.root) {

    private var item: T? = null

    fun bindItem(item: T?) {
        item?.let {
            this.item = it
            binding.apply {
                setVariable(BR.item, it)
                executePendingBindings()
            }
        }
        initActions()
    }

    private fun initActions() {
        binding.root.setOnClickListener {
            itemClickListener?.onItemClick(adapterPosition, item!!)
        }
    }
}