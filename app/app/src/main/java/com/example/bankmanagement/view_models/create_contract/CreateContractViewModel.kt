package com.example.bankmanagement.view_models.create_contract

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.*
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.Utils.Companion.getFileName
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.create_contract.CreateContractFragmentArgs
import com.example.bankmanagement.view.create_contract.CreateContractUICallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.sql.Array
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@HiltViewModel
class CreateContractViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.CreateContractArgs private val createContractFragmentArgs: ValueWrapper<CreateContractFragmentArgs>
) : BaseUiViewModel<CreateContractUICallBack>() {
    private val TAG: String = "CreateContractViewModel";
    val commitment = MutableLiveData<String>()
    val signatureImg = MutableLiveData<Uri>()
    val loanProfile = createContractFragmentArgs.value.loanProfile
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val signatureUrl =
                    Utils.uploadFile(v.context, listOf(signatureImg.value!!), mainRepo)
                val newContract = mainRepo.createContract(
                    profileId = loanProfile.id,
                    commitment = commitment.value!!,
                    signatureImg = signatureUrl.first()
                )
                Log.d(TAG, "Create contract result: $newContract")
                withContext(Dispatchers.Main) {
                    Utils.showCompleteDialog(
                        v.context,
                        mainText = "Contract created successfully",
                        onDismiss = {
                            uiCallback?.dismissDialog()
                        })
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Create contract error: ${e.response()?.errorBody()?.string()}")

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