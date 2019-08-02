package com.mycroft.awesomelibrary.activity.matisse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jakewharton.rxbinding3.view.clicks
import com.lxj.matisse.Matisse
import com.lxj.matisse.MimeType
import com.lxj.matisse.engine.impl.GlideEngine
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import kotlinx.android.synthetic.main.activity_matisse.*
import java.util.concurrent.TimeUnit

class MatisseActivity : BaseCommonComponentActivity() {

    companion object {
        private const val REQUEST_CHOOSE = 0x110;
    }

    override fun getResId(): Int {
        return R.layout.activity_matisse
    }

    private val imageList = mutableListOf<Uri>()

    override fun initViews() {
        super.initViews()

        recyclerView.adapter = object : BaseQuickAdapter<Uri, BaseViewHolder>(R.layout.item_image_preview, imageList) {
            override fun convert(helper: BaseViewHolder?, item: Uri?) {
                val imageView = helper?.getView<ImageView>(R.id.imageView)
//                imageView?.setImageURI(item, null)
            }
        }

        chooseButton.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe { choosePicture() }
    }

    override fun loadData() {

    }

    private fun choosePicture() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .capture(true)
                .isCrop(true)
                .imageEngine(GlideEngine())
                .forResult(REQUEST_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHOOSE && resultCode == RESULT_OK) {

            val uris = Matisse.obtainSelectUriResult(data)

            imageList.addAll(uris)
            recyclerView.adapter?.notifyDataSetChanged()
        }

    }
}
