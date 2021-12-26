package com.example.bankmanagement.repo

import com.example.bankmanagement.models.*
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDto
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.up_files.UpFileResp
import java.io.File


interface MainRepository {
    suspend fun getBranchInfo(
        branchCode: String
    ): BranchInfo

    suspend fun login(
        email: String,
        password: String,
        branchId: String,
    ): Pair<Staff, ClockInOutResponse>

    suspend fun clockIn()
    suspend fun clockOut()

    suspend fun getClockInOutTime(): ClockInOutResponse

    suspend fun getLoanProfiles(

    ): ArrayList<LoanProfile>

    suspend fun searchCustomers(
        query: Map<String, Any>,

        ): ArrayList<Customer>


    suspend fun createLoanProfile(
        data: CreateLoanProfileData
    ): LoanProfileDto

    suspend fun upFiles(
        files: List<File>
    ): UpFileResp

    suspend fun updateLoanStatus(
        status: LoanStatus,
        profileId: String,
    )

    suspend fun getContracts(
        customerPhone: String? = null,
        contractNumber: String? = null,
        staffName: String? = null,
        approver: String? = null,
        loanType: LoanType? = null,
        createdAt: String? = null,
        profileNumber: String? = null,
        moneyToLoan: Double? = null,
    ): ArrayList<LoanContract>

    suspend fun hasContract(
        profileId: String
    ): Boolean


    suspend fun createContract(
        profileId: String,
        commitment: String,
        signatureImg: String,
    ): LoanContract


    suspend fun getExemptionApplications(
    ): ArrayList<ExemptionApplication>

    suspend fun getContract(
        contractId: String? = null,
        contractNumber: String? = null,
    ): LoanContract


    fun getToken(): String


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
