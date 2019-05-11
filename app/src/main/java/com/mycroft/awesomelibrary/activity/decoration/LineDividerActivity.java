package com.mycroft.awesomelibrary.activity.decoration;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.itemdecoration.LinearDividerItemDecoration;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author mycroft
 */
public class LineDividerActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getResId() {
        return R.layout.activity_line_divider;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    private static final int DEFAULT_VIEW_TYPE = -0xff;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setAdapter(Utils.getImageSampleAdapter());

        LinearDividerItemDecoration linearDividerItemDecoration = new LinearDividerItemDecoration(this, LinearDividerItemDecoration.LINEAR_DIVIDER_VERTICAL);
        linearDividerItemDecoration.registerTypeDrawable(DEFAULT_VIEW_TYPE, (recyclerView, i) ->
                ContextCompat.getDrawable(this, R.drawable.line_divider));

        recyclerView.addItemDecoration(linearDividerItemDecoration);
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
