package com.mycroft.awesomelibrary.activity.selector;

public interface OnItemActionListener {

    void onItemClicked(int pos);

    void onItemLongClicked(DraggableImageAdapter.PostViewHolder viewHolder);

    void onItemCloseClicked(int pos);

    void onPlusClicked();
}
