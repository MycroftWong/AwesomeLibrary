package com.mycroft.awesomelibrary.activity.pinyin

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.blankj.utilcode.util.LogUtils
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import kotlinx.android.synthetic.main.activity_pinyin.*
import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType
import java.lang.StringBuilder
import java.util.*

class PinyinActivity : BaseCommonComponentActivity() {


    override fun getResId(): Int {
        return R.layout.activity_pinyin
    }

    override fun initViews() {
        super.initViews()

        editText.setOnEditorActionListener { v, actionId, event ->
            LogUtils.e(v, actionId, event)

            if (EditorInfo.IME_ACTION_DONE == actionId) {
                contentText.text = transferSimpleChinese(editText.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    override fun loadData() {

    }

    private fun transferSimpleChinese(text: String): String {
        val charArray = text.toCharArray()
        val format = HanyuPinyinOutputFormat()
        format.caseType = HanyuPinyinCaseType.UPPERCASE
        format.toneType = HanyuPinyinToneType.WITHOUT_TONE
        format.vCharType = HanyuPinyinVCharType.WITH_V

        var pinyin: Array<String?>

        val allString = StringBuilder()

        for (item in charArray) {
            if (item.toString().matches(Regex("[\\u4E00-\\u9FA5]+"))) {
                try {
                    pinyin = PinyinHelper.toHanyuPinyinStringArray(item, format)
                    LogUtils.e(Arrays.asList(pinyin).toString())
                    allString.append(pinyin[0]).append(" ")
                } catch (e: Exception) {
                    e.printStackTrace()
                    LogUtils.e(e)
                }

            } else {
                allString.append(Character.toString(item))
            }
        }

        return allString.toString()
    }
}
