package com.example.bankmanagement.models.application

import com.example.bankmanagement.models.LoanStatus

abstract class BaseApplication(
    open val id: String,
    open val loanContract: String,
    open val reason: String,
    open val amount: Double,
    open val status: LoanStatus,
    open val signatureImg: String,
    open val createdAt: String,
    open val applicationNumber: String,
    open val decision: BaseDecision? = null,
)


enum class ApplicationType(
    value:Int
){
    Liquidation(1),
    Exemption(2),
    Extension(3),
}