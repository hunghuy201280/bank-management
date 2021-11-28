package com.example.bankmanagement.repo.dtos.sign_in

import com.google.gson.annotations.SerializedName

data class SignInResponse (
    @SerializedName("staff" ) val staff : StaffDto,
    @SerializedName("token" ) val token : String
)