package com.example.bankmanagement.di

import com.example.bankmanagement.constants.AppConfigs
import com.example.bankmanagement.repo.ApiService
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.MainRepositoryImpl
import com.example.bankmanagement.repo.dtos.branch_info.BranchInfoDtoMapper
import com.example.bankmanagement.repo.dtos.customer.CustomerDtoMapper
import com.example.bankmanagement.repo.dtos.loan_contract.*
import com.example.bankmanagement.repo.dtos.loan_profiles.LoanProfileDtoMapper
import com.example.bankmanagement.repo.dtos.sign_in.StaffDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseURL

    @Singleton
    @Provides
    fun provideApiService(
        @BaseURL baseUrl: String,
        converterFactory: Converter.Factory,
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePaymentReceiptMapper(): PaymentReceiptDtoMapper {
        return PaymentReceiptDtoMapper()
    }

    @Singleton
    @Provides
    fun provideLiquidationDecisionDtoMapper(
        paymentReceiptDtoMapper: PaymentReceiptDtoMapper
    ): LiquidationDecisionDtoMapper {
        return LiquidationDecisionDtoMapper(paymentReceiptDtoMapper)
    }

    @Singleton
    @Provides
    fun provideLiquidationApplicationDtoMapper(
        decisionDtoMapper: LiquidationDecisionDtoMapper,
    ): LiquidationApplicationDtoMapper {
        return LiquidationApplicationDtoMapper(decisionDtoMapper)
    }

    @Singleton
    @Provides
    fun provideDisburseCertificateDtoMapper(): DisburseCertificateDtoMapper {
        return DisburseCertificateDtoMapper()
    }

    @Singleton
    @Provides
    fun provideLoanContractDtoMapper(
        disburseCertificateDtoMapper: DisburseCertificateDtoMapper,
        liquidationApplicationDtoMapper: LiquidationApplicationDtoMapper
    ): LoanContractDtoMapper {
        return LoanContractDtoMapper(disburseCertificateDtoMapper, liquidationApplicationDtoMapper)
    }

    @Singleton
    @Provides
    fun provideBranchInfoMapper(): BranchInfoDtoMapper {
        return BranchInfoDtoMapper()
    }

    @Singleton
    @Provides
    fun provideCustomerDtoMapper(): CustomerDtoMapper {
        return CustomerDtoMapper()
    }

    @Singleton
    @Provides
    fun provideLoanProfileDtoMapper(
        customerDtoMapper: CustomerDtoMapper,
        staffDtoMapper: StaffDtoMapper
    ): LoanProfileDtoMapper {
        return LoanProfileDtoMapper(customerDtoMapper, staffDtoMapper)
    }

    @Singleton
    @Provides
    fun provideStaffMapper(): StaffDtoMapper {
        return StaffDtoMapper()
    }


    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Singleton
    @Provides
    @BaseURL
    fun provideBaseUrl(): String {
        return AppConfigs.baseUrl;
    }

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        branchInfoMapper: BranchInfoDtoMapper,
        staffDtoMapper: StaffDtoMapper,
        profileDtoMapper: LoanProfileDtoMapper,
        customerDtoMapper: CustomerDtoMapper,
        loanContractDtoMapper: LoanContractDtoMapper,
    ): MainRepository {
        return MainRepositoryImpl(
            apiService = apiService,
            branchInfoMapper = branchInfoMapper,
            staffDtoMapper = staffDtoMapper,
            profileMapper = profileDtoMapper,
            customerDtoMapper = customerDtoMapper,
            loanContractDtoMapper = loanContractDtoMapper
        );
    }
}