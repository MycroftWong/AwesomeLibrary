package com.mycroft.awesomelibrary.activity.drag;

import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.ImageLoader;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 拖动图片展示
 *
 * @author mycroft
 */
public class DragImageActivity extends BaseCommonActivity {

    private final String[] normalImageUlr = new String[]{
            "http://img1.juimg.com/140908/330608-140ZP1531651.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa9a8c1f06.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa463997e3.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa1efad0f0.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf9e907373c.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf96aa9c275.png",
    };

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private final ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_drag_image;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        mImageUrls.addAll(Arrays.asList(normalImageUlr));
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_drag_drawee, mImageUrls) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                SimpleDraweeView draweeView = helper.getView(R.id.draweeView);
                ImageLoader.loadImage(draweeView, item);
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((a, view, position) -> showPhoto(view, position));

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                String sharedName = getString(R.string.share_drawee_name);
                sharedElements.remove(sharedName);
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(mSharedPosition);

                if (viewHolder != null) {
                    sharedElements.put(getString(R.string.share_drawee_name), viewHolder.itemView);
                }
            }
        });

    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private int mSharedPosition;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Integer position) {
        mSharedPosition = position;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPhoto(View view, int position) {
        mSharedPosition = position;

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.share_drawee_name));
        Intent intent = PicturePreviewActivity.getIntent(this, mImageUrls, position);
        startActivity(intent, options.toBundle());
    }
}
