package com.example.bankmanagement.di

import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.create_contract.CreateContractFragmentArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Provides
    @Singleton
    @ClockedInOut
    fun provideClockedOut(): ClockInOutResponse = ClockInOutResponse();


    @Provides
    @Singleton
    @ReviewLoanProfileArgs
    fun provideReviewProfileArgs(): ValueWrapper<LoanProfile?> = ValueWrapper(value = null);

    @Provides
    @Singleton
    @ReviewLoanContractArgs
    fun provideReviewLoanContractArgs(): ValueWrapper<LoanContract?> = ValueWrapper(value = null);

    @Provides
    @Singleton
    @CreateContractArgs
    fun provideCreateContractArgs(): ValueWrapper<CreateContractFragmentArgs?> =
        ValueWrapper(value = null)

    @Provides
    @Singleton
    @ReviewCustomerArgs
    fun provideReviewCustomerArgs(): ValueWrapper<Customer?> =
        ValueWrapper(value = null)




    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ClockedInOut

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ReviewLoanProfileArgs

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ReviewLoanContractArgs

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ReviewCustomerArgs

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class CreateContractArgs



}