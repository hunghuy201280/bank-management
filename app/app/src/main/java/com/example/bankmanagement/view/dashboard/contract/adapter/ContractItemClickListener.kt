package com.example.bankmanagement.view.dashboard.contract.adapter

import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile

interface ContractItemClickListener {
    fun onContractNumberClick(item: LoanContract)
    fun onLoanNumberClick(item: LoanProfile)
}