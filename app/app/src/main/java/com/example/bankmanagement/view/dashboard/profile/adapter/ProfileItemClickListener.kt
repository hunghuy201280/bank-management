package com.example.bankmanagement.view.dashboard.profile.adapter

import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile

interface ProfileItemClickListener {
    fun onLoanNumberClick(item: LoanProfile)
}