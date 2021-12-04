package com.example.bankmanagement.view.sign_in

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentSignInBinding
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.models.Staff
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view_models.MainViewModel
import com.example.bankmanagement.view_models.sign_in.SignInViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment(
) : BaseFragment<FragmentSignInBinding, SignInViewModel>(),SignInUiCallback {

    private val TAG="SignInFragment";

    override fun layoutRes(): Int =R.layout.fragment_sign_in

    override val viewModel: SignInViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun viewModelClass(): Class<SignInViewModel> =SignInViewModel::class.java

    override fun initViewModel(viewModel: SignInViewModel) {
        binding.viewModel=viewModel;
        viewModel.init(this);
        mainViewModel.currentStaff.observe(this,{
            it?.let {
                Log.v(TAG,"Staff received: $it");
            }
        })
    }

    override fun initView() {
        
    }

    override fun initData() {
        
    }

    override fun initAction() {
        binding.signInButton.setOnClickListener{
            viewModel.signIn(mainViewModel.currentBranch.value!!.id)
        }

    }

    override fun onLoggedIn(staff: Staff) {
        mainViewModel.currentStaff.postValue(staff);
        findNavController().navigate(R.id.action_signInFragment_to_clockInOutFragment)
    }

}