package com.example.bankmanagement.view.create_application
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.Staff

interface CreateApplicationUICallback: BaseUserView {
    fun dismissDialog(refresh:Boolean=false)
}