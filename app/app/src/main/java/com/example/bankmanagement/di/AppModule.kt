package com.example.bankmanagement.di

import androidx.fragment.app.FragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideSomeString():String="This is some String";


}