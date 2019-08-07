package com.mycroft.awesomelibrary.activity.luban;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.format.Formatter;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.ImageLoader;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;
import com.mycroft.awesomelibrary.activity.rximagepicker.WechatImagePicker;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class LubanActivity extends BaseCommonComponentActivity {

    @BindView(R.id.beforeText)
    TextView beforeText;
    @BindView(R.id.draweeView)
    SimpleDraweeView draweeView;
    @BindView(R.id.afterText)
    TextView afterText;
    @BindView(R.id.compressdDraweeView)
    SimpleDraweeView compressdDraweeView;

    @Override
    protected int getResId() {
        return R.layout.activity_luban;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.chooseButton)
    public void onViewClicked() {
        WechatImagePicker wechatImagePicker = RxImagePicker.create(WechatImagePicker.class);
        Disposable disposable = wechatImagePicker.openGallery(this, new WechatConfigrationBuilder(MimeType.INSTANCE.ofImage(), false)
                .countable(true)
                .spanCount(3)
                .maxSelectable(1)
                .build())
                .subscribe(result -> compress(result.getUri()),
                        LogUtils::e,
                        () -> LogUtils.e("finished"));
    }

    @SuppressLint("CheckResult")
    private void compress(Uri uri) {
        draweeView.setImageURI(uri);

        showFileSize(beforeText, uri);

        Flowable.defer(() -> Flowable.just(Luban.with(this)
                .ignoreBy(-1)
                .load(uri)
                .get()))
                .subscribeOn(Schedulers.io())
                .map(files -> files.get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                            ImageLoader.loadFile(compressdDraweeView, file.getAbsolutePath());
                            showFileSize(afterText, Uri.fromFile(file));
                        },
                        throwable -> {
                            ToastUtils.showShort(throwable.getMessage());
                            LogUtils.e(throwable);
                        }, () -> LogUtils.e("finished"));
    }

    @SuppressLint("CheckResult")
    private void showFileSize(TextView textView, Uri uri) {
        Flowable.just(uri)
                .subscribeOn(Schedulers.io())
                .flatMap(u -> {

                    try {
                        ParcelFileDescriptor fileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                        if (fileDescriptor != null) {
                            long size = fileDescriptor.getStatSize();
                            return Flowable.just(size);
                        } else {
                            return Flowable.just(0L);
                        }
                    } catch (FileNotFoundException e) {
                        return Flowable.error(e);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> textView.append(Formatter.formatFileSize(this, aLong)),
                        LogUtils::e,
                        () -> LogUtils.e("finished"));
    }

}
