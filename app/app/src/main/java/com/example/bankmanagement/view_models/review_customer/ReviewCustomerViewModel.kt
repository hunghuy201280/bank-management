package com.example.bankmanagement.view_models.review_customer

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.*
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.models.customer.CustomerDetail
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.create_contract.CreateContractFragmentArgs
import com.example.bankmanagement.view.review_profile.ReviewContractUICallback
import com.example.bankmanagement.view.review_profile.ReviewCustomerUICallback
import com.example.bankmanagement.view.review_profile.ReviewProfileUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReviewCustomerViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewCustomerArgs val reviewCustomerArgs: ValueWrapper<Customer>,
    @AppModule.ReviewLoanContractArgs val reviewLoanContractArgs: ValueWrapper<LoanContract>,

    ) : BaseUiViewModel<ReviewCustomerUICallback>() {
    private val TAG: String = "ReviewContractViewModel";

    val customer: MutableLiveData<Customer> = MutableLiveData(reviewCustomerArgs.value)

    val customerDetail = MutableLiveData<CustomerDetail>()

    //region transform
    val recentServiceList: LiveData<List<LoanType>> = Transformations.map(customerDetail) {
        return@map it.recentContracts.map { contract -> contract.loanProfile.loanType }
    }
    //endregion

    init {
        loadData()
    }

     fun loadData() {
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = mainRepo.getCustomerDetail(customerId = customer.value!!.id)
                customerDetail.postValue(result)

            } catch (e: HttpException) {
                Utils.logError(TAG, e)
            }
            finally {
                withContext(Dispatchers.Main){
                    showLoading(false)
                }
            }
        }
    }

    //region transform live data
    val totalLoan = Transformations.map(customerDetail) {
        return@map it.statistics.total
    }
    val paid = Transformations.map(customerDetail) {
        return@map it.statistics.paid
    }
    val unPaid = Transformations.map(customerDetail) {
        return@map it.statistics.unPaid
    }

    val lastYearLoan = Transformations.map(customerDetail) {
        return@map it.statistics.lastYear
    }
    val thisYearLoan = Transformations.map(customerDetail) {
        return@map it.statistics.thisYear
    }


    //endregion

    fun onDisburseAdded(v: View) {

    }

    fun onPaymentReceiptAdded(v: View) {

    }

}
