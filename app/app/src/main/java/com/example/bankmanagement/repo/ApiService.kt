package com.example.bankmanagement.repo

import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoResponse
import com.example.bankmanagement.repo.dtos.customer.GetCustomerResponse
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDto
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.sign_in.SignInData
import com.example.bankmanagement.repo.dtos.sign_in.SignInResponse
import com.example.bankmanagement.repo.dtos.up_files.UpFileResp
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {
    @GET("branch_info/{branchCode}")
    suspend fun getBranchInfo(
        @Path("branchCode") branchCode: String
    ): BranchInfoResponse

    @POST("staffs/login")
    suspend fun login(
        @Body body: SignInData
    ): SignInResponse

    @POST("staffs/clock_in")
    suspend fun clockIn(
        @Header("Authorization") token: String
    )

    @POST("staffs/clock_out")
    suspend fun clockOut(
        @Header("Authorization") token: String
    )

    @GET("staffs/clock_in_out_time")
    suspend fun getClockInOutTime(
        @Header("Authorization") token: String
    ): ClockInOutResponse

    @GET("loan_profiles/")
    suspend fun getLoanProfiles(
        @Header("Authorization") token: String
    ): ArrayList<LoanProfileDto>

    @GET("customers/")
    @JvmSuppressWildcards
    suspend fun searchCustomer(
        @Header(
            "Authorization",
        ) token: String,
        @QueryMap query: Map<String, Any>,
    ): GetCustomerResponse

    @POST("loan_profiles/")
    @JvmSuppressWildcards
    suspend fun createLoanProfile(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: CreateLoanProfileData,
    ): LoanProfileDto


    @Multipart
    @POST("images/")
    suspend fun upFiles(
        @Header(
            "Authorization",
        ) token: String,
        @Part images: List<MultipartBody.Part>
    ): UpFileResp;


    @PATCH("loan_profiles/status/{id}")
    @JvmSuppressWildcards
    suspend fun updateLoanStatus(
        @Header(
            "Authorization",
        ) token: String,
        @Body body: Map<String, Any>,
        @Path(value="id",encoded = true) profileId:String,
        )


    @GET("loan_contracts?sortBy=createdAt:desc")
    suspend fun getLoanContracts(
        @Header(
            "Authorization",
        ) token: String,
    ): ArrayList<LoanContractDto>

}