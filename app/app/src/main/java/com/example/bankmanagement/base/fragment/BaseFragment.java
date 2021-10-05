package com.example.bankmanagement.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;

import com.example.bankmanagement.base.BaseActions;
import com.example.bankmanagement.base.activity.BaseFragmentActivity;
import com.example.bankmanagement.base.navigation.FragmentNavigator;

public abstract class BaseFragment
        extends Fragment implements BaseActions, LifecycleObserver {

    protected View rootView;
    private Context fragmentContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentContext = context;
        getLifecycle().addObserver(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentContext = null;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(fragmentContext)
                .inflate(setLayout(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initValues();
        initViews();
        initActions();
        fetchData();
    }

    protected void initViews() {

    }

    protected void initBackButtons(View... views) {
        for (View view : views) {
            onBackPress();
        }
    }

    protected abstract void fetchData();

    public Context getFragmentContext() {
        return fragmentContext;
    }


    /**
     * Get fragmentNavigator
     */
    public FragmentNavigator getNavigator() {
        return ((BaseFragmentActivity) fragmentContext).getNavigator();
    }

    /**
     * Get active fragment
     *
     * @return
     */
    protected Fragment getActiveFragment() {
        return getNavigator().getActiveFragment();
    }

    /**
     * Set fragment to a Container view
     *
     * @param containerViewId
     * @param fragment
     */
    protected void setFragmentToContainer(@IdRes int containerViewId, @NonNull Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment).commit();
    }

    /**
     * Check if fragment is already in ContainerView then show fragment
     * else add new one to
     *
     * @param containerViewId
     * @param fragment
     */
    protected void showOrAddFragment(
            @IdRes int containerViewId,
            Fragment fragment
    ) {
        showOrAddFragment(null, containerViewId, fragment);
    }

    /**
     * Check if fragment is already in ContainerView then show fragment
     * else add new one to
     *
     * @param containerViewId
     * @param fragment
     */
    protected void showOrAddFragment(
            FragmentManager fragmentManager,
            @IdRes int containerViewId,
            Fragment fragment
    ) {
        if (fragment == null) return;

        if (fragmentManager == null)
            fragmentManager = getChildFragmentManager();

        String fragmentTag = getFragmentTag(fragment);

        Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragmentByTag != null) {
            //if the fragment exists, show it.
            fragmentManager.beginTransaction().show(fragmentByTag).commit();
        } else {
            //if the fragment does not exist, add it to fragment manager.
            fragmentManager.beginTransaction().add(containerViewId, fragment, fragmentTag).commit();
        }
    }

    protected void hideFragment(
            Fragment fragment
    ) {
        if (fragment == null) return;

        FragmentManager fragmentManager = getChildFragmentManager();
        String fragmentTag = getFragmentTag(fragment);

        if (fragmentManager.findFragmentByTag(fragmentTag) != null) {
            //if the other fragment is visible, hide it.
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    @NonNull
    private String getFragmentTag(@NonNull Fragment fragment) {
        return fragment.getClass().getSimpleName();
    }

    /**
     * The extend fragment can overRide this to handle the Activity backPress
     *
     * @return true --> swallow the back press
     * false --> keep it
     */
    public boolean onBackPress() {
        return false;
    }
}
