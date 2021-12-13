package com.example.bankmanagement.repo.dtos.loan_profiles

import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.models.ProofOfIncomeResp
import com.example.bankmanagement.repo.dtos.customer.CustomerDto
import com.example.bankmanagement.repo.dtos.sign_in.StaffDto
import com.google.gson.annotations.SerializedName

data class CreateLoanProfileData(
    val customerId: String,
    val proofOfIncome: List<ProofOfIncomeResp>,
    val moneyToLoan: Double,
    val loanPurpose: String,
    val loanDuration: Long,
    val collateral: String,
    val expectedSourceMoneyToRepay: String,
    val benefitFromLoan: String,
    val signatureImg: String,
    val loanType: LoanType,
    val branchInfo: String,
)