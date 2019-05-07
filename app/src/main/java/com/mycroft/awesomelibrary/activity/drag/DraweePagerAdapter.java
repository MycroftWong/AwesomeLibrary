package com.mycroft.awesomelibrary.activity.drag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.ImageLoader;
import com.mycroft.awesomelibrary.R;

import java.util.List;

/**
 * @author mycroft
 */
public class DraweePagerAdapter extends PagerAdapter {

    private final List<String> mImageUrls;

    public DraweePagerAdapter(List<String> imageUrls) {
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
        View view = mLayoutInflater.inflate(R.layout.item_picture_preview, container, true);
        SimpleDraweeView draweeView = view.findViewById(R.id.draweeView);
        ImageLoader.loadImage(draweeView, mImageUrls.get(position));
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
