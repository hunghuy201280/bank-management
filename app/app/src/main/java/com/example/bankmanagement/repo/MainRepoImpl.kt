package com.example.bankmanagement.repo

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDtoMapper


class MainRepositoryImpl
    constructor(
        private val branchInfoMapper:BranchInfoDtoMapper,
        private val apiService: ApiService,
    ):MainRepository {


    override suspend fun getBranchInfo(branchCode: String): BranchInfo {
        return branchInfoMapper.fromDto(apiService.getBranchInfo(branchCode =branchCode).data);
    }

}

