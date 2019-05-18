package com.mycroft.awesomelibrary.activity.check;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author mycroft
 */
public class Stream {
    private Object d;
    private RecyclerView.ViewHolder v;
    private boolean isToCheck;

    public Stream(Object d, RecyclerView.ViewHolder v, boolean isToCheck) {
        this.d = d;
        this.v = v;
        this.isToCheck = isToCheck;
    }

    public Object getD() {
        return d;
    }

    public RecyclerView.ViewHolder getV() {
        return v;
    }

    public boolean isToCheck() {
        return isToCheck;
    }

    public void setChecked(boolean isToCheck) {
        this.isToCheck = isToCheck;
    }
}
