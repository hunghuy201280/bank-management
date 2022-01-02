package com.example.bankmanagement.view_models.dashboard.customer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.constants.*
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.*
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.models.customer.CustomerType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.dashboard.customer.CustomerUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class CustomerViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewCustomerArgs val reviewCustomerArgs: ValueWrapper<Customer>,

    val state: SavedStateHandle,
) : BaseUiViewModel<CustomerUICallback>() {
    private val TAG: String = "CustomerViewModel";

    val customerName = state.getLiveData<String>(STATE_KEY_CUSTOMER_NAME)
    val identityNumber = state.getLiveData<String>(STATE_KEY_CUSTOMER_IDENTITY_NUMBER)
    val customerType = state.getLiveData(STATE_KEY_CUSTOMER_TYPE, CustomerType.All)
    val email = state.getLiveData<String>(STATE_KEY_CUSTOMER_EMAIL)
    val phoneNumber = state.getLiveData<String>(STATE_KEY_CUSTOMER_PHONE)
    val customers = state.getLiveData<ArrayList<Customer>>(STATE_KEY_CUSTOMER_LIST)

    init {
        getCustomers()
    }

    fun getCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            _getCustomers()
        }

    }


    fun resetFilter() {
        customerName.value = null
        identityNumber.value = null
        customerType.value = CustomerType.All
        email.value = null
        phoneNumber.value = null
        customers.value = null
        getCustomers()

    }

    private suspend fun _getCustomers() {
        val result = mainRepo.searchCustomers()
        customers.postValue(result)
        val temp=mainRepo.getCustomerDetail(customerId = result.first().id)

        println(temp)

    }


    fun onFindClicked() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = mainRepo.searchCustomers(
                    name = customerName.value,
                    phoneNumber = phoneNumber.value,
                    customerType = if (customerType.value == CustomerType.All) null else customerType.value,
                    email = email.value,
                    identityNumber = identityNumber.value,
                )
                customers.postValue(result);

            } catch (e: HttpException) {
                customers.postValue(null)
            }
        }
    }

    fun onCreateClicked(type: CustomerType) {
        uiCallback?.onCreateClicked(type)
    }

}