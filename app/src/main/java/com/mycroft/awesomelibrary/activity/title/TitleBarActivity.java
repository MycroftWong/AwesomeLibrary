package com.mycroft.awesomelibrary.activity.title;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;
import com.stx.xhb.commontitlebar.CustomTitleBar;
import com.stx.xhb.commontitlebar.widget.UIAlphaImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangqiang
 */
public class TitleBarActivity extends BaseCommonComponentActivity {

    @BindView(R.id.titleBar)
    CustomTitleBar titleBar;

    @Override
    protected int getResId() {
        return R.layout.activity_title_bar;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        super.initFields(savedInstanceState);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ButterKnife.bind(this);

        titleBar.setTitle(getTitle().toString());
        UIAlphaImageButton backButton = titleBar.addLeftBackImageButton();
        backButton.setOnClickListener(v -> finish());

        titleBar.addRightImageButton(R.mipmap.icon_shopping_cart, View.generateViewId())
                .setOnClickListener(v -> ToastUtils.showShort("shopping"));

        UIAlphaImageButton searchButton = titleBar.addRightImageButton(R.mipmap.icon_search, View.generateViewId());
        searchButton.setOnClickListener(v -> ToastUtils.showShort("search"));
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

}
