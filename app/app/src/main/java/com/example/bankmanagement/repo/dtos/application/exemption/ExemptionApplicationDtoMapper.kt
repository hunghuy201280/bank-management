//package com.example.bankmanagement.repo.dtos.application.exemption
//
//import com.example.bankmanagement.models.LiquidationApplication
//import com.example.bankmanagement.models.LiquidationDecision
//import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
//import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDto
//import com.example.bankmanagement.repo.dtos.loan_contract.PaymentReceiptDtoMapper
//import com.example.bankmanagement.utils.ModelMapper
//
//class ExemptionApplicationDtoMapper
//    (private val decisionDtoMapper: LiquidationDecisionDtoMapper) :
//    ModelMapper<ExemptionDecisionDto, LiquidationApplication> {
//    override fun fromDto(dto: LiquidationApplicationDto): LiquidationApplication {
//        return LiquidationApplication(
//            id = dto.id,
//            loanContract = dto.loanContract,
//            reason = dto.reason,
//            amount = dto.amount,
//            status = dto.status,
//            signatureImg = dto.signatureImg,
//            createdAt = dto.createdAt,
//            applicationNumber = dto.applicationNumber,
//            decision = dto.decision?.let { decisionDtoMapper.fromDto(it) },
//        )
//    }
//
//    override fun toDto(model: LiquidationApplication): LiquidationApplicationDto {
//        return LiquidationApplicationDto(
//            id = model.id,
//            loanContract = model.loanContract,
//            reason = model.reason,
//            amount = model.amount,
//            status = model.status,
//            signatureImg = model.signatureImg,
//            createdAt = model.createdAt,
//            applicationNumber = model.applicationNumber,
//            decision = model.decision?.let { decisionDtoMapper.toDto(it) },
//        )
//    }
//
//}
