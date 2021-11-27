package com.example.bankmanagement.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

class MainFragmentFactory

@Inject
constructor(
    private val someString: String
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MyFragment::class.java.name -> {
                MyFragment(someString)
            }
            else -> super.instantiate(classLoader, className)
        }

    }
}