package com.mycroft.awesomelibrary.activity.picker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerViewActivity extends BaseCommonComponentActivity {

    @BindView(R.id.chooseDateButton)
    Button chooseDateButton;
    @BindView(R.id.chooseTimeButton)
    Button chooseTimeButton;
    @BindView(R.id.chooseCityButton)
    Button chooseCityButton;

    @Override
    protected int getResId() {
        return R.layout.activity_picker_view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.chooseDateButton, R.id.chooseTimeButton, R.id.chooseCityButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chooseDateButton:
                chooseDate();
                break;
            case R.id.chooseTimeButton:
                chooseTime();
                break;
            case R.id.chooseCityButton:
                chooseCity();
                break;
            default:
                break;
        }
    }

    private void chooseDate() {
        Calendar preCalendar = Calendar.getInstance();
        preCalendar.set(1990, 0, 1);

        Calendar start = Calendar.getInstance();
        start.set(1900, 0, 1);
        Calendar end = Calendar.getInstance();

        TimePickerView pickerView =
                new TimePickerBuilder(this, (date, v) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    chooseDateButton.setText(String.format(Locale.CHINA, "%d年%d月%d日",
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH)));
                })
                        .setTitleText(getString(R.string.choose_date))
                        .setTitleColor(Color.BLACK)
                        .setCancelColor(Color.BLACK)
                        .setSubmitColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK)
                        .setTextColorOut(ContextCompat.getColor(this, R.color.common_text_color))
                        .setRangDate(start, end)
                        .setDate(preCalendar)
                        .isDialog(false)
                        .build();

        pickerView.show(true);
    }

    private void chooseTime() {
        Calendar preCalendar = Calendar.getInstance();
        preCalendar.set(Calendar.HOUR, 0);
        preCalendar.set(Calendar.MINUTE, 0);

        TimePickerView pickerView =
                new TimePickerBuilder(this, (date, v) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    chooseTimeButton.setText(String.format(Locale.CHINA, "%d时%d分",
                            calendar.get(Calendar.HOUR),
                            calendar.get(Calendar.MINUTE)));
                })
                        .setType(new boolean[]{false, false, false, true, true, false})
                        .setDate(preCalendar)
                        .isDialog(false)
                        .build();

        pickerView.show(true);
    }

    private void chooseCity() {
        ToastUtils.showShort(R.string.not_support_now);
    }
}
