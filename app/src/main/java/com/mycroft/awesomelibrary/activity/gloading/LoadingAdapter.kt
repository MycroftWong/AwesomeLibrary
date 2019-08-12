package com.mycroft.awesomelibrary.activity.gloading

import android.view.View
import com.mycroft.lib.view.LoadingAdapter
import com.mycroft.lib.view.LoadingHolder

class LoadingAdapter : LoadingAdapter {
    override fun getView(holder: LoadingHolder?, convertView: View?, status: Int): View {
        var statusView: StatusView? = null
        if (convertView != null && convertView is StatusView) {
            statusView = convertView
        }
        if (statusView == null) {
            statusView = StatusView(holder!!.context, holder.retryTask)
        }
        statusView.setStatus(status)

        return statusView
    }
}