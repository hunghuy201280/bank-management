package com.example.bankmanagement.repo

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDtoMapper
import com.example.bankmanagement.repo.dtos.sign_in.LoginData
import com.example.bankmanagement.repo.dtos.sign_in.StaffDto
import com.example.bankmanagement.repo.dtos.sign_in.StaffDtoMapper
import kotlinx.coroutines.withContext


class MainRepositoryImpl
    constructor(
        private val branchInfoMapper:BranchInfoDtoMapper,
        private val staffDtoMapper: StaffDtoMapper,
        private val apiService: ApiService,
        private var accessToken:String="",
    ):MainRepository {


    override suspend fun getBranchInfo(branchCode: String): BranchInfo {
        return branchInfoMapper.fromDto(apiService.getBranchInfo(branchCode =branchCode).data);
    }

    override suspend fun login(email: String, password: String, branchId: String): Staff {
        val response= apiService.login(body = LoginData(
            email=email,
            password=password,
            branchId=branchId,
        ));
        accessToken=response.token;
        return staffDtoMapper.fromDto(response.staff);

    }

    override fun getToken(): String =accessToken;
}

