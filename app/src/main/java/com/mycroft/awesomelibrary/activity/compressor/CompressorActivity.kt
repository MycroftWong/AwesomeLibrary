package com.mycroft.awesomelibrary.activity.compressor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.Formatter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding3.view.clicks
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import com.winfo.photoselector.PhotoSelector
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_compressor.*
import me.shouheng.compress.Compress
import me.shouheng.compress.strategy.Strategies
import java.io.File
import java.util.concurrent.TimeUnit

class CompressorActivity : BaseCommonComponentActivity() {

    companion object {
        private const val REQUEST_CHOOSE = 0x1110
    }

    override fun getResId(): Int {
        return R.layout.activity_compressor
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        chooseButton.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe { chooseSinglePicture() }
    }

    override fun loadData(savedInstanceState: Bundle?) {

    }

    private fun chooseSinglePicture() {
        PhotoSelector.builder()
                .setShowCamera(true)
                .setSingle(true)
                .setGridColumnCount(3)
                .setMaterialDesign(true)
                .setToolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setBottomBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .start(this, REQUEST_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHOOSE && resultCode == Activity.RESULT_OK) {

            val selectedImages = data!!.getStringArrayListExtra(PhotoSelector.SELECT_RESULT)
            val imageFile = File(selectedImages[0])

            showFileSize(beforeText, imageFile)

            draweeView.setImageURI(Uri.fromFile(imageFile), null)

            compressImage(imageFile)
        }
    }

    private fun compressImage(imageFile: File) {
        val d = Compress.with(this, imageFile).setQuality(90)
                .strategy(Strategies.luban())
                .asFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    compressdDraweeView.setImageURI(Uri.fromFile(it), null)
                    showFileSize(afterText, it)
                }, {
                    ToastUtils.showShort(R.string.toast_compress_error)
                    LogUtils.e(it)
                })
    }

    private fun showFileSize(textView: TextView, file: File) {
        val d = Flowable.just(file)
                .subscribeOn(Schedulers.io())
                .map { file.length() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { textView.append("：${Formatter.formatFileSize(this, it)}") }
    }
}
