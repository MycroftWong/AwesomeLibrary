package com.mycroft.awesomelibrary.activity.decoration;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.itemdecoration.GridOffsetsItemDecoration;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * offset item decoration, 偏移的距离中间没有任何修饰
 * @author mycroft
 */
public class OffsetsActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getResId() {
        return R.layout.activity_offsets;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.Adapter adapter = Utils.getImageSampleAdapter();
        recyclerView.setAdapter(adapter);

        GridOffsetsItemDecoration gridOffsetsItemDecoration = new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL);
        gridOffsetsItemDecoration.registerTypeDrawable(0, new GridOffsetsItemDecoration.OffsetsCreator() {
            @Override
            public int createVertical(RecyclerView recyclerView, int i) {
                return 10;
            }

            @Override
            public int createHorizontal(RecyclerView recyclerView, int i) {
                return 10;
            }
        });
        recyclerView.addItemDecoration(gridOffsetsItemDecoration);
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
