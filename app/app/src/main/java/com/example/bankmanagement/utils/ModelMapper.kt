package com.example.bankmanagement.utils

interface ModelMapper <Dto, Model>{

    fun fromDto(model: Dto): Model

    fun toDto(domainModel: Model): Dto
}