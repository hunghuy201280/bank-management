package com.example.bankmanagement.view.create_profile

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.Staff

interface ClockInOutUICallback: BaseUserView {
    fun onClockedIn()
    fun onClockedOut()
    fun onContinueClicked()
}