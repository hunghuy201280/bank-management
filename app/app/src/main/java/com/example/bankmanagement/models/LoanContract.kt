package com.example.bankmanagement.models

import android.net.Uri
import com.example.bankmanagement.constants.AppConfigs
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.models.application.exemption.ExemptionDecision
import com.example.bankmanagement.models.application.extension.ExtensionApplication
import com.example.bankmanagement.models.application.extension.ExtensionDecision
import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationDecision
import com.example.bankmanagement.repo.dtos.application.exemption.ExemptionApplicationDto
import com.example.bankmanagement.repo.dtos.application.extension.ExtensionApplicationDto
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDto
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
    val exemptionApplications: List<ExemptionApplication>,
    val extensionApplications: List<ExtensionApplication>,
):Serializable {
    override fun toString(): String {
        return contractNumber
    }

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
    fun getExtensionDecisions(): List<ExtensionDecision> {
        val temp = ArrayList(extensionApplications)

        return temp.filter { it.decision != null }.map { it.decision!! }
    }
    fun getExemptionDecisions(): List<ExemptionDecision> {
        val temp = ArrayList(exemptionApplications)

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

    fun getRemainingDisburseAmount(): Double {
        return loanProfile.moneyToLoan - getDisburseAmount()
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
