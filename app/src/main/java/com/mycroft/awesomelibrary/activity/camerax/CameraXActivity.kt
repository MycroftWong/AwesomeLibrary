package com.mycroft.awesomelibrary.activity.camerax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraX
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity

class CameraXActivity : BaseCommonComponentActivity() {

    override fun getResId(): Int {
        return R.layout.activity_camera_x
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)



//        CameraX.bindToLifecycle(this, )
    }

    override fun loadData(savedInstanceState: Bundle?) {
    }
}
