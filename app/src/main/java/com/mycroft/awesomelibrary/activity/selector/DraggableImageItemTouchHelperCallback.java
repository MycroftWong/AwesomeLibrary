package com.mycroft.awesomelibrary.activity.selector;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mycroft.awesomelibrary.R;

/**
 * 拖动图
 *
 * @author mycroft
 */
public class DraggableImageItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchMoveListener mMoveListener;

    public DraggableImageItemTouchHelperCallback(ItemTouchMoveListener moveListener) {
        mMoveListener = moveListener;
    }

    /**
     * Callback回调监听时先调用的，用来判断当前是什么动作，比如判断方向
     * 作用：哪个方向的拖动
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 我要监听的拖拽方向是哪个方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, 0);
    }

    /**
     * 是否打开长按拖拽效果
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    //当上下移动的时候回调的方法
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder srcHolder, @NonNull RecyclerView.ViewHolder targetHolder) {
        // 在拖拽过程中不断地调用adapter.notifyItemMoved(from,to);
        if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) {
            return false;
        }
        //在拖拽的过程中不断调用adapter.notifyItemMoved(from,to);
        return mMoveListener.onItemMove(srcHolder.getAdapterPosition(), targetHolder.getAdapterPosition());
    }

    //侧滑的时候回调的方法
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder holder, int direction) {
        //监听侧滑，1.删除数据 2.调用adapter.notifyItemRemove(position);
//        mMoveListener.onItemRemove(holder.getAdapterPosition());
        // nothing 不支持滑动删除
    }

    //设置滑动item的背景
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //判断选中状态
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.common_text_color_detail));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //清除滑动item的背景
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 恢复
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        //防止出现复用问题 而导致条目不显示 方式一
        viewHolder.itemView.setAlpha(1);//1-0
        //设置滑出大小
        super.clearView(recyclerView, viewHolder);
    }

    //设置滑动时item的背景透明度
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //dX:水平方向移动的增量(负：往左；正：往右) 0-view.getWidth()
        float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //透明度动画
            viewHolder.itemView.setAlpha(alpha);//1-0
            //设置滑出大小
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
