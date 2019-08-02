package com.mycroft.awesomelibrary.activity.rximagepicker;

import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;
import com.mycroft.lib.util.DisposableUtil;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * RxImagePicker
 *
 * @author mycroft
 */
public class RxImagePickerActivity extends BaseCommonComponentActivity {

    @Override
    protected int getResId() {
        return R.layout.activity_rx_image_picker;
    }

    private final List<Result> resultList = new ArrayList<>();

    @Override
    protected void initViews() {
        super.initViews();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        BaseQuickAdapter<Result, BaseViewHolder> adapter = new BaseQuickAdapter<Result, BaseViewHolder>(R.layout.item_image_preview, resultList) {
            @Override
            protected void convert(BaseViewHolder helper, Result item) {
                ImageView imageView = helper.getView(R.id.imageView);
                Glide.with(imageView).load(item.getUri()).into(imageView);
            }
        };
        recyclerView.setAdapter(adapter);

        Button chooseButton = findViewById(R.id.chooseButton);

        chooseButton.setOnClickListener(v -> {
            // 做您想做的，比如将选取的图片展示在ImageView中

            WechatImagePicker imagePicker = RxImagePicker.create(WechatImagePicker.class);

            disposable = imagePicker.openGallery(this,
                    new WechatConfigrationBuilder(MimeType.INSTANCE.ofImage(), false)
                            .imageEngine(WechatGlideEngine.getInstance())
                            .maxSelectable(9)
                            .countable(true)
                            .spanCount(3)
                            .build())
                    .subscribe(adapter::addData,
                            throwable -> disposable = null,
                            () -> disposable = null);
        });
    }

    private Disposable disposable;

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        DisposableUtil.dispose(disposable);
        disposable = null;
        super.onDestroy();
    }
}
