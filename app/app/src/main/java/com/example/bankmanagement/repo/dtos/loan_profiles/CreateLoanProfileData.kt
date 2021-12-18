package com.example.bankmanagement.repo.dtos.loan_profiles

import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.models.ProofOfIncomeRequest

data class CreateLoanProfileData(
    val customerId: String,
    val proofOfIncome: List<ProofOfIncomeRequest>,
    val moneyToLoan: Double,
    val loanPurpose: String,
    val loanDuration: Long,
    val collateral: String,
    val expectedSourceMoneyToRepay: String,
    val benefitFromLoan: String,
    val signatureImg: String,
    val loanType: LoanType,
    val branchInfo: String,
){
    fun validate():String?{
        if(proofOfIncome.isEmpty()) return "Please choose proof of income images"
        if(signatureImg.isBlank()) return "Please choose proof of income images"
        if(moneyToLoan<=0.0) return "Invalid money to loan"
        if(loanPurpose.isBlank()) return "Invalid loan purpose"
        if(loanDuration<=0) return "Invalid loan duration"
        if(collateral.isBlank()) return "Invalid collateral"
        if(expectedSourceMoneyToRepay.isBlank()) return "Invalid expected money to repay"
        if(benefitFromLoan.isBlank()) return "Invalid benefit from loan"

        return null;
    }
}