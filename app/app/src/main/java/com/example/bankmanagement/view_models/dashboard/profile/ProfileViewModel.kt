package com.example.bankmanagement.view_models.dashboard.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.view.dashboard.profile.ProfileUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<ProfileUICallback>() {
    private val TAG: String = "ProfileViewModel";

    var selectedLoanType = MutableLiveData<LoanType>()
    var customerName = MutableLiveData<String>()
    var moneyToLoan = MutableLiveData<Double>()
    var loanId = MutableLiveData<String>()
    var dateCreated = MutableLiveData<DateTime>()
    var loanStatus = MutableLiveData<LoanStatus>()
    var loanProfiles = MutableLiveData<List<LoanProfile>>(listOf())

    init {
        getProfiles()
    }

    private fun getProfiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _getProfiles();
        }
    }

    private suspend fun _getProfiles() {
        val profiles = mainRepo.getLoanProfiles();
        loanProfiles.postValue(profiles);
    }

    fun onFindClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            _getProfiles();
            if (selectedLoanType.value == null &&
                customerName.value == null &&
                moneyToLoan.value == null &&
                loanId.value == null &&
                dateCreated.value == null &&
                loanStatus.value == null
            ) {
                return@launch
            }
            val result = loanProfiles.value?.filter {
                val _loanType =
                    if (selectedLoanType.value == null) true else selectedLoanType.value == it.loanType

                val _customerName = if (customerName.value == null) true
                else it.customer.name.contains(customerName.value!!)

                val _moneyToLoan =
                    if (moneyToLoan.value == null) true else moneyToLoan.value == it.moneyToLoan

                val _loanId = if (loanId.value == null) true else loanId.value == it.id
                val _dateCreated =
                    if (dateCreated.value == null) true else dateCreated.value!!.toLocalDate()
                        .isEqual(it.getDate().toLocalDate())
                val _loanStatus =
                    if (loanStatus.value == null) true else loanStatus.value == it.loanStatus

                _loanType && _customerName && _moneyToLoan && _loanId && _dateCreated && _loanStatus
            }

            loanProfiles.postValue(result);

        }
    }

    fun onCreateClicked() {
        uiCallback?.onCreateClicked()
    }

}