package com.example.bankmanagement.repo.dtos.sign_in

import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.models.StaffRole
import com.example.bankmanagement.utils.ModelMapper

class StaffDtoMapper :ModelMapper<StaffDto, Staff>{
    override fun fromDto(dto: StaffDto): Staff {
        return Staff(
            id = dto.id,
            role = StaffRole.fromInt(dto.role),
            name = dto.name,
            email = dto.email,
            branchId = dto.branchId
        );
    }

    override fun toDto(model: Staff): StaffDto {
        return StaffDto(
            id = model.id,
            role = model.role.value,
            name = model.name,
            email = model.email,
            branchId = model.branchId
        );
    }

}