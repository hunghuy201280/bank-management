package com.example.bankmanagement.repo.dtos.loan_profiles

import com.example.bankmanagement.models.*
import com.example.bankmanagement.repo.dtos.customer.CustomerDtoMapper
import com.example.bankmanagement.repo.dtos.sign_in.StaffDto
import com.example.bankmanagement.repo.dtos.sign_in.StaffDtoMapper
import com.example.bankmanagement.utils.ModelMapper
import javax.inject.Inject

class LoanProfileDtoMapper
    @Inject
    constructor(
        private val customerMapper:CustomerDtoMapper,
       private val staffDtoMapper: StaffDtoMapper
    )
    :ModelMapper<LoanProfileDto, LoanProfile> {
    override fun fromDto(dto: LoanProfileDto): LoanProfile {
        return LoanProfile(
            id=dto.id,
         customer=customerMapper.fromDto(dto.customer),
         staff=staffDtoMapper.fromDto(dto.staff),
         loanApplicationNumber=dto.loanApplicationNumber,
         proofOfIncome=dto.proofOfIncome,
         moneyToLoan=dto.moneyToLoan,
         loanPurpose=dto.loanPurpose,
         loanDuration=dto.loanDuration,
         collateral=dto.collateral,
         expectedSourceMoneyToRepay=dto.expectedSourceMoneyToRepay,
         benefitFromLoan=dto.benefitFromLoan,
         signatureImg=dto.signatureImg,
         loanType=dto.loanType,
         loanStatus=dto.loanStatus,
         branchInfo=dto.branchInfo,
         approver= dto.approver?.let { staffDtoMapper.fromDto(it) },
         createdAt=dto.createdAt,
        );
    }

    override fun toDto(model: LoanProfile): LoanProfileDto {
        return LoanProfileDto(
            id=model.id,
            customer=customerMapper.toDto(model.customer),
            staff=staffDtoMapper.toDto(model.staff),
            loanApplicationNumber=model.loanApplicationNumber,
            proofOfIncome=model.proofOfIncome,
            moneyToLoan=model.moneyToLoan,
            loanPurpose=model.loanPurpose,
            loanDuration=model.loanDuration,
            collateral=model.collateral,
            expectedSourceMoneyToRepay=model.expectedSourceMoneyToRepay,
            benefitFromLoan=model.benefitFromLoan,
            signatureImg=model.signatureImg,
            loanType=model.loanType,
            loanStatus=model.loanStatus,
            branchInfo=model.branchInfo,
            approver= model.approver?.let { staffDtoMapper.toDto(it) },
            createdAt=model.createdAt,
        );
    }
}