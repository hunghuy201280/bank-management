package com.example.bankmanagement.view.review_profile

import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.models.Staff

interface ReviewProfileUICallback: BaseUserView {
   fun onBack();
   fun showCreateContractDialogFragment()

}