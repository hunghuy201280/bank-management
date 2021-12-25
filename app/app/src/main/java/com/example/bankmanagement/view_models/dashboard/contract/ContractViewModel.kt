package com.example.bankmanagement.view_models.dashboard.contract

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.constants.STATE_KEY_CONTRACT_LOAN_TYPE
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.utils.listener.ValueCallBack
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
    val state: SavedStateHandle,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "ApplicationViewModel"

    //region search
    val loanType = state.getLiveData(STATE_KEY_CONTRACT_LOAN_TYPE,LoanType.All)
    val profileNumber = MutableLiveData<String>()
    val contractNumber = MutableLiveData<String>()
    val staffInCharge = MutableLiveData<String>()
    val BODInCharge = MutableLiveData<String>()
    val moneyToLoan = MutableLiveData<Double>()
    val dateCreated = MutableLiveData<DateTime>()
    val phoneNumber = MutableLiveData<String>()

    //endregion
    val loanContracts = MutableLiveData<List<LoanContract>>(listOf())




    private fun restoreState(){
        if(loanType.value!=LoanType.All){
            onFindClicked()
        }
    }

    init {
        getContract()
    }

    fun getContract() {
        viewModelScope.launch(Dispatchers.IO) {
            _getContracts()
            restoreState()

        }
    }


    fun resetFilter() {
        getContract();
        loanType.value = LoanType.All
        profileNumber.value = null
        staffInCharge.value = null
        BODInCharge.value = null
        moneyToLoan.value = null
        dateCreated.value = null
        phoneNumber.value = null
        contractNumber.value=null
    }

    private suspend fun _getContracts() {
        val profiles = mainRepo.getContracts();
        loanContracts.postValue(profiles);
    }

    fun showDatePicker(v: View) {
        Utils.showDatePicker(v, callback = object : ValueCallBack<DateTime> {
            override fun onValue(value: DateTime) {
                dateCreated.postValue(value)
            }
        })
    }

    fun onFindClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _getContracts();
            if (loanType.value == LoanType.All &&
                profileNumber.value.isNullOrBlank() &&
                staffInCharge.value.isNullOrBlank() &&
                BODInCharge.value.isNullOrBlank() &&
                moneyToLoan.value == null &&
                dateCreated.value == null &&
                phoneNumber.value.isNullOrBlank()&&
                contractNumber.value.isNullOrBlank()
            ) {
                return@launch
            }

            val result = loanContracts.value?.filter {
                val _loanType =
                    if (loanType.value == null || loanType.value == LoanType.All) true else loanType.value == it.loanProfile.loanType

                val _profileNumber = if (profileNumber.value.isNullOrBlank()) true
                else it.loanProfile.loanApplicationNumber.contains(profileNumber.value!!)

                val _staffInCharge = if (staffInCharge.value.isNullOrBlank()) true
                else it.loanProfile.staff.name.contains(staffInCharge.value!!)

                val _BODInCharge = if (BODInCharge.value.isNullOrBlank()) true
                else it.loanProfile.approver?.name?.contains(BODInCharge.value!!) ?: false

                val _moneyToLoan =
                    if (moneyToLoan.value == null) true else moneyToLoan.value == it.loanProfile.moneyToLoan

                val _dateCreated =
                    if (dateCreated.value == null) true else dateCreated.value!!.toLocalDate()
                        .isEqual(it.getDate().toLocalDate())

                val _phoneNumber = if (phoneNumber.value.isNullOrBlank()) true
                else it.loanProfile.customer.phoneNumber.contains(phoneNumber.value!!)

                val _contractNumber = if (contractNumber.value.isNullOrBlank()) true
                else it.contractNumber.contains(contractNumber.value!!)

                val predicateRes =
                    _loanType &&
                            _profileNumber &&
                            _staffInCharge &&
                            _BODInCharge &&
                            _moneyToLoan &&
                            _dateCreated &&
                            _contractNumber
                predicateRes
            }

            loanContracts.postValue(result);

        }
    }

//    fun onCreateClicked() {
//        uiCallback?.onCreateClicked()
//    }

}