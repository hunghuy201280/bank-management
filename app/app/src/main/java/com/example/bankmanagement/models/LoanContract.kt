package com.example.bankmanagement.models

data class LoanContract(
    val id: String,
    val branchId: String,
    val loanProfile: LoanProfile,
    val commitment: String,
    val signatureUrl: String,
    val dateCreated: String,
) {

}