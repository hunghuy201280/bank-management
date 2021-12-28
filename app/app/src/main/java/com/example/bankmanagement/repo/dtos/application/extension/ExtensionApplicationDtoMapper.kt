package com.example.bankmanagement.repo.dtos.application.extension

import com.example.bankmanagement.models.application.extension.ExtensionApplication
import com.example.bankmanagement.utils.ModelMapper

class ExtensionApplicationDtoMapper
    (
    private val decisionDtoMapper: ExtensionDecisionDtoMapper
) :
    ModelMapper<ExtensionApplicationDto, ExtensionApplication> {
    override fun fromDto(dto: ExtensionApplicationDto): ExtensionApplication {
        return ExtensionApplication(
            id = dto.id,
            loanContract = dto.loanContract,
            reason = dto.reason,
            amount = dto.amount,
            status = dto.status,
            signatureImg = dto.signatureImg,
            createdAt = dto.createdAt,
            applicationNumber = dto.applicationNumber,
            decision = dto.decision?.let { decisionDtoMapper.fromDto(it) },
            duration = dto.duration
        )
    }

    override fun toDto(model: ExtensionApplication): ExtensionApplicationDto {
        return ExtensionApplicationDto(
            id = model.id,
            loanContract = model.loanContract,
            reason = model.reason,
            amount = model.amount,
            status = model.status,
            signatureImg = model.signatureImg,
            createdAt = model.createdAt,
            applicationNumber = model.applicationNumber,
            decision = model.decision?.let { decisionDtoMapper.toDto(it) },
            duration = model.duration

        )
    }


}
