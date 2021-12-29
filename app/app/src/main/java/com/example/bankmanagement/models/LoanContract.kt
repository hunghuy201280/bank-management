package com.example.bankmanagement.models

import android.net.Uri
import com.example.bankmanagement.constants.AppConfigs
import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationDecision
import org.joda.time.DateTime
import java.io.Serializable


data class LoanContract(
    val id: String,
    val branchInfo: String,
    val loanProfile: LoanProfile,
    val commitment: String,
    val signatureImg: String,
    val createdAt: String,
    val contractNumber: String,
    val disburseCertificates: List<DisburseCertificate> =  listOf(),
    val liquidationApplications: List<LiquidationApplication> = listOf(),
):Serializable {
    fun getDate(): DateTime {
        return DateTime.parse(createdAt);
    }

    fun getSignatureImage(): Uri {
        return Uri.parse("${AppConfigs.baseUrl}images/$signatureImg")
    }

    fun getLiquidationDecisions(): List<LiquidationDecision> {
        val temp = ArrayList(liquidationApplications)

        return temp.filter { it.decision != null }.map { it.decision!! }
    }

    fun getPaid(): Double {
        val temp = ArrayList(liquidationApplications)
        return temp.filter { it.decision != null }.map { it.decision!! }.sumOf { it.amount }
    }

    fun getUnPaid(): Double {
        val temp = ArrayList(liquidationApplications)
        return getDisburseAmount() - temp.filter { it.decision != null }.map { it.decision!! }
            .sumOf { it.amount }
    }
    fun getDisburseAmount():Double{
        return disburseCertificates.sumOf { it.amount }
    }
}

data class DisburseCertificate(
    val id: String,
    val loanContract: String,
    val certNumber: String,
    val amount: Double,
    val createdAt: String,
):Serializable



data class PaymentReceipt(
    val id: String,
    val amount: Double,
    val createdAt: String,
    val receiptNumber: String,
):Serializable
