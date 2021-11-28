package com.example.bankmanagement.repo

import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.Staff


interface MainRepository {
    suspend fun getBranchInfo(
        branchCode: String
    ): BranchInfo
    suspend fun login(
        email: String,
        password: String,
        branchId:String,
    ): Staff

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
