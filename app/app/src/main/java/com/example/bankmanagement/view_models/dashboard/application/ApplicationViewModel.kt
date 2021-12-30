package com.example.bankmanagement.view_models.dashboard.application

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.constants.*
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.application.ApplicationType
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.utils.listener.ValueCallBack
import com.example.bankmanagement.view.dashboard.application.ApplicationUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import retrofit2.HttpException
import javax.inject.Inject



@HiltViewModel
class ApplicationViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewLoanContractArgs val reviewLoanContractArgs: ValueWrapper<LoanContract>,

    val state: SavedStateHandle,
) : BaseUiViewModel<ApplicationUICallback>() {
    private val TAG: String = "ApplicationViewModel"

    val dateCreated = state.getLiveData<DateTime>(STATE_KEY_APPLICATION_DATE_CREATED,null)
    val contractNumber = state.getLiveData<String>(STATE_KEY_APPLICATION_CONTRACT_NUMBER,null)
    val applicationNumber = state.getLiveData<String>(STATE_KEY_APPLICATION_NUMBER,null)
    val status = state.getLiveData(STATE_KEY_APPLICATION_STATUS,LoanStatus.All)
    //val type = state.getLiveData<LoanStatus>(STATE_KEY_APPLICATION_STATUS,LoanStatus.All)
    val applications = state.getLiveData<ArrayList<BaseApplication>>(
        STATE_KEY_APPLICATION_LIST,
        arrayListOf())
   // val applications = MutableLiveData<ArrayList<BaseApplication>>(arrayListOf())


    init {
        loadApplications()

    }

    fun loadApplications(){
        viewModelScope.launch(Dispatchers.IO) {
            val exemptions=mainRepo.getApplications()
            val result= arrayListOf<BaseApplication>()
            result.addAll(exemptions)
            applications.postValue(result)
        }
    }

    fun onContractNumberClicked(contractNumber:String){
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val res=mainRepo.getContract(contractNumber=contractNumber)
                reviewLoanContractArgs.value = res
                withContext(Dispatchers.Main){
                    showLoading(false)

                    uiCallback?.onContractNumberClicked()
                }
            }
            catch (e:HttpException){
                Log.d(TAG, "Contract number clicked: ${e.response()?.errorBody()?.string()} ");

                withContext(Dispatchers.Main){
                    showLoading(false)
                }
            }


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
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = mainRepo.getApplications(
                    contractNumber = contractNumber.value,
                    status = if (status.value == LoanStatus.All) null else status.value,
                    applicationNumber=applicationNumber.value,
                    createdAt = dateCreated.value?.toDateTime(DateTimeZone.UTC)?.toString(),
                )
                val newList= ArrayList<BaseApplication>()
                newList.addAll(result)
                applications.postValue(newList)
            } catch (e: HttpException) {
                applications.postValue(null)
            }
            finally {
                withContext(Dispatchers.Main){
                    showLoading(false)

                }

            }
        }

    }
    fun resetFilter(){
        loadApplications()
        dateCreated.value=null
        contractNumber.value=null
        applicationNumber.value=null
        status.value=LoanStatus.All

    }


    fun onCreateClicked(type: ApplicationType){
        uiCallback?.onCreateClicked(type)
    }



}