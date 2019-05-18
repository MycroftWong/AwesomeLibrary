package com.mycroft.awesomelibrary.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.tabs.TabLayout;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.fragment.ComponentsFragment;
import com.mycroft.awesomelibrary.fragment.HelpersFragment;

/**
 * @author mycroft
 */
public class MainActivity extends BaseCommonActivity {

    public static Intent getIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override

    protected void initViews(@Nullable Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);

        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_components));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_helpers));

        tabLayout.addOnTabSelectedListener(mOnTabSelectedListener);
        mOnTabSelectedListener.onTabSelected(tabLayout.getTabAt(0));
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    private Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ComponentsFragment.newInstance();
            case 1:
                return HelpersFragment.newInstance();
            default:
                return ComponentsFragment.newInstance();
        }
    }

    private Fragment mCurrentFragment;

    private final TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            startFragment(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    private void startFragment(int pos) {
        // 下面是一连串的处理fragment切换的代码
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(makeFragmentName(pos));

        if (fragment == null) {
            fragment = getItem(pos);

            if (mCurrentFragment != null) {
                ft.hide(mCurrentFragment);
            }

            ft.add(R.id.fragmentContainer, fragment, makeFragmentName(pos))
                    .setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            mCurrentFragment = fragment;
        } else {
            if (fragment != mCurrentFragment) {
                ft.show(fragment)
                        .setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment)
                            .setMaxLifecycle(mCurrentFragment, Lifecycle.State.STARTED);
                }
                mCurrentFragment = fragment;
            }
        }

        ft.commitAllowingStateLoss();
    }

    private static String makeFragmentName(long id) {
        return "android:switcher:" + R.id.fragmentContainer + ":" + id;
    }
}
