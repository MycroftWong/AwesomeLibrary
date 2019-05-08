package com.mycroft.awesomelibrary.activity.diooto;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.util.Utils;

import net.moyokoo.diooto.Diooto;
import net.moyokoo.diooto.config.DiootoConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author mycroft
 */
public class DiootoActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getResId() {
        return R.layout.activity_diooto;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_small_picture, Utils.getSampleImages()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.imageView);
                Glide.with(DiootoActivity.this).load(item).into(imageView);
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((a, view, position) -> showPhoto(position));
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

    private void showPhoto(int position) {
        //图片模式
        Diooto diooto = new Diooto(this)
                .urls(Utils.getSampleImages().toArray(new String[]{}))
                //图片或者视频
                .type(DiootoConfig.PHOTO)
                //当前的Activity是否为沉浸式,默认为false
                .immersive(false)
                //点击的位置 如果你的RecyclerView有头部View  则使用 .position(holder.getAdapterPosition(),headSize) headSize为头部布局数量
                .position(position)
                //可以传recylcerview自动识别(需要传在item布局中的viewId)  也可以手动传view数组
                .views(recyclerView, R.id.imageView)
                //设置选择器 默认CircleIndexIndicator  可实现IIndicator接口自定义
//                .setIndicator(new CircleIndexIndicator())
                //设置进度条样式  默认DefaultProgress 可实现IProgress接口自定义
//                .setProgress(new DefaultProgress())
                .start();
    }
}
