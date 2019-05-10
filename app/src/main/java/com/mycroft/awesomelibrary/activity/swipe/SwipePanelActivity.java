package com.mycroft.awesomelibrary.activity.swipe;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.blankj.swipepanel.SwipePanel;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 侧滑返回
 *
 * @author mycroft
 */
public class SwipePanelActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipePanel)
    SwipePanel swipePanel;

    @Override
    protected int getResId() {
        return R.layout.activity_swipe_panel;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipePanel.setOnFullSwipeListener(direction -> finish());
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
