package com.mycroft.awesomelibrary.activity.rximagepicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.qingmei2.rximagepicker_extension.engine.ImageEngine;

import org.jetbrains.annotations.NotNull;

public final class WechatGlideEngine implements ImageEngine {

    public static WechatGlideEngine getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final WechatGlideEngine INSTANCE = new WechatGlideEngine();
    }

    private WechatGlideEngine() {
    }

    @Override
    public void loadGifImage(@NotNull Context context, int i, int i1, @NotNull ImageView imageView, @NotNull Uri uri) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(@NotNull Context context, int i, @NotNull Drawable drawable, @NotNull ImageView imageView, @NotNull Uri uri) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(RequestOptions.centerCropTransform().placeholder(drawable))
                .into(imageView);

    }

    @Override
    public void loadImage(@NotNull Context context, int i, int i1, @NotNull ImageView imageView, @NotNull Uri uri) {
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.priorityOf(Priority.HIGH))
                .into(imageView);
    }

    @Override
    public void loadThumbnail(@NotNull Context context, int i, @NotNull Drawable drawable, @NotNull ImageView imageView, @NotNull Uri uri) {
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.centerCropTransform().placeholder(drawable))
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}
