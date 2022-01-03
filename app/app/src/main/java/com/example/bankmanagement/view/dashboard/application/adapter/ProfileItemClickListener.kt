package com.example.bankmanagement.view.dashboard.application.adapter

import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.application.BaseApplication

interface ApplicationItemClickListener {
    fun onContractClicked(item: String)
    fun onApplicationClicked(item:BaseApplication)
}