package com.example.bankmanagement.view_models.review_application

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.models.application.extension.ExtensionApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.view.dashboard.application.review_application.ReviewApplicationUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class ReviewApplicationViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    val state: SavedStateHandle,
) : BaseUiViewModel<ReviewApplicationUICallback>() {
    private val TAG: String = "ReviewApplicationViewModel"
    val application= MutableLiveData<BaseApplication>()
    fun onBack() {
        uiCallback?.onBack()
    }
    val temp=MutableLiveData<String>()

    fun rejectApplication(v: View) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                when (application.value) {
                    is LiquidationApplication -> {
                        mainRepo.rejectLiquidation(applicationId = application.value!!.id)
                    }
                    is ExemptionApplication -> {
                        mainRepo.rejectExemption(applicationId = application.value!!.id)
                    }
                    is ExtensionApplication -> {
                        mainRepo.rejectExtension(applicationId = application.value!!.id)
                    }
                }

                withContext(Dispatchers.Main){
                    Utils.showCompleteDialog(v.context,"Reject successfully", onDismiss = {
                        uiCallback?.onBack(true)
                    })
                }
            }
            catch(e: HttpException){
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
                withContext(Dispatchers.Main){
                    Utils.showCompleteDialog(v.context,"Error")
                }
            }
        }
    }

    fun approveApplication(v:View) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                when (application.value) {
                    is LiquidationApplication -> {
                        mainRepo.approveLiquidation(applicationId = application.value!!.id,"61cd80510176836fb1df9f00_trongtruonghop.png")
                    }
                    is ExemptionApplication -> {
                        mainRepo.approveExemption(applicationId = application.value!!.id,"61cd80510176836fb1df9f00_trongtruonghop.png")
                    }
                    is ExtensionApplication -> {
                        mainRepo.approveExtension(applicationId = application.value!!.id,"61cd80510176836fb1df9f00_trongtruonghop.png")
                    }
                }

                withContext(Dispatchers.Main){
                    Utils.showCompleteDialog(v.context,"Approved", onDismiss = {
                        uiCallback?.onBack(true)
                    })
                }
            }
            catch(e: HttpException){
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ")
                withContext(Dispatchers.Main){
                    Utils.showCompleteDialog(v.context,"Error")
                }
            }
        }
    }

}