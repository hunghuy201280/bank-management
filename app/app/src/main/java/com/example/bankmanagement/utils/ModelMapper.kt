package com.example.bankmanagement.utils

interface ModelMapper <Dto, Model>{

    fun fromDto(dto: Dto): Model

    fun toDto(model: Model): Dto
}