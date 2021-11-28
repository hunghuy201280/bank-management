package com.example.bankmanagement.repo.dtos.sign_in

import com.google.gson.annotations.SerializedName

class LoginData (
    @SerializedName("email")
    val email:String,

    @SerializedName("password")
    val password:String,

    @SerializedName("branchId")
    val branchId:String,
)