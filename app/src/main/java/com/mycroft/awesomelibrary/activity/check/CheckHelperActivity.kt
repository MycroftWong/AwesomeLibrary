package com.mycroft.awesomelibrary.activity.check

import android.os.Bundle
import com.chad.library.adapter.base.BaseViewHolder
import com.mycroft.awesomelibrary.R
import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.SingleCheckHelper;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import kotlinx.android.synthetic.main.fragment_components.*

/**
 * 使用效果并不好，单选不能设置选中后不能取消，多选不能设置数量
 *
 * */
class CheckHelperActivity : BaseCommonComponentActivity() {
    override fun getResId(): Int {
        return R.layout.activity_check_helper
    }

    private val checkableDataList = mutableListOf<CheckableData>()

    override fun initFields(savedInstanceState: Bundle?) {
        super.initFields(savedInstanceState)

        for (index in 1..100) {
            checkableDataList.add(CheckableData("item: $index"))
        }
    }

    private val checkHelper = SingleCheckHelper()

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val adapter = CheckableAdapter(checkableDataList, checkHelper)

        checkHelper.isCanCancel = false
        recyclerView.adapter = adapter

        val checker = object : CheckHelper.Checker<CheckableData, BaseViewHolder> {
            override fun check(checkableData: CheckableData, helper: BaseViewHolder) {
                checkableData.isSelected = true
                helper.setChecked(R.id.checkbox, true)
            }

            override fun unCheck(checkableData: CheckableData, helper: BaseViewHolder) {
                checkableData.isSelected = false
                helper.setChecked(R.id.checkbox, false)
            }
        }

        checkHelper.register(CheckableData::class.java, checker)
    }

    override fun loadData(savedInstanceState: Bundle?) {
    }
}