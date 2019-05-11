package com.mycroft.awesomelibrary.activity.decoration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 使用 item decoration
 *
 * @author mycroft
 */
public class ItemDecorationActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getResId() {
        return R.layout.activity_item_decoration;
    }

    private final List<ItemDecorationModel> mItemDecorationModels = new ArrayList<>();

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        mItemDecorationModels.addAll(Arrays.asList(MODELS));
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ItemDecorationAdapter adapter = new ItemDecorationAdapter(mItemDecorationModels);
        adapter.setOnItemClickListener((a, view, position) -> startActivity(new Intent(this, mItemDecorationModels.get(position).klazz)));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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

    static class ItemDecorationAdapter extends BaseQuickAdapter<ItemDecorationModel, BaseViewHolder> {

        ItemDecorationAdapter(@Nullable List<ItemDecorationModel> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ItemDecorationModel item) {
            helper.setText(android.R.id.text1, item.name);
        }
    }

    static class ItemDecorationModel {
        final String name;
        final Class<? extends Activity> klazz;

        ItemDecorationModel(String name, Class<? extends Activity> klazz) {
            this.name = name;
            this.klazz = klazz;
        }
    }

    private static final ItemDecorationModel[] MODELS = {
            new ItemDecorationModel("PinnedHeader", PinnedHeaderActivity.class),
            new ItemDecorationModel("Shader", ShaderActivity.class),
            new ItemDecorationModel("LineDivider", LineDividerActivity.class),
            new ItemDecorationModel("Offset", OffsetsActivity.class)};
}