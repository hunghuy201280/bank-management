package com.example.bankmanagement.repo

import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDto
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoResponse
import com.example.bankmanagement.repo.dtos.sign_in.LoginData
import com.example.bankmanagement.repo.dtos.sign_in.SignInResponse
import com.example.bankmanagement.repo.dtos.sign_in.StaffDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("branch_info/{branchCode}")
    suspend fun getBranchInfo(
        @Path("branchCode") branchCode:String
    ): BranchInfoResponse

    @POST("staffs/login")
    suspend fun login(
        @Body body: LoginData
    ): SignInResponse
}