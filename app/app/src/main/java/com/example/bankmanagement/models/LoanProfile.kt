package com.example.bankmanagement.models


import android.net.Uri
import com.example.bankmanagement.constants.AppConfigs
import com.example.bankmanagement.models.customer.Customer
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class LoanProfile(
    @SerializedName("_id")
    val id: String="",
    val customer: Customer,
    val staff: Staff,
    val loanApplicationNumber: String,
    val proofOfIncome: List<ProofOfIncomeResp>,
    val moneyToLoan: Double,
    val loanPurpose: String,
    val loanDuration: Long,
    val collateral: String,
    val expectedSourceMoneyToRepay: String,
    val benefitFromLoan: String,
    val signatureImg: String,
    val loanType: LoanType,
    val loanStatus: LoanStatus,
    val branchInfo: String,
    val approver: Staff?,
    val createdAt:String,
)  {
    fun getDate():DateTime{
        return DateTime.parse(createdAt);
    }
    fun getSignatureImage():Uri{
          return  Uri.parse( "${AppConfigs.baseUrl}images/$signatureImg")
    }

}


enum class LoanStatus(val value: Int) {
    @SerializedName("1")
    Pending(1),
    @SerializedName("2")
    Done(2),
    @SerializedName("3")
    Rejected(3),
    @SerializedName("4")
    Deleted(4),
    @SerializedName("-1")
    All(-1);
    companion object {
        fun getValues():List<String> = values().map { it.getName() }

    }
    fun getName():String{
        return when(this){
            Pending ->"Pending"
            Done ->"Done"
            Rejected ->"Rejected"
            Deleted ->"Deleted"
            All ->"All"
        }
    }
}

enum class LoanType(val value: Int) {
    @SerializedName("1")
    EachTime(1),

    @SerializedName("2")

    CreditLine(2),

    @SerializedName("3")

    InvestmentProject(3),

    @SerializedName("4")

    Installment(4),

    @SerializedName("5")

    StandbyCreditLimit(5),

    @SerializedName("6")

    CapitalMeeting(6),

    @SerializedName("7")
    UnderOverdraftLimit(7),
    @SerializedName("-1")
    All(-1);
    companion object {
        private val map = LoanType.values().associateBy(LoanType::value)
        fun fromInt(type: Int): LoanType = map[type]!!


        fun getValues():List<String> = values().dropLast(1).map { it.getName() }
        fun getFilterValues():List<String> = values().map { it.getName() }
    }
    fun getName():String{
        return when(this){
            CapitalMeeting ->"Capital Meeting"
            UnderOverdraftLimit->"Under Overdraft Limit"
            StandbyCreditLimit->"Standby Credit Limit"
            Installment->"Installment"
            InvestmentProject->"Investment Project"
            CreditLine->"Credit Line"
            EachTime->"Each Time"
            All->"All"
            else->""
        }
    }
}