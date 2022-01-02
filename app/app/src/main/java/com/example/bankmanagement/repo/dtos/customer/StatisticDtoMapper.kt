package com.example.bankmanagement.repo.dtos.customer

import com.example.bankmanagement.models.customer.Statistic
import com.example.bankmanagement.utils.ModelMapper

class StatisticDtoMapper
    : ModelMapper<StatisticDto, Statistic> {
    override fun fromDto(dto: StatisticDto): Statistic = Statistic(
        thisYear = dto.thisYear,
        lastYear = dto.lastYear,
        total = dto.total,
        paid = dto.paid,
        unPaid = dto.unPaid,
    )

    override fun toDto(model: Statistic): StatisticDto = StatisticDto(
        thisYear = model.thisYear,
        lastYear = model.lastYear,
        total = model.total,
        paid = model.paid,
        unPaid = model.unPaid,
    )

}