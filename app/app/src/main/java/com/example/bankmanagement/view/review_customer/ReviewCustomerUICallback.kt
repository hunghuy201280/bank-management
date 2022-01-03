package com.example.bankmanagement.view.review_profile

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.Staff

interface ReviewCustomerUICallback: BaseUserView {
   fun onBack();
   fun showCreateContractDialogFragment()
   fun onEditCustomerInfo()
   fun onViewCustomerInfo()
}