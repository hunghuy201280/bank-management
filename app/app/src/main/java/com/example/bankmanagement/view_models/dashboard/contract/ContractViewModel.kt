package com.example.bankmanagement.view_models.dashboard.contract

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.constants.STATE_KEY_CONTRACT_LOAN_TYPE
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanType
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.utils.helper.SocketHelper
import com.example.bankmanagement.utils.listener.ValueCallBack
import com.example.bankmanagement.utils.toUtcISO
import com.example.bankmanagement.view.dashboard.contract.ContractUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class ContractViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewLoanContractArgs val reviewLoanContractArgs: ValueWrapper<LoanContract>,
    @AppModule.ReviewLoanProfileArgs val reviewLoanProfileArgs: ValueWrapper<LoanProfile>,
    private val socketHelper: SocketHelper,
    val state: SavedStateHandle,
) : BaseUiViewModel<ContractUICallback>() {
    private val TAG: String = "ApplicationViewModel"

    //region search
    val loanType = state.getLiveData(STATE_KEY_CONTRACT_LOAN_TYPE, LoanType.All)
    val profileNumber = MutableLiveData<String>()
    val contractNumber = MutableLiveData<String>()
    val staffInCharge = MutableLiveData<String>()
    val BODInCharge = MutableLiveData<String>()
    val moneyToLoan = MutableLiveData<Double>()
    val dateCreated = MutableLiveData<DateTime>()
    val phoneNumber = MutableLiveData<String>()

    //endregion
    val loanContracts = MutableLiveData<List<LoanContract>>(listOf())


    private fun restoreState() {
        if (loanType.value != LoanType.All) {
            onFindClicked()
        }
    }

    init {
        getContract()

    }


    fun onTitleTap(){
        socketHelper.getSocket().emit("testEvent",DateTime.now().toUtcISO())
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
        contractNumber.value = null
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
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = mainRepo.getContracts(
                    contractNumber = contractNumber.value,
                    staffName = staffInCharge.value,
                    approver = BODInCharge.value,
                    profileNumber = profileNumber.value,
                    loanType = if (loanType.value == LoanType.All) null else loanType.value,
                    createdAt = dateCreated.value?.toUtcISO(),
                    moneyToLoan = moneyToLoan.value,
                    customerPhone = phoneNumber.value
                )
                loanContracts.postValue(result);
            } catch (e: HttpException) {
                loanContracts.postValue(null)
            }
            finally {
                withContext(Dispatchers.Main){
                    showLoading(false)

                }

            }
        }
    }

}