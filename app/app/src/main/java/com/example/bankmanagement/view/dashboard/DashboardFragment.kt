package com.example.bankmanagement.view.dashboard

import androidx.fragment.app.activityViewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentDashboardBinding
import com.example.bankmanagement.models.BoardOfDirector
import com.example.bankmanagement.view_models.MainViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import io.secf4ult.verticaltablayout.VerticalTabLayoutMediator

class DashboardFragment : BaseFragment<FragmentDashboardBinding, BaseViewModel>() {

    private val tabSettings = arrayListOf<Map<String, Any>>(
        mapOf("name" to "Contract", "icon" to R.drawable.ic_contract),
        mapOf("name" to "Profile", "icon" to R.drawable.ic_contract),
        mapOf("name" to "Application", "icon" to R.drawable.ic_contract),
        mapOf("name" to "Customer", "icon" to R.drawable.ic_contract),
    )


    override fun layoutRes(): Int = R.layout.fragment_dashboard

    override val viewModel: BaseViewModel = object : BaseViewModel() {}

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun viewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java


    override fun initViewModel(viewModel: BaseViewModel) {
        binding.mainVM = mainViewModel
    }

    override fun initView() {

        if (mainViewModel.currentStaff.value!! is BoardOfDirector) {
            tabSettings.add(
                mapOf("name" to "Admin", "icon" to R.drawable.ic_contract),
            )
        }

        binding.pager.isUserInputEnabled = false;
        val dashboardViewPagerAdapter = DashboardViewPagerAdapter(this)
        binding.pager.adapter = dashboardViewPagerAdapter
        VerticalTabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            if(position<tabSettings.size){

                    tab.text = tabSettings[position]["name"] as String
                    tab.setIcon(tabSettings[position]["icon"] as Int)
                }
        }.attach()
    }

    override fun initData() {
    }

    override fun initAction() {

    }


}