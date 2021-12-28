package com.example.bankmanagement.models.application.extension

import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.application.BaseApplication
import com.google.gson.annotations.SerializedName

data class ExtensionApplication (
    @SerializedName("_id")
    override val id: String,
    override val loanContract: String,
    override val reason: String,
    override val amount: Double,
    override val status: LoanStatus,
    override val signatureImg: String,
    override val createdAt: String,
    override val applicationNumber: String,
    override val decision: ExtensionDecision? = null,
    val duration:Long,
) : BaseApplication(
    id = id,
    loanContract = loanContract,
    reason = reason,
    amount = amount,
    status = status,
    signatureImg = signatureImg,
    createdAt = createdAt,
    applicationNumber = applicationNumber,
    decision = decision,
)