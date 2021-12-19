package com.example.bankmanagement.view_models.review_contract

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.*
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.create_contract.CreateContractFragmentArgs
import com.example.bankmanagement.view.review_profile.ReviewContractUICallback
import com.example.bankmanagement.view.review_profile.ReviewProfileUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReviewContractViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    @AppModule.ReviewLoanContractArgs val reviewLoanContractArgs: ValueWrapper<LoanContract>,

    ) : BaseUiViewModel<ReviewContractUICallback>() {
    private val TAG: String = "ReviewContractViewModel";

    val loanContract:LoanContract=reviewLoanContractArgs.value

    fun onDisburseAdded(v:View){

    }
    fun onPaymentReceiptAdded(v:View){

    }

}
