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
import javax.inject.Inject

@HiltViewModel
class CreateProfile2ViewModel
@Inject
constructor(
    private val mainRepo: MainRepository,
) : BaseUiViewModel<BaseUserView>() {
    private val TAG: String = "CreateProfile2ViewModel";

    val moneyToLoan=MutableLiveData<Double>()
    val loanDuration=MutableLiveData<Long>()
    val benefitFromLoan=MutableLiveData<String>()
    val loanPurpose=MutableLiveData<String>()
    val expectedSourceMoneyToRepay=MutableLiveData<String>()
    val collateral=MutableLiveData<String>()



    var selectedLoanType=MutableLiveData<LoanType>()
    val proofOfIncomes=MutableLiveData<ArrayList<ProofOfIncome>>();
    val signatureImage=MutableLiveData<Uri>();

    fun onProfileCreated(view: View){
        viewModelScope.launch(Dispatchers.IO) {
               val proofOfIncomeUrls= uploadFile(context=view.context,uris=proofOfIncomes.value?.map { it.getPathOrUrl() })
               val signatureUrls= uploadFile(context=view.context,uris= signatureImage.value?.let { listOf(it) })

        }
    }
    private suspend fun uploadFile(context: Context, uris:List<Uri>?):List<String>{
        if(uris==null || uris.isEmpty())return listOf();
        val cacheDir=context.cacheDir;
        val contentResolver=context.contentResolver;

        val files=uris.map {
            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(it, "r", null) ?: return listOf()

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(it))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            file
        }
        return try{
            val urls=mainRepo.upFiles(files = files);
            Log.d(TAG,"File uploaded $urls");
            urls.resultId;

        }catch (e:HttpException){
            Log.d(TAG, "Error happened: ${e.response()?.errorBody()?.string()} ");
            listOf()

        }
    }

    fun signatureImageSelected(uri:Uri){
        signatureImage.value=uri;
    }
    fun signatureImageRemoved(){
        signatureImage.value=null;

    }
    fun proofOfIncomesAdded(item:List<Uri>){
        val temp=proofOfIncomes.value?: arrayListOf();
        temp.addAll(item.map { ProofOfIncomeUI(imageType = IncomeType.BusinessLicense, imageUri = it) })
        proofOfIncomes.value=temp;
    }

    fun proofOfIncomeDeleted(position: Int){
        val temp=proofOfIncomes.value?: arrayListOf();
        println("Delete $position $temp")
        temp.removeAt(position)
        proofOfIncomes.value=temp;
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