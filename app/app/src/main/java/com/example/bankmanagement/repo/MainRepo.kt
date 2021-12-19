package com.example.bankmanagement.repo

import com.example.bankmanagement.models.*
import com.example.bankmanagement.repo.dtos.loan_contract.LoanContractDto
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

    suspend fun clockIn()
    suspend fun clockOut()

    suspend fun getClockInOutTime():ClockInOutResponse

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

    suspend fun updateLoanStatus(
        status: LoanStatus,
        profileId:String,
    )

    suspend fun getContracts(
    ):ArrayList<LoanContract>

    suspend fun hasContract(
        profileId: String
    ):Boolean


    suspend fun createContract(
        profileId: String,
        commitment:String,
        signatureImg:String,
    ):LoanContract



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
