package com.example.bankmanagement.view.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bankmanagement.view.dashboard.application.ApplicationFragment
import com.example.bankmanagement.view.dashboard.contract.ContractFragment
import com.example.bankmanagement.view.dashboard.customer.CustomerFragment
import com.example.bankmanagement.view.dashboard.profile.ProfileFragment

class DashboardViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val TAG="DashboardViewPagerAdapter";
    override fun getItemCount(): Int = fragments.size
    val fragments= listOf<Fragment>(
        ContractFragment(),
        ProfileFragment(),
        ApplicationFragment(),
        CustomerFragment(),
    )
    override fun createFragment(position: Int): Fragment {
        if(position<fragments.size){
          return  fragments[position];
        }
        else{
            throw Exception("[$TAG] Error: Exceed Fragment count")

        }
//        return when(position){
//            0->fragments[position]
//            1->ProfileFragment()
//            2->ApplicationFragment()
//            else -> {
//                throw Exception("[$TAG] Error: Exceed Fragment count");
//            }
//        }
    }
}