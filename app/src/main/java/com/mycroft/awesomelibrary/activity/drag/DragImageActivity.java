package com.mycroft.awesomelibrary.activity.drag;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.SharedElementCallback;
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
 * 可以实现，还有诸多问题
 * <p>
 * 1. 如果只是{@link RecyclerView}的child 转换，那么返回时会出现原child view间断空白的情况，解决方案：使用tmp view代替
 * 2. 如何使用 tmp view, 估计得进出时都使用tmp view代替
 * <p>
 * <p>
 * 实际使用感受：不适合RecyclerView不全部显示在屏幕上的情况，使用ViewPager展示回来之后，需要保证所有的view存在，才不会出现回来时找不到对应view的情况
 *
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

        adapter.setOnItemClickListener((a, view, position) -> showPhoto2(view, position));
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.put(getString(R.string.share_image_name), mTmpImageView);
            }

            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                mTmpImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
//                removeTmpView();
                mTmpImageView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        mTmpImageView = null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Integer position) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);

        if (viewHolder == null) {
            return;
        }
        if (mTmpImageView == null) {
            initTmpView(viewHolder.itemView);
        } else {
            updateTmpView(viewHolder.itemView);
        }
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

    private void removeTmpView() {
        if (mTmpImageView != null) {
            ((ViewGroup) getWindow().getDecorView()).removeView(mTmpImageView);
            mTmpImageView = null;
        }
    }

    /**
     * 根据指定的view 初始化view
     *
     * @param view 指定的view
     */
    private void initTmpView(View view) {
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
    }

    /**
     * 根据指定的view更新位置
     *
     * @param view 指定的view
     */
    private void updateTmpView(View view) {
        if (mTmpImageView != null) {
            BitmapDrawable drawable = (BitmapDrawable) mTmpImageView.getDrawable();
            drawable.getBitmap().recycle();
        }

        Bitmap bitmap = getCacheBitmapFromView(view);
        mTmpImageView.setImageBitmap(bitmap);
        mTmpImageView.setTransitionName(getString(R.string.share_image_name));

        int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mTmpImageView.getLayoutParams();
        lp.leftMargin = xy[0];
        lp.topMargin = xy[1];

        getWindow().getDecorView().requestLayout();
    }

    private void showPhoto2(View view, int position) {
        initTmpView(view);
        mTmpImageView.post(() -> showPhoto(mTmpImageView, position));
    }

    /**
     * 启动显示
     *
     * @param view
     * @param position
     */
    private void showPhoto(View view, int position) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.share_image_name));
        Intent intent = ImagePreviewActivity.getIntent(this, mImageUrls, position);
        startActivity(intent, options.toBundle());
    }

    private Bitmap getCacheBitmapFromView(View view) {
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(b);
        view.draw(canvas);
        return b;
    }
}
