package com.example.bankmanagement.view_models.create_profile

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.*
import com.example.bankmanagement.models.customer.Customer
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.Utils.Companion.showNotifyDialog
import com.example.bankmanagement.utils.Utils.Companion.uploadFile
import com.example.bankmanagement.view.create_profile.CreateProfile2UICallback
import com.example.bankmanagement.view.create_profile.CreateProfile3UICallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "CreateProfileViewModel";

    val selectedCustomer = MutableLiveData<Customer>();

    var uiCallBack3: CreateProfile3UICallback? = null
    var uiCallBack2: CreateProfile2UICallback? = null

    val moneyToLoan = MutableLiveData<Double>()
    val loanDuration = MutableLiveData<Long>()
    val benefitFromLoan = MutableLiveData<String>()
    val loanPurpose = MutableLiveData<String>()
    val expectedSourceMoneyToRepay = MutableLiveData<String>()
    val collateral = MutableLiveData<String>()

    lateinit var branchInfo: BranchInfo;


    var selectedLoanType = MutableLiveData<LoanType>(LoanType.CapitalMeeting)
    var currentIncomeType = MutableLiveData<IncomeType>(IncomeType.BusinessLicense)
    val proofOfIncomes = MutableLiveData<HashMap<IncomeType, ArrayList<Uri>>>()


    init {
        initProofOfIncomes()
    }

    val signatureImage = MutableLiveData<Uri>();

    private fun initProofOfIncomes() {
        val result = HashMap<IncomeType, ArrayList<Uri>>();
        for (item in IncomeType.values()) {
            result[item] = arrayListOf();
        }
        proofOfIncomes.postValue(result);

    }

    fun onProfileCreated(view: View) {
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val proofOfIncomeData = arrayListOf<ProofOfIncomeRequest>();
            proofOfIncomes.value?.let { incomeMap ->
                for (item in incomeMap.entries) {
                    val tempUrls = uploadFile(view.context, item.value.toList(), mainRepo);
                    proofOfIncomeData.addAll(tempUrls.map { url ->
                        ProofOfIncomeRequest(
                            imageType = item.key,
                            imageID = url
                        )
                    })
                }
            }
            val signatureUrls =
                uploadFile(
                    context = view.context,
                    uris = signatureImage.value?.let { listOf(it) },
                    mainRepo
                )

            val data = CreateLoanProfileData(
                customerId = selectedCustomer.value?.id ?: "",
                proofOfIncome = proofOfIncomeData,
                moneyToLoan = moneyToLoan.value ?: 0.0,
                loanPurpose = loanPurpose.value ?: "",
                loanDuration = loanDuration.value ?: 0,
                collateral = collateral.value ?: "",
                expectedSourceMoneyToRepay = expectedSourceMoneyToRepay.value ?: "",
                benefitFromLoan = benefitFromLoan.value ?: "",
                signatureImg = signatureUrls.firstOrNull() ?: "",
                loanType = selectedLoanType.value!!,
                branchInfo = branchInfo.id,
            );
            Log.d(
                TAG,
                "create loan data :$data "
            );
            try {
                val validateDataResult = data.validate();
                if (validateDataResult != null) {
                    throw Exception(validateDataResult)
                }

                mainRepo.createLoanProfile(data = data);
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Utils.showCompleteDialog(
                        view.context,
                        "Loan profile created successfully",
                        onDismiss = {

                            uiCallBack3?.onProfileCreated()
                        })
                }


            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Utils.showCompleteDialog(
                        view.context,
                        "Error: ${e.response()?.errorBody()?.string()}",
                        onDismiss = {
                        }, isError = true)
                }

                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Utils.showCompleteDialog(
                        view.context,
                        "Error: ${e.message}",
                        onDismiss = {
                        }, isError = true)
                }

                Log.d(TAG, "Create profile validate data error: ${e.message} ");

            } finally {
                withContext(Dispatchers.Main) {
                    showLoading(false)

                }
            }
        }
    }

    fun navigateFrom2To3(v: View) {
        val proofOfIncomeData = arrayListOf<ProofOfIncomeRequest>();
        proofOfIncomes.value?.let { incomeMap ->
            for (item in incomeMap.entries) {
                val tempUrls = item.value.toList();
                proofOfIncomeData.addAll(tempUrls.map { url ->
                    ProofOfIncomeRequest(
                        imageType = item.key,
                        imageID = url.toString()
                    )
                })
            }
        }
        val data = CreateLoanProfileData(
            customerId = selectedCustomer.value?.id ?: "",
            proofOfIncome = proofOfIncomeData,
            moneyToLoan = moneyToLoan.value ?: 0.0,
            loanPurpose = loanPurpose.value ?: "",
            loanDuration = loanDuration.value ?: 0,
            collateral = collateral.value ?: "",
            expectedSourceMoneyToRepay = expectedSourceMoneyToRepay.value ?: "",
            benefitFromLoan = benefitFromLoan.value ?: "",
            signatureImg = signatureImage.value?.toString() ?: "",
            loanType = selectedLoanType.value!!,
            branchInfo = branchInfo.id,
        );
        try {
            val validateDataResult = data.validate();
            if (validateDataResult != null) {
                return showNotifyDialog(v.context, title = "Error", mainText = validateDataResult)
            }
            uiCallBack2?.onNextClick()
        } catch (e: HttpException) {
            Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
        } catch (e: Exception) {
            Log.d(TAG, "Create profile validate data error: ${e.message} ");

        }
    }

    fun signatureImageSelected(uri: Uri) {
        signatureImage.value = uri
    }

    fun signatureImageRemoved() {
        signatureImage.value = null;

    }


    fun proofOfIncomesAdded(item: List<Uri>) {
        if (proofOfIncomes.value == null)
            initProofOfIncomes();
        val temp = proofOfIncomes.value!!;
        temp[currentIncomeType.value!!]!!.addAll(item);
        proofOfIncomes.value = temp;
    }

    fun proofOfIncomeDeleted(position: Int) {
        if (proofOfIncomes.value == null)
            initProofOfIncomes();
        val temp = proofOfIncomes.value!!;
        println("Delete $position $temp")
        temp[currentIncomeType.value!!]!!.removeAt(position)
        proofOfIncomes.value = temp;
    }


}