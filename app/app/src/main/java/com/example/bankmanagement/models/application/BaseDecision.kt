package com.example.bankmanagement.models.application

abstract class BaseDecision(
  open  val id: String,
  open  val reason: String,
  open  val amount: Double,
  open  val BODSignature: String,
  open  val createdAt: String,
  open  val decisionNumber: String,
)