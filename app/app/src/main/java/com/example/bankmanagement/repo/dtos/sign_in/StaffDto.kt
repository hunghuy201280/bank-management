package com.example.bankmanagement.repo.dtos.sign_in

import com.google.gson.annotations.SerializedName

data class StaffDto(
    @SerializedName("_id")
    val id: String="",
    @SerializedName("role")
    val role: Int=1,
    @SerializedName("name")
    val name: String="",
    @SerializedName("email")
    val email: String,
    @SerializedName("branchInfo")
    val branchId: String,
)