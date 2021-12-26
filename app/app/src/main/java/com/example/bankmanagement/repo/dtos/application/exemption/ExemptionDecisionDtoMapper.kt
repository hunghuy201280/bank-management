package com.example.bankmanagement.repo.dtos.application.exemption

import com.example.bankmanagement.models.application.exemption.ExemptionDecision
import com.example.bankmanagement.utils.ModelMapper

class ExemptionDecisionDtoMapper
    : ModelMapper<ExemptionDecisionDto, ExemptionDecision> {
    override fun fromDto(dto: ExemptionDecisionDto): ExemptionDecision {
        return ExemptionDecision(
            id = dto.id,
            reason = dto.reason,
            amount = dto.amount,
            BODSignature = dto.BODSignature,
            createdAt = dto.createdAt,
            decisionNumber = dto.decisionNumber,
        )
    }

    override fun toDto(model: ExemptionDecision): ExemptionDecisionDto {
        return ExemptionDecisionDto(
            id = model.id,
            reason = model.reason,
            amount = model.amount,
            BODSignature = model.BODSignature,
            createdAt = model.createdAt,
            decisionNumber = model.decisionNumber,
        )
    }


}
