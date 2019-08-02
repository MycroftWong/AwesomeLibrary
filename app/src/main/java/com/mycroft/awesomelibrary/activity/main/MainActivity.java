package com.mycroft.awesomelibrary.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.fragment.ComponentsFragment;
import com.mycroft.awesomelibrary.fragment.HelpersFragment;
import com.mycroft.lib.util.FragmentSwitcher;

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

    private FragmentSwitcher fragmentSwitcher;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        fragmentSwitcher = new FragmentSwitcher(getSupportFragmentManager(), R.id.fragmentContainer, this::getItem);
    }

    @Override

    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_components));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_helpers));

        tabLayout.addOnTabSelectedListener(mOnTabSelectedListener);
        mOnTabSelectedListener.onTabSelected(tabLayout.getTabAt(0));
    }

    @Override
    protected void loadData() {

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

    private final TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            fragmentSwitcher.startFragment(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

}
