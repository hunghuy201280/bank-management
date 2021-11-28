package com.example.bankmanagement.repo

import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDto
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("branch_info/{branchCode}")
    suspend fun getBranchInfo(
        @Path("branchCode") branchCode:String
    ): BranchInfoResponse
}