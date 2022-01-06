package com.example.bankmanagement.models.admin

import com.example.bankmanagement.models.LoanType

class RevenueStatistic(
    val totalPayment: Double,
    val totalDisburse: Double,
    val revenueByType: Map<LoanType, Double>,
    val revenueByMonth: Map<String, Double>,
)