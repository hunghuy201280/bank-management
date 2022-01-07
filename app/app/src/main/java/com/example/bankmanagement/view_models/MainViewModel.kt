package com.example.bankmanagement.view_models


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.utils.helper.SocketHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    private val socketHelper: SocketHelper,
    val branchInfo: ValueWrapper<BranchInfo?>
) : BaseUiViewModel<BaseUserView>() {
    companion object{
        val TAG= "MainViewModel"
    }
    val currentBranch: MutableLiveData<BranchInfo> = MutableLiveData();
    val currentStaff: MutableLiveData<Staff> = MutableLiveData();

    init {
        socketHelper.apply {
            setSocket()
            establishConnection()
        }
        listenSocket()
    }

    private fun listenSocket() {
        socketHelper.getSocket().on("balanceChanged") {
            if (currentBranch.value == null) {
                Log.d(TAG,"Current branch is null")
                return@on;
            }
            if (it.isNotEmpty() && it[0] != null) {
                val newBalance = it[0].toString().toDouble()
                val temp = currentBranch.value!!
                currentBranch.postValue(temp.copy(branchBalance = newBalance))
                Log.d(TAG,"balance updated")

            }
        }
    }

    fun setNewBranch(branchInfo: BranchInfo) {
        currentBranch.value = branchInfo
        this.branchInfo.value = branchInfo
    }

    fun updateBranchInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mainRepository.getBranchInfo(currentBranch.value!!.branchCode)
            currentBranch.postValue(result)
        }
    }

}