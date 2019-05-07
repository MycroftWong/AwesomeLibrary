package com.mycroft.awesomelibrary.fragment;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.model.ComponentModel;

import java.util.List;

/**
 * 组件adapter
 *
 * @author mycroft
 */
public class ComponentAdapter extends BaseQuickAdapter<ComponentModel, BaseViewHolder> {
    public ComponentAdapter(@Nullable List<ComponentModel> data) {
        super(R.layout.item_component, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComponentModel item) {
        helper.setText(R.id.nameText, item.getName())
                .setText(R.id.descriptionText, item.getDescription());
    }
}
