package com.example.bankmanagement.models.customer

import com.example.bankmanagement.models.LoanContract


class CustomerDetail(
    val customer: Customer,
    val recentContracts: List<LoanContract>,
    val statistics: Statistic,
)

class Statistic(
    val  thisYear:Double,
    val  lastYear:Double,
    val  total:Double,
    val  paid:Double,
    val  unPaid:Double,
)