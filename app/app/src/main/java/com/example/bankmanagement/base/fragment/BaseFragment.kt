package com.hanheldpos.ui.base.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.bankmanagement.base.activity.BaseActivity
import com.example.bankmanagement.base.dialog.AppAlertDialog
import com.example.bankmanagement.base.viewmodel.BaseViewModel
import com.example.bankmanagement.base.viewmodel.ViewState

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var fragmentContext: Context

    // Binding
    protected lateinit var binding: T

    @LayoutRes
    protected abstract fun layoutRes(): Int

    // ViewModel
    protected abstract val viewModel:VM
    protected abstract fun viewModelClass(): Class<VM>
    protected abstract fun initViewModel(viewModel: VM)

    // Data & Actions
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initAction()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(fragmentContext),
            layoutRes(),
            container,
            false
        )
        binding.apply {
            root.setOnTouchListener { v: View, _: MotionEvent? ->
                v.isClickable = true
                v.isFocusable = true
                false
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        viewModel
            .apply {
                initViewModel(this)
            }

        initView()
        initData()
        initAction()

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState?.run {
                when (viewState) {
                    ViewState.SHOW_LOADING -> showLoading(true)
                    ViewState.HIDE_LOADING -> showLoading(false)
                    ViewState.SHOW_ERROR -> viewModel.errMessage?.run {
                        showMessage(this)
                    }
                }
            }
        })
    }




    /**
     * Show loading dialog
     */
    fun showLoading(show: Boolean) {
        (activity as? BaseActivity<*, *>)?.showLoading(show)
    }

    /**
     * Show error dialog
     */
    private fun showError() {
        (activity as? BaseActivity<*, *>)?.showError()
    }

    /**
     * Show error dialog
     */
    fun showMessage(message: String?) {
        showAlert(message = message)
    }

    /**
     * Show alert dialog with full options
     * ------------------------------------------
     * | Title                                  |
     * | Message                                |
     * ------NEGATIVE BUTTON---POSITIVE BUTTON---
     */
    fun showAlert(
        title: String? = null,
        message: String?,
        positiveText: String? = null,
        negativeText: String? = null,
        onClickListener: AppAlertDialog.AlertDialogOnClickListener? = null
    ) {
        (activity as? BaseActivity<*, *>)?.showAlert(
            title,
            message,
            positiveText,
            negativeText,
            onClickListener
        )
    }

    /**
     * Request permission
     */
    fun requestPermission(
        explainPermission: String,
        permissions: List<String>,
        callback: BaseActivity.RequestPermissionCallback
    ) {
        (activity as? BaseActivity<*, *>)?.requestPermission(
            explainPermission,
            permissions,
            callback
        )
    }


}