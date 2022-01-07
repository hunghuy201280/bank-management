package com.example.bankmanagement.models.customer

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.bankmanagement.constants.AppConfigs
import com.example.bankmanagement.utils.toLocalDate
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextInt


abstract class Customer(
    @SerializedName("_id")
    open val id: String,
    open val name: String,
    open val address: String,
    open val identityNumber: String,
    open val identityCardCreatedDate: String,
    open val phoneNumber: String,
    open val email: String?,
) : Serializable {

    val point: Int = (0..5000).random()

    abstract fun getType(): CustomerType

    @RequiresApi(Build.VERSION_CODES.O)
    fun getIdentityCardCreatedDateFormatted(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy");

        return sdf.format(identityCardCreatedDate.toLocalDate())
    }

    fun getCustomerLevel(): String {
        if (point >= 2000)
            return "Gold"
        else if (point >= 1000)
            return "Silver"
        else
            return "Bronze"
    }

    fun getCustomerCode(): String {
        return (id.substring(0,4) + id.substring(id.length - 4, id.length)).uppercase()
    }

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
    override val email: String? = null,

    ) : Customer(
    id = id,
    name = name,
    address = address,
    identityNumber = identityNumber,
    identityCardCreatedDate = identityCardCreatedDate,
    phoneNumber = phoneNumber,
    email = email

) {
    override fun getType(): CustomerType = CustomerType.Resident

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDOB(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy");

        return sdf.format(dateOfBirth.toLocalDate())
    }
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
    override val email: String? = null,
) : Customer(
    id = id,
    name = name,
    address = address,
    identityNumber = identityNumber,
    identityCardCreatedDate = identityCardCreatedDate,
    phoneNumber = phoneNumber,
    email = email
) {
    override fun getType(): CustomerType = CustomerType.Business
    fun getCert(): String = "${AppConfigs.baseUrl}images/$businessRegistrationCertificate"


}

enum class CustomerType(val value: Int) {
    @SerializedName("1")
    Business(1),

    @SerializedName("2")
    Resident(2),

    @SerializedName("-1")
    All(-1);

    fun getTypeName(): String {
        return when (this) {
            Business -> "Business"
            Resident -> "Resident"
            All -> "All"
        }
    }

    companion object {
        fun getValues(): List<String> = values().dropLast(1).map { it.getTypeName() }
        fun getFilterValues(): List<String> = values().map { it.getTypeName() }
    }
}