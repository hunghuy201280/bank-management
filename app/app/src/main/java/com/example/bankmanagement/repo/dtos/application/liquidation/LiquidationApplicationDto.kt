package com.example.bankmanagement.repo.dtos.application.liquidation

import com.example.bankmanagement.models.LoanStatus
import com.google.gson.annotations.SerializedName

data class LiquidationApplicationDto(
    @SerializedName("_id")
    val id: String? = null,
    val loanContract: String? = null,
    val reason: String? = null,
    val amount: Double? = null,
    val status: LoanStatus? = null,
    val signatureImg: String? = null,
    val createdAt: String? = null,
    val applicationNumber: String? = null,
    val decision: LiquidationDecisionDto? = null,
)

