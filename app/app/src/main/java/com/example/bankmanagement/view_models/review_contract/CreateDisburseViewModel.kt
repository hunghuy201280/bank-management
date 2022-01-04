package com.example.bankmanagement.view_models.review_contract

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.DisburseCertificate
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.review_contract.disburse.CreateDisburseUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CreateDisburseViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewLoanContractArgs val reviewLoanContractArgs: ValueWrapper<LoanContract>
) : BaseUiViewModel<CreateDisburseUICallback>() {

    private val TAG: String = "CreateDisburseViewModel"

    var contractId: String? = null
    var remainingDisburseAmount: Double? = null

    val amountString = MutableLiveData<String>()

    fun onCancel() {
        uiCallback?.dismissDialog()
    }

    fun onAdd() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mainRepo.createDisburseCertificate(
                    contractId = contractId!!,
                    remainingDisburseAmount = remainingDisburseAmount!!,
                    amount = amountString.value!!.toDouble()
                )
            } catch (e: HttpException) {
                Log.e(TAG, "Create disburse certificate error: ${e.response()?.errorBody()?.string()}")
            }
        }
    }
}