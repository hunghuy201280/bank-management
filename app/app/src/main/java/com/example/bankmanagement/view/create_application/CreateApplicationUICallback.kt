package com.example.bankmanagement.view.create_application
import com.example.bankmanagement.base.BaseUserView

interface CreateApplicationUICallback: BaseUserView {
    fun dismissDialog()
    fun refreshDataCallback()
}