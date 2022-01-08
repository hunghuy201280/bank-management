package com.example.bankmanagement.view_models.create_contract

import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.utils.helper.LoanContractPDFGenerator
import com.example.bankmanagement.view.create_contract.CreateContractFragmentArgs
import com.example.bankmanagement.view.create_contract.CreateContractUICallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CreateContractViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.CreateContractArgs private val createContractFragmentArgs: ValueWrapper<CreateContractFragmentArgs>,
    private val loanContractPDFGenerator: LoanContractPDFGenerator,
) : BaseUiViewModel<CreateContractUICallBack>() {
    private val TAG: String = "CreateContractViewModel";
    val commitment = MutableLiveData<String>()
    val signatureImg = MutableLiveData<Uri>()
    val loanProfile = createContractFragmentArgs.value.loanProfile

    @RequiresApi(Build.VERSION_CODES.O)
    fun onContractCreated(v: View) {
        if (loanProfile.id.isBlank()
            || commitment.value.isNullOrBlank()
            || signatureImg.value == null
        ) {
            Utils.showNotifyDialog(
                v.context,
                title = "Invalid data",
                mainText = "Please fill all the required information to create contract"
            )
            return
        }
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val signatureUrl =
                    Utils.uploadFile(v.context, listOf(signatureImg.value!!), mainRepo)
                val newContract = mainRepo.createContract(
                    profileId = loanProfile.id,
                    commitment = commitment.value!!,
                    signatureImg = signatureUrl.first()
                )
                val pdfPath =
                    loanContractPDFGenerator.generatePDF(
                        newContract,
                        v.context
                    )
                try {
                    mainRepo.sendMail(
                        file = pdfPath,
                        contractId = newContract.id,
                    )
                } catch (e: HttpException) {
                    println(e.response()?.errorBody()?.string())
                }
                Log.d(TAG, "Create contract result: $newContract")
                withContext(Dispatchers.Main) {
                    showLoading(false)

                    Utils.showCompleteDialog(
                        v.context,
                        mainText = "Contract created successfully",
                        onDismiss = {
                            uiCallback?.dismissDialog()
                        })
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Create contract error: ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    showLoading(false)

                    Utils.showCompleteDialog(
                        v.context,
                        mainText = "Error: An error has occurred, please try again",
                        onDismiss = {
                        },
                        isError = true
                    )
                }
            }
        }
    }

    fun signatureImageSelected(uri: Uri) {
        signatureImg.value = uri
    }

    fun signatureImageRemoved() {
        signatureImg.value = null;

    }

}