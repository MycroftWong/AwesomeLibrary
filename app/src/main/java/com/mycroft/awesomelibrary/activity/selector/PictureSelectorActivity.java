package com.mycroft.awesomelibrary.activity.selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.fresco.helper.photoview.PhotoX;
import com.facebook.fresco.helper.photoview.entity.PhotoInfo;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;
import com.winfo.photoselector.PhotoSelector;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片选择器
 *
 * @author mycroft
 */
public class PictureSelectorActivity extends BaseCommonComponentActivity {

    private static final int REQUEST_SELECTOR = 0x110;

    private static final int MAX_IMAGE_COUNT = 9;

    @Override
    protected int getResId() {
        return R.layout.activity_picture_selector;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DraggableImageAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ButterKnife.bind(this);

        mAdapter = new DraggableImageAdapter(mImages, mItemClickListener);
        recyclerView.setAdapter(mAdapter);
        DraggableImageItemTouchHelperCallback callback = new DraggableImageItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private final ArrayList<String> mImages = new ArrayList<>();

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    private final OnItemActionListener mItemClickListener = new OnItemActionListener() {
        @Override
        public void onItemClicked(int pos) {
            // 展示图片
            ArrayList<PhotoInfo> photos = new ArrayList<>(mImages.size());
            for (String item : mImages) {
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.originalUrl = item;
                photos.add(photoInfo);
            }
            PhotoX.with(PictureSelectorActivity.this)
                    .setCurrentPosition(pos)
                    .setPhotoList(photos)
                    .start();
        }

        @Override
        public void onItemLongClicked(DraggableImageAdapter.PostViewHolder viewHolder) {
            mItemTouchHelper.startDrag(viewHolder);
        }

        @Override
        public void onItemCloseClicked(int pos) {
            mAdapter.onItemRemove(pos);
        }

        @Override
        public void onPlusClicked() {
            selectPictures();
        }
    };

    private void selectPictures() {
        //多选(最多9张)
        PhotoSelector.builder()
                .setShowCamera(true)
                .setMaxSelectCount(MAX_IMAGE_COUNT)
                .setSelected(mImages)
                .setGridColumnCount(3)
                .setMaterialDesign(true)
                .setToolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setBottomBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .start(this, REQUEST_SELECTOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECTOR && resultCode == Activity.RESULT_OK) {
            mImages.clear();
            ArrayList<String> selectedImages = data.getStringArrayListExtra(PhotoSelector.SELECT_RESULT);
            mImages.addAll(selectedImages);
            mAdapter.notifyDataSetChanged();
        }
    }
}
