package com.example.bankmanagement.view.dashboard.application.review_application

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.models.application.ApplicationType

interface ReviewApplicationUICallback: BaseUserView {
    fun onBack(refresh: Boolean=false)
}