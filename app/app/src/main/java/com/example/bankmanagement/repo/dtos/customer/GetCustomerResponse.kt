package com.example.bankmanagement.repo.dtos.customer

import com.google.gson.annotations.SerializedName

data class GetCustomerResponse (
    @SerializedName("data")
    val data:ArrayList<CustomerDto>
        )