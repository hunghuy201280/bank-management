package com.example.bankmanagement.base.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sInstance;
    private Map<String, ViewModel> hashMapViewModel = new HashMap<>();

    public static ViewModelFactory getInstance() {
        if (sInstance == null) {
            synchronized (ViewModelFactory.class) {
                if (sInstance == null) {
                    sInstance = new ViewModelFactory();
                }
            }
        }
        return sInstance;
    }

    void addViewModel(String key, ViewModel viewModel) {
        hashMapViewModel.put(key, viewModel);
    }

    ViewModel getViewModel(String key) {
        return hashMapViewModel.get(key);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(modelClass)) {
            String key = modelClass.getName();
            try {
                if (hashMapViewModel.containsKey(key)) {
                    return (T) getViewModel(key);
                } else {
                    T t = null;
                    t = modelClass.newInstance();

                    addViewModel(key, t);
                    return (T) getViewModel(key);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}