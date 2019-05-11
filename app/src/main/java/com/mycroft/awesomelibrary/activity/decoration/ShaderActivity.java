package com.mycroft.awesomelibrary.activity.decoration;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.dinuscxj.itemdecoration.ShaderItemDecoration;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * shadow item decoration
 *
 * @author mycroft
 */
public class ShaderActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getResId() {
        return R.layout.activity_shader;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setAdapter(Utils.getImageSampleAdapter());
        ShaderItemDecoration shaderItemDecoration = new ShaderItemDecoration(this,
                ShaderItemDecoration.SHADER_TOP | ShaderItemDecoration.SHADER_BOTTOM);

        int shaderDistance = SizeUtils.dp2px(48);
        shaderItemDecoration.setShaderBottomDistance(shaderDistance);
        shaderItemDecoration.setShaderTopDistance(shaderDistance);
        recyclerView.addItemDecoration(shaderItemDecoration);
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
