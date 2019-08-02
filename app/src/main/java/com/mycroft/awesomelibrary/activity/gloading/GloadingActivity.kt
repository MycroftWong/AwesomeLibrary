package com.mycroft.awesomelibrary.activity.gloading

import android.os.Bundle
import com.billy.android.loading.Gloading
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import kotlinx.android.synthetic.main.activity_gloading.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class GloadingActivity : BaseCommonComponentActivity() {

    override fun getResId(): Int {
        return R.layout.activity_gloading
    }

    private val data: List<String> by lazy {
        val list = mutableListOf<String>()
        for (index in 0..99) {
            list.add("item: $index")
        }
        return@lazy list
    }

    private val holder: Gloading.Holder by lazy {
        Gloading.from(LoadingAdapter()).wrap(recyclerView).withRetry {
            GlobalScope.launch {
                TimeUnit.SECONDS.sleep(2)
                withContext(Dispatchers.Main) { holder.showLoadSuccess() }
            }
        }
    }

    override fun initViews() {
        super.initViews()
        recyclerView.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1, data) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper?.setText(android.R.id.text1, item)
            }
        }
    }

    override fun loadData() {
        holder.showLoading()

        GlobalScope.launch {
            TimeUnit.SECONDS.sleep(3)
            withContext(Dispatchers.Main) {
                holder.showLoadFailed()
            }
        }
    }
}
