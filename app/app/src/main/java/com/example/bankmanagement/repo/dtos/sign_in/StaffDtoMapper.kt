package com.example.bankmanagement.repo.dtos.sign_in

import com.example.bankmanagement.models.*
import com.example.bankmanagement.utils.ModelMapper
import java.lang.Exception

class StaffDtoMapper :ModelMapper<StaffDto, Staff>{
    override fun fromDto(dto: StaffDto): Staff {
        return when(val role=StaffRole.fromInt(dto.role)){
            StaffRole.Appraisal->AppraisalStaff(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                branchId = dto.branchId
            )
            StaffRole.Business->BusinessStaff(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                branchId = dto.branchId
            )
            StaffRole.Director->BoardOfDirector(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                branchId = dto.branchId
            )
            StaffRole.Support->SupportStaff(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                branchId = dto.branchId
            )
            else->{throw Exception("Unknown role $role")}
        }
    }

    override fun toDto(model: Staff): StaffDto {
        val role= when(model.javaClass){
            AppraisalStaff::javaClass->StaffRole.Appraisal
            BoardOfDirector::javaClass->StaffRole.Director
            SupportStaff::javaClass->StaffRole.Support
            BusinessStaff::javaClass->StaffRole.Business
            else->{throw Exception("Unknown staff role ${model.javaClass}")}
        }
        return StaffDto(
            id = model.id,
            role = role.value,
            name = model.name,
            email = model.email,
            branchId = model.branchId
        );
    }

}