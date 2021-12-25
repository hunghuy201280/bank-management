package com.example.bankmanagement.repo.dtos.application.exemption

import com.example.bankmanagement.repo.dtos.loan_contract.PaymentReceiptDto
import com.google.gson.annotations.SerializedName

data class ExemptionDecisionDto(
    @SerializedName("_id")
    val id: String,
    val reason: String,
    val amount: Double,
    val BODSignature: String,
    val createdAt: String,
    val decisionNumber: String,
)