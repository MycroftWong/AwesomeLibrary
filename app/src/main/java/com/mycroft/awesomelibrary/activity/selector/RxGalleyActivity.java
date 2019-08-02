package com.mycroft.awesomelibrary.activity.selector;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.ImageLoader;
import com.facebook.fresco.helper.photoview.PhotoX;
import com.facebook.fresco.helper.photoview.entity.PhotoInfo;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.ImageCropBean;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;

/**
 * 图片选择，不好用的库，这样使用rx不如正常启动，然后在onActivityResult中处理结果
 *
 * @author wangqiang
 */
public class RxGalleyActivity extends BaseCommonComponentActivity {

    private static final int MAX_IMAGE_COUNT = 9;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.draweeView)
    SimpleDraweeView draweeView;

    @Override
    protected int getResId() {
        return R.layout.activity_rx_galley;
    }

    private DraggableImageAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);

        mAdapter = new DraggableImageAdapter(mImages, mItemClickListener);
        recyclerView.setAdapter(mAdapter);
        DraggableImageItemTouchHelperCallback callback = new DraggableImageItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private final ArrayList<String> mImages = new ArrayList<>();

    @Override
    protected void loadData() {
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
            PhotoX.with(RxGalleyActivity.this)
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

    private RxGalleryFinalApi mRxGalleryFinalApi;

    private void selectPictures() {
        int size = MAX_IMAGE_COUNT - mImages.size();
        if (size <= 0) {
            return;
        }

        RxGalleryFinal.with(this)
                .imageLoader(ImageLoaderType.FRESCO)
                .image()
                .multiple()
                .maxSize(size)
                .hideCamera()
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        LogUtils.e(GsonUtils.toJson(imageMultipleResultEvent.getResult()));
                        for (MediaBean item : imageMultipleResultEvent.getResult()) {
                            mImages.add(item.getOriginalPath());
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("onComplete");
                    }
                })
                .openGallery();

        RxGalleryListener.getInstance()
                .setMultiImageCheckedListener(new IMultiImageCheckedListener() {
                    @Override
                    public void selectedImg(Object t, boolean isChecked) {
                        LogUtils.e(t.getClass().getName());
                    }

                    @Override
                    public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                        LogUtils.e(GsonUtils.toJson(t));
                    }
                });
    }

    @OnClick(R.id.chooseSingleButton)
    public void onViewClicked() {
        // 因为glide版本冲突，所以无法使用，有bug
        final List<ImageCropBean> cropBeans = new ArrayList<>(1);
        RxGalleryFinalApi
                .onCrop(true)
                .openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent radioResultEvent) throws Exception {
                        LogUtils.e(GsonUtils.toJson(radioResultEvent.getResult()));
                        cropBeans.clear();
                        cropBeans.add(radioResultEvent.getResult());
                    }
                })
                .onCropImageResult(new IRadioImageCheckedListener() {
                    @Override
                    public void cropAfter(Object t) {
                        LogUtils.e(GsonUtils.toJson(t));

                        if (!cropBeans.isEmpty()) {
                            ImageLoader.loadImage(draweeView, cropBeans.get(0).getCropPath());
                        }
                    }

                    @Override
                    public boolean isActivityFinish() {
                        LogUtils.e("isActivityFinish");
                        return true;
                    }
                });
    }
}
