package com.example.bankmanagement.view_models.dashboard.application

import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.constants.STATE_KEY_APPLICATION_CONTRACT_NUMBER
import com.example.bankmanagement.constants.STATE_KEY_APPLICATION_DATE_CREATED
import com.example.bankmanagement.constants.STATE_KEY_APPLICATION_PROFILE_NUMBER
import com.example.bankmanagement.constants.STATE_KEY_APPLICATION_STATUS
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.listener.ValueCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject



@HiltViewModel
class ApplicationViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    val state: SavedStateHandle,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "ApplicationViewModel"

    val dateCreated = state.getLiveData<DateTime>(STATE_KEY_APPLICATION_DATE_CREATED,null)
    val contractNumber = state.getLiveData<String>(STATE_KEY_APPLICATION_CONTRACT_NUMBER,null)
    val profileNumber = state.getLiveData<String>(STATE_KEY_APPLICATION_PROFILE_NUMBER,null)
    val status = state.getLiveData<LoanStatus>(STATE_KEY_APPLICATION_STATUS,LoanStatus.All)
    val type = state.getLiveData<LoanStatus>(STATE_KEY_APPLICATION_STATUS,LoanStatus.All)
    val exemptions = state.getLiveData<ArrayList<ExemptionApplication>>(STATE_KEY_APPLICATION_STATUS,
        arrayListOf())


    init {
        loadExemptions()
    }

    fun loadExemptions(){
        viewModelScope.launch(Dispatchers.IO) {
            val res=mainRepo.getExemptionApplications()
            exemptions.postValue(res)
        }
    }

    fun showDatePicker(v: View) {
        Utils.showDatePicker(v, callback = object : ValueCallBack<DateTime> {
            override fun onValue(value: DateTime) {
                dateCreated.postValue(value)
            }
        })
    }


    fun onFindClicked(){

    }
    fun resetFilter(){

    }



}