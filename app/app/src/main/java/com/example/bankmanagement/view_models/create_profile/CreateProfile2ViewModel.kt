package com.example.bankmanagement.view_models.create_profile

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.*
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.repo.dtos.loan_profiles.CreateLoanProfileData
import com.example.bankmanagement.utils.Utils
import com.example.bankmanagement.utils.Utils.Companion.getFileName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.sql.Array
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@HiltViewModel
class CreateProfile2ViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "CreateProfile2ViewModel";

    val moneyToLoan = MutableLiveData<Double>()
    val loanDuration = MutableLiveData<Long>()
    val benefitFromLoan = MutableLiveData<String>()
    val loanPurpose = MutableLiveData<String>()
    val expectedSourceMoneyToRepay = MutableLiveData<String>()
    val collateral = MutableLiveData<String>()

    lateinit var customer: Customer;
    lateinit var branchInfo: BranchInfo;


    var selectedLoanType = MutableLiveData<LoanType>()
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
        viewModelScope.launch(Dispatchers.IO) {
            val proofOfIncomeData= arrayListOf<ProofOfIncomeRequest>();
            proofOfIncomes.value?.let { incomeMap ->
                for (item in incomeMap.entries) {
                    val tempUrls=uploadFile(view.context,item.value.toList());
                    proofOfIncomeData.addAll(tempUrls.map { url->ProofOfIncomeRequest(imageType = item.key, imageID =url ) });
                }
            }
            val signatureUrls =
                uploadFile(context = view.context, uris = signatureImage.value?.let { listOf(it) })
            Log.d(
                TAG,
                "money ${moneyToLoan.value} duration ${loanDuration.value} benefit ${benefitFromLoan.value}" +
                        "purpose ${loanPurpose.value} expected ${expectedSourceMoneyToRepay.value} colle ${collateral.value}"
            );
            val data = CreateLoanProfileData(
                customerId = customer.id,
                proofOfIncome = proofOfIncomeData,
                moneyToLoan = moneyToLoan.value!!,
                loanPurpose = loanPurpose.value!!,
                loanDuration = loanDuration.value!!,
                collateral = collateral.value!!,
                expectedSourceMoneyToRepay = expectedSourceMoneyToRepay.value!!,
                benefitFromLoan = benefitFromLoan.value!!,
                signatureImg = signatureUrls.first(),
                loanType = selectedLoanType.value!!,
                branchInfo = branchInfo.id,
            );
            try {
                mainRepo.createLoanProfile(data = data);
            } catch (e: HttpException) {
                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");

            }
        }
    }

    private suspend fun uploadFile(context: Context, uris: List<Uri>?): List<String> {
        if (uris == null || uris.isEmpty()) return listOf();
        val cacheDir = context.cacheDir;
        val contentResolver = context.contentResolver;

        val files = uris.map {
            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(it, "r", null) ?: return listOf()

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(it))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            file
        }
        return try {
            val urls = mainRepo.upFiles(files = files);
            Log.d(TAG, "File uploaded $urls");
            urls.resultId;

        } catch (e: HttpException) {
            Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
            listOf()

        }
    }

    fun signatureImageSelected(uri: Uri) {
        signatureImage.value = uri;
    }

    fun signatureImageRemoved() {
        signatureImage.value = null;

    }



    fun proofOfIncomesAdded(item: List<Uri>) {
        if(proofOfIncomes.value==null)
            initProofOfIncomes();
        val temp = proofOfIncomes.value!!;
        temp[currentIncomeType.value!!]!!.addAll(item);
        proofOfIncomes.value = temp;
    }

    fun proofOfIncomeDeleted(position: Int) {
        if(proofOfIncomes.value==null)
            initProofOfIncomes();
        val temp = proofOfIncomes.value!!;
        println("Delete $position $temp")
        temp[currentIncomeType.value!!]!!.removeAt(position)
        proofOfIncomes.value = temp;
    }

    fun onSearch(query: String) {
//        if(query.isEmpty()) return;
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val result = mainRepo.searchCustomers(query = mapOf("name" to query,"isStartWith" to true));
//                customers.postValue(result);
//
//                println("Customer loaded $result");
//
//            } catch (e: HttpException) {
//                customers.postValue(arrayListOf())
//                Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
//
//            } catch (e: Exception) {
//                customers.postValue(arrayListOf())
//                Log.d(TAG, "Error happened: $e ");
//            }
//        }
    }


}