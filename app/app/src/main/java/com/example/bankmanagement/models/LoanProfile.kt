package com.example.bankmanagement.models

class LoanProfile {

}

enum class LoanType(val value:Int){
    EachTime (1),
    CreditLine (2),
    InvestmentProject (3),
    Installment (4),
    StandbyCreditLimit (5),
    CapitalMeeting (6),
    UnderOverdraftLimit (7);

    companion object {
        private val map = StaffRole.values().associateBy(StaffRole::value)
        fun fromInt(type: Int):StaffRole = map[type]!!
    }
}