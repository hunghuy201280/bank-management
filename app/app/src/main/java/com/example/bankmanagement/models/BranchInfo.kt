package com.example.bankmanagement.models

import com.google.gson.annotations.SerializedName

data class BranchInfo (
    val id:String,

    val branchAddress:String,

    val branchPhoneNumber:String,

    val branchFax:String,

    val branchCode:String,
    val branchBalance:Double,
    )