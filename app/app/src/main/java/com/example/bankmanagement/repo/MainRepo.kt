package com.example.bankmanagement.repo

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse


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
