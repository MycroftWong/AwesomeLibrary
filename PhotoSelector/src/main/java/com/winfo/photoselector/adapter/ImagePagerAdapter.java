package com.winfo.photoselector.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.winfo.photoselector.R;
import com.winfo.photoselector.entity.Image;

import java.io.File;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Image> mImgList;
    private OnItemClickListener mListener;

    public ImagePagerAdapter(Context context, List<Image> imgList) {
        this.mContext = context;
//        createImageViews();
        mImgList = imgList;
    }

    @Override
    public int getCount() {
        return mImgList == null ? 0 : mImgList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_view_pager, container, false);
        final ImageView imageView = itemView.findViewById(R.id.iv_pager);

        final String path = mImgList.get(position).getPath();

        final Uri uri;
        if (path.startsWith("http")) {
            uri = Uri.parse(path);
        } else {
            uri = Uri.fromFile(new File(path));
        }
        final Image image = mImgList.get(position);
        Glide.with(mContext).setDefaultRequestOptions(new RequestOptions()
                .dontTransform()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_img_load_fail)
                .override(800, 1200))
                .load(uri)
                .into(imageView);

        imageView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position, image);
            }
        });
        container.addView(itemView);

        return itemView;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }

    public interface OnItemClickListener {
        /**
         * 选择监听器
         *
         * @param position
         * @param image
         */
        void onItemClick(int position, Image image);
    }
}
