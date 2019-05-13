package com.mycroft.awesomelibrary.activity.drag;

import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.ImageLoader;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;
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
 * 拖动图片展示
 *
 * @author mycroft
 */
public class DragDraweeActivity extends BaseCommonComponentActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private final ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_drag_drawee;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        super.initFields(savedInstanceState);
        mImageUrls.addAll(Utils.getSampleImages());
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ButterKnife.bind(this);
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

    private void showPhoto(View view, int position) {
        mSharedPosition = position;

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.share_drawee_name));
        Intent intent = DraweePreviewActivity.getIntent(this, mImageUrls, position);
        startActivity(intent, options.toBundle());
    }
}
