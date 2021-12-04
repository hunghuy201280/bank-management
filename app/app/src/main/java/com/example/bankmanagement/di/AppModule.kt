package com.example.bankmanagement.di

import androidx.fragment.app.FragmentFactory
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.repo.dtos.sign_in.SignInResponse
import com.example.bankmanagement.utils.ValueWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {





    @Provides
    @Singleton
    @ClockedInOut
    fun provideClockedOut():ClockInOutResponse = ClockInOutResponse();






    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ClockedInOut




}