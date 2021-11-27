package com.example.bankmanagement.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bankmanagement.R
import com.example.bankmanagement.databinding.FragmentMyBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyFragment constructor(
  private val  someString :String
): Fragment() {
    private lateinit var binding:FragmentMyBinding;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMyBinding.inflate(layoutInflater)
        println(someString)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
}