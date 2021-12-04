package com.example.bankmanagement.view_models.clockin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.view.clockin.ClockInOutUICallback
import com.example.bankmanagement.view.sign_in.SignInUiCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ClockInOutViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ClockedInOut
    private val clockInOut: ClockInOutResponse,
) : BaseUiViewModel<ClockInOutUICallback>() {
    private val TAG: String = "DeviceCodeViewModel";


    val isClockedIn: MutableLiveData<Boolean> = MutableLiveData(clockInOut.isClockedIn);
    val isClockedOut: MutableLiveData<Boolean> = MutableLiveData(clockInOut.isClockedOut);
    val clockedInTime: MutableLiveData<String?> = MutableLiveData();
    val clockedOutTime: MutableLiveData<String?> = MutableLiveData();

    fun onButtonClicked() {
        if (!isClockedIn.value!!) {
            onClockedInClicked();
        } else if (!isClockedOut.value!!) {
            onClockedOutClicked();
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
        isClockedIn.value = clockInOutTime.isClockedIn;
        isClockedOut.value = clockInOutTime.isClockedOut
        clockedInTime.value = clockInOutTime.clockedInTime
        clockedOutTime.value = clockInOutTime.clockedOutTime
        clockInOut.isClockedIn = clockInOutTime.isClockedIn;
        clockInOut.isClockedOut = clockInOutTime.isClockedOut;
        clockInOut.clockedOutTime = clockInOutTime.clockedOutTime;
        clockInOut.clockedInTime = clockInOutTime.clockedInTime;
        Log.d(TAG, "Clockin successfully $clockInOutTime");

    }

}