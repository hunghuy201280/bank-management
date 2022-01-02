package com.example.bankmanagement.repo.dtos.customer

import com.example.bankmanagement.models.customer.CustomerType
import com.google.gson.annotations.SerializedName

data class CustomerDto (
    @SerializedName("_id")
    val id: String,
    val name: String,
    val dateOfBirth: String?=null,
    val address: String,
    val identityNumber: String,
    val identityCardCreatedDate: String,
    val phoneNumber: String,
    val permanentResidence: String?=null,
    val customerType: CustomerType,
    val businessRegistrationCertificate: String?=null,
    val companyRules: String?=null,
    val email:String?=null,
    )

