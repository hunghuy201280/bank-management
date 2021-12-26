package com.example.bankmanagement.repo.dtos.loan_contract

import com.example.bankmanagement.models.DisburseCertificate
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.PaymentReceipt
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDtoMapper
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDtoMapper
import com.example.bankmanagement.utils.ModelMapper

class LoanContractDtoMapper
    (
    private val disburseCertificateDtoMapper: DisburseCertificateDtoMapper,
    private val liquidationApplicationDtoMapper: LiquidationApplicationDtoMapper,
    private val loanProfileDtoMapper: LoanProfileDtoMapper

) : ModelMapper<LoanContractDto, LoanContract> {
    override fun fromDto(dto: LoanContractDto): LoanContract {
        return LoanContract(
            id = dto.id,
            branchInfo = dto.branchInfo,
            loanProfile = loanProfileDtoMapper.fromDto(dto.loanProfile),
            commitment = dto.commitment,
            signatureImg = dto.signatureImg,
            createdAt = dto.createdAt,
            contractNumber = dto.contractNumber,
            disburseCertificates = dto.disburseCertificates?.map {
                disburseCertificateDtoMapper.fromDto(
                    it
                )
            } ?: listOf(),
            liquidationApplications = dto.liquidationApplications?.map {
                liquidationApplicationDtoMapper.fromDto(
                    it
                )
            } ?: listOf(),
        )
    }

    override fun toDto(model: LoanContract): LoanContractDto {
        return LoanContractDto(
            id = model.id,
            branchInfo = model.branchInfo,
            loanProfile = loanProfileDtoMapper.toDto(model.loanProfile),
            commitment = model.commitment,
            signatureImg = model.signatureImg,
            createdAt = model.createdAt,
            contractNumber = model.contractNumber,
            disburseCertificates = model.disburseCertificates.map {
                disburseCertificateDtoMapper.toDto(
                    it
                )
            },
            liquidationApplications = model.liquidationApplications.map {
                liquidationApplicationDtoMapper.toDto(
                    it
                )
            },
        )
    }
}

class DisburseCertificateDtoMapper : ModelMapper<DisburseCertificateDto, DisburseCertificate> {
    override fun fromDto(dto: DisburseCertificateDto): DisburseCertificate {
        return DisburseCertificate(
            id = dto.id,
            loanContract = dto.loanContract,
            certNumber = dto.certNumber,
            amount = dto.amount,
            createdAt = dto.createdAt,
        )
    }

    override fun toDto(model: DisburseCertificate): DisburseCertificateDto {
        return DisburseCertificateDto(
            id = model.id,
            loanContract = model.loanContract,
            certNumber = model.certNumber,
            amount = model.amount,
            createdAt = model.createdAt,
        )
    }

}


class PaymentReceiptDtoMapper : ModelMapper<PaymentReceiptDto, PaymentReceipt> {
    override fun fromDto(dto: PaymentReceiptDto): PaymentReceipt {
        return PaymentReceipt(
            id = dto.id,
            amount = dto.amount,
            createdAt = dto.createdAt,
            receiptNumber = dto.receiptNumber,
        )
    }

    override fun toDto(model: PaymentReceipt): PaymentReceiptDto {
        return PaymentReceiptDto(
            id = model.id,
            amount = model.amount,
            createdAt = model.createdAt,
            receiptNumber = model.receiptNumber,
        )
    }

}