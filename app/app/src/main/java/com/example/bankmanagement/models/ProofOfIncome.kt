package com.example.bankmanagement.models


import com.google.gson.annotations.SerializedName

data class ProofOfIncome (
    @SerializedName("imageId")
    val imageID: String,

    val imageType: Int,

    @SerializedName("_id")
    val id: String
)