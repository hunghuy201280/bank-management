package com.example.bankmanagement.view.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bankmanagement.view.dashboard.contract.ContractFragment
import com.example.bankmanagement.view.dashboard.profile.ProfileFragment

class DashboardViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val TAG="DashboardViewPagerAdapter";
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)

        return when(position){
            0->ContractFragment()
            1->ProfileFragment()
            else -> {
                throw Exception("[$TAG] Error: Exceed Fragment count");
            }
        }
    }
}