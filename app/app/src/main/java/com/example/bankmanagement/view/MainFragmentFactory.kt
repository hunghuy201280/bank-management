package com.example.bankmanagement.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.bankmanagement.view.device_code.DeviceCodeFragment
import javax.inject.Inject

class MainFragmentFactory

@Inject
constructor(
    private val someString: String
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            DeviceCodeFragment::class.java.name -> {
                DeviceCodeFragment()
            }
            else -> super.instantiate(classLoader, className)
        }

    }
}