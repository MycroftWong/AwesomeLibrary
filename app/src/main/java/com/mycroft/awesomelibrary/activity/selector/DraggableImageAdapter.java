package com.mycroft.awesomelibrary.activity.selector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mycroft.awesomelibrary.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 多图展示，可以拖动排序
 *
 * @author mycroft
 */
public class DraggableImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchMoveListener {

    public static final int TYPE_LAST_ONE = 0X1110;
    public static final int TYPE_DATA = 0X1111;

    private final List<String> mData;
    private final OnItemActionListener mOnItemActionListener;

    public DraggableImageAdapter(List<String> data, OnItemActionListener listener) {
        mData = data;
        mOnItemActionListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        if (itemType == TYPE_LAST_ONE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fashioner_post_plus, viewGroup, false);
            return new PostPlusViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fashioner_post_image, viewGroup, false);
            return new PostViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_LAST_ONE) {
            PostPlusViewHolder vh = (PostPlusViewHolder) viewHolder;

            Glide.with(vh.imageView).load(R.mipmap.icon_plus).into(vh.imageView);

            vh.itemView.setOnClickListener(new OnClickListener(vh, mOnItemActionListener, mData));
            vh.itemView.setOnLongClickListener(null);

        } else {
            PostViewHolder vh = (PostViewHolder) viewHolder;
            Glide.with(vh.imageView).load(mData.get(position)).into(vh.imageView);

            vh.closeView.setVisibility(View.VISIBLE);

            vh.itemView.setOnClickListener(new OnClickListener(vh, mOnItemActionListener, mData));
            vh.itemView.setOnLongClickListener(new OnItemLongClickListener(mOnItemActionListener, vh));

            vh.closeView.setOnClickListener(new OnCloseClickListener(vh, mOnItemActionListener));
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() >= 9) {
            return 9;
        } else {
            return mData.size() + 1;
        }
    }

    @Override
    public boolean onItemMove(int from, int to) {
        //1.数据移动 2.刷新
        if (inRange(from) && inRange(to)) {
            mData.add(to, mData.remove(from));
            notifyItemMoved(from, to);
        }
        return true;
    }

    private boolean inRange(int position) {
        return position >= 0 && position < mData.size();
    }

    @Override
    public boolean onItemRemove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        return true;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size() && mData.size() < 9) {
            return TYPE_LAST_ONE;
        }
        return TYPE_DATA;
    }

    static class OnClickListener implements View.OnClickListener {

        private final RecyclerView.ViewHolder mViewHolder;
        private final OnItemActionListener mItemClickListener;
        private final List<String> mData;

        public OnClickListener(RecyclerView.ViewHolder viewHolder, OnItemActionListener itemClickListener, List<String> data) {
            mViewHolder = viewHolder;
            mItemClickListener = itemClickListener;
            mData = data;
        }

        @Override
        public void onClick(View v) {
            if (mData.size() != 9 && mData.size() == mViewHolder.getAdapterPosition()) {
                mItemClickListener.onPlusClicked();
            } else {
                mItemClickListener.onItemClicked(mViewHolder.getAdapterPosition());
            }
        }
    }

    static class OnCloseClickListener implements View.OnClickListener {

        private final PostViewHolder mViewHolder;
        private final OnItemActionListener mItemClickListener;

        public OnCloseClickListener(PostViewHolder viewHolder, OnItemActionListener itemClickListener) {
            mViewHolder = viewHolder;
            mItemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemCloseClicked(mViewHolder.getAdapterPosition());
        }
    }

    static class OnItemLongClickListener implements View.OnLongClickListener {

        private final PostViewHolder mViewHolder;
        private final OnItemActionListener mItemClickListener;

        OnItemLongClickListener(OnItemActionListener onItemActionListener, PostViewHolder viewHolder) {
            mItemClickListener = onItemActionListener;
            mViewHolder = viewHolder;
        }

        @Override
        public boolean onLongClick(View v) {
            mItemClickListener.onItemLongClicked(mViewHolder);
            return true;
        }
    }

    /**
     * 图片的 view holder
     */
    public static class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.closeView)
        View closeView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 加号的 view holder
     */
    static class PostPlusViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;

        public PostPlusViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
