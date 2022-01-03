package com.example.bankmanagement.view_models.dashboard.profile

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.utils.listener.ValueCallBack
import com.example.bankmanagement.view.dashboard.profile.ProfileUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewLoanProfileArgs val reviewLoanProfileArgs: ValueWrapper<LoanProfile>,
) : BaseUiViewModel<ProfileUICallback>() {
    private val TAG: String = "ProfileViewModel";

    val selectedLoanType = MutableLiveData<LoanType>(LoanType.All)
    val customerName = MutableLiveData<String>()
    val moneyToLoan = MutableLiveData<Double>()
    val loanNumber = MutableLiveData<String>()
    val dateCreated = MutableLiveData<DateTime>()
    val loanStatus = MutableLiveData<LoanStatus>(LoanStatus.All)
    val loanProfiles = MutableLiveData<List<LoanProfile>>(listOf())

    init {
        getProfiles()
    }

    fun getProfiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _getProfiles();
        }
    }


    fun resetFilter() {
        getProfiles();
        selectedLoanType.value = LoanType.All
        customerName.value = null
        moneyToLoan.value = null
        loanNumber.value = null
        dateCreated.value = null
        loanStatus.value = LoanStatus.All
        loanProfiles.value = null
    }

    private suspend fun _getProfiles() {
        val profiles = mainRepo.getLoanProfiles()
        loanProfiles.postValue(profiles)

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

            try {
                val result = mainRepo.getLoanProfiles(
                    profileNumber = loanNumber.value,
                    customerName = customerName.value,
                    moneyToLoan = moneyToLoan.value,
                    loanType = if (selectedLoanType.value == LoanType.All) null else selectedLoanType.value,
                    createdAt = dateCreated.value?.toDateTime(DateTimeZone.UTC)?.toString(),
                    loanStatus = if (loanStatus.value == LoanStatus.All) null else loanStatus.value,
                )
                loanProfiles.postValue(result);

            } catch (e: HttpException) {
                loanProfiles.postValue(null)
            }
        }
    }

    fun onCreateClicked() {
        uiCallback?.onCreateClicked()
    }

}