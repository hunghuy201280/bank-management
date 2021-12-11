package com.example.bankmanagement.view_models.create_profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.view.clockin.ClockInOutUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "CreateProfileViewModel";



}