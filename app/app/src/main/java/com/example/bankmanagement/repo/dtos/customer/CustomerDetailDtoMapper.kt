package com.example.bankmanagement.repo.dtos.customer

import com.example.bankmanagement.models.customer.CustomerDetail
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDtoMapper
import com.example.bankmanagement.utils.ModelMapper

class CustomerDetailDtoMapper(
    private val statisticDtoMapper: StatisticDtoMapper,
    private val customerDtoMapper: CustomerDtoMapper,
    private val loanContractDtoMapper: LoanContractDtoMapper,
) : ModelMapper<CustomerDetailDto, CustomerDetail> {
    override fun fromDto(dto: CustomerDetailDto): CustomerDetail = CustomerDetail(
        customer = customerDtoMapper.fromDto(dto.customer),
        recentContracts = dto.recentContracts.map { loanContractDtoMapper.fromDto(it) },
        statistics = statisticDtoMapper.fromDto(dto.statistics)
    )

    override fun toDto(model: CustomerDetail): CustomerDetailDto = CustomerDetailDto(
        customer = customerDtoMapper.toDto(model.customer),
        recentContracts = model.recentContracts.map { loanContractDtoMapper.toDto(it) },
        statistics = statisticDtoMapper.toDto(model.statistics)
    )

}