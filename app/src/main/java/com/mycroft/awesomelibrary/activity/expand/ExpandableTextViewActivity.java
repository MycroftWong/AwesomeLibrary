package com.mycroft.awesomelibrary.activity.expand;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文字伸缩
 *
 * @author mycroft
 */
public class ExpandableTextViewActivity extends BaseCommonComponentActivity {

    @BindView(R.id.expandable_text_view)
    ExpandableTextView expandableTextView;

    @Override
    protected int getResId() {
        return R.layout.activity_expandable_text_view;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        super.initFields(savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);

        expandableTextView.setText(getString(R.string.expandable_text));
    }

    @Override
    protected void loadData() {

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
