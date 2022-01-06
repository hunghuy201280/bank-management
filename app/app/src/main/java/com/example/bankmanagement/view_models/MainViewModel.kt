package com.example.bankmanagement.view_models


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.repo.MainRepository
import com.example.bankmanagement.utils.ValueWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    val branchInfo: ValueWrapper<BranchInfo?>
): BaseUiViewModel<BaseUserView>() {
    val currentBranch:MutableLiveData<BranchInfo> = MutableLiveData();
    val currentStaff:MutableLiveData<Staff> = MutableLiveData();

    fun setNewBranch(branchInfo:BranchInfo){
        currentBranch.value=branchInfo
        this.branchInfo.value=branchInfo
    }
    fun updateBranchInfo(){
        viewModelScope.launch(Dispatchers.IO) {
           val result= mainRepository.getBranchInfo(currentBranch.value!!.branchCode)
            currentBranch.postValue(result)
        }
    }

}