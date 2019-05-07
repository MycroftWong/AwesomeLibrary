package com.mycroft.awesomelibrary.activity.drag;

import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangqiang
 */
public class DragImageActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getResId() {
        return R.layout.activity_drag_image;
    }

    private final ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        mImageUrls.addAll(Utils.getSampleImages());
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_drag_image, mImageUrls) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.imageView);
                Glide.with(imageView).load(item).into(imageView);
            }
        };

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((a, view, position) -> showPhotoWithNewView(view, position));

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                String sharedName = getString(R.string.share_image_name);
                sharedElements.remove(sharedName);
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(mSharedPosition);

                if (viewHolder != null) {
                    sharedElements.put(getString(R.string.share_image_name), viewHolder.itemView);
                }
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                if (mTmpImageView != null) {
                    ((ViewGroup) getWindow().getDecorView()).removeView(mTmpImageView);
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

    private ImageView mTmpImageView;

    private void showPhotoWithNewView(View view, int position) {
        Bitmap bitmap = getCacheBitmapFromView(view);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        imageView.setTransitionName(getString(R.string.share_image_name));

        int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        lp.leftMargin = xy[0];
        lp.topMargin = xy[1];

        ((FrameLayout) getWindow().getDecorView()).addView(imageView, lp);

        mTmpImageView = imageView;
        showPhoto(imageView, position);
    }

    private void showPhoto(View view, int position) {
        mSharedPosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.share_image_name));
        Intent intent = ImagePreviewActivity.getIntent(this, mImageUrls, position);
        startActivity(intent, options.toBundle());
    }

    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
}
