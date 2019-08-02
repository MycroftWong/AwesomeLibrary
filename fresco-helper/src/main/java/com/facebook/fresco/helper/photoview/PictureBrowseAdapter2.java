package com.facebook.fresco.helper.photoview;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.fresco.helper.LargePhotoView;
import com.facebook.fresco.helper.Phoenix;
import com.facebook.fresco.helper.R;
import com.facebook.fresco.helper.photoview.entity.PhotoInfo;
import com.facebook.fresco.helper.photoview.loading.LoadingProgressBarView;
import com.facebook.fresco.helper.photoview.photodraweeview.OnPhotoTapListener;
import com.facebook.fresco.helper.photoview.photodraweeview.PhotoDraweeView;
import com.facebook.fresco.helper.utils.DensityUtil;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author android_ls
 * @date 16/9/13
 */
public class PictureBrowseAdapter2 extends PagerAdapter {

    private ArrayList<Uri> mItems;
    private OnPhotoTapListener mOnPhotoTapListener;
    private View.OnLongClickListener mOnLongClickListener;

    private int widthPixels;
    private int heightPixels;
    private long mMaxValue = 10000;

    public PictureBrowseAdapter2(Context context, ArrayList<Uri> items,
                                 OnPhotoTapListener photoTapListener, View.OnLongClickListener onLongClickListener) {
        mItems = items;
        mOnPhotoTapListener = photoTapListener;
        mOnLongClickListener = onLongClickListener;
        widthPixels = DensityUtil.getDisplayWidth(context);
        heightPixels = DensityUtil.getDisplayHeight(context);
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull final ViewGroup container, int position) {
        final Uri photoInfo = mItems.get(position);

        View contentView = createPhotoDraweeView(container.getContext(), photoInfo);
        container.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return contentView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (mItems != null && mItems.size() > 0 && position < mItems.size()) {
            Uri photoInfo = mItems.get(position);

            evictFromMemoryCache(photoInfo);
        }
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private View createPhotoDraweeView(Context context, final Uri uri) {
        View contentView = View.inflate(context, R.layout.picture_browse_item, null);

        final LoadingProgressBarView progressBarView = contentView.findViewById(R.id.progress_view);
        progressBarView.setProgress(0);
        progressBarView.setText(String.format(Locale.getDefault(), "%d%%", 0));
        progressBarView.setVisibility(View.VISIBLE);

        final PhotoDraweeView photoDraweeView = contentView.findViewById(R.id.photo_drawee_view);
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();

        controller.setUri(uri);

        GenericDraweeHierarchy hierarchy = photoDraweeView.getHierarchy();
        hierarchy.setProgressBarImage(new ProgressBarDrawable() {
            @Override
            protected boolean onLevelChange(int level) {
                int progress = (int) (((double) level / mMaxValue) * 100);
                progressBarView.setProgress(progress);
                progressBarView.setText(String.format(Locale.getDefault(), "%d%%", progress));
                if (progress == 100) {
                    progressBarView.setVisibility(View.GONE);
                }
                return super.onLevelChange(progress);
            }
        });

        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                super.onIntermediateImageSet(id, imageInfo);
            }
        });
        photoDraweeView.setController(controller.build());

        photoDraweeView.setOnPhotoTapListener((view, x, y) -> {
            if (mOnPhotoTapListener != null) {
                mOnPhotoTapListener.onPhotoTap(view, x, y);
            }
        });

        if (mOnLongClickListener != null) {
            photoDraweeView.setOnLongClickListener(mOnLongClickListener);
        }

        photoDraweeView.setOnViewTapListener((view, x, y) -> {
            if (mOnPhotoTapListener != null) {
                mOnPhotoTapListener.onPhotoTap(view, x, y);
            }
        });
        return contentView;
    }

    private View createLargePhotoView(Context context, final PhotoInfo photoInfo) {
        View contentView = View.inflate(context, R.layout.big_image_item, null);
        final LoadingProgressBarView progressBarView = contentView.findViewById(R.id.progress_view);
        progressBarView.setProgress(0);
        progressBarView.setText(String.format(Locale.getDefault(), "%d%%", 0));
        progressBarView.setVisibility(View.VISIBLE);

        final LargePhotoView imageView = contentView.findViewById(R.id.photo_view);
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageView.setMinScale(1.0f);
        imageView.setMaxScale(2.0f);
        imageView.setOnProgressListener(progress -> {
            progressBarView.setProgress(progress);
            progressBarView.setText(String.format(Locale.getDefault(), "%d%%", progress));
            if (progress == 100) {
                progressBarView.setVisibility(View.GONE);
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (imageView.isReady()) {
                            PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());

                            if (mOnPhotoTapListener != null) {
                                mOnPhotoTapListener.onPhotoTap(imageView, (int) sCoord.x, ((int) sCoord.y));
                            }
                        }
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        if (imageView.isReady()) {
                            PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());

                            if (mOnLongClickListener != null) {
                                mOnLongClickListener.onLongClick(imageView);
                            }
                        } else {
                        }
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (imageView.isReady()) {
                            PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());
                        } else {
                        }
                        return false;
                    }
                });

        imageView.setOnTouchListener((view, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));

        // 加载网络的
        String fileCacheDir = context.getCacheDir().getAbsolutePath();
        Phoenix.with(imageView)
                .setDiskCacheDir(fileCacheDir)
                .load(photoInfo.originalUrl);

        return contentView;
    }

    private void evictFromMemoryCache(Uri photoInfo) {
        if (photoInfo != null) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            if (imagePipeline.isInBitmapMemoryCache(photoInfo)) {
                imagePipeline.evictFromMemoryCache(photoInfo);
            }
        }
    }

    private boolean isBigImage(PhotoInfo photoInfo) {
        return (photoInfo.width > 2 * widthPixels || photoInfo.height > 2 * heightPixels);
    }

    public void recycler() {
        mOnPhotoTapListener = null;
        mOnLongClickListener = null;
    }

}