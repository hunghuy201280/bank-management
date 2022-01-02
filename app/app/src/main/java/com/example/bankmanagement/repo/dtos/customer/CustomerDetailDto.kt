package com.example.bankmanagement.repo.dtos.customer

import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDto

class CustomerDetailDto(
    val customer: CustomerDto,
    val recentContracts: List<LoanContractDto>,
    val statistics: StatisticDto,
)

class StatisticDto(
  val  thisYear:Double,
  val  lastYear:Double,
  val  total:Double,
  val  paid:Double,
  val  unPaid:Double,
)