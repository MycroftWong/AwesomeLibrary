package com.mycroft.awesomelibrary.activity.rximagepicker;

import android.content.Context;

import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;
import com.qingmei2.rximagepicker_extension.entity.SelectionSpec;
import com.qingmei2.rximagepicker_extension_wechat.ui.WechatImagePickerActivity;

import io.reactivex.Observable;

public interface WechatImagePicker {

    @Gallery(componentClazz = WechatImagePickerActivity.class,
            openAsFragment = false)
    Observable<Result> openGallery(Context context, SelectionSpec config);

    @Camera
    Observable<Result> openCamera(Context context);
}
