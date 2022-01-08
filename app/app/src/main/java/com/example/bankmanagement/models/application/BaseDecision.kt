package com.example.bankmanagement.models.application

import android.net.Uri
import com.example.bankmanagement.constants.AppConfigs
import java.io.Serializable

abstract class BaseDecision(
    open val id: String,
    open val reason: String,
    open val amount: Double,
    open val BODSignature: String,
    open val createdAt: String,
    open val decisionNumber: String,
) : Serializable {
    fun getSignatureImage(): Uri {
        return Uri.parse("${AppConfigs.baseUrl}images/$BODSignature")
    }
}