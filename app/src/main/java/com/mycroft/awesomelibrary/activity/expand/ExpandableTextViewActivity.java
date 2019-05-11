package com.mycroft.awesomelibrary.activity.expand;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文字伸缩
 *
 * @author mycroft
 */
public class ExpandableTextViewActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.expandable_text_view)
    ExpandableTextView expandableTextView;

    @Override
    protected int getResId() {
        return R.layout.activity_expandable_text_view;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expandableTextView.setText(getString(R.string.expandable_text));
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
