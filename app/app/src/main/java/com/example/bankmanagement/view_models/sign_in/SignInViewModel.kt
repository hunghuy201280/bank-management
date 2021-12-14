package com.example.bankmanagement.view_models.sign_in

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.sign_in.SignInUiCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ClockedInOut private val clockInOut:ClockInOutResponse,

    ) : BaseUiViewModel<SignInUiCallback>() {
    private val TAG: String = "DeviceCodeViewModel";


    val email:MutableLiveData<String> =MutableLiveData();
    val password:MutableLiveData<String> = MutableLiveData();
    val isClockInEnabled:MutableLiveData<Boolean> = MutableLiveData(false);

    fun clockInToggled(){
        isClockInEnabled.value=!isClockInEnabled.value!!;
    }

    fun signIn(branchId:String){
        showLoading(true);
        viewModelScope.launch(Dispatchers.IO) {


            try{
                if(email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
                    throw Exception("Please enter email and password")
                }
                val loginResult =mainRepo.login(email=email.value!!, password = password.value!!, branchId = branchId);
                val staff=loginResult.first;
                clockInOut.clockedInTime=loginResult.second.clockedInTime;
                clockInOut.isClockedIn=loginResult.second.isClockedIn;
                clockInOut.clockedOutTime=loginResult.second.clockedOutTime;
                clockInOut.isClockedOut=loginResult.second.isClockedOut;

                Log.d(TAG, "Login successfully: $staff \n Token ${mainRepo.getToken()}");
                withContext(Dispatchers.Main){
                    showLoading(false);

                    uiCallback?.onLoggedIn(staff);

                }
            }
            catch(e:HttpException){
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
                withContext(Dispatchers.Main){
                    showLoading(false);
                }
            }
            catch (e:Exception){
                withContext(Dispatchers.Main){
                    showLoading(false);
                }
            }



        }

    }
}