package com.example.bankmanagement.view.review_profile

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.Staff

interface ReviewContractUICallback: BaseUserView {
   fun onBack();
   fun showCreateDisburseDialogFragment(contractId: String, maxAmount: Double)
}