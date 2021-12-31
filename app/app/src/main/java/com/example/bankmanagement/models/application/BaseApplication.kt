package com.example.bankmanagement.models.application

import android.net.Uri
import com.example.bankmanagement.constants.AppConfigs
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import java.io.Serializable

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
):Serializable{
    fun getSignatureImage(): Uri {
        return Uri.parse("${AppConfigs.baseUrl}images/$signatureImg")}
}


enum class ApplicationType(
    value:Int
){
    Liquidation(1),
    Exemption(2),
    Extension(3),
    All(-1),
    ;
    companion object {

        fun getValues():List<String> = values().dropLast(1).map { it.name }
        fun getFilterValues():List<String> = values().map { it.name }

    }
}