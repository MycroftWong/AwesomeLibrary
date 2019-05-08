package com.mycroft.awesomelibrary.activity.city;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityPickerActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chooseCityButton)
    Button chooseCityButton;
    @BindView(R.id.contentText)
    TextView contentText;

    @Override
    protected int getResId() {
        return R.layout.activity_city_picker;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {

    }

    private final CityPickerView mCityPickerView = new CityPickerView();

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseCityButton.setOnClickListener(v -> chooseCity());

        mCityPickerView.init(this);
        CityConfig config = new CityConfig.Builder()
                .title(getString(R.string.title_choose_city))
                .titleTextSize(16)
                .titleTextColor("#000000")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#000000")
                .confirmText(getString(R.string.ok))
                .confirmTextSize(16)
                .cancelTextColor("#000000")
                .cancelText(getString(R.string.cancel))
                .cancelTextSize(15)
                //显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)
                .showBackground(true)
                .visibleItemsCount(3)
                .province("四川省")
                .city("成都市")
                .district("锦江区")
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
//                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
//                .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                .drawShadows(false)
                //是否显示港澳台数据，默认不显示
                .setShowGAT(false)
                .setLineHeigh(3)
                .setLineColor("#F7F7F7")
                .build();
        mCityPickerView.setConfig(config);
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseCity() {
        mCityPickerView.setOnCityItemClickListener(mCityItemClickListener);
        mCityPickerView.showCityPicker();
    }

    private final OnCityItemClickListener mCityItemClickListener = new OnCityItemClickListener() {
        @Override
        public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
            contentText.setText(String.format(Locale.CHINA, "%s %s %s", province.getName(), city.getName(), district.getName()));
        }

        @Override
        public void onCancel() {
            // ignore
        }
    };
}
