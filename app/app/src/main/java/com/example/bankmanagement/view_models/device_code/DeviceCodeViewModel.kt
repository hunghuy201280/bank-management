package com.example.bankmanagement.view_models.device_code


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DeviceCodeViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<BaseUserView>() {

    private val TAG: String = "DeviceCodeViewModel";
    val pinCode: MutableLiveData<String> = MutableLiveData("");
    val branch: MutableLiveData<BranchInfo> = MutableLiveData();

    fun getBranchInfo() {
        showLoading(true);

        viewModelScope.launch(Dispatchers.IO) {

            // simulate a delay to show loading
            delay(1000)
            try {
                val info: BranchInfo = mainRepo.getBranchInfo(pinCode.value!!)

                Log.d(TAG, info.branchAddress);
                withContext(Dispatchers.Main){
                    showLoading(false);
                }
                branch.postValue(info);

            } catch (e: HttpException) {
                Log.d(TAG, "Error happened: ${e.response()} ");
                withContext(Dispatchers.Main){
                    showLoading(false);
                }
            }


        }

    }

}