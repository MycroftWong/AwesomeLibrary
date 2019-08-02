package com.mycroft.awesomelibrary.activity.swipe;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.blankj.swipepanel.SwipePanel;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 侧滑返回
 *
 * @author mycroft
 */
public class SwipePanelActivity extends BaseCommonComponentActivity {

    @BindView(R.id.swipePanel)
    SwipePanel swipePanel;

    @Override
    protected int getResId() {
        return R.layout.activity_swipe_panel;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        super.initFields(savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);

        swipePanel.setOnFullSwipeListener(direction -> finish());
    }

    @Override
    protected void loadData() {

    }
}
