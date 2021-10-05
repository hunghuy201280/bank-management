package com.example.bankmanagement.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseBindingFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        ViewDataBinding dataBinding = DataBindingUtil.inflate(
                inflater,
                setLayout(),
                container,
                false
        );
        onCreateBinding(dataBinding);

        View rootView = dataBinding.getRoot();
        rootView.setOnTouchListener((v, event) -> {
            v.setClickable(true);
            v.setFocusable(true);
            return false;
        });

        return rootView;
    }

    protected abstract void onCreateBinding(ViewDataBinding dataBinding);
}
