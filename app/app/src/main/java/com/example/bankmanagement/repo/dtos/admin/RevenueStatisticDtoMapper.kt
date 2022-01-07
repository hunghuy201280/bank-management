package com.example.bankmanagement.repo.dtos.admin

import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.models.admin.RevenueStatistic
import com.example.bankmanagement.utils.ModelMapper

class RevenueStatisticDtoMapper
    : ModelMapper<RevenueStatisticDto, RevenueStatistic> {
    override fun fromDto(dto: RevenueStatisticDto): RevenueStatistic {
        return RevenueStatistic(
            totalPayment = dto.totalPayment,
            totalDisburse = dto.totalDisburse,
            revenueByType = dto.revenueByType.entries.associate {
                LoanType.fromInt(Integer.parseInt(it.key)) to it.value
            },
            revenueByMonth = dto.revenueByMonth,
        )
    }

    override fun toDto(model: RevenueStatistic): RevenueStatisticDto {
        return RevenueStatisticDto(
            totalPayment = model.totalPayment,
            totalDisburse = model.totalDisburse,
            revenueByType = model.revenueByType.entries.associate {
                it.key.value.toString() to it.value
            },
            revenueByMonth = model.revenueByMonth,
        )
    }

}