package com.example.bankmanagement.repo.dtos.branch_info

import com.google.gson.annotations.SerializedName

data class BranchInfoResponse(
    @SerializedName("data")
    val data:BranchInfoDto
)

data class BranchInfoDto (
    @SerializedName("_id")
    val id:String,

    @SerializedName("branchAddress")
    val branchAddress:String,

    @SerializedName("branchPhoneNumber")
    val branchPhoneNumber:String,

    @SerializedName("branchFax")
    val branchFax:String,

    @SerializedName("branchCode")
    val branchCode:String,

    @SerializedName("branchBalance")
    val branchBalance:Double,
    )