package com.example.bankmanagement.repo.dtos.sign_in

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("staff") val staff: StaffDto,
    @SerializedName("token") val token: String,
    @SerializedName("clockInOut") val clockInOut: ClockInOutResponse,

)


data class ClockInOutResponse(
    @SerializedName("isClockedIn") var isClockedIn: Boolean=false,
    @SerializedName("isClockedOut") var isClockedOut: Boolean=false,
    @SerializedName("clockedInTime") var clockedInTime: String?=null,
    @SerializedName("clockedOutTime") var clockedOutTime: String?=null,
)