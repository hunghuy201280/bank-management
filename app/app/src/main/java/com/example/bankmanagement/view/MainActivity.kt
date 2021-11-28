package com.example.bankmanagement.view

import android.util.Log
import androidx.activity.viewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.activity.BaseActivity
import com.example.bankmanagement.databinding.ActivityMainBinding
import com.example.bankmanagement.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.bankmanagement.utils.helper.SystemHelper


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {
    override fun layoutRes(): Int =R.layout.activity_main
    private val TAG:String="MainActivity";

    override val viewModel: MainViewModel by viewModels()
    override fun initView() {
        SystemHelper.hideNavBar(this)

    }

    override fun initData() {

    }

    override fun initAction() {

    }

    override fun viewModelClass(): Class<MainViewModel> =MainViewModel::class.java

    override fun initViewModel(viewModel: MainViewModel) {
        viewModel.currentBranch.observe(this,{
            it?.let {
                Log.v(TAG,"Branch Updated $it");
            }
        })
    }

}