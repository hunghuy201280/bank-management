package com.example.bankmanagement.base.navigation;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.bankmanagement.base.fragment.BaseFragment;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FragmentNavigator {

    @NonNull
    private final FragmentManager mFragmentManager;
    @IdRes
    private final int mDefaultContainer;
    private onStackChanged onStackChanged;
    private String mRootFragmentTag;

    /**
     * This constructor should be only called once per
     *
     * @param fragmentManager  Your FragmentManger
     * @param defaultContainer Your container id where the fragments should be placed
     */
    public FragmentNavigator(@NonNull final FragmentManager fragmentManager
            , @IdRes final int defaultContainer) {
        mFragmentManager = fragmentManager;
        mDefaultContainer = defaultContainer;
        mFragmentManager.addOnBackStackChangedListener(() -> {
            if (onStackChanged != null) {
                onStackChanged.onChanged(getActiveFragment());
            }
        });
    }

    public void setOnStackChanged(FragmentNavigator.onStackChanged onStackChanged) {
        this.onStackChanged = onStackChanged;
    }

    /**
     * @return the current active fragment. If no fragment is active it return null.
     */
    public Fragment getActiveFragment() {
        String tag;
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            tag = mRootFragmentTag;
        } else {
            tag = mFragmentManager
                    .getBackStackEntryAt(mFragmentManager.getBackStackEntryCount() - 1).getName();
        }
        return mFragmentManager.findFragmentByTag(tag);
    }

    public void goTo(final Fragment fragment) {
        goTo(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    /**
     * Pushes the fragment, and add it to the history (BackStack) if you have set a default animation
     * it will be added to the transaction.
     *
     * @param fragment The fragment which will be added
     */
    public void goTo(final Fragment fragment, int transition) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.addToBackStack(getTag(fragment));
        transaction.add(mDefaultContainer, fragment, getTag(fragment));
        if (transition != FragmentTransaction.TRANSIT_NONE)
            transaction.setTransition(transition);
        transaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    /**
     * Check the Fragment in backStack first if it exist then go back to that fragment
     *
     * @param fragment
     */
    public void goToCheckBackStack(final Fragment fragment, int transition) {
        if (!goBackTo(fragment)) {
            goTo(fragment, transition);
        }
    }

    public void goToCheckBackStack(final Fragment fragment) {
        goToCheckBackStack(fragment, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }


    /**
     * Pushes the fragment, and add it to the history (BackStack) if you have set a default animation
     * it will be added to the transaction. (with arguments)
     *
     * @param T      fragment class
     * @param bundle fragment arguments
     */
    public <T extends Fragment> void goTo(Class<T> T, Bundle bundle) {
        T t = null;
        try {
            t = T.newInstance();
            t.setArguments(bundle);
            goTo(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goTo(final Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        goTo(fragment);
    }

    public void goToWithAnimation(final Fragment fragment, final int id_enter, int id_exit) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(id_enter, id_enter, id_exit, id_exit);
        transaction.addToBackStack(getTag(fragment));
        transaction.add(mDefaultContainer, fragment, getTag(fragment));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    /**
     * This is just a helper method which returns the simple name of the fragment.
     *
     * @param fragment That get added to the history (BackStack)
     * @return The simple name of the given fragment
     */
    private String getTag(final Fragment fragment) {
        return fragment.getClass().getSimpleName();
    }

    /**
     * Get root fragment
     *
     * @return
     */
    public Fragment getRootFragment() {
        return mFragmentManager.findFragmentByTag(mRootFragmentTag);
    }

    /**
     * Set the new root fragment. If there is any entry in the history (BackStack) it will be
     * deleted.
     *
     * @param rootFragment The new root fragment
     */
    public void setRootFragment(final Fragment rootFragment) {
        if (rootFragment == null) return;

        if (getSize() > 0) {
            this.clearHistory();
        }
        mRootFragmentTag = getTag(rootFragment);
        replaceFragment(rootFragment);
    }

    /**
     * Replace the current fragment with the given one, without to add it to backstack. So when the
     * users navigates away from the given fragment it will not appaer in the history.
     *
     * @param fragment The new fragment
     */
    private void replaceFragment(final Fragment fragment) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mFragmentManager.beginTransaction()
                    .replace(mDefaultContainer, fragment, getTag(fragment))
                    .commitNow();
        }else{
            mFragmentManager.beginTransaction()
                    .replace(mDefaultContainer, fragment, getTag(fragment))
                    .commit();
            mFragmentManager.executePendingTransactions();
        }
    }

    public void replaceFragmentNew(final Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(mDefaultContainer, fragment, getTag(fragment))
                .commit();
        mRootFragmentTag = getTag(fragment);
        mFragmentManager.executePendingTransactions();
    }

    /**
     * Goes one entry back in the history
     */
    public void goOneBack() {
        mFragmentManager.popBackStackImmediate();
    }

    /**
     * Goes one entry back in the history
     */
    public void goOneBackTo(String tagFragment) {
        int i = getSize() - 1;
        while (getSize() >= 1) {
            if (!Objects.requireNonNull(mFragmentManager.getBackStackEntryAt(i).getName())
                    .equals(tagFragment)) {
                goOneBack();
                i--;
            } else {
                return;
            }
        }
    }

    /**
     * Go back to @fragment that in the backStack
     *
     * @param fragment
     * @return true -> DID return to the fragment
     */
    public boolean goBackTo(Fragment fragment) {
        if (contain(fragment)) {
            while (getActiveFragment() != fragment) {
                goOneBack();
            }
            return true;
        }
        return false;
    }

    /**
     * Go back to @fragment with type "className" that in the backStack
     *
     * @param className
     * @return true -> DID return to the fragment
     */
    public <T extends Fragment> boolean goBackTo(Class<T> className) {
        if (contain(className)) {
            while (!className.isInstance(getActiveFragment())) {
                goOneBack();
            }
            return true;
        }
        return false;
    }

    /**
     * Go back to @fragment with type "className" that in the backStack and go one back
     */
    public <T extends Fragment> boolean goBackToAndOneBack(Class<T> className) {
        boolean goBackTo = goBackTo(className);
        if (goBackTo) {
            goOneBack();
        }
        return goBackTo;
    }

    /**
     * Check if the fragment class is existed
     *
     * @param className
     * @return
     */
    public <T extends Fragment> boolean contain(Class<T> className) {
        for (Fragment addFragment : mFragmentManager.getFragments()) {
            if (className.isInstance(addFragment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * getFragment by class
     *
     * @param className
     * @param <T>
     * @return
     */
    public <T extends Fragment> Fragment getFragment(Class<T> className) {
        for (Fragment addFragment : mFragmentManager.getFragments()) {
            if (className.isInstance(addFragment)) {
                return addFragment;
            }
        }
        return null;
    }


    /**
     * Get previous fragment on back stack
     *
     * @return
     */
    public Fragment getPreviousFragment() {
        return mFragmentManager.getFragments().get(mFragmentManager.getBackStackEntryCount() - 1);
    }

    /**
     * @return The current size of the history (BackStack)
     */
    public int getSize() {
        return mFragmentManager.getBackStackEntryCount();
    }

    /**
     * Goes the whole history back until to the first fragment in the history. It would be the same if
     * the user would click so many times the back button until he reach the first fragment of the
     * app.
     */
    public void goToRoot() {
        while (getSize() >= 1) {
            goOneBack();
        }
    }

    public void goToRoot(RootFragmentListener listener) {
        while (getSize() >= 1) {

            if (getSize() <= 1) {
                listener.onAlreadyAtRootFragment(getRootFragment());
                break;
            }

            goOneBack();
        }
    }

    /**
     * Clears the whole history so it will no BackStack entry there any more.
     */
    private void clearHistory() {
        try {
            //noinspection StatementWithEmptyBody - it works as designed
            while (mFragmentManager.popBackStackImmediate()) {
            }
        } catch (IllegalStateException ignored) {

        }
    }

    /**
     * Check if the fragment is existed
     *
     * @param fragment
     * @return
     */
    public boolean contain(@NotNull Fragment fragment) {
        for (Fragment addFragment : mFragmentManager.getFragments()) {
            if (addFragment == fragment) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the index of fragment in backStack
     *
     * @param fragment
     * @return if not exist return -1
     */
    public int indexOf(@NotNull BaseFragment fragment) {
        int i = 0;
        for (Fragment existedFragment : mFragmentManager.getFragments()) {
            if (existedFragment == fragment) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public interface onStackChanged {

        void onChanged(Fragment fragment);
    }

    public interface RootFragmentListener {
        void onAlreadyAtRootFragment(Fragment rootFragment);
    }
}
