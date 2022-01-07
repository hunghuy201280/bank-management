package com.example.bankmanagement.view.review_contract.review_decision

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.models.application.ApplicationType

interface ReviewDecisionUICallback: BaseUserView {
    fun onBack(refresh: Boolean=false)
}