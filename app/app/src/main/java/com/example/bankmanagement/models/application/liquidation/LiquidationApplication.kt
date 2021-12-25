package com.example.bankmanagement.models.application.liquidation

import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.application.BaseApplication

data class LiquidationApplication(
    override val id: String,
    override val loanContract: String,
    override val reason: String,
    override val amount: Double,
    override val status: LoanStatus,
    override val signatureImg: String,
    override val createdAt: String,
    override val applicationNumber: String,
    override val decision: LiquidationDecision? = null,
) : BaseApplication(
    id =id,
    loanContract =loanContract,
    reason =reason,
    amount =amount,
    status =status,
    signatureImg =signatureImg,
    createdAt =createdAt,
    applicationNumber =applicationNumber,
    decision =decision,
)
