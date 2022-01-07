package com.example.bankmanagement.repo.dtos.admin

class RevenueStatisticDto(
    val totalPayment: Double,
    val totalDisburse: Double,
    val revenueByType: Map<String, Double>,
    val revenueByMonth: Map<String, Double>,
)