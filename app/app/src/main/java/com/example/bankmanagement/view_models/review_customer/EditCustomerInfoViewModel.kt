package com.example.bankmanagement.view_models.review_customer

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.customer.BusinessCustomer
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.models.customer.CustomerType
import com.example.bankmanagement.models.customer.ResidentCustomer
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.listener.ValueCallBack
import com.example.bankmanagement.utils.toLocalDateTime
import com.example.bankmanagement.utils.toUtcISO
import com.example.bankmanagement.view.review_profile.ReviewCustomerUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EditCustomerInfoViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,

    ) : BaseUiViewModel<ReviewCustomerUICallback>() {
    private val TAG: String = "CustomerInfoViewModel";

    val customer = MutableLiveData<Customer>()


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

    fun setInitialData(customer: Customer) {
        this.customer.value = customer
        type.value = customer.getType()
        if (customer is ResidentCustomer) {
            dateOfBirth.value = customer.dateOfBirth.toLocalDateTime()
            permanentResidence.value = customer.permanentResidence

        } else if (customer is BusinessCustomer) {

            companyRules.value = customer.companyRules
            businessCert.value = Uri.parse(customer.getCert())
        }
        name.value = customer.name
        phoneNumber.value = customer.phoneNumber
        email.value = customer.email
        identityNumber.value = customer.identityNumber
        identityCreatedDate.value =customer.identityCardCreatedDate.toLocalDateTime()
        address.value = customer.address
    }

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

    fun onSave(v: View) {
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val businessCertImg=businessCert.value?.let {
                    Utils.uploadFile(v.context, listOf(it),mainRepo)
                }

                mainRepo.updateCustomer(
                    id = customer.value!!.id,
                    customerType = type.value!!,
                    name=name.value,
                address=address.value,
                identityNumber=identityNumber.value,
                identityCardCreatedDate=identityCreatedDate.value?.toUtcISO(),
                phoneNumber=phoneNumber.value,
                email=email.value,
                permanentResidence=permanentResidence.value,
                dateOfBirth=dateOfBirth.value?.toUtcISO(),
                businessRegistrationCertificate= businessCertImg?.firstOrNull(),
                companyRules=companyRules.value,
                )
                withContext(Dispatchers.Main) {
                    showLoading(false)

                    Utils.showCompleteDialog(
                        v.context,
                        mainText = "Customer updated",
                        onDismiss = {
                            uiCallback?.refreshData()
                            uiCallback?.onViewCustomerInfo()
                        })
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Edit customer error: ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    showLoading(false)

                }
            }

        }
    }

}
