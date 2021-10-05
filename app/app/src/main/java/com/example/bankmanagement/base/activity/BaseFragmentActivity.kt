package com.example.bankmanagement.base.activity

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.bankmanagement.base.fragment.BaseFragment
import com.example.bankmanagement.base.navigation.FragmentNavigator

abstract class BaseFragmentActivity<T : ViewDataBinding> : BasePermissionActivity<T>() {

    var navigator: FragmentNavigator? = null
        private set

    protected fun setRootFragment(rootFragment: Fragment?, rootLayout: Int) {
        navigator = FragmentNavigator(supportFragmentManager, rootLayout)
        navigator!!.rootFragment = rootFragment
    }

    val activeFragment: Fragment
        get() = navigator!!.activeFragment

    override fun onBackPressed() {
        if (navigator == null) {
            super.onBackPressed()
        } else {
            val activeFragment = navigator!!.activeFragment
            if (activeFragment is BaseFragment) {
                if (activeFragment.onBackPress()) {
                    // the active fragment swallow the backPress
                    return
                }
            }
            if (navigator!!.size > 0) {
                navigator!!.goOneBack()
                return
            }
        }
        super.onBackPressed()
    }
}