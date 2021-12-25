package com.example.bankmanagement.repo.dtos.application.exemption

import com.example.bankmanagement.models.LoanStatus
import com.google.gson.annotations.SerializedName

data class ExemptionApplicationDto(
    @SerializedName("_id")
    val id: String,
    val loanContract: String,
    val reason: String,
    val amount: Double,
    val status: LoanStatus,
    val signatureImg: String,
    val createdAt: String,
    val applicationNumber: String,
    val decision: ExemptionDecisionDto? = null,
)

