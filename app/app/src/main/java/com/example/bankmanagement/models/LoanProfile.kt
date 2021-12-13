package com.example.bankmanagement.models


import com.google.gson.annotations.SerializedName

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
)


enum class LoanStatus(val value: Int) {
    @SerializedName("1")
    Pending(1),
    @SerializedName("2")
    Done(2),
    @SerializedName("3")
    Rejected(3),
    @SerializedName("4")
    Deleted(4);
    companion object {
        private val map = LoanStatus.values().associateBy(LoanStatus::value)
        fun fromInt(type: Int): LoanStatus = map[type]!!
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
    UnderOverdraftLimit(7);

    companion object {
        private val map = LoanType.values().associateBy(LoanType::value)
        fun fromInt(type: Int): LoanType = map[type]!!
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
            else->""
        }
    }
}