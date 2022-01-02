package com.example.bankmanagement.view.create_customer
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.Staff

interface CreateCustomerUICallback: BaseUserView {
    fun dismissDialog(refresh:Boolean=false)
}