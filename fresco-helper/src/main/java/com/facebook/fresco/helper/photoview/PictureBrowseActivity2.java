package com.facebook.fresco.helper.photoview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.facebook.fresco.helper.R;
import com.facebook.fresco.helper.photoview.anim.TransitionCompat;
import com.facebook.fresco.helper.photoview.entity.PhotoInfo;
import com.facebook.fresco.helper.photoview.photodraweeview.OnPhotoTapListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author android_ls
 * @date 16/9/13
 */
public class PictureBrowseActivity2 extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, OnPhotoTapListener, View.OnLongClickListener {

    public static Intent getIntent(@NonNull Context context, @NonNull List<Uri> uriList) {
        return getIntent(context, uriList, 0);
    }

    public static Intent getIntent(@NonNull Context context, @NonNull List<Uri> uriList, int currentPosition) {
        Intent intent = new Intent(context, PictureBrowseActivity2.class);
        intent.putExtra(PhotoX.PHOTO_CURRENT_POSITION_KEY, currentPosition);
        intent.putExtra(PhotoX.PHOTO_LIST_KEY, new ArrayList<>(uriList));
        return intent;
    }

    protected int mPhotoIndex;
    protected int mPhotoCount;
    protected PhotoInfo mLookPhoto;

    protected TextView tvPhotoIndex;
    protected MViewPager mViewPager;

    protected PictureBrowseAdapter2 mAdapter;
    protected ArrayList<Uri> mItems;

    protected TransitionCompat mTransitionCompat;
    protected boolean mPhotoOnlyOne;
    protected boolean mIsAnimation;
    protected boolean mLongClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        Intent data = getIntent();
        mItems = data.getParcelableArrayListExtra(PhotoX.PHOTO_LIST_KEY);
        if (mItems == null || mItems.size() == 0) {
            onBackPressed();
            return;
        }

        mPhotoIndex = data.getIntExtra(PhotoX.PHOTO_CURRENT_POSITION_KEY, 0);
        mIsAnimation = data.getBooleanExtra(PhotoX.PHOTO_IS_ANIMATION_KEY, false);
        mPhotoOnlyOne = data.getBooleanExtra(PhotoX.PHOTO_ONLY_ONE_KEY, false);
        mLongClick = data.getBooleanExtra(PhotoX.PHOTO_LONGCLICK_KEY, true);

        setupViews();

        if (mIsAnimation) {
            mTransitionCompat = new TransitionCompat(PictureBrowseActivity2.this);
            mTransitionCompat.setCurrentPosition(mPhotoIndex);
            mTransitionCompat.startTransition();
        }
    }

    protected int getLayoutResId() {
        return R.layout.activity_picture_browse;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mPhotoOnlyOne) {
            return;
        }

        mPhotoIndex = position;
        setPhotoIndex();

        if (mTransitionCompat != null && mIsAnimation) {
            mTransitionCompat.setCurrentPosition(mPhotoIndex);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if (mTransitionCompat != null && mIsAnimation) {
            mTransitionCompat.finishAfterTransition();
        } else {
//            super.onBackPressed();
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        onBackPressed();
    }

    protected void setupViews() {
        mViewPager = findViewById(R.id.vp_picture_browse);
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(this);

        if (mLongClick) {
            mAdapter = new PictureBrowseAdapter2(this, mItems, this, this);
        } else {
            mAdapter = new PictureBrowseAdapter2(this, mItems, this, null);
        }
        mViewPager.setAdapter(mAdapter);

        mPhotoCount = mItems.size();
        setupBottomViews();
        mViewPager.setCurrentItem(mPhotoIndex);
    }

    protected void setupBottomViews() {
        tvPhotoIndex = findViewById(R.id.tv_photo_count);
        if (mPhotoOnlyOne) {
            findViewById(R.id.photo_bottom_view).setVisibility(View.GONE);
            tvPhotoIndex.setVisibility(View.GONE);
        } else {
            setPhotoIndex();
        }
    }

    protected void setPhotoIndex() {
        tvPhotoIndex.setText(String.format(Locale.getDefault(), "%d/%d", mPhotoIndex + 1, mPhotoCount));
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public ArrayList<Uri> getItems() {
        return mItems;
    }

    public Uri getItem(int position) {
        if (mItems != null && mItems.size() > 0) {
            return mItems.get(position);
        }
        return null;
    }

    /**
     * 获取当前PhotoInfo在集合中Position
     *
     * @return
     */
    public int getCurrentPosition() {
        return mPhotoIndex;
    }

    public Uri getCurrentPhotoInfo() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.get(mPhotoIndex);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        if (mLookPhoto != null) {
            mLookPhoto = null;
        }

        if (mItems != null) {
            mItems = null;
        }

        if (mAdapter != null) {
            mAdapter.recycler();
            mAdapter = null;
        }

        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
            mViewPager.setAdapter(null);
            mViewPager = null;
        }

        super.onDestroy();
    }

}
