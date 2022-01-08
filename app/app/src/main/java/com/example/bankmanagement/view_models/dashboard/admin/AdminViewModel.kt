package com.example.bankmanagement.view_models.dashboard.admin

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.constants.*
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.LoanContract
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.models.admin.RevenueStatistic
import com.example.bankmanagement.models.application.ApplicationType
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.utils.listener.ValueCallBack
import com.example.bankmanagement.utils.toUtcISO
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
class AdminViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    val state: SavedStateHandle,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "AdminViewModel"

    val revenueStatistic=MutableLiveData<RevenueStatistic>()

    init {
        getStatistic("2021")
    }

    fun getStatistic(year: String){
        val yearInt = year.toInt()
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val result = mainRepo.getRevenueStatistic(yearInt)
                withContext(Dispatchers.Main) {
                    showLoading(false)
                }
                revenueStatistic.postValue(result)
            }
            catch(e:HttpException){
                Log.e(TAG,"Get statistic error: ${e.response()?.errorBody()?.string()}")
                withContext(Dispatchers.Main) {
                    showLoading(false)
                }
            }
            finally {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                }
            }
        }
    }
}