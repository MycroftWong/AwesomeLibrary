package com.mycroft.awesomelibrary.activity.gloading

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.jakewharton.rxbinding3.view.clicks
import com.mycroft.awesomelibrary.R
import com.mycroft.lib.view.Loading
import kotlinx.android.synthetic.main.layout_status_view.view.*
import java.util.concurrent.TimeUnit

@SuppressLint("ViewConstructor")
class StatusView(context: Context, private val retryTask: Runnable) : FrameLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_status_view, this, true)
        setBackgroundColor(Color.WHITE)
        val d = retryView.clicks().throttleFirst(1, TimeUnit.SECONDS)
                .subscribe {
                    loadingView.visibility = View.VISIBLE
                    failedView.visibility = View.GONE
                    emptyView.visibility = View.GONE
                    retryTask.run()
                }
    }

    fun setStatus(status: Int) {
        when (status) {
            Loading.STATUS_LOAD_SUCCESS -> {
                visibility = View.GONE
            }

            Loading.STATUS_LOADING -> {
                loadingView.visibility = View.VISIBLE
                failedView.visibility = View.GONE
                emptyView.visibility = View.GONE
            }

            Loading.STATUS_LOAD_FAILED -> {
                loadingView.visibility = View.GONE
                failedView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            }

            Loading.STATUS_EMPTY_DATA -> {
                loadingView.visibility = View.GONE
                failedView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
        }
    }
}