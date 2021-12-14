package com.example.bankmanagement.models

import com.google.gson.annotations.SerializedName


abstract class Customer(
    @SerializedName("_id")
    open val id: String,
    open val name: String,
    open val address: String,
    open val identityNumber: String,
    open val identityCardCreatedDate: String,
    open val phoneNumber: String,
    open val email: String?,

){
     abstract fun getType():CustomerType
}

data class ResidentCustomer(
    val permanentResidence: String,
    val dateOfBirth: String,
    override val id: String,
    override val name: String,
    override val address: String,
    override val identityNumber: String,
    override val identityCardCreatedDate: String,
    override val phoneNumber: String,
    override val email: String?=null,

    ) : Customer(
    id=id,
    name=name,
    address=address,
    identityNumber=identityNumber,
    identityCardCreatedDate=identityCardCreatedDate,
    phoneNumber=phoneNumber,
    email=email

) {
    override fun getType(): CustomerType = CustomerType.Resident
}

data class BusinessCustomer(
    val businessRegistrationCertificate: String,
    val companyRules: String,
    override val id: String,
    override val name: String,
    override val address: String,
    override val identityNumber: String,
    override val identityCardCreatedDate: String,
    override val phoneNumber: String,
    override val email: String?=null,
) : Customer(
    id=id,
    name=name,
    address=address,
    identityNumber=identityNumber,
    identityCardCreatedDate=identityCardCreatedDate,
    phoneNumber=phoneNumber,
    email=email
) {
    override fun getType(): CustomerType = CustomerType.Business

}

enum class CustomerType(val value: Int) {
    @SerializedName("1")
    Business(1),

    @SerializedName("2")
    Resident(2);

    companion object {
        private val map = CustomerType.values().associateBy(CustomerType::value)
        fun fromInt(type: Int): CustomerType = map[type]!!
    }
}