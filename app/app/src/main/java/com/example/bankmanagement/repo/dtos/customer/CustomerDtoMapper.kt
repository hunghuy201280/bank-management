package com.example.bankmanagement.repo.dtos.customer

import com.example.bankmanagement.models.*
import com.example.bankmanagement.utils.ModelMapper

class CustomerDtoMapper
    :ModelMapper<CustomerDto, Customer> {
    override fun fromDto(dto: CustomerDto): Customer {
        return when(dto.customerType){
            CustomerType.Business->
                BusinessCustomer(
                    businessRegistrationCertificate=dto.businessRegistrationCertificate!!,
                    companyRules=dto.companyRules!!,
                    id=dto.id,
                    name=dto.name,
                    address=dto.address,
                    identityNumber=dto.identityNumber,
                    identityCardCreatedDate=dto.identityCardCreatedDate,
                    phoneNumber=dto.phoneNumber,
                )

            CustomerType.Resident->ResidentCustomer(
                dateOfBirth=dto.dateOfBirth!!,
                permanentResidence =dto.permanentResidence!!,
                id=dto.id,
                name=dto.name,
                address=dto.address,
                identityNumber=dto.identityNumber,
                identityCardCreatedDate=dto.identityCardCreatedDate,
                phoneNumber=dto.phoneNumber,
            )
            else->{throw Exception("Invalid Customer type ${dto.customerType}")}
        }
    }

    override fun toDto(model: Customer): CustomerDto {
        return CustomerDto(
            dateOfBirth=if(model is ResidentCustomer)(model as ResidentCustomer).dateOfBirth else null,
            permanentResidence =if(model is ResidentCustomer)(model as ResidentCustomer).permanentResidence else null,
            businessRegistrationCertificate=if(model is BusinessCustomer)(model as BusinessCustomer).businessRegistrationCertificate else null,
            companyRules=if(model is BusinessCustomer)(model as BusinessCustomer).companyRules else null,
            id=model.id,
            name=model.name,
            address=model.address,
            identityNumber=model.identityNumber,
            identityCardCreatedDate=model.identityCardCreatedDate,
            phoneNumber=model.phoneNumber,
            customerType = model.getType()
        )
    }

}