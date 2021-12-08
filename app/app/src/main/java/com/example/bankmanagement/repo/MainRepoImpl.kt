package com.example.bankmanagement.repo

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDtoMapper
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDtoMapper
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.sign_in.SignInData
import com.example.bankmanagement.repo.dtos.sign_in.StaffDtoMapper


class MainRepositoryImpl
    constructor(
        private val branchInfoMapper:BranchInfoDtoMapper,
        private val staffDtoMapper: StaffDtoMapper,
        private val profileMapper: LoanProfileDtoMapper,
        private val apiService: ApiService,
        private var accessToken:String="",
    ):MainRepository {


    override suspend fun getBranchInfo(branchCode: String): BranchInfo {
        return branchInfoMapper.fromDto(apiService.getBranchInfo(branchCode =branchCode).data);
    }

    override suspend fun login(email: String, password: String, branchId: String): Pair<Staff,ClockInOutResponse> {
        val response= apiService.login(body = SignInData(
            email=email,
            password=password,
            branchId=branchId,
        ));
        accessToken=response.token;
        return Pair(staffDtoMapper.fromDto(response.staff),response.clockInOut);

    }

    override suspend fun clockIn() {
        apiService.clockIn(accessToken);

        return;
    }

    override suspend fun clockOut() {
        apiService.clockOut(accessToken);

        return;
    }

    override suspend fun getClockInOutTime(): ClockInOutResponse {
        val response= apiService.getClockInOutTime(token=accessToken);
        return response;
    }

    override suspend fun getLoanProfiles(): ArrayList<LoanProfile> {
        val response=apiService.getLoanProfiles(accessToken);
        return ArrayList(response.map { profileMapper.fromDto(it) });
    }

    override fun getToken(): String =accessToken;
}

