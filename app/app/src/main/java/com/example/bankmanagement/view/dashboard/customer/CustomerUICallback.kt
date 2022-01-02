package com.example.bankmanagement.view.dashboard.customer

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.customer.CustomerType

interface CustomerUICallback: BaseUserView {
    fun onCreateClicked(type: CustomerType)
}