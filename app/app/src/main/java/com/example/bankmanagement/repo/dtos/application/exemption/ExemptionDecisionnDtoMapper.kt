//package com.example.bankmanagement.repo.dtos.application.exemption
//
//import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationDecisionDto
//import com.example.bankmanagement.models.LiquidationApplication
//import com.example.bankmanagement.models.LiquidationDecision
//import com.example.bankmanagement.repo.dtos.loan_contract.PaymentReceiptDtoMapper
//import com.example.bankmanagement.utils.ModelMapper
//
//
//class LiquidationDecisionDtoMapper
//constructor(
//    private val paymentReceiptDtoMapper: PaymentReceiptDtoMapper
//) : ModelMapper<com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationDecisionDto, LiquidationDecision> {
//    override fun fromDto(dto: com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationDecisionDto): LiquidationDecision {
//        return LiquidationDecision(
//            id = dto.id,
//            reason = dto.reason,
//            amount = dto.amount,
//            BODSignature = dto.BODSignature,
//            createdAt = dto.createdAt,
//            decisionNumber = dto.decisionNumber,
//            paymentReceipt = dto.paymentReceipt?.let { paymentReceiptDtoMapper.fromDto(it) },
//        )
//    }
//
//    override fun toDto(model: LiquidationDecision): com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationDecisionDto {
//        return com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationDecisionDto(
//            id = model.id,
//            reason = model.reason,
//            amount = model.amount,
//            BODSignature = model.BODSignature,
//            createdAt = model.createdAt,
//            decisionNumber = model.decisionNumber,
//            paymentReceipt = model.paymentReceipt?.let { paymentReceiptDtoMapper.toDto(it) },
//        )
//    }
//
//}
