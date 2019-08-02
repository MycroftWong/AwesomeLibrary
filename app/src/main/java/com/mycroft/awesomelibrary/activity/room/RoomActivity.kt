package com.mycroft.awesomelibrary.activity.room

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import chihane.jdaddressselector.AddressProvider
import chihane.jdaddressselector.OnAddressSelectedListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jakewharton.rxbinding3.view.clicks
import com.mycroft.awesomelibrary.R
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity
import com.mycroft.awesomelibrary.activity.room.dao.AddressDao
import com.mycroft.awesomelibrary.activity.room.entity.County
import com.mycroft.awesomelibrary.activity.room.util.AddressDatabaseCopier
import com.mycroft.awesomelibrary.view.BottomDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class RoomActivity : BaseCommonComponentActivity() {

    override fun getResId(): Int {
        return R.layout.activity_room
    }

    /**
     * 显示查询到的数据列表
     */
    private var data = mutableListOf<String>()


    val dao: AddressDao by lazy {
        val database = AddressDatabaseCopier.getInstance(this).roomDatabase
        val dao = database.addressDao()

        return@lazy dao
    }


    override fun initViews() {
        super.initViews()

        queryButton.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe { showQueryDialog() }

        addressButton.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe { showAddressDialog() }

        recyclerView.hasFixedSize()
        recyclerView.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1, data) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper?.setText(android.R.id.text1, item)
            }
        }
    }

    override fun loadData() {

    }

    /**
     * 显示查询的类型dialog
     **/
    private fun showQueryDialog() {
        AlertDialog.Builder(this)
                .setAdapter(
                        ArrayAdapter<String>(
                                this,
                                android.R.layout.simple_list_item_1,
                                android.R.id.text1,
                                arrayOf("Province", "City", "County", "Street")
                        )
                ) { dialog, which ->
                    when (which) {
                        0 -> queryProvince()
                        1 -> queryCity()
                        2 -> queryCounty()
                        3 -> queryStreet()
                    }
                }
                .show()
    }

    /**
     * 查询省份，使用 coroutines
     **/
    private fun queryProvince() {
        GlobalScope.launch {
            val provinces = dao.loadAllProvinces()
            data.clear()
            for (item in provinces) {
                data.add("id=${item.id}, name=${item.name}")
            }

            withContext(Dispatchers.Main) { recyclerView.adapter?.notifyDataSetChanged() }
        }
    }

    /**
     * 查询城市，使用RxJava
     **/
    private fun queryCity() {
        val disposable = dao.loadAllCities()
                .subscribeOn(Schedulers.io())
                .map {
                    data.clear()
                    for (item in it) {
                        data.add("id=${item.id}, name=${item.name}")
                    }
                    return@map data
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { recyclerView.adapter?.notifyDataSetChanged() }
    }

    /**
     * 查询区域，使用LiveData, 不确定这种用法是否正确
     **/
    private fun queryCounty() {
        dao.loadAllCounties().observe(this,
                Observer<List<County>> {
                    data.clear()
                    for (item in it) {
                        data.add("id=${item.id}, name=${item.name}")
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                })
    }

    private fun queryStreet() {
        GlobalScope.launch {
            val streets = dao.loadAllStreets()
            data.clear()
            for (item in streets) {
                data.add("id=${item.id}, name=${item.name}")
            }
            withContext(Dispatchers.Main) { recyclerView.adapter?.notifyDataSetChanged() }
        }
    }


    /**
     * 显示地址选择dialog
     **/
    @SuppressLint("SetTextI18n")
    private fun showAddressDialog() {
        GlobalScope.launch {
            val provider = object : AddressProvider {
                override fun provideProvinces(
                        addressReceiver: AddressProvider.AddressReceiver<chihane.jdaddressselector.model.Province>?
                ) {

                    GlobalScope.launch {
                        val provinceList = dao.loadAllProvinces()

                        val provinces = mutableListOf<chihane.jdaddressselector.model.Province>()
                        for (item in provinceList) {
                            val p = chihane.jdaddressselector.model.Province()
                            p.id = item.id
                            p.name = item.name
                            provinces.add(p)
                        }

                        withContext(Dispatchers.Main) { addressReceiver?.send(provinces) }
                    }
                }

                override fun provideCountiesWith(
                        cityId: Int,
                        addressReceiver: AddressProvider.AddressReceiver<chihane.jdaddressselector.model.County>?
                ) {
                    GlobalScope.launch {
                        val countyList = dao.loadCountiesByCityId(cityId)
                        val counties = mutableListOf<chihane.jdaddressselector.model.County>()
                        for (item in countyList) {
                            val c = chihane.jdaddressselector.model.County()
                            c.id = item.id
                            c.name = item.name
                            c.city_id = item.cityId
                            counties.add(c)
                        }

                        withContext(Dispatchers.Main) { addressReceiver?.send(counties) }
                    }
                }

                override fun provideCitiesWith(
                        provinceId: Int,
                        addressReceiver: AddressProvider.AddressReceiver<chihane.jdaddressselector.model.City>?
                ) {
                    GlobalScope.launch {
                        val cityList = dao.loadCitiesByProvinceId(provinceId)
                        val cities = mutableListOf<chihane.jdaddressselector.model.City>()
                        for (item in cityList) {
                            val c = chihane.jdaddressselector.model.City()
                            c.id = item.id
                            c.name = item.name
                            c.province_id = item.provinceId
                            cities.add(c)
                        }

                        withContext(Dispatchers.Main) { addressReceiver?.send(cities) }
                    }
                }

                override fun provideStreetsWith(
                        countyId: Int,
                        addressReceiver: AddressProvider.AddressReceiver<chihane.jdaddressselector.model.Street>?
                ) {
                    GlobalScope.launch {
                        val streetList = dao.loadStreetsByCountyId(countyId)
                        val streets = mutableListOf<chihane.jdaddressselector.model.Street>()
                        for (item in streetList) {
                            val s = chihane.jdaddressselector.model.Street()
                            s.id = item.id
                            s.name = item.name
                            s.county_id = item.countyId
                            streets.add(s)
                        }

                        withContext(Dispatchers.Main) { addressReceiver?.send(streets) }
                    }
                }

            }

            val listener = OnAddressSelectedListener { province, city, county, street ->
                addressButton.text = "${province.name}, ${city.name}, ${county?.name}, ${street?.name}"
            }

            withContext(Dispatchers.Main) { BottomDialog.show(this@RoomActivity, listener, provider) }
        }
    }
}
