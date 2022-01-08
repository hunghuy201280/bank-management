package com.example.bankmanagement.view_models.dashboard.admin

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.DisburseCertificate
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.dashboard.admin.add_balance.CreateDepositUICallback
import com.example.bankmanagement.view.review_contract.disburse.CreateDisburseUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CreateDepositViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<CreateDepositUICallback>() {

    private val TAG: String = "CreateDepositViewModel"

    var branchCode: String? = null

    val amountString = MutableLiveData<String>()

    fun onCancel() {
        uiCallback?.dismissDialog()
    }

    fun onAdd(v: View) {
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mainRepo.createDeposit(
                    branchCode = branchCode!!,
                    amount = amountString.value!!.toDouble()
                )
                withContext(Dispatchers.Main) {
                    showLoading(false)

                    Utils.showCompleteDialog(v.context, "Add balance successfully!", onDismiss = {
                        uiCallback?.dismissDialog()
                    })
                }
            } catch (e: HttpException) {
                Log.e(
                    TAG,
                    "Add balance error: ${e.response()?.errorBody()?.string()}"
                )
            } finally {
                withContext(Dispatchers.Main){
                    showLoading(false)
                }
            }
        }
    }
}