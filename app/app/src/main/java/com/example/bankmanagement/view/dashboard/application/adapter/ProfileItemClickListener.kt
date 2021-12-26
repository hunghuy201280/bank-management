package com.example.bankmanagement.view.dashboard.application.adapter

import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile

interface ApplicationItemClickListener {
    fun onContractClicked(item: String)
}