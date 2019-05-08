package com.mycroft.awesomelibrary.activity.splash;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.main.MainActivity;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 启动页
 *
 * @author mycroft
 */
public class SplashActivity extends BaseCommonActivity {

    private static final int SPLASH_TIME = 3000;

    @Override
    protected int getResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
        Disposable disposable = Observable.just(System.currentTimeMillis())
                .observeOn(Schedulers.io())
                .map(s -> {
                    long gap = System.currentTimeMillis() - s;
                    TimeUnit.MILLISECONDS.sleep(SPLASH_TIME - gap);
                    return gap;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(gap -> new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA))
                .subscribe(granted -> {
                    if (granted) {
                        startActivity(MainActivity.getIntent(this));
                    }
                    finish();
                }, throwable -> finish());
    }
}
