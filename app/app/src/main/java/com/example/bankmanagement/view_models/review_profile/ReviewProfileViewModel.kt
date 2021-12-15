package com.example.bankmanagement.view_models.review_profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.IncomeType
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.clockin.ClockInOutUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReviewProfileViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewLoanProfileArgs val reviewLoanProfileArgs: ValueWrapper<LoanProfile>,

    ) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "ReviewProfileViewModel";

    val loanProfile = MutableLiveData<LoanProfile>(reviewLoanProfileArgs.value);
    var currentIncomeType = MutableLiveData(IncomeType.BusinessLicense)
    val proofOfIncomes = MutableLiveData<HashMap<IncomeType, ArrayList<Uri>>>()


    init {
        initProofOfIncomes()
    }

    private fun initProofOfIncomes() {
        val result = HashMap<IncomeType, ArrayList<Uri>>();
        for (item in IncomeType.values()) {
            result[item] = arrayListOf();
        }
        loanProfile.value?.let {
            it.proofOfIncome.forEach { it1 ->
                result[it1.imageType]!!.add(it1.getUrl())
            }
        }

        proofOfIncomes.postValue(result);

    }


}