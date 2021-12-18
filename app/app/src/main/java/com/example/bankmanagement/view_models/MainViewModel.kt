package com.example.bankmanagement.view_models


import androidx.lifecycle.MutableLiveData
import com.example.bankmanagement.base.BaseUserView
import com.example.bankmanagement.base.viewmodel.BaseUiViewModel
import com.example.bankmanagement.models.BranchInfo
import com.example.bankmanagement.models.Staff
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(): BaseUiViewModel<BaseUserView>() {
    val currentBranch:MutableLiveData<BranchInfo> = MutableLiveData();
    val currentStaff:MutableLiveData<Staff> = MutableLiveData();


}