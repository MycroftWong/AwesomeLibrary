package com.mycroft.awesomelibrary.activity.check;

public class CheckableData {

    private boolean mSelected = false;

    private final String mData;

    public CheckableData(String data) {
        mData = data;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public String getData() {
        return mData;
    }
}
