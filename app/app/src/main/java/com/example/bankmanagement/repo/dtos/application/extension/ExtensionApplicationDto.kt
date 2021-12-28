package com.example.bankmanagement.repo.dtos.application.extension

import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.application.extension.ExtensionDecision
import com.google.gson.annotations.SerializedName

data class ExtensionApplicationDto(
    @SerializedName("_id")
    val id: String,
    val loanContract: String,
    val reason: String,
    val amount: Double,
    val status: LoanStatus,
    val signatureImg: String,
    val createdAt: String,
    val applicationNumber: String,
    val decision: ExtensionDecisionDto? = null,
    val duration:Long,
)

