package com.example.bankmanagement.repo.dtos.loan_contract


import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDto
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.google.gson.annotations.SerializedName

data class LoanContractDto(
    @SerializedName("_id")
    val id: String,
    val branchInfo: String,
    val loanProfile: LoanProfileDto,
    val commitment: String,
    val signatureImg: String,
    val createdAt: String,
    val contractNumber: String,
    val disburseCertificates: List<DisburseCertificateDto>,
    val liquidationApplications: List<LiquidationApplicationDto>
)

data class DisburseCertificateDto(
    @SerializedName("_id")
    val id: String,
    val loanContract: String,
    val certNumber: String,
    val amount: Double,
    val createdAt: String,
)



data class PaymentReceiptDto(
    @SerializedName("_id")
    val id: String,
    val amount: Double,
    val createdAt: String,
    val receiptNumber: String,
)
