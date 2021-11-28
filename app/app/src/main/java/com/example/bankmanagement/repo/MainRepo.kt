package com.example.bankmanagement.repo

import com.example.bankmanagement.models.BranchInfo


interface MainRepository {
    suspend fun getBranchInfo(
        branchCode: String
    ): BranchInfo
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
