package com.example.bankmanagement.view.dashboard

import androidx.fragment.app.activityViewModels
import com.example.bankmanagement.R
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.databinding.FragmentDashboardBinding
import com.example.bankmanagement.models.BoardOfDirector
import com.example.bankmanagement.view_models.MainViewModel
import com.hanheldpos.ui.base.fragment.BaseFragment
import io.secf4ult.verticaltablayout.VerticalTabLayoutMediator

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.bankmanagement.utils.Utils
import io.secf4ult.verticaltablayout.VerticalTabLayout


class DashboardFragment : BaseFragment<FragmentDashboardBinding, BaseViewModel>() {

    private val tabSettings = arrayListOf<Map<String, Any>>(
        mapOf("name" to "Contract", "icon" to R.drawable.ic_contract),
        mapOf("name" to "Profile", "icon" to R.drawable.ic_profile),
        mapOf("name" to "Application", "icon" to R.drawable.ic_application),
        mapOf("name" to "Customer", "icon" to R.drawable.ic_customer),
    )


    override fun layoutRes(): Int = R.layout.fragment_dashboard

    override val viewModel: BaseViewModel = object : BaseViewModel() {}

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun viewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java


    override fun initViewModel(viewModel: BaseViewModel) {
        binding.mainVM = mainViewModel
    }

    override fun initView() {

        initTabLayout()
    }

    private fun initTabLayout() {
        if (mainViewModel.currentStaff.value!! is BoardOfDirector) {
            tabSettings.add(
                mapOf("name" to "Admin", "icon" to R.drawable.ic_contract),
            )
        }

        binding.pager.isUserInputEnabled = false;
        val dashboardViewPagerAdapter = DashboardViewPagerAdapter(this)
        binding.pager.adapter = dashboardViewPagerAdapter

        binding.tabLayout.addOnTabSelectedListener(object:VerticalTabLayout.OnTabSelectedListener<VerticalTabLayout.Tab>{
            override fun onTabSelected(tab: VerticalTabLayout.Tab?) {
                if(tab==null ||tab.customView==null) return
                val view=tab.customView
                val textView=view!!.findViewById<TextView>(R.id.titleTextView)
                textView.apply {
                    setTextColor(ContextCompat.getColor(context,R.color.primaryText))
                    setBackgroundColor(ContextCompat.getColor(binding.tabLayout.context,R.color.white))
                    Utils.setTextViewDrawableColor(this,R.color.cornflower_blue)
                }

            }

            override fun onTabUnselected(tab: VerticalTabLayout.Tab?) {
                if(tab==null ||tab.customView==null) return
                val view=tab.customView
                val textView=view!!.findViewById<TextView>(R.id.titleTextView)
                textView.apply {
                    setTextColor(ContextCompat.getColor(context,R.color.disableText))
                    setBackgroundColor(ContextCompat.getColor(binding.tabLayout.context,android.R.color.transparent))
                    Utils.setTextViewDrawableColor(this,R.color.disableText)
                }
            }

            override fun onTabReselected(tab: VerticalTabLayout.Tab?) {
            }
        })
        VerticalTabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->

            val tabView: View =
                LayoutInflater.from(context).inflate(R.layout.custom_tab_item, null)
            val textView=tabView.findViewById<TextView>(R.id.titleTextView)
            textView.apply {
                text=tabSettings[position]["name"] as String
                setCompoundDrawablesWithIntrinsicBounds(tabSettings[position]["icon"] as Int, 0, 0, 0)

                //set initial style
                if(position==0){
                    setTextColor(ContextCompat.getColor(context,R.color.primaryText))
                    setBackgroundColor(ContextCompat.getColor(binding.tabLayout.context,R.color.white))
                    Utils.setTextViewDrawableColor(this,R.color.cornflower_blue)
                }else{
                    setTextColor(ContextCompat.getColor(context,R.color.disableText))
                    setBackgroundColor(ContextCompat.getColor(binding.tabLayout.context,android.R.color.transparent))
                    Utils.setTextViewDrawableColor(this,R.color.disableText)
                }
            }
            tab.customView = tabView
        }.attach()

    }

    override fun initData() {
    }

    override fun initAction() {

    }


}