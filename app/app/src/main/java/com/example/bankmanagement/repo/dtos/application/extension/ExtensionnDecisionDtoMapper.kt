package com.example.bankmanagement.repo.dtos.application.extension

import com.example.bankmanagement.models.application.extension.ExtensionDecision
import com.example.bankmanagement.utils.ModelMapper

class ExtensionDecisionDtoMapper
    : ModelMapper<ExtensionDecisionDto, ExtensionDecision> {
    override fun fromDto(dto: ExtensionDecisionDto): ExtensionDecision {
        return ExtensionDecision(
            id = dto.id,
            reason = dto.reason,
            amount = dto.amount,
            BODSignature = dto.BODSignature,
            createdAt = dto.createdAt,
            decisionNumber = dto.decisionNumber,
            duration = dto.duration
        )
    }

    override fun toDto(model: ExtensionDecision): ExtensionDecisionDto {
        return ExtensionDecisionDto(
            id = model.id,
            reason = model.reason,
            amount = model.amount,
            BODSignature = model.BODSignature,
            createdAt = model.createdAt,
            decisionNumber = model.decisionNumber,
            duration = model.duration

        )
    }


}
