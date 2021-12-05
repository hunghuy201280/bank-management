package com.example.bankmanagement.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentDashboardBinding
import com.example.bankmanagement.view_models.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.hanheldpos.ui.base.fragment.BaseFragment
import io.secf4ult.verticaltablayout.VerticalTabLayoutMediator

class DashboardFragment : BaseFragment<FragmentDashboardBinding,BaseViewModel>() {

    private val tabSettings= arrayListOf<Map<String,Any>>(
        mapOf("name" to "Contract", "icon" to R.drawable.ic_contract),
        mapOf("name" to "Profile", "icon" to R.drawable.ic_contract),
    );


    override fun layoutRes(): Int=R.layout.fragment_dashboard

    override val viewModel: BaseViewModel=MainViewModel()
        

    override fun viewModelClass(): Class<BaseViewModel>
        =BaseViewModel::class.java


    override fun initViewModel(viewModel: BaseViewModel) {
    }

    override fun initView() {
       val dashboardViewPagerAdapter = DashboardViewPagerAdapter(this)
        binding.pager.adapter=dashboardViewPagerAdapter;
        VerticalTabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabSettings[position]["name"] as String;
            tab.setIcon(tabSettings[position]["icon"] as Int);
        }.attach()
    }

    override fun initData() {
    }

    override fun initAction() {
//        binding.contractButton.setOnClickListener{
//            binding.pager.setCurrentItem(0,false);
//        }
//        binding.profileButton.setOnClickListener{
//            binding.pager.setCurrentItem(1,false);
//        }
    }


}