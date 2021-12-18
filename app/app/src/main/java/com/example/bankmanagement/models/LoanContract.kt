package com.example.bankmanagement.models

import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto




data class LoanContract(
    val id: String,
    val branchInfo: String,
    val loanProfile: LoanProfileDto,
    val commitment: String,
    val signatureImg: String,
    val createdAt: String,
    val contractNumber: String,
    val disburseCertificates: List<DisburseCertificate>,
    val liquidationApplications: List<LiquidationApplication>
)

data class DisburseCertificate(
    val id: String,
    val loanContract: String,
    val certNumber: String,
    val amount: Double,
    val createdAt: String,
)

data class LiquidationApplication(
    val id: String,
    val loanContract: String,
    val reason: String,
    val amount: Double,
    val status: LoanStatus,
    val signatureImg: String,
    val createdAt: String,
    val applicationNumber: String,
    val decision: LiquidationDecision? = null,
)

data class LiquidationDecision(
    val id: String,
    val reason: String,
    val amount: Double,
    val BODSignature: String,
    val createdAt: String,
    val decisionNumber: String,
    val paymentReceipt: PaymentReceipt? = null,
)

data class PaymentReceipt(
    val id: String,
    val amount: Double,
    val createdAt: String,
    val receiptNumber: String,
)
