package com.example.bankmanagement.base.fragment;

import androidx.databinding.ViewDataBinding;

public abstract class BaseBindingGenFragment<T extends ViewDataBinding> extends BaseBindingFragment {

    protected T binding;

    @Override
    protected void onCreateBinding(ViewDataBinding dataBinding) {
        binding = (T) dataBinding;
        binding.setLifecycleOwner(this);
    }
}
