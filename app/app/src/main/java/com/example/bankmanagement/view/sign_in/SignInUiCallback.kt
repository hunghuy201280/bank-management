package com.example.bankmanagement.view.sign_in

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.Staff

interface SignInUiCallback: BaseUserView {
    fun onLoggedIn(staff: Staff)
}