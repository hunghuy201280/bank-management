package com.example.bankmanagement.repo

import com.example.bankmanagement.models.*
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDtoMapper
import com.example.bankmanagement.repo.dtos.customer.CustomerDtoMapper
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDto
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDtoMapper
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDtoMapper
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.sign_in.SignInData
import com.example.bankmanagement.repo.dtos.sign_in.StaffDtoMapper
import com.example.bankmanagement.repo.dtos.up_files.UpFileResp
import com.example.bankmanagement.utils.Utils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class MainRepositoryImpl
constructor(
    private val branchInfoMapper: BranchInfoDtoMapper,
    private val staffDtoMapper: StaffDtoMapper,
    private val profileMapper: LoanProfileDtoMapper,
    private val customerDtoMapper: CustomerDtoMapper,
    private val apiService: ApiService,
    private var accessToken: String = "",
    private val loanContractDtoMapper: LoanContractDtoMapper,
    ) : MainRepository {


    override suspend fun getBranchInfo(branchCode: String): BranchInfo {
        return branchInfoMapper.fromDto(apiService.getBranchInfo(branchCode = branchCode).data);
    }

    override suspend fun login(
        email: String,
        password: String,
        branchId: String
    ): Pair<Staff, ClockInOutResponse> {
        val response = apiService.login(
            body = SignInData(
                email = email,
                password = password,
                branchId = branchId,
            )
        );
        accessToken = response.token;
        return Pair(staffDtoMapper.fromDto(response.staff), response.clockInOut);

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
        val response = apiService.getClockInOutTime(token = accessToken);
        return response;
    }

    override suspend fun getLoanProfiles(): ArrayList<LoanProfile> {
        val response = apiService.getLoanProfiles(accessToken);
        return ArrayList(
            response.map
            {
                profileMapper.fromDto(it)
            });
    }

    override suspend fun searchCustomers(query: Map<String, Any>): ArrayList<Customer> {
        val response = apiService.searchCustomer(query = query, token = accessToken);

        return ArrayList(response.data.map { customerDtoMapper.fromDto(it) })

    }

    override suspend fun createLoanProfile(data: CreateLoanProfileData): LoanProfileDto {
        val response = apiService.createLoanProfile(body = data, token = accessToken);
        return response;
    }

    override suspend fun upFiles(files: List<File>): UpFileResp {
        val multipartFiles = arrayListOf<MultipartBody.Part>();

        for (file in files) {
            val fileBody: RequestBody =
                file.asRequestBody(Utils.getMimeType(file.absolutePath)?.toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData(
                "images",
                file.name,
                fileBody
            );
            multipartFiles.add(part);
        }

        val response = apiService.upFiles(token = accessToken, images = multipartFiles);
        return response;

    }

    override suspend fun updateLoanStatus(status: LoanStatus, profileId: String) {
        val response = apiService.updateLoanStatus(
            token = accessToken,
            profileId = profileId,
            body = mapOf("status" to status.value)
        )
    }

    override suspend fun getContracts(): ArrayList<LoanContract> {
        val response = apiService.getLoanContracts(token = accessToken)
        val contracts=response.map { loanContractDtoMapper.fromDto(it) }
        return  ArrayList(contracts)

    }


    override fun getToken(): String = accessToken;
}

