package com.example.bankmanagement.repo.dtos.application.exemption

import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.utils.ModelMapper

class ExemptionApplicationDtoMapper
    (
    private val decisionDtoMapper: ExemptionDecisionDtoMapper
) :
    ModelMapper<ExemptionApplicationDto, ExemptionApplication> {
    override fun fromDto(dto: ExemptionApplicationDto): ExemptionApplication {
        return ExemptionApplication(
            id = dto.id!!,
            loanContract = dto.loanContract!!,
            reason = dto.reason!!,
            amount = dto.amount!!,
            status = dto.status!!,
            signatureImg = dto.signatureImg!!,
            createdAt = dto.createdAt!!,
            applicationNumber = dto.applicationNumber!!,
            decision = dto.decision?.let { decisionDtoMapper.fromDto(it) },
        )
    }

    override fun toDto(model: ExemptionApplication): ExemptionApplicationDto {
        return ExemptionApplicationDto(
            id = model.id,
            loanContract = model.loanContract,
            reason = model.reason,
            amount = model.amount,
            status = model.status,
            signatureImg = model.signatureImg,
            createdAt = model.createdAt,
            applicationNumber = model.applicationNumber,
            decision = model.decision?.let { decisionDtoMapper.toDto(it) },
        )
    }


}
