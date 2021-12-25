package com.example.bankmanagement.models.application.exemption

import com.example.bankmanagement.repo.dtos.loan_contract.PaymentReceiptDto
import com.google.gson.annotations.SerializedName

data class ExemptionDecision(
    @SerializedName("_id")
    val id: String,
    val reason: String,
    val amount: Double,
    val BODSignature: String,
    val createdAt: String,
    val decisionNumber: String,
)