package com.mycroft.awesomelibrary.activity.crop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ImageUtils
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import com.winfo.photoselector.PhotoSelector
import kotlinx.android.synthetic.main.activity_smart_cropper.*
import java.io.File
import java.util.concurrent.TimeUnit

class SmartCropperActivity : BaseCommonComponentActivity() {

    override fun getResId(): Int {
        return R.layout.activity_smart_cropper
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        var d = chooseButton.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            PhotoSelector.builder()
                    .setShowCamera(true)
                    .setSingle(true)
                    .setGridColumnCount(3)
                    .setMaterialDesign(true)
                    .setToolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setBottomBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                    .start(this)
        }

        d = cropButton.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            Glide.with(this)
                    .load(cropImageView.crop())
                    .into(imageView)
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PhotoSelector.DEFAULT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImages = data!!.getStringArrayListExtra(PhotoSelector.SELECT_RESULT)
            val file = File(selectedImages[0])
            val bitmap = ImageUtils.getBitmap(file, 800, 800)
            cropImageView.setImageToCrop(bitmap)
        }
    }
}
