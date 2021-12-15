package com.example.bankmanagement.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.bankmanagement.di.AppModule
import com.example.bankmanagement.repo.dtos.sign_in.ClockInOutResponse
import com.example.bankmanagement.utils.ValueWrapper
import com.example.bankmanagement.view.clockin.ClockInOutFragment
import com.example.bankmanagement.view.device_code.DeviceCodeFragment
import com.example.bankmanagement.view.review_profile.ReviewProfileFragment
import com.example.bankmanagement.view.sign_in.SignInFragment
import javax.inject.Inject

class MainFragmentFactory

@Inject
constructor(

    ) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            DeviceCodeFragment::class.java.name -> {
                DeviceCodeFragment()
            }
            SignInFragment::class.java.name -> {
                SignInFragment()
            }
            ClockInOutFragment::class.java.name -> {
                ClockInOutFragment();
            }
            ReviewProfileFragment::class.java.name -> {
                ReviewProfileFragment();
            }
            else -> super.instantiate(classLoader, className)
        }

    }
}