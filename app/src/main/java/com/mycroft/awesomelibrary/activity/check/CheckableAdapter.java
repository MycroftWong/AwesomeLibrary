package com.mycroft.awesomelibrary.activity.check;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mycroft.awesomelibrary.R;
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;

import java.util.List;

/**
 * @author mycroft
 */
public class CheckableAdapter extends BaseQuickAdapter<CheckableData, BaseViewHolder> {

    private final CheckHelper mCheckHelper;

    public CheckableAdapter(@Nullable List<CheckableData> data, @NonNull CheckHelper checkHelper) {
        super(R.layout.item_checkable, data);
        mCheckHelper = checkHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckableData item) {
        mCheckHelper.bind(item, helper, R.id.checkbox);

        helper.setText(R.id.checkbox, item.getData())
                .setChecked(R.id.checkbox, item.isSelected());
    }
}