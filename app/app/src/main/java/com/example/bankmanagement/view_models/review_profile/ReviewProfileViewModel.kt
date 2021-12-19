package com.example.bankmanagement.view_models.review_profile

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.IncomeType
import com.example.bankmanagement.models.LoanProfile
import com.example.bankmanagement.models.LoanStatus
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.create_contract.CreateContractFragment
import com.example.bankmanagement.view.create_contract.CreateContractFragmentArgs
import com.example.bankmanagement.view.review_profile.ReviewProfileUICallback
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
    @AppModule.CreateContractArgs val createContractFragmentArgs: ValueWrapper<CreateContractFragmentArgs>,

    ) : BaseUiViewModel<ReviewProfileUICallback>() {
    private val TAG: String = "ReviewProfileViewModel";

    val loanProfile = MutableLiveData<LoanProfile>(reviewLoanProfileArgs.value);
    var currentIncomeType = MutableLiveData(IncomeType.BusinessLicense)
    val proofOfIncomes = MutableLiveData<HashMap<IncomeType, ArrayList<Uri>>>()
    val hasContract = MutableLiveData(true)


    init {
        initProofOfIncomes()
        initHasContract()
    }

    fun initHasContract() {
        viewModelScope.launch(Dispatchers.IO) {
            hasContract.postValue(loanProfile.value?.let { mainRepo.hasContract(it.id) } ?: true)
        }
    }

    fun showCreateContractDialog(v:View){
        createContractFragmentArgs.value= CreateContractFragmentArgs(loanProfile = loanProfile.value!!)
        uiCallback?.showCreateContractDialogFragment()
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



    fun approveProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mainRepo.updateLoanStatus(
                    status = LoanStatus.Done,
                    profileId = loanProfile.value!!.id
                )
                withContext(Dispatchers.Main) {
                    uiCallback?.onBack()
                }

            } catch (e: HttpException) {
                Log.e(TAG, "Approve Error: ${e.response()?.errorBody()?.string()}")
            }


        }
    }

    fun rejectProfile() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                mainRepo.updateLoanStatus(
                    status = LoanStatus.Rejected,
                    profileId = loanProfile.value!!.id
                )
                withContext(Dispatchers.Main) {
                    uiCallback?.onBack()
                }
            } catch (e: HttpException) {
                Log.e(TAG, "Reject Error: ${e.response()?.errorBody()?.string()}")

            }


        }
    }

    fun deleteProfile(view: View) {
        Utils.showAlertDialog(
            view.context,
            onPositiveClick = {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        mainRepo.updateLoanStatus(
                            status = LoanStatus.Deleted,
                            profileId = loanProfile.value!!.id
                        )
                        withContext(Dispatchers.Main) {
                            Utils.showCompleteDialog(view.context, onDismiss = {
                                uiCallback?.onBack()
                            })
                        }
                    } catch (e: HttpException) {
                        Log.e(TAG, "Delete Error: ${e.response()?.errorBody()?.string()}")
                    }
                }
            },
        );

    }


}
