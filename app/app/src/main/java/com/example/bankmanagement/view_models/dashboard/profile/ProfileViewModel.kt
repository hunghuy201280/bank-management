package com.example.bankmanagement.view_models.dashboard.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanType
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
class ProfileViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<ClockInOutUICallback>() {
    private val TAG: String = "ProfileViewModel";

    public val loanTypes= LoanType.values().map { it.name }
    var selectedTypePosition=MutableLiveData<Int?>()
    var loanProfiles=MutableLiveData<List<LoanProfile>>(listOf())
    init {
     getProfiles()
    }

    fun getProfiles(){
        viewModelScope.launch(Dispatchers.IO) {
            val profiles=mainRepo.getLoanProfiles();
            loanProfiles.postValue(profiles);
            println("$TAG: ${profiles.first()}")
        }
    }
    private fun onClockedInClicked() {
        showLoading(true);
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginResult = mainRepo.clockIn();
                val clockInOutTime = mainRepo.getClockInOutTime();
                Log.d(TAG, "Clockin successfully $clockInOutTime");
                withContext(Dispatchers.Main) {
                    updateClockInOut(clockInOutTime);
                    showLoading(false);
                    uiCallback?.onClockedIn();
                }
            } catch (e: HttpException) {
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
                withContext(Dispatchers.Main) {
                    showLoading(false);
                }
            }

        }
    }

    private fun onClockedOutClicked() {
        showLoading(true);
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginResult = mainRepo.clockOut();
                val clockInOutTime = mainRepo.getClockInOutTime();

                Log.d(TAG, "Clockout successfully");
                withContext(Dispatchers.Main) {
                    updateClockInOut(clockInOutTime);
                    showLoading(false);
                    uiCallback?.onClockedOut();
                }
            } catch (e: HttpException) {
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
                withContext(Dispatchers.Main) {
                    showLoading(false);
                }
            }

        }
    }

    private fun updateClockInOut(clockInOutTime: ClockInOutResponse) {

    }

}