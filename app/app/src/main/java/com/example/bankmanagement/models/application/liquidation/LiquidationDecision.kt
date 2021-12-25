package com.example.bankmanagement.models.application.liquidation

import com.example.bankmanagement.models.PaymentReceipt
import com.example.bankmanagement.models.application.BaseDecision


data class LiquidationDecision(
    override val id: String,
    override val reason: String,
    override val amount: Double,
    override val BODSignature: String,
    override val createdAt: String,
    override val decisionNumber: String,
    val paymentReceipt: PaymentReceipt? = null,
) : BaseDecision(
    id = id,
    reason = reason,
    amount = amount,
    BODSignature = BODSignature,
    createdAt = createdAt,
    decisionNumber = decisionNumber,
)