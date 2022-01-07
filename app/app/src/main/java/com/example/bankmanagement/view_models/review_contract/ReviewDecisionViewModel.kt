package com.example.bankmanagement.view_models.review_contract

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.application.BaseApplication
import com.example.bankmanagement.models.application.BaseDecision
import com.example.bankmanagement.models.application.exemption.ExemptionApplication
import com.example.bankmanagement.models.application.extension.ExtensionApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationApplication
import com.example.bankmanagement.models.application.liquidation.LiquidationDecision
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.view.dashboard.application.review_application.ReviewApplicationUICallback
import com.example.bankmanagement.view.review_contract.review_decision.ReviewDecisionUICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReviewDecisionViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
    val state: SavedStateHandle,
) : BaseUiViewModel<ReviewDecisionUICallback>() {

    private val TAG: String = "ReviewDecisionViewModel"

    val decision = MutableLiveData<BaseDecision>()

    fun onBack() {
        uiCallback?.onBack()
    }

}