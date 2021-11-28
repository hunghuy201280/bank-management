package com.example.bankmanagement.view_models.sign_in

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.view.sign_in.SignInUiCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    ) : BaseUiViewModel<SignInUiCallback>() {
    private val TAG: String = "DeviceCodeViewModel";


    val email:MutableLiveData<String> =MutableLiveData();
    val password:MutableLiveData<String> = MutableLiveData();


    fun signIn(branchId:String){
        showLoading(true);
        viewModelScope.launch(Dispatchers.IO) {
            if(email.value.isNullOrEmpty() || password.value.isNullOrEmpty())
                return@launch;

            try{
                val staff =mainRepo.login(email=email.value!!, password = password.value!!, branchId = branchId);
                Log.d(TAG, "Login successfully: $staff \n Token ${mainRepo.getToken()}");
                uiCallback?.onLoggedIn(staff);
            }
            catch(e:HttpException){
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
            }
            withContext(Dispatchers.Main){
                showLoading(false);
            }
        }

    }
}