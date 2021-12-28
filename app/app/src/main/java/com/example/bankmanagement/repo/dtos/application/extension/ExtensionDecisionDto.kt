package com.example.bankmanagement.repo.dtos.application.extension

import com.google.gson.annotations.SerializedName

data class ExtensionDecisionDto(
    @SerializedName("_id")
    val id: String,
    val reason: String,
    val amount: Double,
    val BODSignature: String,
    val createdAt: String,
    val decisionNumber: String,
    val duration: Long,
)