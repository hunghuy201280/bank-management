package com.example.bankmanagement.view_models.review_contract

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

    val loanContract: MutableLiveData<LoanContract> = MutableLiveData(reviewLoanContractArgs.value)

    //region transform live data
    val disburseCertificateList = Transformations.map(loanContract) {
        return@map it.disburseCertificates
    }

    val extensionDecisions = Transformations.map(loanContract) {
        return@map it.getExtensionDecisions()
    }
    val exemptionDecisions = Transformations.map(loanContract) {
        return@map it.getExemptionDecisions()
    }
    val liquidationDecisions = Transformations.map(loanContract) {
        return@map it.getLiquidationDecisions()
    }

    val liquidationApplicationList = Transformations.map(loanContract) {
        return@map it.getLiquidationDecisions()
    }

    val totalDisburse = Transformations.map(loanContract) {
        return@map it.getDisburseAmount()
    }

    val unPaid = Transformations.map(loanContract) {
        return@map it.getUnPaid()
    }

    val totalPayment = Transformations.map(loanContract) {
        return@map it.getPaid()
    }

    val containChartData = Transformations.map(loanContract) {
        val result =
            it.disburseCertificates.isNotEmpty() || it.getLiquidationDecisions().isNotEmpty()
        return@map result
    }
    //endregion

    fun onDisburseAdded(v: View) {
        uiCallback?.showCreateDisburseDialogFragment(
            loanContract.value!!.id,
            loanContract.value!!.getRemainingDisburseAmount()
        )
    }

    fun onPaymentReceiptAdded(v: View) {

    }

}
