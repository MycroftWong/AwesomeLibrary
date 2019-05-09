package com.mycroft.awesomelibrary.activity.drag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.viewpager.widget.ViewPager;

import com.dragclosehelper.library.DragCloseHelper;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangqiang
 */
public class ImagePreviewActivity extends BaseCommonActivity {

    private static final String EXTRA_PICTURE_URL = "picture_url.extra";
    private static final String EXTRA_CURRENT_POSITION = "current_position.extra";

    public static Intent getIntent(@NonNull Context context, @NonNull ArrayList<String> imageUrls, int currPos) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra(EXTRA_PICTURE_URL, imageUrls);
        intent.putExtra(EXTRA_CURRENT_POSITION, currPos);
        return intent;
    }

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getResId() {
        return R.layout.activity_image_preview;
    }

    private ArrayList<String> mPictureUrls;

    private DragCloseHelper mDragCloseHelper;

    private int mCurrentPosition;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mPictureUrls = getIntent().getStringArrayListExtra(EXTRA_PICTURE_URL);
            mCurrentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);
        } else {
            mPictureUrls = savedInstanceState.getStringArrayList(EXTRA_PICTURE_URL);
            mCurrentPosition = savedInstanceState.getInt(EXTRA_CURRENT_POSITION);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(EXTRA_PICTURE_URL, mPictureUrls);
        outState.putInt(EXTRA_CURRENT_POSITION, viewPager.getCurrentItem());
    }

    private boolean mScrolling = false;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);

        viewPager.setAdapter(new ImagePagerAdapter(mPictureUrls));
        viewPager.setCurrentItem(mCurrentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mScrolling = state != 0;
            }
        });
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (sharedElements.isEmpty()) {
                    sharedElements.put(getString(R.string.share_image_name), viewPager);
                }
            }
        });
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        mDragCloseHelper = new DragCloseHelper(this);
        mDragCloseHelper.setShareElementMode(true);
        mDragCloseHelper.setDragCloseViews(container, viewPager);
        mDragCloseHelper.setDragCloseListener(new DragCloseHelper.DragCloseListener() {
            @Override
            public boolean intercept() {
                //默认false 不拦截 如果图片是放大状态，或者处于滑动返回状态，需要拦截
                return mScrolling;
            }

            @Override
            public void dragStart() {
                //拖拽开始。可以在此额外处理一些逻辑
                viewPager.setTransitionName(getString(R.string.share_image_name));
                EventBus.getDefault().post(mCurrentPosition);
            }

            @Override
            public void dragging(float percent) {
                //拖拽中。percent当前的进度，取值0-1，可以在此额外处理一些逻辑
            }

            @Override
            public void dragCancel() {
                //拖拽取消，会自动复原。可以在此额外处理一些逻辑
            }

            @Override
            public void dragClose(boolean isShareElementMode) {
                //拖拽关闭，如果是共享元素的页面，需要执行activity的onBackPressed方法，注意如果使用finish方法，则返回的时候没有共享元素的返回动画
                if (isShareElementMode) {
                    getWindow().getDecorView().post(() -> onBackPressed());
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mDragCloseHelper.handleEvent(event)) {
            return true;
        } else {
            return super.dispatchTouchEvent(event);
        }
    }
}
