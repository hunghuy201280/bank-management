package com.example.bankmanagement.base.activity

import androidx.databinding.ViewDataBinding
import com.example.bankmanagement.base.viewmodel.BaseViewModel

abstract class BaseFragmentBindingActivity<T : ViewDataBinding, VM : BaseViewModel> :
    BaseActivity<T, VM>() {




    override fun onBackPressed() {

        super.onBackPressed()
    }
}