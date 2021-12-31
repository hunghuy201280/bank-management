package com.example.bankmanagement.view_models.create_application

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.application.ApplicationType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.application.exemption.ExemptionApplicationDto
import com.example.bankmanagement.repo.dtos.application.extension.ExtensionApplicationDto
import com.example.bankmanagement.repo.dtos.application.liquidation.LiquidationApplicationDto
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.view.create_application.CreateApplicationUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CreateApplicationViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<CreateApplicationUICallback>() {
    private val TAG: String = "CreateContractViewModel";
    val reason = MutableLiveData<String>()
    val signatureImg = MutableLiveData<Uri>()
    val amount = MutableLiveData<Double>()
    val loanContract = MutableLiveData<LoanContract>()
    val duration = MutableLiveData<Long>()
    val contractSuggestions = MutableLiveData<ArrayList<LoanContract>>()
    lateinit var applicationType: ApplicationType

    private fun validateAction(): Boolean = (loanContract.value == null
            || reason.value.isNullOrBlank()
            || signatureImg.value == null
            || amount.value == null
            || (applicationType == ApplicationType.Extension && duration.value == null));

    fun onContractCreated(v: View) {

        if (validateAction()) {
            Utils.showNotifyDialog(
                v.context,
                title = "Invalid data",
                mainText = "Please fill all the required information to create contract"
            )
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val signatureUrl =
                    Utils.uploadFile(v.context, listOf(signatureImg.value!!), mainRepo)
                if (signatureUrl.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Utils.showCompleteDialog(
                            v.context,
                            mainText = "Error: signatureUrl is empty",
                            onDismiss = {
                                uiCallback?.dismissDialog()
                            })
                    }
                    return@launch
                }
                val newContract = when (applicationType) {
                    ApplicationType.Liquidation -> mainRepo.createLiquidation(
                        LiquidationApplicationDto(
                            amount = amount.value,
                            reason = reason.value,
                            signatureImg = signatureUrl.first(),
                            loanContract = loanContract.value!!.id,
                        )
                    )
                    ApplicationType.Extension -> mainRepo.createExtension(
                        ExtensionApplicationDto(
                            amount = amount.value,
                            reason = reason.value,
                            signatureImg = signatureUrl.first(),
                            loanContract = loanContract.value!!.id,
                            duration = duration.value,
                        )
                    )
                    ApplicationType.Exemption -> mainRepo.createExemption(
                        ExemptionApplicationDto(
                            amount = amount.value,
                            reason = reason.value,
                            signatureImg = signatureUrl.first(),
                            loanContract = loanContract.value!!.id,
                        )
                    )
                    else -> {}
                }
                Log.d(TAG, "Create application result: $newContract")
                withContext(Dispatchers.Main) {
                    Utils.showCompleteDialog(
                        v.context,
                        mainText = "application created successfully",
                        onDismiss = {
                            uiCallback?.dismissDialog(true)
                        })
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Create application error: ${e.response()?.errorBody()?.string()}")

            }
        }
    }

    fun signatureImageSelected(uri: Uri) {
        signatureImg.value = uri
    }

    fun signatureImageRemoved() {
        signatureImg.value = null;

    }

    fun onSearch(query: String) {

        if (query.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    mainRepo.getContracts(contractNumber = query);
                contractSuggestions.postValue(result);


            } catch (e: HttpException) {
                contractSuggestions.postValue(arrayListOf())
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");

            } catch (e: Exception) {
                contractSuggestions.postValue(arrayListOf())
                Log.d(TAG, "Error happened: $e ");
            }
        }

    }
}