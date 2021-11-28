package com.example.bankmanagement.base.adapter

interface BaseItemClickListener<T> {
    fun onItemClick(adapterPosition: Int, item: T)
}