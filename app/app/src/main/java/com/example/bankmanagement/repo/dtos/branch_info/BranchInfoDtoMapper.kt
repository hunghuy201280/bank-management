package com.example.bankmanagement.repo.dtos.branch_info

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.utils.ModelMapper

class BranchInfoDtoMapper :ModelMapper<BranchInfoDto,BranchInfo>{
    override fun fromDto(model: BranchInfoDto): BranchInfo {
        return BranchInfo(
            id = model.id,
            branchAddress= model.branchAddress,
            branchPhoneNumber= model.branchPhoneNumber,
            branchFax= model.branchFax,
            branchCode= model.branchCode,
        );
    }

    override fun toDto(domainModel: BranchInfo): BranchInfoDto {
        return BranchInfoDto(
            id = domainModel.id,
            branchAddress= domainModel.branchAddress,
            branchPhoneNumber= domainModel.branchPhoneNumber,
            branchFax= domainModel.branchFax,
            branchCode= domainModel.branchCode,
        );
    }
}