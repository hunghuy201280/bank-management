package com.example.bankmanagement.models.application.exemption

import com.example.bankmanagement.models.application.BaseDecision
import com.google.gson.annotations.SerializedName

data class ExemptionDecision(
    @SerializedName("_id")
    override val id: String,
    override val reason: String,
    override val amount: Double,
    override val BODSignature: String,
    override val createdAt: String,
    override val decisionNumber: String,
) : BaseDecision(
    id = id,
    reason = reason,
    amount = amount,
    BODSignature = BODSignature,
    createdAt = createdAt,
    decisionNumber = decisionNumber,
)