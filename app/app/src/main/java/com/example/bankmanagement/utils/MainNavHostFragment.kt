package com.example.bankmanagement.utils

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.example.bankmanagement.view.MainFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainNavHostFragment :NavHostFragment() {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory;


    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory=fragmentFactory;
    }
}
