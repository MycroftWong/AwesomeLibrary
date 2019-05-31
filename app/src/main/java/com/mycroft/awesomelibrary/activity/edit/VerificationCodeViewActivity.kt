package com.mycroft.awesomelibrary.activity.edit

import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import com.tuo.customview.VerificationCodeView
import kotlinx.android.synthetic.main.activity_verification_code_view.*
import java.util.concurrent.TimeUnit

class VerificationCodeViewActivity : BaseCommonComponentActivity() {

    override fun getResId(): Int {
        return R.layout.activity_verification_code_view
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        clearButton.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            verificationCodeView.clearInputContent()
            verificationPwdCodeView.clearInputContent()
        }

        verificationCodeView.setInputCompleteListener(object : VerificationCodeView.InputCompleteListener {
            override fun inputComplete() {
                contentText.text = verificationCodeView.inputContent
            }

            override fun deleteContent() {
                contentText.text = getString(R.string.clear)
            }
        })
    }

    override fun loadData(savedInstanceState: Bundle?) {


    }
}
