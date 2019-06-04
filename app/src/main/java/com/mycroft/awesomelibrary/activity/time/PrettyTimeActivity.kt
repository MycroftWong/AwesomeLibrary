package com.mycroft.awesomelibrary.activity.time

import android.os.Bundle
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.jakewharton.rxbinding3.view.clicks
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import kotlinx.android.synthetic.main.activity_pretty_time.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import java.util.concurrent.TimeUnit

class PrettyTimeActivity : BaseCommonComponentActivity() {

    override fun getResId(): Int {
        return R.layout.activity_pretty_time
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        chooseTimeButton.clicks().throttleFirst(1, TimeUnit.SECONDS)
                .subscribe { chooseTime() }
    }

    override fun loadData(savedInstanceState: Bundle?) {

    }


    private fun chooseTime() {
        val preCalendar = Calendar.getInstance()
        preCalendar.set(Calendar.HOUR, 0)
        preCalendar.set(Calendar.MINUTE, 0)

        val pickerView = TimePickerBuilder(this) { date, v ->
            val calendar = Calendar.getInstance()
            calendar.time = date

            val prettyTime = PrettyTime(Locale.ENGLISH)
            contentText.text = prettyTime.format(calendar)
        }
                .setType(booleanArrayOf(true, true, true, true, true, false))
                .setDate(preCalendar)
                .isDialog(false)
                .build()

        pickerView.show(true)
    }
}
