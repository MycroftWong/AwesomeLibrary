package com.winfo.photoselector.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.winfo.photoselector.R;
import com.winfo.photoselector.entity.Image;

import java.util.List;

public class BottomPreviewAdapter extends RecyclerView.Adapter<BottomPreviewAdapter.CustomHolder> {

    private Context context;
    private List<Image> imagesList;

    public interface OnItemClickListener {
        void onItemClick(int position, Image image);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnDataChangeFinishListener {
        void changeFinish();
    }

    public OnDataChangeFinishListener onDataChangeFinishListener;

    public void setOnDataChangeFinishListener(OnDataChangeFinishListener onDataChangeFinishListener) {
        this.onDataChangeFinishListener = onDataChangeFinishListener;
    }

    public BottomPreviewAdapter(Context context, List<Image> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomHolder(LayoutInflater.from(context).inflate(R.layout.bootm_preview_item, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final CustomHolder holder, int position) {
        imagesList.get(position).setSelectPosition(position);
        Glide.with(context).load(imagesList.get(holder.getAdapterPosition()).getPath())
//                    .transition(new GenericTransitionOptions<>().transition(R.anim.glide_anim))
//                    .transition(new GenericTransitionOptions<>().transition(android.R.anim.slide_in_left))
//                .transition(new DrawableTransitionOptions().crossFade(300))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .override(800, 800))
                .thumbnail(0.5f)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {
            for (Image image : imagesList) {
                image.setChecked(false);
            }
            imagesList.get(holder.getAdapterPosition()).setChecked(true);
            if (mOnItemClickListener != null) {
                int a = holder.getAdapterPosition();
                mOnItemClickListener.onItemClick(holder.getAdapterPosition(), imagesList.get(holder.getAdapterPosition()));
            }
        });
        if (imagesList.get(position).isChecked()) {
            holder.imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
        } else {
            holder.imageView.setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public CustomHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bottom_imageview_item);
        }
    }

    public void refresh(List<Image> newData) {
        this.imagesList = newData;
        notifyDataSetChanged();
    }
}
