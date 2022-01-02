package com.example.bankmanagement.view_models.create_customer

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.customer.CustomerType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.listener.ValueCallBack
import com.example.bankmanagement.view.create_customer.CreateCustomerUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CreateCustomerViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<CreateCustomerUICallback>() {
    private val TAG: String = "CreateCustomerViewModel";

    val type = MutableLiveData<CustomerType>()
    val dateOfBirth = MutableLiveData<DateTime>()
    val name = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val identityNumber = MutableLiveData<String>()
    val identityCreatedDate = MutableLiveData<DateTime>()
    val address = MutableLiveData<String>()
    val permanentResidence = MutableLiveData<String>()
    val companyRules = MutableLiveData<String>()
    val businessCert = MutableLiveData<Uri>()


    fun onBusinessCertChanged(uri: Uri) {
        businessCert.value = uri
    }

    fun onBusinessCertRemoved() {
        businessCert.value = null
    }

    private fun validateAction(): Boolean = (name.value.isNullOrBlank()
            || phoneNumber.value.isNullOrBlank()
            || (email.value?.isNotBlank() == true && !Utils.isValidEmail(email.value))
            || (type.value == CustomerType.Resident && dateOfBirth.value == null)
            || identityNumber.value.isNullOrBlank()
            || identityCreatedDate.value == null
            || address.value.isNullOrBlank()
            || (type.value == CustomerType.Resident && permanentResidence.value == null)
            || (type.value == CustomerType.Business && companyRules.value.isNullOrBlank())
            || (type.value == CustomerType.Business && businessCert.value == null)
            )


    fun pickDob(v: View) {
        Utils.showDatePicker(v, callback = object : ValueCallBack<DateTime> {
            override fun onValue(value: DateTime) {
                dateOfBirth.postValue(value)
            }
        })
    }

    fun pickIdentityCreatedDate(v: View) {
        Utils.showDatePicker(v, callback = object : ValueCallBack<DateTime> {
            override fun onValue(value: DateTime) {
                identityCreatedDate.postValue(value)
            }
        })
    }

    fun onCustomerAdded(v: View) {

        if (validateAction()) {
            Utils.showNotifyDialog(
                v.context,
                title = "Invalid data",
                mainText = "Please fill all the required information to add customer"
            )
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {

                when (type.value) {
                    CustomerType.Resident -> {
                        mainRepo.addCustomer(
                            customerType = CustomerType.Resident,
                            name = name.value!!,
                            dateOfBirth = dateOfBirth.value!!,
                            address = address.value!!,
                            identityNumber = identityNumber.value!!,
                            identityCardCreatedDate = identityCreatedDate.value!!,
                            phoneNumber = phoneNumber.value!!,
                            permanentResidence = permanentResidence.value!!,
                            email = email.value,
                        )
                    }
                    CustomerType.Business -> {
                        val certImg =
                            Utils.uploadFile(v.context, listOf(businessCert.value!!), mainRepo)
                        if (certImg.isEmpty()) {
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
                        mainRepo.addCustomer(
                            customerType = CustomerType.Business,
                            name = name.value!!,
                            address = address.value!!,
                            identityNumber = identityNumber.value!!,
                            identityCardCreatedDate = identityCreatedDate.value!!,
                            phoneNumber = phoneNumber.value!!,
                            businessRegistrationCertificate = certImg.firstOrNull(),
                            companyRules = companyRules.value!!,
                            email = email.value,

                            )

                    }
                }
                withContext(Dispatchers.Main) {
                    Utils.showCompleteDialog(
                        v.context,
                        mainText = "Customer added successfully",
                        onDismiss = {
                            uiCallback?.dismissDialog(true)
                        })
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Create customer error: ${e.response()?.errorBody()?.string()}")

            }
        }
    }

//    fun signatureImageSelected(uri: Uri) {
//        signatureImg.value = uri
//    }
//
//    fun signatureImageRemoved() {
//        signatureImg.value = null;
//
//    }

}