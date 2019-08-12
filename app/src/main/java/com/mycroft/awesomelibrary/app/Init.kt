package com.mycroft.awesomelibrary.app

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.mycroft.lib.net.RemoteService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit


class Init {

    companion object {

        private var initialized = false

        fun initApp(context: Context) {
            if (initialized) {
                return
            }

            val appContext = context.applicationContext

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            RemoteService.init(appContext) {
                OkHttpClient.Builder()
                        .cache(Cache(File(appContext.cacheDir, "net"), 10.toLong().shl(20)))
                        .addNetworkInterceptor(httpLoggingInterceptor)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .build()
            }
            LogUtils.getConfig().isLogSwitch = true

            initialized = true
        }
    }
}