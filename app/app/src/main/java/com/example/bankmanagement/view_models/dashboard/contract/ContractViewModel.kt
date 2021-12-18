package com.example.bankmanagement.view_models.dashboard.contract

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.dashboard.profile.ProfileUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class ContractViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewLoanContractArgs val reviewLoanContractArgs: ValueWrapper<LoanContract>,
) : BaseUiViewModel<ProfileUICallback>() {
    private val TAG: String = "ContractViewModel"

    var selectedLoanType = MutableLiveData<LoanType>(LoanType.All)
    var customerName = MutableLiveData<String>()
    var moneyToLoan = MutableLiveData<Double>()
    var loanId = MutableLiveData<String>()
    var dateCreated = MutableLiveData<DateTime>()
    var loanStatus = MutableLiveData<LoanStatus>(LoanStatus.All)
    var loanContracts = MutableLiveData<List<LoanContract>>(listOf())

    init {
        getContract()

    }

     fun getContract() {
        viewModelScope.launch(Dispatchers.IO) {
            _getContracts();
        }
    }


    fun resetFilter(){
        getContract();
        selectedLoanType.value=LoanType.All
        customerName.value=null
        moneyToLoan.value=null
        loanId.value=null
        dateCreated.value=null
        loanStatus.value=LoanStatus.All
        loanContracts.value=null
    }
    private suspend fun _getContracts() {
        val profiles = mainRepo.getContracts();
        loanContracts.postValue(profiles);
    }




//    fun onFindClicked() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _getProfiles();
//            if (selectedLoanType.value == null &&
//                customerName.value.isNullOrBlank() &&
//                moneyToLoan.value == null &&
//                loanId.value.isNullOrBlank() &&
//                dateCreated.value == null &&
//                loanStatus.value == null
//            ) {
//                return@launch
//            }
//            Log.d(
//                TAG, "${selectedLoanType.value}  &&\n" +
//                        "                ${customerName.value}  &&\n" +
//                        "                ${moneyToLoan.value}  &&\n" +
//                        "                ${loanId.value}  &&\n" +
//                        "                ${dateCreated.value}  &&\n" +
//                        "                ${loanStatus.value}"
//            );
//            val result = loanContracts.value?.filter {
//                val _loanType =
//                    if (selectedLoanType.value == null || selectedLoanType.value==LoanType.All) true else selectedLoanType.value == it.loanType
//
//                val _customerName = if (customerName.value.isNullOrBlank()) true
//                else it.customer.name.contains(customerName.value!!)
//
//                val _moneyToLoan =
//                    if (moneyToLoan.value == null) true else moneyToLoan.value == it.moneyToLoan
//
//                val _loanId = if (loanId.value.isNullOrBlank()) true else it.loanApplicationNumber.contains(loanId.value!!)
//                val _dateCreated =
//                    if (dateCreated.value == null) true else dateCreated.value!!.toLocalDate()
//                        .isEqual(it.getDate().toLocalDate())
//                val _loanStatus =
//                    if (loanStatus.value == null ||loanStatus.value== LoanStatus.All) true else loanStatus.value == it.loanStatus
//
//               val predicateRes= _loanType && _customerName && _moneyToLoan && _loanId && _dateCreated && _loanStatus
//                predicateRes
//            }
//
//            loanContracts.postValue(result);
//
//        }
//    }

    fun onCreateClicked() {
        uiCallback?.onCreateClicked()
    }

}