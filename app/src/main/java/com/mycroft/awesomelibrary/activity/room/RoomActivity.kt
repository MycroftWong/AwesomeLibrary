package com.mycroft.awesomelibrary.activity.room

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huma.room_for_asset.RoomAsset
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import com.mycroft.awesomelibrary.activity.room.dao.AddressDao
import com.mycroft.awesomelibrary.activity.room.dao.AddressDatabase
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomActivity : BaseCommonComponentActivity() {
    override fun getResId(): Int {
        return R.layout.activity_room
    }

    private var data = mutableListOf<String>()

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        recyclerView.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1, data) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper?.setText(android.R.id.text1, item)
            }
        }

        val database = RoomAsset.databaseBuilder(this, AddressDatabase::class.java, DB_FILE)
                .build()

        val dao = database.addressDao()

        queryProvince.setOnClickListener {
            queryProvince(dao)
        }
    }

    private fun queryProvince(dao: AddressDao) {
        GlobalScope.launch {
            val provinces = dao.loadAllProvinces()
            for (item in provinces) {
                data.add("id=${item.id}, name=${item.name}")
            }

            runOnUiThread { recyclerView.adapter?.notifyDataSetChanged() }
        }
    }

    override fun loadData(savedInstanceState: Bundle?) {

    }


    companion object {
        val DB_FILE = "area.db"
    }
}
