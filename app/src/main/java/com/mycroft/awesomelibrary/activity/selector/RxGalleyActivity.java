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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

/**
 * 图片选择
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

    private void selectPictures() {
        int size = MAX_IMAGE_COUNT - mImages.size();
        if (size <= 0) {
            return;
        }

        final Set<MediaBean> mediaBeans = new LinkedHashSet<>();
        RxGalleryFinal
                .with(this)
                .image()
                .multiple()
                .maxSize(size)
                .imageLoader(ImageLoaderType.FRESCO)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent multipleResultEvent) {
                        LogUtils.e(GsonUtils.toJson(multipleResultEvent.getResult()));
                        mediaBeans.addAll(multipleResultEvent.getResult());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("complete");
                        for (MediaBean item : mediaBeans) {
                            mImages.add(item.getOriginalPath());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .openGallery();
    }

    @OnClick(R.id.chooseSingleButton)
    public void onViewClicked() {
        final List<ImageCropBean> cropBeans = new ArrayList<>(1);
        RxGalleryFinal
                .with(this)
                .image()
                .radio()
                .crop()
                .cropWithAspectRatio(1f, 1f)
                .imageLoader(ImageLoaderType.FRESCO)
                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent radioResultEvent) {
                        cropBeans.clear();
                        cropBeans.add(radioResultEvent.getResult());
                    }

                    @Override
                    public void onComplete() {
                        if (!cropBeans.isEmpty()) {
                            ImageLoader.loadImage(draweeView, cropBeans.get(0).getCropPath());
                        }
                    }
                })
                .openGallery();
    }
}
