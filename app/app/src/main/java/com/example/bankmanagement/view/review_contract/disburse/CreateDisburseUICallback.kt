package com.example.bankmanagement.view.review_contract.disburse

import com.example.bankmanagement.base.BaseUserView

interface CreateDisburseUICallback: BaseUserView {
    fun dismissDialog(refresh:Boolean=false)
}