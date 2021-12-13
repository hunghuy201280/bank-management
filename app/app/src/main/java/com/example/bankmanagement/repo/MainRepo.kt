package com.example.bankmanagement.repo

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.Customer
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.up_files.UpFileResp
import retrofit2.http.QueryMap
import java.io.File


interface MainRepository {
    suspend fun getBranchInfo(
        branchCode: String
    ): BranchInfo
    suspend fun login(
        email: String,
        password: String,
        branchId:String,
    ): Pair<Staff,ClockInOutResponse>

    suspend fun clockIn(

    )
    suspend fun clockOut(

    )

    suspend fun getClockInOutTime(

    ):ClockInOutResponse

    suspend fun getLoanProfiles(

    ):ArrayList<LoanProfile>

    suspend fun searchCustomers(
        query: Map<String, Any>,

        ):ArrayList<Customer>


    suspend fun createLoanProfile(
        data: CreateLoanProfileData
        ):LoanProfileDto

    suspend fun upFiles(
        files: List<File>
        ):UpFileResp



    fun getToken():String;



}


//abstract class TestAb
//constructor(
//    protected open val test2:String
//
//)
//{
//}
//
//
//
//data class Testaaa(
//    val test1:String, override val test2: String,
//): TestAb(test2) {
//
//}
