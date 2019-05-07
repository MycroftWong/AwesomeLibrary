package com.mycroft.awesomelibrary.activity.drag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.mycroft.awesomelibrary.R;

import java.util.List;

/**
 * @author mycroft
 */
public class ImagePagerAdapter extends PagerAdapter {

    private final List<String> mImageUrls;

    public ImagePagerAdapter(List<String> imageUrls) {
        mImageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    private LayoutInflater mLayoutInflater;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(container.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_image_preview, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(imageView).load(mImageUrls.get(position)).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
