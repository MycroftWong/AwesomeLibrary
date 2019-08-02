package com.mycroft.awesomelibrary.activity.immersion

import android.graphics.Color
import android.os.Bundle
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gyf.immersionbar.ktx.immersionBar
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import com.mycroft.awesomelibrary.constants.Constants
import kotlinx.android.synthetic.main.activity_immersion_bar.*

class ImmersionBarActivity : BaseCommonComponentActivity() {

    override fun getResId(): Int {
        return R.layout.activity_immersion_bar
    }

    private val data = mutableListOf<String>()

    override fun initFields(savedInstanceState: Bundle?) {
        super.initFields(savedInstanceState)
        for (index in 0..99) {
            data.add("item: $index")
        }
    }

    override fun initViews() {
        super.initViews()

        immersionBar {
            titleBar(toolbar)
        }

        Glide.with(this).asBitmap().load(Constants.CAPTAIN_AMERICA).into(imageView)
    }

    override fun loadData() {

    }


}
